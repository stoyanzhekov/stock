package com.jpmorgan.stock.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.jpmorgan.entity.Stock;
import com.jpmorgan.entity.TradeRecord;
import com.jpmorgan.stock.exception.StockCalculationException;

public interface SimpleStockCalculator {

	byte PRECISION = 2;
	
	/**
	 * Calculate dividend yield based on particular stock
	 * @param Stock stock
	 * @return BigDecimal dividend yield
	 * @throws StockCalculationException
	 */
	BigDecimal calculateDividendYield(Stock stock) throws StockCalculationException;
	
	/**
	 * Calculate common dividend yield
	 * @param BigDecimal lastDividend
	 * @param BigDecimal tickerPrice
	 * @return BigDecimal dividend yield
	 */
	BigDecimal calculateCommonDividendYield(BigDecimal lastDividend, BigDecimal tickerPrice);
	
	/**
	 * Calculate preferred dividend yield
	 * @param BigDecimal fixedDividend
	 * @param BigDecimal parValue
	 * @param BigDecimal tickerPrice
	 * @return BigDecimal dividend yield
	 */
	BigDecimal calculatePreferredDividendYield(BigDecimal fixedDividend, BigDecimal parValue, BigDecimal tickerPrice);
	
	/**
	 * Calculate price/earn ratio
	 * @param BigDecimal tickerPrice
	 * @param BigDecimal dividend
	 * @return BigDecimal peRatio
	 */
	BigDecimal calculatePERatio(BigDecimal tickerPrice, BigDecimal dividend);
	
	/**
	 * Calculate stock price based on trade records
	 * @param List<TradeRecord> tradeRecords
	 * @return BigDecimal stock price
	 */
	BigDecimal calculateStockPrice(List<TradeRecord> tradeRecords);
	
	/**
	 * Calculate  GBCE index
	 * @param tradeRecords map of trade records representing stock 
	 * symbol name mapped to all trade records for this stocks
	 * @return BigDecimal GDBE index
	 */
	BigDecimal calculateGDBCEIndex(Map<String, List<TradeRecord>> tradeRecords);
}
