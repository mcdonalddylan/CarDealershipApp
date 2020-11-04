package com.dealership.model;

import java.time.LocalDate;

public class Payment {

	private int payId;
	private String username;
	private int carId;
	private double amntRemain;
	private double payment;
	private LocalDate payDate;

	public int getPayId() {
		return payId;
	}

	public void setPayId(int payId) {
		this.payId = payId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public double getAmntRemain() {
		return amntRemain;
	}

	public void setAmntRemain(double amntRemain) {
		this.amntRemain = amntRemain;
	}

	public double getPayment() {
		return payment;
	}

	public void setPayment(double payment) {
		this.payment = payment;
	}

	public LocalDate getPayDate() {
		return payDate;
	}

	public void setPayDate(LocalDate payDate) {
		this.payDate = payDate;
	}

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}
}
