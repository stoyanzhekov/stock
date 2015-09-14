package com.jpmorgan.stock.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jpmorgan.entity.Stock;
import com.jpmorgan.entity.TradeRecord;
import com.jpmorgan.stock.exception.StockCalculationException;
import com.jpmorgan.stock.service.SimpleStockCalculator;

public class SimpleStockCalculatorImpl implements SimpleStockCalculator{

	@Override
	public BigDecimal calculateDividendYield(Stock stock) throws StockCalculationException{
		BigDecimal result = new BigDecimal(0);
		if(stock == null)throw new StockCalculationException();
		if(stock.getStockType().equals(Stock.StockType.COMMON)){
			result = calculateCommonDividendYield(stock.getLastDividend(), stock.getTickerPrice());
		} else {
			result = calculatePreferredDividendYield(stock.getFixedDividend(), stock.getParValue(), stock.getTickerPrice());
		}
		return result;
	}
	
	@Override
	public BigDecimal calculateCommonDividendYield(BigDecimal lastDividend, BigDecimal tickerPrice) {
		return lastDividend.divide(tickerPrice, PRECISION, RoundingMode.CEILING);
	}

	@Override
	public BigDecimal calculatePreferredDividendYield(BigDecimal fixedDividend, BigDecimal parValue, BigDecimal tickerPrice) {
		return fixedDividend.multiply(parValue).divide(tickerPrice, PRECISION, RoundingMode.CEILING);
	}

	@Override
	public BigDecimal calculatePERatio(BigDecimal tickerPrice, BigDecimal dividend) {
		if(dividend.equals(BigDecimal.ZERO)) return BigDecimal.ZERO;
		return tickerPrice.divide(dividend, PRECISION, RoundingMode.CEILING);
	}

	@Override
	public BigDecimal calculateStockPrice(List<TradeRecord> tradeRecords) {
		BigDecimal totalSum = new BigDecimal(0);
		int totalShareQuantity = 0;
		for(TradeRecord tr : tradeRecords){
			BigDecimal currentAmount = tr.getStock().getTradePrice().multiply(new BigDecimal(tr.getShareQuantity()));
			totalSum = totalSum.add(currentAmount);
			totalShareQuantity += tr.getShareQuantity();
		}
		return totalSum.divide(new BigDecimal(totalShareQuantity), PRECISION, RoundingMode.CEILING);
	}

	@Override
	public BigDecimal calculateGDBCEIndex(Map<String, List<TradeRecord>> tradeRecords){
		if(tradeRecords.isEmpty()) return new BigDecimal(0);
		ArrayList<BigDecimal> stockPrices = new ArrayList<>();
		for(Stock.StockSymbol ss : Stock.StockSymbol.values()){
			List<TradeRecord> tr = tradeRecords.get(ss.toString());
			stockPrices.add(calculateStockPrice(tr));
		}
		BigDecimal multiplying = new BigDecimal(1);
		int stockArrSize = stockPrices.size();
		for(int i = 0; stockArrSize > i; i++){
			multiplying = multiplying.multiply(stockPrices.get(i));
		}
		double base = multiplying.setScale(3, RoundingMode.CEILING).doubleValue();
		double exponent = (double)1/stockArrSize;
		return new BigDecimal(Math.pow(base, exponent)).setScale(3, RoundingMode.CEILING);
	}
}