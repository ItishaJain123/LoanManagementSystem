package com.hexaware.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.hexaware.controller.ILoanRepositoryImpl;
import com.hexaware.dao.ILoanRepository;
import com.hexaware.entity.Customer;
import com.hexaware.entity.Loan;
import com.hexaware.exception.InvalidLoanException;
import com.hexaware.util.DBUtil;

public class LoanManagement {

    public static void main(String[] args) {
		// Initializing loan service provider and scanner
        ILoanRepository loanServiceProvider = new ILoanRepositoryImpl();
        Scanner scanner = new Scanner(System.in);
        DBUtil db = new DBUtil();
        Connection con = DBUtil.getConnection();
        int choice;
		Scanner scan = new Scanner(System.in);
		String input = null;
        do {
			// Displaying the menu
            System.out.println("===============================================================");
            System.out.println("===============================================================");
            System.out.println("====================== LOAN MANAGEMENT SYSTEM =================");
            System.out.println("=======================  Coding Challenge =====================");
            System.out.println("===============================================================");
            System.out.println("===============================================================");

			System.out.println("1. Calculate Interest");
			System.out.println("2. Loan Status");
			System.out.println("3. Calculate EMI");
			System.out.println("4. Loan Repayment");
			System.out.println("5. Get All Loans");
			System.out.println("6. Get Loan by ID");
			System.out.println("7. Apply Loan");
            System.out.println("8. Exit");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
			case 1:
				// Calculating interest for a loan
				System.out.print("Enter loan ID: ");
				String loanId2 = scanner.nextLine();
				double ans = 0;
				try {
					ans = loanServiceProvider.calculateInterest(loanId2);
				} catch (InvalidLoanException e) {
					e.printStackTrace();
				}
				System.out.println("Your interest is " + ans);
				break;

			case 2:
				// Checking loan status
				System.out.print("Enter loan ID: ");
				String loanId3 = scanner.nextLine();
				try {
					loanServiceProvider.loanStatus(loanId3);
				} catch (InvalidLoanException e) {
					e.printStackTrace();
				}
				break;

			case 3:
				// Calculating EMI for a loan
				System.out.print("Enter loan ID: ");
				String loanId4 = scanner.nextLine();
				double ans1 = 0;
				try {
					ans1 = loanServiceProvider.calculateEMI(loanId4);
				} catch (InvalidLoanException e) {
					e.printStackTrace();
				}
				System.out.println("Your EMI is " + ans1);
				break;

			case 4:
				// Repaying loan
				System.out.print("Enter loan ID: ");
				String loanId5 = scanner.nextLine();
				System.out.println("Enter the amount you want to repay");
				double amount = scanner.nextDouble();
				scanner.nextLine();
				try {
					loanServiceProvider.loanRepayment(loanId5, amount);
				} catch (InvalidLoanException e) {
					e.printStackTrace();
				}
				break;

			case 5:
				// Getting all loans
				loanServiceProvider.getAllLoan();
				break;

			case 6:
				// Getting loan by ID
				System.out.print("Enter loan ID: ");
				String loanId1 = scanner.nextLine();
				try {
					loanServiceProvider.getLoanById(loanId1);
				} catch (InvalidLoanException e) {
					System.err.println(e.getMessage());
				}
				break;

			case 7:
					// Applying for a loan
                    System.out.println("Are you an existing customer?");
                    System.out.println("1. Yes ");
                    System.out.println("2. No ");
                    int choice1 = scanner.nextInt();
                    scanner.nextLine();
                    Customer customer = new Customer();

                    if (choice1 == 1) {
						System.out.print("Please enter your customerID ");
                        int customerId = scanner.nextInt();

						// Fetching existing customer details
                        String name = "", email = "", phoneNo = "", address = "";
                        int creditScore = 0;
                        try {
                            Statement stmt = con.createStatement();
							ResultSet rs = stmt.executeQuery("SELECT * FROM Customer WHERE customerID = " + customerId);

                            while (rs.next()) {
                                customerId = rs.getInt(1);
                                name = rs.getString(2);
                                email = rs.getString(3);
                                phoneNo = rs.getString(4);
                                address = rs.getString(5);
                                creditScore = rs.getInt(6);
                            }

                        } catch (SQLException e) {
                            e.printStackTrace();
                            System.err.println("can't get the customer details for this id");
                        }
                        Customer c = new Customer(customerId, name, email, phoneNo, address, creditScore);
                        customer = c;


                    } else if (choice1 == 2) {
						// Creating a new customer account
                        System.out.println("before applying for a loan, you have to create an account. Let's create it for you...");

                        int customerId = 0;

                        try {
                            Statement stmt = con.createStatement();
							ResultSet rs = stmt.executeQuery("SELECT * FROM Customer ORDER BY customerID DESC LIMIT 1");

                            while (rs.next()) {
                                customerId = rs.getInt(1) + 1;
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                            System.err.println("can't get the customer table details");
                        }

                        System.out.println("Please enter your name: ");
                        String name = scanner.nextLine();

                        System.out.println("Please enter your Email: ");
                        String email = scanner.nextLine();

                        System.out.println("please enter your phone number: ");
                        String phoneNo = scanner.nextLine();

                        System.out.println("please enter your address");
                        String address = scanner.nextLine();

                        System.out.println("Please enter your credit score");
                        int creditScore = scanner.nextInt();

                        Customer c = new Customer(customerId, name, email, phoneNo, address, creditScore);
                        customer = c;

						// Inserting new customer details into the database
                        PreparedStatement ps;
                        try {
							ps = con.prepareStatement("INSERT INTO Customer VALUES(?,?,?,?,?,?)");
                            ps.setInt(1, customerId);
                            ps.setString(2, name);
                            ps.setString(3, email);
                            ps.setString(4, phoneNo);
                            ps.setString(5, address);
                            ps.setInt(6, creditScore);
                            int ct = ps.executeUpdate();
                            System.out.println(ct + " record updated");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        System.out.println("You have been added as a customer in our bank");

                    } else {
                        System.out.println("you typed the wrong input");
                        break;
                    }

					// Taking loan details from the user
                    System.out.print("Please enter your principal amount ");
                    int principal = scanner.nextInt();

                    System.out.println("please select your loan type: ");
                    System.out.println("1. Home Loan (interest rate - 9%)");
                    System.out.println("2. Car Loan (interest rate - 12%)");

                    int choice2 = scanner.nextInt();

                    String loanType = "";
                    int interest = 0;

                    if (choice2 == 1) {
                        loanType = "homeLoan";
                        interest = 9;
                    } else if (choice2 == 2) {
                        loanType = "carLoan";
                        interest = 12;
                    }
                    System.out.println("Enter loan term in months");
                    int loanTerm = scanner.nextInt();
                    String loanStatus = (customer.getCreditScore() > 650) ? "approved" : "pending";
                    int loanId = 0;

                    try {
                        Statement stmt = con.createStatement();
						ResultSet rs = stmt.executeQuery("SELECT * FROM Loan ORDER BY loanID DESC LIMIT 1");

                        while (rs.next()) {
                            loanId = rs.getInt(1) + 1;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.err.println("can't get the customer details for this for the last customer id");
                    }

                    Loan loan = new Loan(loanId, customer, principal, interest, loanTerm, loanType, loanStatus);

					// Applying for the loan
                    try {
                        loanServiceProvider.applyLoan(loan);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;

                case 8:
					// Exiting the loan management system
                    System.out.println("Exiting Loan Management System. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }

			System.out.println("To Continue - Press 'C' | 'c'");
			input = scan.next();

		} while (input.equals("c") || input.equals("C"));

		System.out.println("===============================================================");
		System.out.println("===============================================================");
		System.out.println("================== THANKS FOR USING OUR SYSTEM=================");
		System.out.println("===============================================================");
		System.out.println("===============================================================");

        scanner.close();
    }
}
