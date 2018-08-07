package com.telstra.health.shot.common.enums;

import com.telstra.health.shot.entity.CustomerPreference.ProductType;

/**
 * Enum that represents the different types of Customer Orders.
 * @author osama.shakeel
 *
 */
public enum OrderType {

	NON_FORMULATED,
	FORMULATED_NON_TPN,
	FORMULATED_TPN;
	
	public static OrderType valueOf(final Integer orderTypeId) {
		switch (orderTypeId) {
		case 0:
			return NON_FORMULATED;
		case 1:
			return OrderType.FORMULATED_NON_TPN;
		case 2:
			return OrderType.FORMULATED_TPN;
		default:
			return null;
		}
	}

	/**
	 * Derive Order Type based on Product Type
	 * @param type
	 * @return
	 */
	public static Integer getByProductType(String productType) {
		Integer orderType = 0;
		/*
         * Need to map the product type to either Formulation (0) or 
         * Non-formulation (1). 
         */
        switch (productType) {
            case ProductType.STANDARD:
            case ProductType.CONSIGNMENT:
            case ProductType.CLINICAL_TRIAL:
            case ProductType.MULTI_DRUG:
                orderType = 0; // Set to Non-Formulation
                break;
            case ProductType.FORMULATION:
            case ProductType.CNF_FORMULATION:
                orderType = 1; // Set to Non-Formulation
                break;
        }
        return orderType;
	}
}
