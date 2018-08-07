package com.telstra.health.shot.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 
 * TODO: Insert class explaination here.
 *
 * @author Marlon Cenita
 *
 */
public class ProductAdministrationRoutesDTO implements Serializable {

	private static final long serialVersionUID = -1348942190604652015L;
	
	private String key;	
	private StockDTO stock;
	private AdministrationRouteDTO administrationRoute;	
	private String active;	
	private Timestamp lastUpdWhen;	
	private int lastUpdBy;	
	private String lastUpdAction;
	
	public ProductAdministrationRoutesDTO() {
		super();
	}

	public ProductAdministrationRoutesDTO(String key, StockDTO stock, AdministrationRouteDTO administrationRoute,
			String active, Timestamp lastUpdWhen, int lastUpdBy, String lastUpdAction) {
		super();
		this.key = key;
		this.stock = stock;
		this.administrationRoute = administrationRoute;
		this.active = active;
		this.lastUpdWhen = lastUpdWhen;
		this.lastUpdBy = lastUpdBy;
		this.lastUpdAction = lastUpdAction;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public StockDTO getStock() {
		return stock;
	}

	public void setStock(StockDTO stock) {
		this.stock = stock;
	}

	public AdministrationRouteDTO getAdministrationRoute() {
		return administrationRoute;
	}

	public void setAdministrationRoute(AdministrationRouteDTO administrationRoute) {
		this.administrationRoute = administrationRoute;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Timestamp getLastUpdWhen() {
		return lastUpdWhen;
	}

	public void setLastUpdWhen(Timestamp lastUpdWhen) {
		this.lastUpdWhen = lastUpdWhen;
	}

	public int getLastUpdBy() {
		return lastUpdBy;
	}

	public void setLastUpdBy(int lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}

	public String getLastUpdAction() {
		return lastUpdAction;
	}

	public void setLastUpdAction(String lastUpdAction) {
		this.lastUpdAction = lastUpdAction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((administrationRoute == null) ? 0 : administrationRoute.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((lastUpdAction == null) ? 0 : lastUpdAction.hashCode());
		result = prime * result + lastUpdBy;
		result = prime * result + ((lastUpdWhen == null) ? 0 : lastUpdWhen.hashCode());
		result = prime * result + ((stock == null) ? 0 : stock.hashCode());
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
		ProductAdministrationRoutesDTO other = (ProductAdministrationRoutesDTO) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (administrationRoute == null) {
			if (other.administrationRoute != null)
				return false;
		} else if (!administrationRoute.equals(other.administrationRoute))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (lastUpdAction == null) {
			if (other.lastUpdAction != null)
				return false;
		} else if (!lastUpdAction.equals(other.lastUpdAction))
			return false;
		if (lastUpdBy != other.lastUpdBy)
			return false;
		if (lastUpdWhen == null) {
			if (other.lastUpdWhen != null)
				return false;
		} else if (!lastUpdWhen.equals(other.lastUpdWhen))
			return false;
		if (stock == null) {
			if (other.stock != null)
				return false;
		} else if (!stock.equals(other.stock))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductAdministrationRoutesDTO [key=" + key + ", stock=" + stock + ", administrationRoute="
				+ administrationRoute + ", active=" + active + ", lastUpdWhen=" + lastUpdWhen + ", lastUpdBy="
				+ lastUpdBy + ", lastUpdAction=" + lastUpdAction + "]";
	}
	
	
}
