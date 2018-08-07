package com.telstra.health.shot.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.CollectingAuthenticationErrorCallback;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.WhitespaceWildcardsFilter;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Repository;

import com.telstra.health.shot.dao.exception.DataAccessException;
import com.telstra.health.shot.dto.UserAuthoritiesDTO;
import com.telstra.health.shot.dto.UserDTO;
import com.telstra.health.shot.dto.UserSearchDTO;
import com.telstra.health.shot.entity.Users;

@Repository
public class UserDAOImpl implements UserDAO {

	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
	@Autowired
	private LdapTemplate ldapTemplate;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Value("${spring.ldap.users.dn}")
	private String dnForUserLocation;

	@Override
	public List<UserDTO> findUser(String userEmail) throws DataAccessException {
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("objectclass", "user"));
		// filter.and(new WhitespaceWildcardsFilter("cn", userName));
		filter.and(new WhitespaceWildcardsFilter("mail", userEmail));
		try {
			return ldapTemplate.search("", filter.encode(),

					new AttributesMapper<UserDTO>() {
						@Override
						public UserDTO mapFromAttributes(Attributes attr) throws NamingException {
							UserDTO person = new UserDTO();
							person.setEmail((String) attr.get("mail").get());
							return person;
						}

					});
		} catch (Exception ex) {
			logger.error("UserRepositoryImpl.findUser() : Failed to search user : Error : ", ex);
			throw new DataAccessException(ex.getMessage());
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String getDefaultSite(String email) throws DataAccessException {
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery criteriaQuery = builder.createQuery(Users.class);
			Root<Users> entity = criteriaQuery.from(Users.class);
			criteriaQuery.where(builder.equal(entity.get("email"), email));
			criteriaQuery.select(entity.get("defaultCustomerSite"));

			return (String) entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (Exception ex) {
			logger.error("UserRepositoryImpl.getDefaultSite() : Failed to getDefaultSite : for user ", email, ex);
			throw new DataAccessException(ex.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<UserAuthoritiesDTO> getUserAuthorities(String email) throws DataAccessException {

		Set<UserAuthoritiesDTO> userAuthorieties = null;
		try {

			List<UserAuthoritiesDTO> parentAuthorietis = entityManager
					.createQuery("  select new com.telstra.health.shot.dto.UserAuthoritiesDTO( " + "   customer.cusKey,"
							+ "   customer.cusName," + "   roles.roleName ) " + "   from   "
							+ "   UserRoles userRoles, Roles roles ,  Users users, Customer customer "
							+ "   where userRoles.role.roleId = roles.roleId " + "   and users.userId = userRoles.user.userId "
							+ "   and userRoles.customer.cusKey = customer.cusKey "
							+ "   and userRoles.isActive = true and users.email = :email "
							+ "   and customer.cusKey = userRoles.customer.cusKey "
							+ "   and coalesce(customer.cusCategory,'NA') in ('P','NA') ")
					.setParameter("email", email).getResultList();

			List<UserAuthoritiesDTO> grendParentAuthorietis = entityManager
					.createQuery("  select new com.telstra.health.shot.dto.UserAuthoritiesDTO( " + "   customer.cusKey,"
							+ "   customer.cusName," + "   roles.roleName ) " + "   from   "
							+ "   UserRoles userRoles, Roles roles ,  Users users, Customer customer "
							+ "   where userRoles.role.roleId = roles.roleId " + "   and users.userId = userRoles.user.userId"
							+ "   and userRoles.customer.cusKey = customer.cusKey "
							+ "   and users.isDeleted = false"
							+ "   and userRoles.isActive = true and users.email = :email "
							+ "   and coalesce(customer.cusCategory,'NA') in ('P','NA') "
							+ "        and (customer.cusBillto.cusKey = userRoles.customer.cusKey "
							+ "             or customer.cusBillto2.cusKey = userRoles.customer.cusKey "
							+ "      		or customer.cusBillto3.cusKey = userRoles.customer.cusKey "
							+ "       		or customer.cusBillto4.cusKey = userRoles.customer.cusKey) ")
					.setParameter("email", email).getResultList();
			userAuthorieties = new HashSet<UserAuthoritiesDTO>();
			userAuthorieties.addAll(parentAuthorietis);
			userAuthorieties.addAll(grendParentAuthorietis);

			return userAuthorieties;

		} catch (Exception ex) {
			logger.error("UserRepositoryImpl.getUserAuthoristies() : Failed to getUserAuthoristies : for user ", email,
					ex);
			throw new DataAccessException(ex.getMessage());
		}
	}

	// Function Purpose:- Authenticate the user is valid or not. If not return the
	// errorcallback with the right code
	// Return value :- true for
	/*
	 * Active directory has several interesting codes: 525 - user not found 52e -
	 * invalid credentials 530 - not permitted to logon at this time 531 - not
	 * permitted to logon from this computer 532 - password expired 533 - account
	 * disabled 701 - account expired 773 - user must reset password 775 - account
	 * locked
	 */

	@Override
	public boolean authenticateUser(String userEmail, String password,
			CollectingAuthenticationErrorCallback errorcallback) {

		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("mail", userEmail));

		return ldapTemplate.authenticate("", filter.toString(), password, errorcallback);
	}

	@Override
	public void createUser(final String email, final String password) throws DataAccessException {
		try {
			Name dn = buildDn(email);
			DirContextAdapter context = new DirContextAdapter(dn);
			context.setAttributeValues("objectclass", new String[] { "top", "person", "organizationalPerson", "user" });
			context.setAttributeValue("mail", email);
			context.setAttributeValue("userAccountControl", "66048");//Normal + don't expire password
			context.setAttributeValue("pwdLastSet", "-1");
			context.setAttributeValue("UnicodePwd", convertToUnicodePwd(password));			
			ldapTemplate.bind(context);

		} catch (Exception ex) {
			logger.error("UserDAOImpl.create() : Failed to create user : Error : ", ex);
			throw new DataAccessException(ex.getMessage());
		}
	}

	@Override
	public void deleteUser(final String email) throws DataAccessException {
		try {
			ldapTemplate.unbind(buildDn(email));
		} catch (Exception ex) {
			logger.error("UserDAOImpl.deleteUser() : Failed to delete user : Error : ", ex);
			throw new DataAccessException(ex.getMessage());
		}
	}

	@Override
	public void updateUserPassword(final String email, final String userNewPassword) {
		try {

			byte pwdArray[] =convertToUnicodePwd(userNewPassword);
			Attribute attr = new BasicAttribute("UnicodePwd", pwdArray);
			ModificationItem item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr);
			ldapTemplate.modifyAttributes(buildDn(email), new ModificationItem[] { item });

		} catch (Exception ex) {
			logger.error("UserDAOImpl.updateUserPassword() : Failed to update user password : Error : ", ex);
			throw new DataAccessException(ex.getMessage());
		}
	}
	
	public List < Users > searchUsers(UserSearchDTO userSearchDTO, Boolean includeSladeAdmin, List < String > userSiteKeys) {
		StringBuilder queryBuilder = new StringBuilder(
						"select distinct user ")
				.append("from Users user ")
				.append("left outer join fetch user.userRoles ur ")
				.append("left outer join fetch ur.customer cus ")
				.append("left outer join fetch ur.billTo1 ")
				.append("left outer join fetch ur.billTo2 ")
				.append("left outer join fetch ur.billTo3 ")
				.append("left outer join fetch ur.billTo4 ")
				.append("left outer join fetch ur.role role ")
				.append("where user.isDeleted = false ");
				//.append("and ur.isActive = true ");
				//.append("and   user.userId != '").append(userSearchDTO.getLoginUserId()).append("' ");
		if (!Objects.isNull(userSearchDTO.getFirstName())) {
			queryBuilder.append("and upper(user.firstName) like '").append(userSearchDTO.getFirstName().toUpperCase()).append("%' ");
		}
		if (!Objects.isNull(userSearchDTO.getLastName())) {
			queryBuilder.append("and upper(user.lastName) like '").append(userSearchDTO.getLastName().toUpperCase()).append("%' ");
		}
		if (!Objects.isNull(userSearchDTO.getEmail())) {
			queryBuilder.append("and upper(user.email) like '%").append(userSearchDTO.getEmail().toUpperCase()).append("%' ");
		}
		if (!Objects.isNull(userSearchDTO.getIsActive())) {
			queryBuilder.append("and user.isActive = ").append(userSearchDTO.getIsActive()).append(" ");
		}
		if (!Objects.isNull(userSearchDTO.getSiteKey())) {
			queryBuilder.append("and (ur.customer.cusKey = '").append(userSearchDTO.getSiteKey()).append("' ");
			if (Boolean.TRUE.equals((includeSladeAdmin))) {
				queryBuilder.append("or user.isShotAdmin = true ");
			}
			queryBuilder.append(") ");
		} else {
			if (!includeSladeAdmin) {
				if (userSiteKeys != null && !userSiteKeys.isEmpty()) {
					queryBuilder.append("and ur.customer.cusKey in (")
					.append(userSiteKeys.stream().map(cusKey -> "'"+cusKey+"'").collect(Collectors.joining(",")))
					.append(") ");
				}
				
			}
		}
		if (!Objects.isNull(userSearchDTO.getRoleId())) {
			queryBuilder.append("and ("
					+ "(exists (select ur2 from UserRoles ur2 where ur2.user = user and ur2.customer.cusKey = ur.customer.cusKey and ur2.role.roleId = ").append(userSearchDTO.getRoleId()).append(")) ");
			if (Boolean.TRUE.equals((includeSladeAdmin))) {
				queryBuilder.append("or user.isShotAdmin = true ");
			}
			queryBuilder.append(") ");
		}
		if (!includeSladeAdmin) {
			queryBuilder.append("and user.isShotAdmin = false ");
		}
		queryBuilder.append("order by user.lastName, user.firstName, cus.cusName, ur.role.roleName");

		return this.entityManager.createQuery(queryBuilder.toString()).getResultList();
	}

	private byte[] convertToUnicodePwd(final String password) {
		
		String quotedPassword = "\"" + password + "\"";
		char unicodePwd[] = quotedPassword.toCharArray();
		byte pwdArray[] = new byte[unicodePwd.length * 2];
		for (int i = 0; i < unicodePwd.length; i++) {
			pwdArray[i * 2 + 1] = (byte) (unicodePwd[i] >>> 8);
			pwdArray[i * 2 + 0] = (byte) (unicodePwd[i] & 0xff);
		}
		return pwdArray;		
	}

	private Name buildDn(final String email) {
		Name dn = LdapNameBuilder.newInstance().add(dnForUserLocation).add("cn", email).build();
		return dn;
	}

}
