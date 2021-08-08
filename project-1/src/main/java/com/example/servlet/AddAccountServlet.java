package com.example.servlet;

import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.entity.Account;
import com.example.repo.AccountRepo;

@WebServlet(urlPatterns = {"/add-account"})
public class AddAccountServlet extends HttpServlet{
	
	AccountRepo jpa = new AccountRepo();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		int accountNo = Integer.parseInt(req.getParameter("accountNo"));
		String phoneNo = req.getParameter("phoneNo");
		double balance = Double.parseDouble(req.getParameter("deposit"));
		
		Account acc = new Account();
//		acc.setAccountNo(accountNo);
		acc.setPhoneNo(phoneNo);
		acc.setBalance(balance);
		
		jpa.saveAccount(acc);
		req.setAttribute("add-status", "Account added successfully");
		
		req.getRequestDispatcher("addAccountStatus.jsp").forward(req, resp);
//		resp.sendRedirect("index");
		
	}
	
}
