package com.telstra.health.shot.dao;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;

import com.telstra.health.shot.dao.exception.DataAccessException;
import com.telstra.health.shot.dto.GeneralSearchPatientDTO;
import com.telstra.health.shot.dto.PatientHistoryDTO;
import com.telstra.health.shot.entity.Patient;
 

public class PatientDAOImpl implements PatientDAOCustom {
	
    private static final Logger logger = LoggerFactory.getLogger(PatientDAOImpl.class);

	
	@PersistenceContext
    private EntityManager entityManager; 
	
    @Autowired
    private Environment env;
	
	@Override
	public List<Patient> searchPatients(String customerKey, 
								   		String firstName, 
								   		String surName, 
								   		String ur,
								   		Boolean status,
								   		Pageable pageable) throws DataAccessException {
		
		try{
			
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Patient> criteriaQuery = builder.createQuery(Patient.class);		
	        Root<Patient> entity = criteriaQuery.from(Patient.class);
	        
	        List<Predicate> Predicates = new ArrayList<Predicate>(); 
	        
	        Predicates.add(builder.equal(entity.get("customerKey"), customerKey)); 
	        
	        if(StringUtils.isNotEmpty(firstName))
	            Predicates.add(builder.like(entity.get("firstName"), firstName+"%"));
	        if(StringUtils.isNotEmpty(surName))
	            Predicates.add(builder.like(entity.get("surName"), surName+"%"));
	        if(StringUtils.isNotEmpty(ur))
	            Predicates.add(builder.like(entity.get("ur"), "%"+ur+"%"));
	        if(null != status)
	        {
	        	Predicates.add(builder.equal(entity.get("isActive"), status));
	        }
	        
	        criteriaQuery.where(Predicates.toArray(new Predicate[Predicates.size()]));
	        
	        List<Order> ordering = new LinkedList<Order>();
			ordering.add(builder.desc(entity.get("updatedDate")));
			criteriaQuery.orderBy(ordering);
			
	        return entityManager.createQuery(criteriaQuery)
	        	.setFirstResult(pageable.getPageNumber())
	        	.setMaxResults(Integer.parseInt(env.getProperty("patient.maxPatients")))
	        	.getResultList();
			
		}catch(Exception ex){
			logger.error("PatientRepositoryImpl.searchPatients() : Failed to searchPatients : Error : " , ex);
			throw new DataAccessException(ex.getMessage());
		} 
     
     
    }
	
	@Override
	public List<Patient> searchPatientsWithUR( String customerKey, 
										List<String> listOfURs )  throws DataAccessException{
		try{
			
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Patient> criteriaQuery = builder.createQuery(Patient.class);		
	        Root<Patient> entity = criteriaQuery.from(Patient.class);
	        
	        List<Predicate> Predicates = new ArrayList<Predicate>(); 
	        
	        Predicates.add(builder.equal(entity.get("customerKey"), customerKey)); 
	        Predicates.add(entity.get("ur").in(listOfURs)); 

	        criteriaQuery.where(Predicates.toArray(new Predicate[Predicates.size()]));
	        
	        List<Order> ordering = new LinkedList<Order>();
			ordering.add(builder.desc(entity.get("updatedDate")));
			criteriaQuery.orderBy(ordering);
			
	        return entityManager.createQuery(criteriaQuery)
	        	.getResultList();
			
		}catch(Exception ex){
			logger.error("PatientRepositoryImpl.searchPatients() : Failed to searchPatients : Error : " , ex);
			throw new DataAccessException(ex.getMessage());
		} 
		
	}
	
	@Override
	public Long getPatientCount(String customerKey, 
								   		String firstName, 
								   		String surName, 
								   		String ur,
								   		Boolean status) throws DataAccessException {
		
		try{
			
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);		
	        Root<Patient> entity = criteriaQuery.from(Patient.class);
	        criteriaQuery.select(builder.count(entity));
	        List<Predicate> Predicates = new ArrayList<Predicate>(); 
	        
	        Predicates.add(builder.equal(entity.get("customerKey"), customerKey)); 
	        
	        if(StringUtils.isNotEmpty(firstName))
	            Predicates.add(builder.like(entity.get("firstName"), firstName+"%"));
	        if(StringUtils.isNotEmpty(surName))
	            Predicates.add(builder.like(entity.get("surName"), surName+"%"));
	        if(StringUtils.isNotEmpty(ur))
	            Predicates.add(builder.like(entity.get("ur"), "%"+ur+"%"));
	        if(null != status)
	        {
	        	Predicates.add(builder.equal(entity.get("isActive"), status));
	        }
	        
	        criteriaQuery.where(Predicates.toArray(new Predicate[Predicates.size()]));
	        return entityManager.createQuery(criteriaQuery).getSingleResult();	        
			
		}catch(Exception ex){
			logger.error("PatientRepositoryImpl.getPatientCount() : Failed to getPatientCount : Error : " , ex);
			throw new DataAccessException(ex.getMessage());
		} 
     
     
    }	
	
 	@SuppressWarnings("unchecked")
 	@Override
	public List<PatientHistoryDTO> getPatientHistory(long patientId) throws DataAccessException {
 		
 		try{
 		
	 		return entityManager.createQuery("  select new com.telstra.health.shot.dto.PatientHistoryDto( " +
	 										  "   b.BAT_Key,  " +
	 										  "   b.BAT_ORDKey ,  " +
	 										  "   b.BAT_PATKey , " +
	 										  "   b.BAT_DateEntered  ,  " +
	 										  "   b.BAT_Description  ,  " +
	 										  "   b.BAT_Dose ,  " +
	 										  "   b.BAT_Exact ,   " +
	 										  "   b.BAT_SpecifiedVolume ,  " +
	 										  "   b.BAT_AssemblyInstructions ,  " +
	 										  "   b.BAT_LabelInstructions ,  " +
	 										  "   b.BAT_AdminRouteCode , " +
	 										  "   b.BAT_Status )  " +
	 										  "   from   " +
	 										  "   Batch b  " +
	 										  "   where BAT_PATKey = :patientId")
	 										  .setParameter("patientId", String.valueOf(patientId))
	 										  .getResultList();
 		}catch(Exception ex){
			logger.error("PatientRepositoryImpl.getPatientHistory() : Failed to getPatientHistory : for patientId {} ", patientId, ex);
			throw new DataAccessException(ex.getMessage());
		} 
		
	}
 	
 	@SuppressWarnings("unchecked")
 	@Override
	public List<GeneralSearchPatientDTO> generalSearchPatients(String customerKey, String searchStr) {
 		try {
	 		return entityManager.createQuery("  select new com.telstra.health.shot.dto.GeneralSearchPatientDTO( " +
	 										  "   patientId,  " +
	 										  "   firstName, " +
	 										  "   surName,  " +
	 										  "   dob,  " +
	 										  "   ur)" +
	 										  "   from   " +
	 										  "   Patient " +
	 										  "   where customerKey = :customerKey" + 
	 										  "   and   isActive = true" +
	 										  "   and   (isnull(lower(firstName), '') + ' ' + isnull(lower(surName), '') + ' ' + isnull(lower(ur), '')) like :searchStr " +
//	 										  "	  and (" +
//	 										  "		lower(firstName) like :searchStr" +
//	 										  "		or" +
//	 										  "		lower(surName) like :searchStr" +
//	 										  "		or" +
//	 										  "		lower(ur) like :searchStr" +
//	 										  "	  )" +
	 										  "   order by firstName"
	 										  )
	 										 .setParameter("customerKey", customerKey)
	 										 .setParameter("searchStr", "%" + String.valueOf(searchStr).toLowerCase() + "%")
	 										 .getResultList();
 		} catch(Exception ex){
			logger.error("PatientRepositoryImpl.generalSearchPatients() : Failed to generalSearchPatients : for customerKey {} and search string {} ", customerKey, searchStr, ex);
			throw new RuntimeException(ex.getMessage());
		} 
	}
 	
 	@SuppressWarnings("finally")
	public String retrieveNextMRN() {
 		
 		String returnValue = "";
 		boolean doesPatientExist = true;
 		try {
 	 		while(doesPatientExist) {
		 		SessionImplementor session = this.entityManager.unwrap(SessionImplementor.class);
				PreparedStatement ps = null;
				ResultSet rs = null;
				Connection connection = session.connection();
		 		
				String updateQuery = "update idn_identity set idn_value=idn_value + 1  , idn_lastupdby= ?, idn_lastupdwhen=getdate(), idn_lastupdaction='U'   where idn_type = ?";
				ps = connection.prepareStatement(updateQuery);
				ps.setString(1, "679");
				ps.setString(2, "AUR");
				int returnUpdate = ps.executeUpdate();
				if (returnUpdate == 1) {
		
					String query = "select ? + cast(IDN_VALUE as varchar(10)) as NextMRNNumber from IDN_IDENTITY where IDN_TYPE = ?";
		
					ps = connection.prepareStatement(query);
					ps.setString(1, "AUR");
					ps.setString(2, "AUR");
					rs = ps.executeQuery();
					if (rs.next()) {
						System.out.println(rs.getString(1));
						returnValue = rs.getString(1);
					}
					
					List<Patient> patientsForNewUR = getPatientByUR(returnValue);
					if(patientsForNewUR.size() == 0) {
						doesPatientExist = false;
					}
				}
 	 		}
 		}
 		catch(Exception ex){
			logger.error("PatientRepositoryImpl.retrieveNextMRN() : Failed to retrieveNextMRN ", ex);
			throw new RuntimeException(ex.getMessage());
		}
 		finally {
 			return returnValue;
 		}
 	}
 	
 	private List<Patient> getPatientByUR(String ur) {
		try{
			
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Patient> criteriaQuery = builder.createQuery(Patient.class);		
	        Root<Patient> entity = criteriaQuery.from(Patient.class);
	        
	        List<Predicate> Predicates = new ArrayList<Predicate>(); 
	        
	        Predicates.add(builder.equal(entity.get("ur"), ur)); 

	        criteriaQuery.where(Predicates.toArray(new Predicate[Predicates.size()]));
	        
	        return entityManager.createQuery(criteriaQuery)
	        	.getResultList();
			
		}catch(Exception ex){
			logger.error("PatientRepositoryImpl.searchPatients() : Failed to searchPatients : Error : " , ex);
			throw new DataAccessException(ex.getMessage());
		} 
 	}
 	
 	public int getPatientByURForCustomer(String ur, String customerKey) {
		try{
			
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Patient> criteriaQuery = builder.createQuery(Patient.class);		
	        Root<Patient> entity = criteriaQuery.from(Patient.class);
	        
	        List<Predicate> Predicates = new ArrayList<Predicate>(); 
	        
	        Predicates.add(builder.equal(entity.get("ur"), ur)); 
	        Predicates.add(builder.equal(entity.get("customerKey"), customerKey)); 

	        criteriaQuery.where(Predicates.toArray(new Predicate[Predicates.size()]));
	        
	        List<Patient> patientsForNewUR = entityManager.createQuery(criteriaQuery)
	        	.getResultList();
	        
	        return patientsForNewUR.size();
			
		}catch(Exception ex){
			logger.error("PatientRepositoryImpl.searchPatients() : Failed to searchPatients : Error : " , ex);
			throw new DataAccessException(ex.getMessage());
		} 
 	}

}
