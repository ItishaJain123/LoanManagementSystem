package com.hexaware.dao;

import java.sql.SQLException;

import com.hexaware.entity.Loan;
import com.hexaware.exception.InvalidLoanException;

public interface ILoanRepository {
	void applyLoan(Loan loan) throws SQLException;

	double calculateInterest(String loanId) throws InvalidLoanException;

	void loanStatus(String loanId) throws InvalidLoanException;

	double calculateEMI(String loanId) throws InvalidLoanException;

	void loanRepayment(String loanId, double amount) throws InvalidLoanException;

	void getAllLoan();

	void getLoanById(String loanId) throws InvalidLoanException;
}
