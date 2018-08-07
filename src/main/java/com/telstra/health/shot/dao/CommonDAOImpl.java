package com.telstra.health.shot.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.telstra.health.shot.dao.exception.DataAccessException;
import com.telstra.health.shot.entity.HomePageActions;

@Repository
public class CommonDAOImpl implements CommonDAO {
	
	@PersistenceContext
    private EntityManager entityManager; 
	
	@SuppressWarnings("rawtypes")
	@Override
	public List getAppWebPageAceeses () throws DataAccessException {
		
		try{
	 		
			return entityManager.createQuery("select wprm.webPageName, roles.roleName "  +
					  "   from   " + 
					  "   WebPageRoleMapping wprm, Roles roles" +
					  "   where wprm.roleId = roles.roleId "  +
					  "   and wprm.isActive = 'Y') " )
					  .getResultList();
			
 		}catch(Exception ex){
 			throw new DataAccessException(ex.getMessage());
		} 
		
	}
	
	@SuppressWarnings("unchecked")
	public List findURLRoleAccesses() {
		return (List < String[] >)entityManager.createQuery(
			  "select urm.url, urm.role.roleName "  +
			  "from   URLRoleMapping urm " +
			  "where  urm.isActive = 'Y') " )
			  .getResultList();
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<HomePageActions> getHomePageActions () throws DataAccessException {
		
		try{
	 		
			return entityManager.createQuery(" from HomePageActions where isActive = 'Y'" ).getResultList();
			
 		}catch(Exception ex){
 			throw new DataAccessException(ex.getMessage());
		} 
		
	}

}
