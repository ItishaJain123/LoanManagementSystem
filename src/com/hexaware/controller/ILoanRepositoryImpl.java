package com.hexaware.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.hexaware.dao.ILoanRepository;
import com.hexaware.entity.CarLoan;
import com.hexaware.entity.HomeLoan;
import com.hexaware.entity.Loan;
import com.hexaware.exception.InvalidLoanException;
import com.hexaware.util.DBUtil;

public class ILoanRepositoryImpl implements ILoanRepository {

	DBUtil db = new DBUtil();
	Connection con = db.getConnection();
	Scanner sc = new Scanner(System.in);

	@Override
	public void applyLoan(Loan loan) throws SQLException {
		PreparedStatement ps = con.prepareStatement("INSERT INTO Loan VALUE(?,?,?,?,?,?,?)");

		ps.setInt(1, loan.getLoanId());
		ps.setInt(2, loan.getCustomer().getCustomerId());
		ps.setInt(3, loan.getPrincipalAmount());
		ps.setInt(4, loan.getInterestRate());
		ps.setInt(5, loan.getLoanTerm());
		ps.setString(6, loan.getLoanType());
		ps.setString(7, loan.getLoanStatus());

		int c = ps.executeUpdate();
		System.out.println(c + " record updated");
		System.out.println("your loan is " + loan.getLoanStatus());

		if (loan.getLoanType() == "homeLoan") {
			System.out.println("Enter the property address: ");
			String address = sc.nextLine();

			System.out.println("Enter the property value");
			int propVal = sc.nextInt();

			int homeLoanId = 0;

			try {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM HomeLoan ORDER BY HomeLoanId DESC LIMIT 1");

				while (rs.next()) {
					homeLoanId = rs.getInt(1) + 1;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.err.println("cant get the homeLoan details for this for last customer id");
			}

			HomeLoan h1 = new HomeLoan(homeLoanId, loan.getLoanId(), loan.getCustomer(), loan.getPrincipalAmount(),
					loan.getInterestRate(), loan.getLoanTerm(), loan.getLoanType(), loan.getLoanStatus(), address,
					propVal);
			PreparedStatement ps1 = con.prepareStatement("INSERT INTO HomeLoan VALUE(?,?,?,?)");

			ps1.setInt(1, h1.getHomeLoanId());
			ps1.setInt(2, h1.getLoanId());
			ps1.setString(3, h1.getPropertyAddress());
			ps1.setInt(4, h1.getPropertyValue());

			int c1 = ps1.executeUpdate();
			System.out.println(c1 + " record updated");
		} else if (loan.getLoanType() == "carLoan") {
			System.out.println("Enter the car modle: ");
			String modle = sc.next();

			System.out.println("Enter the car value");
			int carVal = sc.nextInt();

			int carLoanId = 0;

			try {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM carLoan ORDER BY carLoanId DESC LIMIT 1");

				while (rs.next()) {
					carLoanId = rs.getInt(1) + 1;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.err.println("cant get the carLoan details for this for last customer id");
			}

			CarLoan h2 = new CarLoan(carLoanId, loan.getLoanId(), loan.getCustomer(), loan.getPrincipalAmount(),
					loan.getInterestRate(), loan.getLoanTerm(), loan.getLoanType(), loan.getLoanStatus(), modle,
					carVal);
			PreparedStatement ps2 = con.prepareStatement("INSERT INTO carLoan VALUE(?,?,?,?)");

			ps2.setInt(1, h2.getCarLoanId());
			ps2.setInt(2, h2.getLoanId());
			ps2.setString(3, h2.getCarModel());
			ps2.setInt(4, h2.getCarValue());

			int c2 = ps2.executeUpdate();
			System.out.println(c2 + " record updated");
		}

	}

	@Override
	public double calculateInterest(String loanId) throws InvalidLoanException {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Loan WHERE loanID =" + loanId);

			if (rs.next()) {
				int principalAmount = rs.getInt("principalAmount");
				int interestRate = rs.getInt("interestRate");
				int loanTerm = rs.getInt("loanTerm");

				return (principalAmount * interestRate * loanTerm) / 12.0;
			} else {
				throw new InvalidLoanException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Can't get the loan details");
		}

		return 0;
	}

	@Override
	public void loanStatus(String loanId) throws InvalidLoanException {
		int creditScore = 0;
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT creditScore FROM Customer WHERE customerID = "
					+ "( SELECT customerID FROM Loan WHERE loanID =  " + loanId + ")");

			if (rs.next()) {
				// If the result set is not empty, retrieve the credit score
				creditScore = rs.getInt(1);
				if (creditScore > 650)
					System.out.println("Loan approved as your credit score is more than 650");
				else
					System.out.println("Loan not approved as your credit score is less than or equal to 650");
			} else {
				// If the result set is empty, loan ID not found
				System.out.println("Loan ID not found in the table.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("can't get the customer table details");
		}
	}

	@Override
	public double calculateEMI(String loanId) throws InvalidLoanException {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM loan WHERE loanID=" + loanId);

			if (!rs.next()) {
				throw new InvalidLoanException();
			}

			int principalAmount = rs.getInt("principalAmount");
			int interestRate = rs.getInt("interestRate");
			double monthlyInterestRate = (double) interestRate / 12 / 100; // Convert annual interest rate to monthly
																			// decimal rate
			int loanTerm = rs.getInt("loanTerm");

			double emi = (principalAmount * monthlyInterestRate * Math.pow((1 + monthlyInterestRate), loanTerm))
					/ (Math.pow((1 + monthlyInterestRate), loanTerm) - 1);

			return emi;
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Unable to get loan details from the database.");
		}

		return 0;
	}

	@Override
	public void loanRepayment(String loanId, double amount) throws InvalidLoanException {

		double emi = calculateEMI(loanId);
		if (emi > amount)
			System.out.println("Amount is too less please increase the amount");
		else {
			double noOfEmi = amount / emi;
			System.out.println("With this amount you can pay " + noOfEmi + " months of emi");
		}
	}

	@Override
	public void getAllLoan() {
		// TODO Auto-generated method stub
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT c.name, l.loanID, l.customerID, l.principalAmount, "
							+ "l.interestRate, l.loanTerm, l.loanType, l.loanStatus "
							+ "FROM Loan l LEFT JOIN Customer c ON l.customerID = c.customerID; ");

			while (rs.next()) {

				System.out.println("name: " + rs.getString(1));
				System.out.println("loanId " + rs.getInt(2));
				System.out.println("customerId " + rs.getInt(3));
				System.out.println("principalAmount: " + rs.getInt(4));
				System.out.println("interestRate: " + rs.getInt(5));
				System.out.println("loanTerm: " + rs.getInt(6));
				System.out.println("loanType: " + rs.getString(7));
				System.out.println("loanStatus: " + rs.getString(8));
				System.out.println("\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("cant get the customer table details");
		}

	}

	@Override
	public void getLoanById(String loanId) throws InvalidLoanException {
		// TODO Auto-generated method stub

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from loan where loanID =" + loanId);

			while (rs.next()) {

				System.out.println("loanID: " + rs.getInt(1));
				System.out.println("customerID " + rs.getInt(2));
				System.out.println("principalAmount " + rs.getInt(3));
				System.out.println("interestRate: " + rs.getInt(4));
				System.out.println("loanTerm: " + rs.getInt(5));
				System.out.println("loanType: " + rs.getString(6));
				System.out.println("loanStatus: " + rs.getString(7));
				System.out.println("\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("cant get the customer table details");
		}

	}

}
