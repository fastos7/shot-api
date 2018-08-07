package com.telstra.health.shot.dao;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.telstra.health.shot.dto.UserAuthoritiesDTO;
import com.telstra.health.shot.dto.UserRoleDTO;
import com.telstra.health.shot.entity.Users;

public interface UserAccountDAO extends JpaRepository<Users, Long> {

	/**
	 * Find all Customer sites and their Bill To details for the given User and his role.
	 * @param userId
	 * @param roleId
	 * @return
	 */
	@Query(
		  "select	"
		  + "new com.telstra.health.shot.dto.UserAuthoritiesDTO( "
		  + "		c.cusKey, c.cusName, ur.role.roleName, ur.role.description "
		  + "		,b1.cusKey, b1.cusName "
		  + "		,b2.cusKey, b2.cusName"
		  + "		,b3.cusKey, b3.cusName"
		  + "		,b4.cusKey, b4.cusName"
		  + ") "
		+ "from		com.telstra.health.shot.entity.UserRoles ur "
		+ "left outer join ur.billTo1 b1 "
		+ "left outer join ur.billTo2 b2 "
		+ "left outer join ur.billTo3 b3 "
		+ "left outer join ur.billTo4 b4 "
		+ "			,com.telstra.health.shot.entity.Customer c "
		+ "where	ur.user.userId = ?1 "
		+ "and		ur.customer.cusKey = c.cusKey "
		+ "and		ur.isActive = true "
		+ "and 		coalesce(c.cusCategory,'NA') in ('P','NA') "
		+ "order by c.cusName"
	)
	public List < UserAuthoritiesDTO > findCustomerSitesAndBillTosByUser(Long userId);

	/**
	 * Find all Customer sites and their Bill To details for the given User and his role.
	 * @param userId
	 * @param roleId
	 * @return
	 */
	@Query(
		  "select	"
		  + "new com.telstra.health.shot.dto.UserAuthoritiesDTO( "
		  + "		c.cusKey, c.cusName, ur.role.roleName, ur.role.description "
		  + "		,b1.cusKey, b1.cusName "
		  + "		,b2.cusKey, b2.cusName"
		  + "		,b3.cusKey, b3.cusName"
		  + "		,b4.cusKey, b4.cusName"
		  + ") "
		+ "from		com.telstra.health.shot.entity.UserRoles ur "
		+ "left outer join ur.billTo1 b1 "
		+ "left outer join ur.billTo2 b2 "
		+ "left outer join ur.billTo3 b3 "
		+ "left outer join ur.billTo4 b4 "
		+ "			,com.telstra.health.shot.entity.Customer c "
		+ "where	ur.user.userId = ?1 "
		+ "and		ur.role.roleId = ?2 "
		+ "and		ur.customer.cusKey = c.cusKey "
		+ "and		ur.isActive = true "
		+ "and 		coalesce(c.cusCategory,'NA') in ('P','NA') "
		+ "order by c.cusName"
	)
	public List < UserAuthoritiesDTO > findCustomerSitesAndBillTosByUserAndRole(Long userId, Integer roleId);

	@Query(
			  "select	ur.customer.cusKey "
			+ "from		com.telstra.health.shot.entity.UserRoles ur "
			+ "where	ur.user.userId = ?1 "
			+ "and		ur.role.roleId = ?2 "
			+ "and		ur.isActive = true "
		)
	public List < String > findCustomerSiteKeysByUserAndRole(Long userId, Integer roleId);

	/**
	 * Find all Customer sites and their Bill To details.
	 * @return
	 */
	@Query(
			  "select	new com.telstra.health.shot.dto.UserAuthoritiesDTO( "
			  + "		c.cusKey, c.cusName, '', '', b1.cusKey, b1.cusName, b2.cusKey, b2.cusName, b3.cusKey, b3.cusName, b4.cusKey, b4.cusName) "
			+ "from		com.telstra.health.shot.entity.Customer c "
			+ "left outer join c.cusBillto b1 "
			+ "left outer join c.cusBillto2 b2 "
			+ "left outer join c.cusBillto3 b3 "
			+ "left outer join c.cusBillto4 b4 "
			+ "where	c.cusActive = 'A' "
			+ "and 		coalesce(c.cusCategory,'NA') in ('P','NA') "
			+ "order by c.cusName"
		)
	public List < UserAuthoritiesDTO > findAllCustomerSitesAndBillTo();
	
	/**
	 * Find all Roles instances configured in the System
	 * @return UserRoleDTO List
	 */
	@Query("select new com.telstra.health.shot.dto.UserRoleDTO(role.roleId, role.roleName, role.description, role.hasBillingRight) from com.telstra.health.shot.entity.Roles role order by role.roleName")
	public List < UserRoleDTO > findAllRoles();

	/**
	 * Find active and non-deleted User by email.
	 * @param email User's email
	 * @return List of Users.
	 */
	@Query("select user from com.telstra.health.shot.entity.Users user where upper(user.email) = upper(?1) and user.isActive = true and user.isDeleted = false")
	public List < Users > findByEmail(String email);

	/**
	 * Delete all User Roles for the given User
	 * @param userId Id of the User
	 */
	@Transactional
	@Modifying
	@Query("delete from com.telstra.health.shot.entity.UserRoles where user.userId = ?1")
	public void deleteUserRoles(Long userId);

	/**
	 * Delete User's roles by Site
	 * @param userId Id of User
	 * @param userSiteKeys User's site keys whose roles will be deleted from User.
	 */
	@Transactional
	@Modifying
	@Query("delete from com.telstra.health.shot.entity.UserRoles where user.userId = ?1 and customer.cusKey in (?2)")
	public void deleteUserRolesBySites(Long userId, List < String > userSiteKeys);
	
	/**
	 * Get list of active Users represented by User Ids that have been in-active for the given time.
	 * @param inActiveUserTime Time in days that users have been in-active
	 * @return List of User Ids
	 */
	@Query("select user.userId from com.telstra.health.shot.entity.Users user where user.isActive = true and datediff(day, user.lastLoginDate, getdate()) > ?1")
	public List < Long > findActiveUsersNotLoggedIn(Integer inActiveUserTime);
	
	/**
	 * Finds the User and locks its row for update.
	 * @param userId Id of User.
	 * @return Database Locked User instance.
	 */
	@Query("select user from com.telstra.health.shot.entity.Users user where user.userId = ?1")
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public Users findUserAndLockForUpdate(Long userId);
}
