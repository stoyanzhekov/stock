package com.jpmorgan.entity;

import java.time.LocalDateTime;

public class TradeRecord {

	private final LocalDateTime timestamp;
	private final int shareQuantity;
	private Stock stock;
	/**
	 * true value is buying otherwise false
	 */
	private boolean buySellIndicator = false;
	
	
	private TradeRecord(TradeRecordBuilder trb) {
		this.timestamp = trb.timestamp;
		this.shareQuantity = trb.shareQuantity;
		this.buySellIndicator = trb.buySellIndicator;
		this.stock = trb.stock;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public int getShareQuantity() {
		return shareQuantity;
	}

	public boolean isBuySellIndicator() {
		return buySellIndicator;
	}

	public Stock getStock() {
		return stock;
	}

	public static class TradeRecordBuilder{
		
		private final LocalDateTime timestamp;
		private final int shareQuantity;
		private final Stock stock;
		/**
		 * true value is buying otherwise false
		 */
		private final boolean buySellIndicator;
		
		public TradeRecordBuilder(LocalDateTime timestamp, int shareQuantity, boolean buySellIndicator, Stock stock){
			this.timestamp = timestamp;
			this.shareQuantity = shareQuantity;
			this.buySellIndicator = buySellIndicator;
			this.stock = stock;
		}
		
		//User.UserBuilder('Jhon', 'Doe')
		public TradeRecord build(){
			return new TradeRecord(this);
		}
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (buySellIndicator ? 1231 : 1237);
		result = prime * result + shareQuantity;
		result = prime * result + ((stock == null) ? 0 : stock.hashCode());
		result = prime * result
				+ ((timestamp == null) ? 0 : timestamp.hashCode());
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
		TradeRecord other = (TradeRecord) obj;
		if (buySellIndicator != other.buySellIndicator)
			return false;
		if (shareQuantity != other.shareQuantity)
			return false;
		if (stock == null) {
			if (other.stock != null)
				return false;
		} else if (!stock.equals(other.stock))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}
	
}
