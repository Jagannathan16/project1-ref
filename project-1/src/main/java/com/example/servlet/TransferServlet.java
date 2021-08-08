package com.example.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.entity.Account;
import com.example.entity.History;
import com.example.entity.TransactionType;
import com.example.repo.TransferRepo;

@WebServlet(urlPatterns = {"/transfer"})
public class TransferServlet extends HttpServlet{
	
	TransferRepo transferRepo = new TransferRepo();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int fromAccountNo = Integer.parseInt(req.getParameter("from"));
		int toAccountNo = Integer.parseInt(req.getParameter("to"));
		double amount = Double.parseDouble(req.getParameter("amount"));
		
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
		
		System.out.println("transaction successfull");
		
		req.setAttribute("transfer-status", "Transaction successfull");
		req.getRequestDispatcher("transferStatus.jsp").forward(req, resp);
	}
}
