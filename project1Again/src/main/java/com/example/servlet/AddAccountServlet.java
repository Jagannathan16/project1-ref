package com.example.servlet;

import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.check.PhoneNoCheck;
import com.example.entity.Account;
import com.example.exception.DuplicateAccountException;
import com.example.exception.InvalidPhoneNo;
import com.example.exception.NullInputException;
import com.example.repo.AccountRepo;

@WebServlet(urlPatterns = {"/add-account"})
public class AddAccountServlet extends HttpServlet{
	
	AccountRepo jpa = new AccountRepo();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String phoneNo = req.getParameter("phoneNo");
		String depositStr = req.getParameter("deposit");
		System.out.println("string = "+depositStr);
		
		
		String status = null;
		PhoneNoCheck phoneCheck = new PhoneNoCheck();
		try {
			phoneCheck.nullCheck(phoneNo);
			phoneCheck.nullCheck(depositStr);
			phoneCheck.numberCheck(phoneNo);
			phoneCheck.duplicateAccountCheck(phoneNo);
			
			double balance = Double.parseDouble(depositStr);
			Account acc = new Account();
			acc.setPhoneNo(phoneNo);
			acc.setBalance(balance);
			jpa.saveAccount(acc);
		    status = "Account added successfully";
		} catch (InvalidPhoneNo e) {
			status = e.getMessage();
		} catch (DuplicateAccountException e) {
			status = e.getMessage();
		} catch (NullInputException e) {
			status = e.getMessage();
		}
		
		req.setAttribute("add-status", status);
		req.getRequestDispatcher("addAccountStatus.jsp").forward(req, resp);
		
	}
	
}
