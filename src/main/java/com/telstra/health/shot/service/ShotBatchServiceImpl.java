package com.telstra.health.shot.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.telstra.health.shot.dao.ShotBatchDAO;
import com.telstra.health.shot.dao.exception.DataAccessException;
import com.telstra.health.shot.dto.AxisBatchAuditDTO;
import com.telstra.health.shot.dto.ShotBatchDTO;
import com.telstra.health.shot.entity.AxisBatchAudit;
import com.telstra.health.shot.entity.ShotBatch;
import com.telstra.health.shot.service.exception.ShotServiceException;

@Service
public class ShotBatchServiceImpl implements ShotBatchService {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerPreferencesServiceImpl.class);

	@Autowired
	ShotBatchDAO shotBatchRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
    private Environment env;

	@Override
	public List<ShotBatch> getShotBatchesNew(String customerId, Date date, boolean week, boolean orderByDeliveryTime)
			throws ShotServiceException {

		try {
			// the fromDate is always same on the date passed in
			Date fromDate = date;

			// calculate the toDate based on the date that was passed in and whether a
			// "week" or "day" view is requested
			int daysToAdd = week ? 7 : 1;
			Date toDate = addDays(date, daysToAdd);

			return shotBatchRepository.getShotBatches(customerId, fromDate, toDate, orderByDeliveryTime);

		} catch (DataAccessException e) {
			logger.error("Failed to get batches for customerId [{}].", customerId, e);
			throw new ShotServiceException(env.getProperty("error.customer.products.exception"));
		}
	}

	@Override
	public List<ShotBatchDTO> getShotBatches(String customerId, Date date, boolean week, boolean orderByDeliveryTime)
			throws ShotServiceException {

		try {
			// the fromDate is always same on the date passed in
			Date fromDate = date;

			// calculate the toDate based on the date that was passed in and whether a
			// "week" or "day" view is requested
			int daysToAdd = week ? 7 : 1;
			Date toDate = addDays(date, daysToAdd);

			List<ShotBatch> shotBatches = shotBatchRepository.getShotBatches(customerId, fromDate, toDate,
					orderByDeliveryTime);

			return shotBatches.stream().map(shotBatch -> convertToDto(shotBatch)).collect(Collectors.toList());
		} catch (DataAccessException e) {
			logger.error("Failed to get batches for customerId [{}].", customerId, e);
			throw new ShotServiceException(env.getProperty("error.customer.products.exception"));
		}
	}

	private ShotBatchDTO convertToDto(ShotBatch shotBatch) {

		DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat time = new SimpleDateFormat("HH:mm:ss");
		
		ShotBatchDTO shotBatchDto = modelMapper.map(shotBatch, ShotBatchDTO.class);
		Date deliveryDateTime = new Date(shotBatchDto.getDeliveryDateTime().getTime());
        String deliveryDateStr = date.format(deliveryDateTime);
        String deliveryTimeStr = time.format(deliveryDateTime);
        shotBatchDto.setDeliveryDate(deliveryDateStr);
        shotBatchDto.setDeliveryTime(deliveryTimeStr);
        
        if(null != shotBatchDto.getTreatmentDateTime()) {
    		Date treatmentDateTime = new Date(shotBatchDto.getTreatmentDateTime().getTime());
            String treatmentDateStr = date.format(treatmentDateTime);
            String treatmentTimeStr = time.format(treatmentDateTime);
            shotBatchDto.setTreatmentDate(treatmentDateStr);
            shotBatchDto.setTreatmentTime(treatmentTimeStr);
        }
        
        shotBatchDto.setOrdDeliverylocation(shotBatch.getOrdDeliveryLocationName());
        shotBatchDto.setDeliveryLocationId(shotBatch.getOrdDeliveryLocation());
        
        return shotBatchDto;
	}

	private Date addDays(Date date, int daysToAdd) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, daysToAdd); // minus number would decrement the days
		return cal.getTime();
	}

	@Override
	public ShotBatchDTO getShotBatch(String customerId, Integer batchId) throws ShotServiceException {
		try {

			ShotBatch shotBatch = shotBatchRepository.getShotBatch(customerId, batchId);
			
			return this.convertToDto(shotBatch);
		} catch (DataAccessException e) {
			logger.error("Failed to get batch for customerId [{}] and batchId[{}].", customerId,batchId, e);
			throw new ShotServiceException(e.getMessage());
		}
	}

	@Override
	public List<AxisBatchAuditDTO> getBatchHistories(Long batchId) throws ShotServiceException {
		try {

			List<AxisBatchAuditDTO> batchHistories = shotBatchRepository.getBatchHistories(batchId);
			
			return batchHistories;
			
		} catch (DataAccessException e) {
			logger.error("Failed to get batch history for batchId[{}].", batchId, e);
			throw new ShotServiceException(e.getMessage());
		}
	}
}
