package com.telstra.health.shot.api;

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
import com.telstra.health.shot.dto.ProductAttributesDTO;
import com.telstra.health.shot.service.ProductsService;
import com.telstra.health.shot.service.exception.ShotServiceException;

/**
 * 
 * RestController for product attributes related APIs.
 *
 * @author Marlon Cenita
 *
 */
@RestController
public class ProductsAPI {

	private static final Logger logger = LoggerFactory.getLogger(ProductsAPI.class);
	
	@Autowired 
	ProductsService productService;
	
	/**
	 * API to get the products attributes for a given product. These only includes the <code>AdministrationRoutes</code>. 
	 * <p/>
	 * URI : <code>/api/customers/{customerId}/products/attributes??drugKey1=:drugKey1&drugKey2=:drugKey2&drugKey3=:drugKey3</code>
	 * <p/>
	 * METHOD : <code>HTTP GET</code>
	 * <p/>
	 * 
	 * @param customerId 
	 * @return
	 */
	@RequestMapping(value="/api/customers/{customerId}/products/attributes",
					method=RequestMethod.GET,
					produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ProductAttributesDTO> getProductAttributes(@PathVariable("customerId") String customerId,
																     @RequestParam(name="drugKey1",required=true) String drugKey,
																	 @RequestParam(name="drugKey2",required=true) String drugKey2,
																	 @RequestParam(name="drugKey3",required=true) String drugKey3){

		if (logger.isDebugEnabled()) {
			logger.debug("Getting product attributes for the following drug keys : ");
			logger.debug("      Drug Key  : {}",drugKey);
			logger.debug("      Drug Key2 : {}",drugKey2);
			logger.debug("      Drug Key3 : {}",drugKey3);
		}
		
		try {
	
			ProductAttributesDTO productAttributes = this.productService.getProductAttributes(drugKey,drugKey2,drugKey3);
			
			return new ResponseEntity<ProductAttributesDTO>(productAttributes,HttpStatus.OK);
			
		} catch (ShotServiceException e) {
			logger.error("Error getting attributes for product with drugkeys [{},{},{}]. Error : {}",drugKey,drugKey2,drugKey3, e.getMessage());
			throw new ApiException(e.getMessage());
		}
	}	
}
