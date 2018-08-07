package com.telstra.health.shot.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;



@Entity
@Table(name = "SHOT_Order")
public class ShotOrder implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/*@OneToMany(mappedBy = "shotOrder", cascade = CascadeType.ALL)
	private List<ShotBatch> batch;*/
	
	
	 
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "com.telstra.health.shot.entity.generator.KeyIdGenerator") 
	@Column(name="OrderKey")
	private String ordKey;
	
	@Column(name="CustomerKey")
	private String ordCuskey;
	@Column(name="PONumber")
	private String ordNo;
	@Column(name="BillTo")
	private String BillTo;
	
	
	
	public String getOrdKey() {
		return ordKey;
	}
	public void setOrdKey(String ordKey) {
		this.ordKey = ordKey;
	}
	public String getOrdCuskey() {
		return ordCuskey;
	}
	public void setOrdCuskey(String ordCuskey) {
		this.ordCuskey = ordCuskey;
	}
	public String getOrdNo() {
		return ordNo;
	}
	public void setOrdNo(String ordNo) {
		this.ordNo = ordNo;
	}
	public String getBillTo() {
		return BillTo;
	}
	public void setBillTo(String billTo) {
		BillTo = billTo;
	}
	/*public List<ShotBatch> getBatch() {
		return batch;
	}
	public void setBatch(List<ShotBatch> batch) {
		this.batch = batch;
	}
	*/
	

}
