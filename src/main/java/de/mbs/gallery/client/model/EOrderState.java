package de.mbs.gallery.client.model;

public enum EOrderState {
	OPEN,
	SUBMIT,
	WIP,
	DOWNLOAD;
	
	public static EOrderState safeParseValue(String value) {
		EOrderState orderState = EOrderState.OPEN;
		if(null != value) {
			try {
				orderState = EOrderState.valueOf(value);
			}
			catch(IllegalArgumentException e) {
			}
		}
		
		return orderState;
	}
}
