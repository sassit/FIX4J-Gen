package com.algoTrader.entity.trade;

import java.math.BigDecimal;
import java.util.Date;

public class LimitOrderImpl {

	private Number number;
	private char handlInst;
	private Security security;
	private char side;
	private Date time;
	private int quantity;
	private BigDecimal price;

	public Number getNumber() {
		return number;
	}

	public void setNumber(Number number) {
		this.number = number;
	}

	public char getHandlInst() {
		return handlInst;
	}

	public void setHandlInst(char handlInst) {
		this.handlInst = handlInst;
	}

	public Security getSecurity() {
		return security;
	}

	public void setSecurity(Security security) {
		this.security = security;
	}

	public char getSide() {
		return side;
	}

	public void setSide(char side) {
		this.side = side;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	class Security {
		private String symbol;

		public String getSymbol() {
			return symbol;
		}

		public void setSymbol(String symbol) {
			this.symbol = symbol;
		}
	}
}
