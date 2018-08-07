package com.telstra.health.shot.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * TODO: Insert class explaination here.
 *
 * @author Marlon Cenita
 *
 */
@Entity
@Table(name="ADR_AdministrationRoute")
public class ProductAdministrationRoutes implements Serializable {

	private static final long serialVersionUID = -3876709063125509499L;
	
	@Id
	@Column(name = "ADR_Key")
	private String key;
	
	@ManyToOne
	@JoinColumn(name = "ADR_STKKey")
	private Stock stock;
	
	@ManyToOne
	@JoinColumn(name = "ADR_AdminRouteCode",referencedColumnName="COD_Code")
	private AdministrationRoute administrationRoute;
	
	@Column(name = "ADR_Active")
	private String active;
	
	@Column(name = "ADR_LastUpdWhen")
	private Timestamp lastUpdWhen;
	
	@Column(name = "ADR_LastUpdBy")
	private int lastUpdBy;
	
	@Column(name = "ADR_LastUpdAction")
	private String lastUpdAction;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public AdministrationRoute getAdministrationRoute() {
		return administrationRoute;
	}

	public void setAdministrationRoute(AdministrationRoute administrationRoute) {
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

	
}
