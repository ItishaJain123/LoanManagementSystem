package com.hexaware.entity;

public class HomeLoan extends Loan {
	private int homeLoanId;
	private String propertyAddress;
	private int propertyValue;

	// Default constructor
	public HomeLoan() {
	}

	// Overloaded constructor with parameters
	public HomeLoan(int homeLoanId, int loanId, Customer customer, int principalAmount, int interestRate, int loanTerm,
			String loanType, String loanStatus, String propertyAddress, int propertyValue) {
		super(loanId, customer, principalAmount, interestRate, loanTerm, loanType, loanStatus);
		this.homeLoanId = homeLoanId;
		this.propertyAddress = propertyAddress;
		this.propertyValue = propertyValue;
	}

	public int getHomeLoanId() {
		return homeLoanId;
	}

	public void setHomeLoanId(int homeLoanId) {
		this.homeLoanId = homeLoanId;
	}

	// Getters and setters
	public String getPropertyAddress() {
		return propertyAddress;
	}

	public void setPropertyAddress(String propertyAddress) {
		this.propertyAddress = propertyAddress;
	}

	public int getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(int propertyValue) {
		this.propertyValue = propertyValue;
	}

	// toString method
	@Override
	public String toString() {
		return "HomeLoan{" + "propertyAddress='" + propertyAddress + '\'' + ", propertyValue=" + propertyValue + "} "
				+ super.toString();
	}
}
