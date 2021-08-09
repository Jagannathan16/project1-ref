package com.example.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.check.PhoneNoCheck;
import com.example.entity.Account;
import com.example.entity.History;
import com.example.entity.TransactionType;
import com.example.exception.AccountNotFountException;
import com.example.exception.FromToAccountEqualException;
import com.example.exception.InsufficientAmountException;
import com.example.exception.NullInputException;
import com.example.repo.TransferRepo;

@WebServlet(urlPatterns = {"/transfer"})
public class TransferServlet extends HttpServlet{
	
	TransferRepo transferRepo = new TransferRepo();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fromAccountNoStr = req.getParameter("from");
		String toAccountNoStr = req.getParameter("to");
		String amountStr = req.getParameter("amount");
		
		String status = null;
		PhoneNoCheck phoneCheck = new PhoneNoCheck();
		try {
			phoneCheck.nullCheck(fromAccountNoStr);
			phoneCheck.nullCheck(toAccountNoStr);
			phoneCheck.nullCheck(amountStr);
			int fromAccountNo = Integer.parseInt(fromAccountNoStr);
			int toAccountNo = Integer.parseInt(toAccountNoStr);
			double amount = Double.parseDouble(amountStr);
			phoneCheck.transferAccountCheck(fromAccountNo);
			phoneCheck.transferAccountCheck(toAccountNo);
			phoneCheck.balanceCheck(fromAccountNo, amount);
			phoneCheck.fromToEqualCheck(fromAccountNo, toAccountNo);
			Account fromAccount = transferRepo.loadAccount(fromAccountNo);
			Account toAccount = transferRepo.loadAccount(toAccountNo);
			
			fromAccount.setBalance(fromAccount.getBalance()-amount);
			toAccount.setBalance(toAccount.getBalance()+amount);
			
			
			transferRepo.updateAccount(fromAccount);
			transferRepo.updateAccount(toAccount);
			
			History debitHistory = new History(fromAccountNo, toAccountNo, amount, TransactionType.DEBIT, new Date(),fromAccount.getBalance());
			History creditHistory = new History(toAccountNo, fromAccountNo, amount, TransactionType.CREDIT, new Date(),toAccount.getBalance());
			
			transferRepo.createHistory(debitHistory);
			transferRepo.createHistory(creditHistory);
			
			status = "Transaction successfull";
		} catch (AccountNotFountException e) {
			status = e.getMessage();
		} catch (InsufficientAmountException e) {
			status = e.getMessage();
		} catch (FromToAccountEqualException e) {
			status = e.getMessage();
		} catch (NullInputException e) {
			status = e.getMessage();
		}
		
		
		
		req.setAttribute("transfer-status", status);
		req.getRequestDispatcher("transferStatus.jsp").forward(req, resp);
	}
}
