package com.telstra.health.shot.common.enums;

public enum BatchStatus {
	ON_HOLD("On Hold"),
	SUBMITTED("Submitted"),
	INVOICED("Invoiced"),
	QUARANTINE("Quarantine"),
	ENTRYCONF("XENTRY"),
	PICKING("XPICK"),
	PICKED("XPICK"),
	PRINTING("XPRINT"),
	PRINTED("XPRINT"),
	NEW("XNEW"),
	COMPOUNDING("Compounding"),
	COMPOUNDED("Compounded"),
	PACKING("Packing"),
	PACKED("Packed");
	private String status;

	BatchStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
