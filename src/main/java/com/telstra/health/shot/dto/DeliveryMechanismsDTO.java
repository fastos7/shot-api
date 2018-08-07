package com.telstra.health.shot.dto;

import java.io.Serializable;

/**
 * 
 * TODO: Insert class explaination here.
 *
 * @author Marlon Cenita
 *
 */
public class DeliveryMechanismsDTO implements Serializable {

	private static final long serialVersionUID = -7636710592387627348L;
	
	
	private String key;
	private DiluentDTO diluent;
	private ContainerDTO container;
	private String active;
	
	public DeliveryMechanismsDTO() {
		super();
	}

	public DeliveryMechanismsDTO(String key, DiluentDTO diluent, ContainerDTO container, String active) {
		super();
		this.key = key;
		this.diluent = diluent;
		this.container = container;
		this.active = active;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public DiluentDTO getDiluent() {
		return diluent;
	}

	public void setDiluent(DiluentDTO diluent) {
		this.diluent = diluent;
	}

	public ContainerDTO getContainer() {
		return container;
	}

	public void setContainer(ContainerDTO container) {
		this.container = container;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((container == null) ? 0 : container.hashCode());
		result = prime * result + ((diluent == null) ? 0 : diluent.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
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
		DeliveryMechanismsDTO other = (DeliveryMechanismsDTO) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (container == null) {
			if (other.container != null)
				return false;
		} else if (!container.equals(other.container))
			return false;
		if (diluent == null) {
			if (other.diluent != null)
				return false;
		} else if (!diluent.equals(other.diluent))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DeliveryMechanismsDTO [key=" + key + ", diluent=" + diluent + ", container=" + container + ", active="
				+ active + "]";
	}
	
}
