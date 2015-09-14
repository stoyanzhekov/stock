package com.jpmorgan.stock;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.jpmorgan.entity.Stock;
import com.jpmorgan.entity.TradeRecord;
import com.jpmorgan.stock.exception.StockCalculationException;
import com.jpmorgan.stock.service.SimpleStockCalculator;
import com.jpmorgan.stock.service.impl.SimpleStockCalculatorImpl;

public class SimpleStockCalculatorTest {

	private static List<Stock> stocks;
	private SimpleStockCalculator simpleStockCalculator;

	@Before
	public void init() {

		simpleStockCalculator = new SimpleStockCalculatorImpl();

		// dividend = last dividend * 4
		stocks = new ArrayList<>();
		Stock TEA = new Stock(Stock.StockType.COMMON, Stock.StockSymbol.TEA, new BigDecimal(0), null,
				new BigDecimal(100), null, new BigDecimal(0), null);
		stocks.add(TEA);
		Stock POP = new Stock(Stock.StockType.COMMON, Stock.StockSymbol.POP,new BigDecimal(8), null,
				new BigDecimal(100), null, new BigDecimal(32), null);
		stocks.add(POP);
		Stock ALE = new Stock(Stock.StockType.COMMON, Stock.StockSymbol.ALE,new BigDecimal(23), null,
				new BigDecimal(60), null, new BigDecimal(92), null);
		stocks.add(ALE);
		Stock GIN = new Stock(Stock.StockType.PREFERRED, Stock.StockSymbol.GIN,new BigDecimal(8),
				new BigDecimal(0.02), new BigDecimal(100), null,
				new BigDecimal(32), null);
		stocks.add(GIN);
		Stock JOE = new Stock(Stock.StockType.COMMON, Stock.StockSymbol.JOE,new BigDecimal(13), null,
				new BigDecimal(250), null, new BigDecimal(52), null);
		stocks.add(JOE);
	}

	@Test(expected = StockCalculationException.class)
	public void testCalculateDividendYieldShouldThrowStockCalculationExp() throws StockCalculationException {
		simpleStockCalculator.calculateDividendYield(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void testCalculateDividendYieldStockCalculationNullPointerExp() throws StockCalculationException {
		for(Stock s: stocks){
			simpleStockCalculator.calculateDividendYield(s);
		}
	}
	
	@Test
	public void testCalculateDividendYieldShouldSucceed() throws StockCalculationException {
		for(Stock s: stocks){
			s.setTickerPrice(new BigDecimal(getRandomNumber(s.getParValue().intValue() + 3, s.getParValue().intValue() - 3)));
			BigDecimal result = simpleStockCalculator.calculateDividendYield(s);
			boolean res = result.compareTo(BigDecimal.ZERO) >= 0;
			assertTrue(res);
		}
	}

	@Test
	public void testCalculateCommonDividendYield() {
		for(Stock s: stocks){
			if(s.getStockType().equals(Stock.StockType.PREFERRED))continue;
			s.setTickerPrice(new BigDecimal(getRandomNumber(s.getParValue().intValue() + 3, s.getParValue().intValue() - 3)));
			BigDecimal result = simpleStockCalculator.calculateCommonDividendYield(s.getLastDividend(), s.getTickerPrice());
			boolean res = result.compareTo(BigDecimal.ZERO) >= 0;
			assertTrue(res);
		}
	}

	@Test
	public void testCalculatePreferredDividendYield() {
		for(Stock s: stocks){
			if(!s.getStockType().equals(Stock.StockType.PREFERRED))continue;
			s.setTickerPrice(new BigDecimal(getRandomNumber(s.getParValue().intValue() + 3, s.getParValue().intValue() - 3)));
			BigDecimal result = simpleStockCalculator.calculatePreferredDividendYield(s.getFixedDividend(), s.getParValue(), s.getTickerPrice());
			boolean res = result.compareTo(BigDecimal.ZERO) >= 0;
			assertTrue(res);
		}
	}

	@Test
	public void testCalculatePERatio() {
		for(Stock s: stocks){
			s.setTickerPrice(new BigDecimal(getRandomNumber(s.getParValue().intValue() + 3, s.getParValue().intValue() - 3)));
			BigDecimal result = simpleStockCalculator.calculatePERatio(s.getTickerPrice(), s.getDividend());
			boolean res = result.compareTo(BigDecimal.ZERO) >= 0;
			assertTrue(res);
		}
	}

	@Test
	public void testCalculateStockPrice() {
		BigDecimal result = simpleStockCalculator.calculateStockPrice(getTradeRecInLast15Min(stocks.get(2)));
		boolean res = result.compareTo(BigDecimal.ZERO) >= 0;
		assertTrue(res);
	}

	@Test
	public void testCalculateGDBCEIndex() {
		BigDecimal result = simpleStockCalculator.calculateGDBCEIndex(getTradeRecForAllStockInLast15Min());
		boolean res = result.compareTo(BigDecimal.ZERO) >= 0;
		assertTrue(res);
	}

	/** returns random number between minimum and maximum range, used for generating ticker price */
	public static double getRandomNumber(int maximum, int minimum) {
		return (Math.random() * (maximum - minimum)) + minimum;
	}
	
	public static Map<String, List<TradeRecord>> getTradeRecForAllStockInLast15Min(){
		Map<String, List<TradeRecord>> result = new HashMap<>();
		for(Stock s : stocks){
			List<TradeRecord> listRec = getTradeRecInLast15Min(s);
			result.put(s.getStockSymbol().toString(), listRec);
		}
		
		return result;
	}
	
	/**
	 * used to emulate trade records. it is assumed that during this time there are 100 records
	 * @return List<TradeRecord>
	 */
	public static List<TradeRecord> getTradeRecInLast15Min(Stock s){
		List<TradeRecord> records = new ArrayList<>();
		for(int i = 0; i < 100; i++){
			s.setTradePrice(new BigDecimal(getRandomNumber(s.getParValue().intValue() + 3, s.getParValue().intValue() - 3)));
			TradeRecord tr = new TradeRecord.TradeRecordBuilder(LocalDateTime.now(), randInt(), i%2 == 0 ? true : false, s).build();
			records.add(tr);
		}
		
		return records;
	}
	
	/**
	 * Returns a psuedo-random number between min and max, inclusive.
	 * Used to generate share quntity
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	public static int randInt() {
	    // Usually this can be a field rather than a method variable
	    Random rand = new Random();
	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((1000 - 100) + 1) + 100;
	    return randomNum;
	}
}
