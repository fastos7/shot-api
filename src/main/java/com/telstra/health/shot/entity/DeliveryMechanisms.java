package com.telstra.health.shot.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

/**
 * 
 * TODO: Insert class explaination here.
 *
 * @author Marlon Cenita
 *
 */
@Entity
@Table(name="DEL_DeliveryMechanism")
@SqlResultSetMapping(name = "transformTrialDrugDeliveryMechanismDTO",
	entities= {
		@EntityResult(entityClass=Diluent.class,
					  fields= {
							  @FieldResult(name="stockKey",column="diluentKey"),
							  @FieldResult(name="stockDescription",column="diluentDescription"),
							  @FieldResult(name="stockType",column="diluentType"),
							  @FieldResult(name="stockCode",column="diluentCode"),
							  @FieldResult(name="stockActive",column="diluentActive")
					  }),
		@EntityResult(entityClass=Container.class,
					  fields= {
							  @FieldResult(name="stockKey",column="containerKey"),
							  @FieldResult(name="stockDescription",column="containerDescription"),
							  @FieldResult(name="stockType",column="containerType"),
							  @FieldResult(name="stockCode",column="containerCode"),
							  @FieldResult(name="stockActive",column="containerActive")
					  })},
	columns= {
		@ColumnResult(name="key")	
	}
)
public class DeliveryMechanisms implements Serializable {

	private static final long serialVersionUID = -8277927375636520277L;
	
	@Id
	@Column(name="DEL_Key")
	private String key;
	
//	@ManyToOne
//	@JoinColumn(name = "DEL_STKKey")
//	private Stock stock;
	
	@ManyToOne
	@JoinColumn(name = "DEL_DiluentType")
	private Diluent diluent;
	
	@ManyToOne
	@JoinColumn(name = "DEL_ContainerType")
	private Container container;
	
	@Column(name="DEL_Active")
	private String active;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

//	public Stock getStock() {
//		return stock;
//	}
//
//	public void setStock(Stock stock) {
//		this.stock = stock;
//	}

	public Diluent getDiluent() {
		return diluent;
	}

	public void setDiluent(Diluent diluent) {
		this.diluent = diluent;
	}

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
}
