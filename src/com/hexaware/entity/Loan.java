package com.hexaware.entity;

public class Loan {
	private int loanId;
	private Customer customer;
	private int principalAmount;
	private int interestRate;
	private int loanTerm;
	private String loanType;
	private String loanStatus;

	// Default constructor
	public Loan() {
	}

	// Overloaded constructor with parameters
	public Loan(int loanId, Customer customer, int principalAmount, int interestRate, int loanTerm, String loanType,
			String loanStatus) {
		this.loanId = loanId;
		this.customer = customer;
		this.principalAmount = principalAmount;
		this.interestRate = interestRate;
		this.loanTerm = loanTerm;
		this.loanType = loanType;
		this.loanStatus = loanStatus;
	}

	// Getters and setters
	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setPrincipalAmount(int principalAmount) {
		this.principalAmount = principalAmount;
	}

	public void setInterestRate(int interestRate) {
		this.interestRate = interestRate;
	}

	public void setLoanTerm(int loanTerm) {
		this.loanTerm = loanTerm;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public int getLoanId() {
		return loanId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public int getPrincipalAmount() {
		return principalAmount;
	}

	public int getInterestRate() {
		return interestRate;
	}

	public int getLoanTerm() {
		return loanTerm;
	}

	public String getLoanType() {
		return loanType;
	}

	public String getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}

	// toString method
	@Override
	public String toString() {
		return "Loan{" + "loanId='" + loanId + '\'' + ", customer=" + customer + ", principalAmount=" + principalAmount
				+ ", interestRate=" + interestRate + ", loanTerm=" + loanTerm + ", loanType='" + loanType + '\''
				+ ", loanStatus='" + loanStatus + '\'' + '}';
	}
}
