package com.telstra.health.shot.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.CollectingAuthenticationErrorCallback;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.telstra.health.shot.api.exception.ApiException;
import com.telstra.health.shot.common.enums.OrderViewType;
import com.telstra.health.shot.common.enums.UserRole;
import com.telstra.health.shot.dao.CustomerDAO;
import com.telstra.health.shot.dao.UserAccountDAO;
import com.telstra.health.shot.dao.UserDAO;
import com.telstra.health.shot.dao.exception.DataAccessException;
import com.telstra.health.shot.dto.UserAuthoritiesDTO;
import com.telstra.health.shot.dto.UserAuthoritiesDTO.UserSite;
import com.telstra.health.shot.dto.UserDTO;
import com.telstra.health.shot.dto.UserRoleDTO;
import com.telstra.health.shot.dto.UserSearchDTO;
import com.telstra.health.shot.entity.Customer;
import com.telstra.health.shot.entity.Roles;
import com.telstra.health.shot.entity.UserRoles;
import com.telstra.health.shot.entity.Users;
import com.telstra.health.shot.service.exception.ShotServiceException;
import com.telstra.health.shot.util.ShotUtils;

@Service
public class UserServiceImpl implements UserService, AuthenticationProvider {

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private LdapContextSource contextSource;

	@Autowired
	UserDAO userDAO;
	
	@Autowired
	CustomerDAO customerDAO;

	@Autowired
	UserAccountDAO userAccountDAO;

	@Autowired
	EntityManager entityManager;

    @Autowired
    private Environment env;

	@Transactional
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		try {

			String userEmail = authentication.getName();
			String password = authentication.getCredentials().toString();

			contextSource.setUserDn(authentication.getName());
			contextSource.setUserDn(String.valueOf(authentication.getCredentials()));

			// Authenticate the user, if success then search for the user
			CollectingAuthenticationErrorCallback errorCallback = new CollectingAuthenticationErrorCallback();

			boolean bauthenication = userDAO.authenticateUser(userEmail, password, errorCallback);

			// Successful authentication
			if (bauthenication == true) {
				List<UserDTO> allPerson = userDAO.findUser(userEmail);
				if (allPerson.size() > 0) {
					Users user = this.getUserByEmail(userEmail);
					if (user == null) {
						throw new BadCredentialsException("Please check the username, password and make sure your account is currently active in order to login.");
					}

					UserDTO userDTO = allPerson.get(0);
					userDTO.setUserId(user.getUserId());
					userDTO.setFirstName(user.getFirstName());
					userDTO.setLastName(user.getLastName());
					userDTO.setDefaultSite(user.getDefaultCustomerSite());//userDAO.getDefaultSite(userDTO.getEmail()));
					userDTO.setDefaultOrderView(user.getDefaultOrderView().name());

					
					if (Boolean.TRUE.equals(user.getIsShotAdmin())) {
						List <UserAuthoritiesDTO> userAuthorities = null;
						userAuthorities = userAccountDAO.findAllCustomerSitesAndBillTo();
						if (CollectionUtils.isEmpty(userAuthorities)) {
							throw new BadCredentialsException("Failed to get User Authorities. Please contact support.");
						}
						List < String > roleNames = Arrays.asList(UserRole.SLADE_ADMIN.getRoleName());
						List < String > roleDescs = Arrays.asList(UserRole.SLADE_ADMIN.getRoleDesc());
						userAuthorities.forEach(userSite -> {
							userSite.setRoles(roleNames);
							userSite.setRoleDescriptions(roleDescs);
						});
						userDTO.setUserAuthorities(userAuthorities);
						userDTO.setIsShotAdmin(new Boolean(true));
					} else {
						Collection <UserAuthoritiesDTO> userAuthorities = null;
						userAuthorities = userAccountDAO.findCustomerSitesAndBillTosByUser(user.getUserId());
//						userAuthorities = userDAO.getUserAuthorities(userDTO.getEmail());
						if (CollectionUtils.isEmpty(userAuthorities)) {
							throw new BadCredentialsException("Failed to get User Authorities. Please contact support.");
						}
						userDTO.setUserAuthorities(mergeRoles(userAuthorities));
						userDTO.setIsShotAdmin(new Boolean(false));
					}
					user.setLastLoginDate(new Date());
					return new UserAuthTokenService(userEmail, password, new ArrayList<>(), userDTO);

				}
			} else {
				logger.info("User [{}] failed to login.", userEmail);
				throw new BadCredentialsException(getAdErrorMessge(errorCallback.getError()));
			}

		} catch (DataAccessException e) {
			logger.error("UserServiceImpl.authenticate() : Failed to get User Authorities  : ", e);
			throw new BadCredentialsException("Failed to get User Authorities. Please contact support.");
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	private String getAdErrorMessge(Exception callbackError) {

		String adErrorMsg = null;

		try {
			if (callbackError == null || StringUtils.isEmpty(callbackError.getMessage())) {
				adErrorMsg = "Please check the username, password and make sure your account is currently active in order to login.";
			} else {
				if (callbackError.getMessage().contains("data ")) {
					String errordatacode = callbackError.getMessage().split("data")[1].split(",")[0].toString().trim();
					if (errordatacode.length() > 3) {
						final Pattern SUB_ERROR_CODE = Pattern.compile("error code");
						Matcher m = SUB_ERROR_CODE.matcher(callbackError.getMessage());

						if (m.find()) {
							MatchResult result = m.toMatchResult();
							String errorCode = callbackError.getMessage().substring(result.end());
							String errorParsecode = errorCode.split("-")[0].trim();
							adErrorMsg = ShotUtils.getAdErrorCodes().get(errorParsecode);
						}
					} else {
						adErrorMsg = ShotUtils.getAdErrorCodes().get(errordatacode);
					}
				} else {
					adErrorMsg = "Unable to log you in. Please contact your SHOT admin.";
				}
			}
		} catch (Exception ex) {
			adErrorMsg = "Unable to log you in. Please contact your SHOT admin.";
		}
		return adErrorMsg;
	}

	private List<UserAuthoritiesDTO> mergeRoles(Collection<UserAuthoritiesDTO> userAuthorities) {

		List<UserAuthoritiesDTO> margedAuthorities = new ArrayList<>();

		for (UserAuthoritiesDTO userAuthoritiesDto : userAuthorities) {
			boolean merged = false;
			for (UserAuthoritiesDTO margedAuthoritiesDto : margedAuthorities) {
				if (userAuthoritiesDto.getCustomerKey().equals(margedAuthoritiesDto.getCustomerKey())) {
					margedAuthoritiesDto.getRoles().add(userAuthoritiesDto.getRole());
					margedAuthoritiesDto.getRoleDescriptions().add(userAuthoritiesDto.getRoleDesc());
					userAuthoritiesDto.setRole(null);
					merged = true;
				}
			}
			if (!merged) {
				userAuthoritiesDto.getRoles().add(userAuthoritiesDto.getRole());
				userAuthoritiesDto.getRoleDescriptions().add(userAuthoritiesDto.getRoleDesc());
				userAuthoritiesDto.setRole(null);
				margedAuthorities.add(userAuthoritiesDto);
			}
		}

		return margedAuthorities;
	}

	// Function responsible for creating a new user in the AD
	// Input param email and password
	@Override
	public boolean createUser(String userEmail, String userPassword) {
		try {
			userDAO.createUser(userEmail, userPassword);
			logger.info("Created User in AD with email: {}", userEmail);
			return true;
		} catch (Exception e) {
			logger.error("Could not create User in AD with email: " + userEmail, e);
			throw new BadCredentialsException(getAdErrorMessge(e));
		}
	}

	// Function responsible for deleting a user in the AD
	// Input param email
	@Override
	public boolean deleteUser(String userEmail) {
		try {
			userDAO.deleteUser(userEmail);
			logger.info("Deleted User in ADFS with email: {}", userEmail);
			return true;
		} catch (Exception e) {
			logger.error("Could not delete User in AD with email: " + userEmail, e);
			throw new BadCredentialsException(getAdErrorMessge(e));
		}
	}

	/**
	 * Updates a user with a new password in the AD
	 * @param userEmail
	 * @param newPassword
	 * @return true if the User's password was successfully updated in AD.
	 */
	@Override
	public boolean updateUserPassword(String userEmail, String newPassword) {
		try {
			userDAO.updateUserPassword(userEmail, newPassword);
			logger.info("Updated User's Password in AD with email: {}", userEmail);
			return true;

		} catch (Exception e) {
			logger.error("Could not update User's Password in AD with email: " + userEmail, e);
			throw new BadCredentialsException(getAdErrorMessge(e));
		}
	}

	/**
	 * Gets the instance of an active and existant SHOT User by email from Database.
	 * @param email Email of the SHOT User.
	 * @return User instance if found else null. 
	 */
	@Transactional(readOnly=true)
	public Users getUserByEmail(String email) {
		List < Users > users = this.userAccountDAO.findByEmail(email);
		if (users != null && !users.isEmpty()) {
			logger.info("Found User with email: {}", email);
			return users.get(0);
		}
		logger.info("User with email: {} could not be found", email);
		return null;
	}

	/**
	 * Gets list of User Sites associated with the given User identified by userId having role identified by roleId.
	 * @param userId Id of the User.
	 * @param roleId Id of the Role.
	 * @return Collection of User sites.
	 */
	@Transactional(readOnly=true)
	public List < UserAuthoritiesDTO > getUserSitesByRole(Long userId, Integer roleId) {
		return this.userAccountDAO.findCustomerSitesAndBillTosByUserAndRole(userId, roleId);
	}

	/**
	 * Get all User Sites (Parents) that exist in SHOT.
	 * @return List of all User Sites.
	 */
	@Transactional(readOnly=true)
	public List < UserAuthoritiesDTO > getAllUserSites() {
		return this.userAccountDAO.findAllCustomerSitesAndBillTo();
	}

	/**
	 * Get all Roles configured in SHOT.
	 * @return List of all valid Roles.
	 */
	@Transactional(readOnly=true)
	public List < UserRoleDTO > getAllRoles() {
		return this.userAccountDAO.findAllRoles();
	}

	/**
	 * Create the given User's Account along with his association with User Sites and Roles.
	 * @param userDTO
	 * @return userDTO
	 */
	@Override
	@Transactional
	public UserDTO createUserAccountAndAuthorities(UserDTO userDTO) {
		// Save User info
		Users user = new Users();
		this.saveUser(user, userDTO);
		logger.info("Details of User with Email: {} Created", user.getEmail());
	
		// If a Shot Admin User is not being created
		if (!Boolean.TRUE.equals(user.getIsShotAdmin())) {
			// Save User Roles details
			saveUserAuthorities(user, userDTO);
			logger.info("User Sites, Roles and Bill To details successfully associated with User with Email: {}", user.getEmail());
		}

		// Create User in ADFS through LDAP
		this.createUser(userDTO.getEmail(), userDTO.getPassword());

		return userDTO;
	}

	/**
	 * Update the given User's Account along with his association with User Sites and Roles.
	 * @param userDTO
	 */
	@Override
	@Transactional
	public void updateUserAccountAndAuthorities(UserDTO userDTO) {
		// Find User instance to update
		Users user = this.userAccountDAO.findOne(userDTO.getUserId());
		
		// Update User
		this.saveUser(user, userDTO);
		List < String > userSiteKeys = userDTO.getUserSiteKeys();
		
		if (!Boolean.TRUE.equals(user.getIsShotAdmin())) {
			// Delete User Permissions/Roles
			this.userAccountDAO.deleteUserRolesBySites(user.getUserId(), userSiteKeys);

			// Save User Roles details
			saveUserAuthorities(user, userDTO);
		} else {
			if (userSiteKeys != null && !userSiteKeys.isEmpty()) {
				this.userAccountDAO.deleteUserRoles(user.getUserId());
			}
		}
	}

	/**
	 * Update given User's Site preferences: Default Customer site and Default Order View.
	 * @param userDTO
	 */
	@Override
	@Transactional
	public void updateSitePreferences(UserDTO userDTO) {
		// Find User instance to update
		Users user = this.userAccountDAO.findOne(userDTO.getUserId());

		if (user != null) {
			user.setDefaultCustomerSite(userDTO.getDefaultSite());
			user.setDefaultOrderView(OrderViewType.valueOf(userDTO.getDefaultOrderView()));
			
			this.userAccountDAO.save(user);
			logger.info("Site Preferences updated for User with Email: {}", user.getEmail());
		} else {
			logger.error("Site Preferences could not be updated as User with UserId: {} could not be found", userDTO.getUserId());
			throw new ShotServiceException("User with UserId: " + userDTO.getUserId() + " could not be found");
		}
	}

	/**
	 * Removes the User Sites and Roles associated the given User in UserDTO. 
	 * If all Sites are removed from the User then the User Account is deleted from both SHOT and AD.
	 * @param userDTO User DTO
	 */
	@Override
	@Transactional
	public void deleteUserAccount(UserDTO userDTO) {
		boolean defaultSiteRemoved = false;
		Date updatedDate = new Date();

		// Find User instance to delete
		Users user = this.userAccountDAO.findOne(userDTO.getUserId());
		if (user != null) {
			// Remove User's Sites and Roles
			if (user.getUserRoles() != null && !Boolean.TRUE.equals(user.getIsShotAdmin())) {
				Iterator < UserRoles > roleItr = user.getUserRoles().iterator();
				while (roleItr.hasNext()) {
					UserRoles userRole = roleItr.next();
					for (UserAuthoritiesDTO userSite: userDTO.getUserAuthorities()) {
						String cusSite = userRole.getCustomer().getCusKey();
						if (cusSite.equals(userSite.getCustomerKey())) {
							roleItr.remove();
							this.entityManager.remove(userRole);
							if (cusSite.equals(user.getDefaultCustomerSite())) {
								defaultSiteRemoved = true;
							}
							break;
						}
					}
				}
				
				if (!user.getUserRoles().isEmpty()) {
					UserRoles userRole = user.getUserRoles().iterator().next();
					user.setDefaultCustomerSite(userRole.getCustomer().getCusKey());
				}
			}
			user.setUpdatedBy(ShotUtils.getLoginUserKey());
			user.setUpdatedDate(updatedDate);

			// If all User's Sites have been removed then delete the User from SHOT and AD.
			if (user.getUserRoles().isEmpty()) {
				user.setIsDeleted(true);
				user.setIsActive(false);
				logger.info("User with email: {} deleted from SHOT", user.getEmail());
				try {
					this.deleteUser(userDTO.getEmail());
				} catch (Exception ex) {
					throw new ApiException(ex.getMessage());
				}
			}
			this.userAccountDAO.save(user);
		}
	}

	/**
	 * Perform search of Users based on the search criteria provided in the given Search DTO.
	 * @param userSearchDTO
	 * @return UserSearchDTO instance containing the search results.
	 */
	public UserSearchDTO searchUsers(UserSearchDTO userSearchDTO) {
		boolean isSladeAdmin = false;
		List < String > userSiteKeys = null;
		
		// If current logged in User is a Slade Admin
		if (UserRole.SLADE_ADMIN.getRoleName().equals(userSearchDTO.getLoginRole())) {
			isSladeAdmin = true;
		} else {
			// Get User Site Keys in which the current logged in User is a Customer Super User.
			userSiteKeys = this.userAccountDAO.findCustomerSiteKeysByUserAndRole(userSearchDTO.getLoginUserId(),
					UserRole.CUSTOMER_SUPER_USER.getRoleId());
		}
		List<Users> users = this.userDAO.searchUsers(userSearchDTO, isSladeAdmin, userSiteKeys);
		if (users != null) {
			logger.info("{} Users found in the search result of User Search", users.size());
			List<UserDTO> userDTOs = users.stream().map(user -> {
				UserDTO userDTO = new UserDTO();
				userDTO.setUserId(user.getUserId());
				userDTO.setFirstName(user.getFirstName());
				userDTO.setLastName(user.getLastName());
				userDTO.setEmail(user.getEmail());
				userDTO.setIsActive(user.getIsActive());
				userDTO.setDefaultSite(user.getDefaultCustomerSite());
				userDTO.setDefaultOrderView(user.getDefaultOrderView().name());
				userDTO.setIsShotAdmin(user.getIsShotAdmin());

				// Populate the User Sites, Roles and Bill Tos
				Map<String, UserAuthoritiesDTO> userSitesMap = new HashMap<>();
				user.getUserRoles().forEach(userRole -> {
					UserAuthoritiesDTO userSite = userSitesMap.get(userRole.getCustomer().getCusKey());
					if (userSite == null) {
						userSite = new UserAuthoritiesDTO();
						userSite.setCustomerKey(userRole.getCustomer().getCusKey());
						userSite.setCustomerName(userRole.getCustomer().getCusName());
						userSite.setIsDefaultSite(userRole.getCustomer().getCusKey().equals(user.getDefaultCustomerSite()));
						userSitesMap.put(userRole.getCustomer().getCusKey(), userSite);
					}
					
					// Add Role
					UserRoleDTO roleDTO = new UserRoleDTO();
					roleDTO.setRoleId(userRole.getRole().getRoleId());
					roleDTO.setDescription(userRole.getRole().getDescription());
					roleDTO.setHasBillingRight(userRole.getRole().getHasBillingRight());
					userSite.getRoleDTOs().add(roleDTO);
					
					// Add BillTos
					if (userSite.getBillTos().isEmpty()) {
						if (userRole.getBillTo1() != null) {
							userSite.getBillTos().add(
									new UserSite(userRole.getBillTo1().getCusKey(), userRole.getBillTo1().getCusName()));
						}
						if (userRole.getBillTo2() != null) {
							userSite.getBillTos().add(
									new UserSite(userRole.getBillTo2().getCusKey(), userRole.getBillTo2().getCusName()));
						}
						if (userRole.getBillTo3() != null) {
							userSite.getBillTos().add(
									new UserSite(userRole.getBillTo3().getCusKey(), userRole.getBillTo3().getCusName()));
						}
						if (userRole.getBillTo4() != null) {
							userSite.getBillTos().add(
									new UserSite(userRole.getBillTo4().getCusKey(), userRole.getBillTo4().getCusName()));
						}
					}
				});
				// If User is a Slade Admin
				if (Boolean.TRUE.equals(user.getIsShotAdmin())) {
					Customer defaultCust = this.customerDAO.findCustomer(user.getDefaultCustomerSite());
					UserAuthoritiesDTO userSite = new UserAuthoritiesDTO();
					userSite.setCustomerKey(defaultCust.getCusKey());
					userSite.setCustomerName(defaultCust.getCusName());
					userSite.setIsDefaultSite(true);
					userSitesMap.put(defaultCust.getCusKey(), userSite);
					
					// Add Slade Admin Role
					UserRoleDTO roleDTO = new UserRoleDTO();
					roleDTO.setRoleId(UserRole.SLADE_ADMIN.getRoleId());
					roleDTO.setDescription(UserRole.SLADE_ADMIN.getRoleDesc());
					userSite.getRoleDTOs().add(roleDTO);
				}
				userDTO.getUserAuthorities()
						.addAll(userSitesMap.values().stream()
								.sorted((site1, site2) -> site1.getCustomerName().compareTo(site2.getCustomerName()))
								.collect(Collectors.toList()));
				return userDTO;
			}).collect(Collectors.toList());

			userSearchDTO.setUsers(userDTOs);
		}
		return userSearchDTO;
	}

	/**
	 * Get list of active Users represented by User Ids that have been in-active for the given time.
	 * @param inActiveUserTime Time in days that users have been in-active
	 * @return List of User Ids
	 */
	@Transactional(readOnly=true)
	public List < Long > getActiveUsersToDeActivate(Integer inActiveUserTime) {
		return this.userAccountDAO.findActiveUsersNotLoggedIn(inActiveUserTime);
	}

	/**
	 * De-activates the User identified by UserId. 
	 * It ensures the User instance is locked for read/write by other processes/threads.
	 * @param userId Id of User
	 */
	@Transactional
	public void deActivateUser(Long userId) {
		// Get locked the User instance
		Users user = this.userAccountDAO.findUserAndLockForUpdate(userId);
		
		// If the User is active then de-active it
		if (Boolean.TRUE.equals(user.getIsActive())) {
			user.setActive(Boolean.FALSE);
			logger.info("User with Id: {} with email {} de-activated", user.getUserId(), user.getEmail());
		}
	}

	/**
	 * Save/Persist the User instance.
	 * @param user User instance to save
	 * @param userDTO
	 */
	private void saveUser(Users user, UserDTO userDTO) {
		Date createdDate = new Date();
		
		// Populate details in the User instance.
		user.setEmail(userDTO.getEmail());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setActive(userDTO.getIsActive());
		user.setIsShotAdmin(userDTO.getIsShotAdmin());
		user.setDefaultOrderView(OrderViewType.valueOf(userDTO.getDefaultOrderView()));
		user.setIsDeleted(false);
		if (userDTO.getUserId() == null) {
			user.setCreatedBy(ShotUtils.getLoginUserKey());
			user.setCreatedDate(createdDate);
		} else {
			user.setUpdatedBy(ShotUtils.getLoginUserKey());
			user.setUpdatedDate(createdDate);
		}
		
		// Get User's default Site and set it in User instance.
		List<UserAuthoritiesDTO> userSites = userDTO.getUserAuthorities();
		if (userSites != null) {
			userSites.forEach(userSite -> {
				if (userSite != null) {
					if (Boolean.TRUE.equals(userSite.getIsDefaultSite())) {
						user.setDefaultCustomerSite(userSite.getCustomerKey());
					}
				}
			});
		}
		userAccountDAO.save(user);
		userDTO.setUserId(user.getUserId());
	}
	
	/**
	 * Save/Persist the Sites and Role details for the given User.
	 * @param user User instance with which User Sites will be associated/added.
	 * @param userDTO
	 */
	private void saveUserAuthorities(Users user, UserDTO userDTO) {
		Date createdDate = new Date();

		// Save User Roles details: User Site, Role and Bill TOs
		List<UserAuthoritiesDTO> userSites = userDTO.getUserAuthorities();
		if (userSites != null) {
			userSites.forEach(userSite -> {
				if (userSite != null) {
					this.validateLoginUserSites(user, userDTO);

					Set < UserRoles > existingUserRoles = user.getUserRoles();
					if (existingUserRoles != null) {
						existingUserRoles.forEach(userRole -> {
							String customerKey = userRole.getCustomer().getCusKey();
							String siteCustomerKey = userSite.getCustomerKey();
							if (customerKey.equals(siteCustomerKey)) {
								throw new ShotServiceException(this.env.getProperty("user.update.duplicate.site"),
										"USER-UPDATE-DUPLICATE-SITE");
							}
						});
					}
					
					List<UserRoleDTO> rolesDTOs = userSite.getRoleDTOs();
					if (rolesDTOs != null) {
						rolesDTOs.forEach(roleDTO -> {
							UserRoles userRole = new UserRoles();
							userRole.setUser(user);
							userRole.setCustomer(createCustomer(userSite.getCustomerKey()));
							Roles role = new Roles();
							role.setRoleId(roleDTO.getRoleId());
							userRole.setRole(role);
							userRole.setIsActive(true);
							userRole.setCreatedBy(ShotUtils.getLoginUserKey());
							try {
								userRole.setCreatedDate(createdDate);
							} catch (Exception e) {
								throw new RuntimeException(e) ;
							}

							if (Boolean.TRUE.equals(roleDTO.getHasBillingRight())) {
								List <UserAuthoritiesDTO.UserSite> billToSites = userSite.getBillTos();
								if (billToSites != null) {
									if (billToSites.size() >= 1) {
										userRole.setBillTo1(createCustomer(billToSites.get(0).getCustomerKey()));
									}
									if (billToSites.size() >= 2) {
										userRole.setBillTo2(createCustomer(billToSites.get(1).getCustomerKey()));
									}
									if (billToSites.size() >= 3) {
										userRole.setBillTo3(createCustomer(billToSites.get(2).getCustomerKey()));
									}
									if (billToSites.size() >= 4) {
										userRole.setBillTo4(createCustomer(billToSites.get(3).getCustomerKey()));
									}
								}
							}
							entityManager.persist(userRole);
						});
					}
				}
			});
		}
	}

	 
	/**
	 *  Validates whether Login User is allowed to assign the given sites to the User.
	 * @param user User being created/updated.
	 * @param userDTO
	 * @return true if Login User is allowed.
	 */
	private boolean validateLoginUserSites(Users user, UserDTO userDTO) {
		// If login User is not Slade Admin then validate the User and Login User's sites
		if (!UserRole.SLADE_ADMIN.getRoleName().equals(userDTO.getLoginRole())) {

			// If Login User is a Customer Super User
			if (UserRole.CUSTOMER_SUPER_USER.getRoleName().equals(userDTO.getLoginRole())) {

				// Get Customer Super User's sites
				final List < String > loginUserSites = this.userAccountDAO.findCustomerSiteKeysByUserAndRole(
						userDTO.getLoginUserId(), UserRole.CUSTOMER_SUPER_USER.getRoleId());
				
				// Validate if the User's sites are a subset of Login User's sites
				List<UserAuthoritiesDTO> userSites = userDTO.getUserAuthorities();
				if (userSites != null) {
					userSites.forEach(userSite -> {
						
						// If User site are not in Login User's sites then throw error.
						if (!loginUserSites.contains(userSite.getCustomerKey())) {
							// User's site is not in Login User's sites 
							throw new ShotServiceException(this.env.getProperty("user.site.not.in.loginUser.sites"),
									"USER-SITE-NOTIN-LOGIN_USER-SITES");
						}
					});
				}
			} else {
				// User's site is not in Login User's sites 
				throw new ShotServiceException(this.env.getProperty("user.site.not.in.loginUser.sites"),
						"USER-SITE-NOTIN-LOGIN_USER-SITES");
			}
		}
		return true;
	}

	/**
	 * Create Customer object for the given Customer Key.
	 * @param customerKey Customer Key
	 * @return Customer object
	 */
	private Customer createCustomer(String customerKey) {
		Customer customer = new Customer();
		customer.setCusKey(customerKey);
		return customer;
	}
}