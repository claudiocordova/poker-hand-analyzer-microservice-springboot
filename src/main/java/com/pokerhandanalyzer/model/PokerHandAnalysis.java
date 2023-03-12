package com.pokerhandanalyzer.model;

public class PokerHandAnalysis {

	public static String VALID_HAND = "VALID_HAND";
	public static String INVALID_HAND = "INVALID_HAND";

	public PokerHandAnalysis(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public String status;
	public String message;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
