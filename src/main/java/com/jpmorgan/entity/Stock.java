package com.jpmorgan.entity;

import java.math.BigDecimal;

public class Stock {

	private StockType stockType;
	private BigDecimal lastDividend;
	private BigDecimal fixedDividend;
	private BigDecimal parValue;
	private BigDecimal tickerPrice;
	private BigDecimal dividend;
	private BigDecimal tradePrice;
	private StockSymbol stockSymbol;
	
	
	public Stock(StockType stockType, StockSymbol stockSymbol, BigDecimal lastDividend,
			BigDecimal fixedDividend, BigDecimal parValue,
			BigDecimal tickerPrice, BigDecimal dividend, BigDecimal tradePrice) {
		this.stockType = stockType;
		this.lastDividend = lastDividend;
		this.fixedDividend = fixedDividend;
		this.parValue = parValue;
		this.tickerPrice = tickerPrice;
		this.dividend = dividend;
		this.tradePrice = tradePrice;
		this.stockSymbol = stockSymbol;
	}

	public StockType getStockType() {
		return stockType;
	}

	public void setStockType(StockType stockType) {
		this.stockType = stockType;
	}

	public BigDecimal getLastDividend() {
		return lastDividend;
	}

	public void setLastDividend(BigDecimal lastDividend) {
		this.lastDividend = lastDividend;
	}

	public BigDecimal getFixedDividend() {
		return fixedDividend;
	}

	public void setFixedDividend(BigDecimal fixedDividend) {
		this.fixedDividend = fixedDividend;
	}

	public BigDecimal getParValue() {
		return parValue;
	}

	public void setParValue(BigDecimal parValue) {
		this.parValue = parValue;
	}

	public BigDecimal getTickerPrice() {
		return tickerPrice;
	}

	public void setTickerPrice(BigDecimal tickerPrice) {
		this.tickerPrice = tickerPrice;
	}

	public BigDecimal getDividend() {
		return dividend;
	}

	public void setDividend(BigDecimal dividend) {
		this.dividend = dividend;
	}

	public BigDecimal getTradePrice() {
		return tradePrice;
	}

	public void setTradePrice(BigDecimal tradePrice) {
		this.tradePrice = tradePrice;
	}

	public StockSymbol getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(StockSymbol stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	public enum StockType{
		COMMON, PREFERRED
	}
	
	public enum StockSymbol{
		TEA, POP, ALE, GIN, JOE;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dividend == null) ? 0 : dividend.hashCode());
		result = prime * result
				+ ((fixedDividend == null) ? 0 : fixedDividend.hashCode());
		result = prime * result
				+ ((lastDividend == null) ? 0 : lastDividend.hashCode());
		result = prime * result
				+ ((parValue == null) ? 0 : parValue.hashCode());
		result = prime * result
				+ ((stockSymbol == null) ? 0 : stockSymbol.hashCode());
		result = prime * result
				+ ((stockType == null) ? 0 : stockType.hashCode());
		result = prime * result
				+ ((tickerPrice == null) ? 0 : tickerPrice.hashCode());
		result = prime * result
				+ ((tradePrice == null) ? 0 : tradePrice.hashCode());
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
		Stock other = (Stock) obj;
		if (dividend == null) {
			if (other.dividend != null)
				return false;
		} else if (!dividend.equals(other.dividend))
			return false;
		if (fixedDividend == null) {
			if (other.fixedDividend != null)
				return false;
		} else if (!fixedDividend.equals(other.fixedDividend))
			return false;
		if (lastDividend == null) {
			if (other.lastDividend != null)
				return false;
		} else if (!lastDividend.equals(other.lastDividend))
			return false;
		if (parValue == null) {
			if (other.parValue != null)
				return false;
		} else if (!parValue.equals(other.parValue))
			return false;
		if (stockSymbol != other.stockSymbol)
			return false;
		if (stockType != other.stockType)
			return false;
		if (tickerPrice == null) {
			if (other.tickerPrice != null)
				return false;
		} else if (!tickerPrice.equals(other.tickerPrice))
			return false;
		if (tradePrice == null) {
			if (other.tradePrice != null)
				return false;
		} else if (!tradePrice.equals(other.tradePrice))
			return false;
		return true;
	}
}
