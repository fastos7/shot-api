package com.telstra.health.shot.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * TODO: Insert class explaination here.
 *
 * @author Marlon Cenita
 *
 */
public class ProductAttributesDTO implements Serializable{

	private static final long serialVersionUID = 6750946668460851775L;

	private List<ProductAdministrationRoutesDTO> administrationRoutes;

	public ProductAttributesDTO() {
		super();
	}
	
	public ProductAttributesDTO(List<ProductAdministrationRoutesDTO> administrationRoutes) {
		super();
		this.administrationRoutes = administrationRoutes;
	}

	public List<ProductAdministrationRoutesDTO> getAdministrationRoutes() {
		return administrationRoutes;
	}

	public void setAdministrationRoutes(List<ProductAdministrationRoutesDTO> administrationRoutes) {
		this.administrationRoutes = administrationRoutes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((administrationRoutes == null) ? 0 : administrationRoutes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductAttributesDTO other = (ProductAttributesDTO) obj;
		if (administrationRoutes == null) {
			if (other.administrationRoutes != null)
				return false;
		} else if (!administrationRoutes.equals(other.administrationRoutes))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductAttributesDTO [administrationRoutes=" + administrationRoutes + "]";
	} 
	
	
}
