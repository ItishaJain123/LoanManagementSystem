package com.hexaware.entity;

public class CarLoan extends Loan {
	private int carLoanId;
	private String carModel;
	private int carValue;

	// Default constructor
	public CarLoan() {
	}

	// Overloaded constructor with parameters
	public CarLoan(int carLoanId, int loanId, Customer customer, int principalAmount, int interestRate, int loanTerm,
			String loanType, String loanStatus, String carModel, int carValue) {
		super(loanId, customer, principalAmount, interestRate, loanTerm, loanType, loanStatus);
		this.carLoanId = carLoanId;
		this.carModel = carModel;
		this.carValue = carValue;
	}

	public int getCarLoanId() {
		return carLoanId;
	}

	public void setCarLoanId(int carLoanId) {
		this.carLoanId = carLoanId;
	}

	// Getters and setters
	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public int getCarValue() {
		return carValue;
	}

	public void setCarValue(int carValue) {
		this.carValue = carValue;
	}

	// toString method
	@Override
	public String toString() {
		return "CarLoan{" + "carModel='" + carModel + '\'' + ", carValue=" + carValue + "} " + super.toString();
	}
}
