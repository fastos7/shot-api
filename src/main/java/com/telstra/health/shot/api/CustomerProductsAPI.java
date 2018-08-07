package com.telstra.health.shot.api;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.telstra.health.shot.api.exception.ApiException;
import com.telstra.health.shot.dto.ProductDTO;
import com.telstra.health.shot.service.ProductsService;
import com.telstra.health.shot.service.exception.ShotServiceException;

@RestController
@RequestMapping("/api/customers/")
public class CustomerProductsAPI {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerProductsAPI.class);
	
	@Autowired 
	ProductsService productService;
	
	/**
	 * API to get the list of products for a given customer.
	 * <p/>
	 * URI : <code>/shot/api/customers/{customerId}/products/searches</code>
	 * <p/>
	 * METHOD : <code>HTTP GET</code>
	 * <p/>
	 * The UI component that consumes this API requires that even if the result is empty it still need to be sent
	 * and status is HTTP 200.
	 * 
	 * @param customerId
	 * @return List of customer products.
	 */	
	@RequestMapping(value="/{customerId}/products/searches",
					method=RequestMethod.GET,
					produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<ProductDTO>> searchCustomerProducts(@PathVariable("customerId") String customerId,
																   @RequestParam(name="resultSet", required=true) String resultSet,		
																   @RequestParam(name="searchStr", required=true) String searchStr){
		
		logger.debug("searching products for customer id : " + customerId + " with search string : " + searchStr);
		
		try {
			if (customerId == null || customerId.equals("")) {
				logger.debug("The customer id passed is empty or null");
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			Optional<List<ProductDTO>> customerProducts = this.productService.searchCustomerProducts(customerId,resultSet,searchStr);
							   
			if (customerProducts.isPresent()) {
				/*
				 * Even if the result does return empty list, it should still be sent because the frontend
				 * component requires this to know that there is no result.
				 */
				logger.debug("Returning " + customerProducts.get().size() + " products for customer id " + customerId);
				return new ResponseEntity<List<ProductDTO>>(customerProducts.get(),HttpStatus.OK);
			} else {
				/*
				 * This will never happen.
				 */
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (ShotServiceException e) {
			throw new ApiException(e.getMessage());
		}
		
	}
}
