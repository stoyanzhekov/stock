package com.jpmorgan.stock.exception;

public class StockCalculationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MSG_EXP = "Calculation exception";
	public StockCalculationException(){
		super(MSG_EXP);
	}
}
