package com.telstra.health.shot.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PatientHistoryDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	
	private String batchKey;
	private String orderKey;
	private String patientKey;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date orderDate;
	private String product;
	private BigDecimal dose;
	private String exact;
	private BigDecimal volume;
	private String assemblyInstructions;
	private String labelInstructions;
	private String adminRouteCode;
	private String status;
	
	
	
	public PatientHistoryDTO(String batchKey, String orderKey, String patientKey,
			Date orderDate, String product, BigDecimal dose, String exact,
			BigDecimal volume, String assemblyInstructions,
			String labelInstructions, String adminRouteCode, String status) {
		super();
		this.batchKey = batchKey;
		this.orderKey = orderKey;
		this.patientKey = patientKey;
		this.orderDate = orderDate;
		this.product = product;
		this.dose = dose;
		this.exact = exact;
		this.volume = volume;
		this.assemblyInstructions = assemblyInstructions;
		this.labelInstructions = labelInstructions;
		this.adminRouteCode = adminRouteCode;
		this.status = status;
	}
	public String getBatchKey() {
		return batchKey;
	}
	public void setBatchKey(String batchKey) {
		this.batchKey = batchKey;
	}
	public String getOrderKey() {
		return orderKey;
	}
	public void setOrderKey(String orderKey) {
		this.orderKey = orderKey;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public BigDecimal getDose() {
		return dose;
	}
	public void setDose(BigDecimal dose) {
		this.dose = dose;
	}
	public String getExact() {
		return exact;
	}
	public void setExact(String exact) {
		this.exact = exact;
	}
	public BigDecimal getVolume() {
		return volume;
	}
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	public String getAssemblyInstructions() {
		return assemblyInstructions;
	}
	public void setAssemblyInstructions(String assemblyInstructions) {
		this.assemblyInstructions = assemblyInstructions;
	}
	public String getLabelInstructions() {
		return labelInstructions;
	}
	public void setLabelInstructions(String labelInstructions) {
		this.labelInstructions = labelInstructions;
	}
	public String getAdminRouteCode() {
		return adminRouteCode;
	}
	public void setAdminRouteCode(String adminRouteCode) {
		this.adminRouteCode = adminRouteCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPatientKey() {
		return patientKey;
	}
	public void setPatientKey(String patientKey) {
		this.patientKey = patientKey;
	}
	
	
	@Override
	public int hashCode() {
		
		return new HashCodeBuilder(17, 37)
        .append(batchKey).append(orderKey).append(patientKey)
        .append(orderDate).append(product).append(dose)
        .append(exact).append(volume).append(assemblyInstructions)
        .append(labelInstructions).append(adminRouteCode).append(status) 
        .toHashCode(); 
	}
 
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		PatientHistoryDTO other = (PatientHistoryDTO) obj;
		
		return new EqualsBuilder()
        .append(batchKey, other.batchKey)
        .append(orderKey, other.orderKey)
        .append(patientKey, other.patientKey)
        .append(orderDate, other.orderDate)
        .append(product, other.product)
        .append(dose, other.dose)
        .append(exact, other.exact)
        .append(volume, other.volume)
        .append(assemblyInstructions, other.assemblyInstructions)
        .append(labelInstructions, other.labelInstructions)
        .append(adminRouteCode, other.adminRouteCode)
        .append(status, other.status) 
        .isEquals();
		
	 
	}
	

}
