package com.example.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.entity.History;
import com.example.repo.HistoryRepo;

@WebServlet(urlPatterns = {"/transaction-history"})
public class TransactionHistoryServlet extends HttpServlet{

	HistoryRepo historyRepo = new HistoryRepo();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int AccountNo = Integer.parseInt(req.getParameter("accountNo"));
		String typeOfHistory = req.getParameter("choose");
		System.out.println("type = "+typeOfHistory);
		if(typeOfHistory.equals("Top-10")) {
			List<History> history = historyRepo.getTop10History(AccountNo);
			req.setAttribute("history-list", history);
			req.getRequestDispatcher("transactionHistory.jsp").forward(req, resp);
		}
		if(typeOfHistory.equals("last-some-month")) {
			int month = Integer.parseInt(req.getParameter("month"));
			List<History> history = historyRepo.getLastSomeMonthHistory(AccountNo, month);
			req.setAttribute("history-list", history);
			req.getRequestDispatcher("transactionHistory.jsp").forward(req, resp);
		}
		if(typeOfHistory.equals("from-to")) {
			String from = req.getParameter("from");
			String to = req.getParameter("to");
			List<History> history = historyRepo.getFromToDateHistory(AccountNo, from, to);
			req.setAttribute("history-list", history);
			req.getRequestDispatcher("transactionHistory.jsp").forward(req, resp);
			
		}
		
		
		
		
	}
}
