package com.example.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.check.PhoneNoCheck;
import com.example.entity.History;
import com.example.exception.AccountNotFountException;
import com.example.exception.NullInputException;
import com.example.repo.HistoryRepo;

@WebServlet(urlPatterns = { "/transaction-history" })
public class TransactionHistoryServlet extends HttpServlet {

	HistoryRepo historyRepo = new HistoryRepo();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String AccountNoStr = req.getParameter("accountNo");
		String typeOfHistory = req.getParameter("choose");
		if(typeOfHistory.equals("default")) {
			typeOfHistory = "";
		}
		List<History> history = null;
		
		String status = null;
		PhoneNoCheck check = new PhoneNoCheck();
		
		try {
			check.nullCheck(AccountNoStr);
			check.nullCheck(typeOfHistory);
			int AccountNo = Integer.parseInt(AccountNoStr);
			check.transferAccountCheck(AccountNo);
			switch (typeOfHistory) {
			case "Top-10":
				history = historyRepo.getTop10History(AccountNo);
				break;
			case "last-some-month":
				int month = Integer.parseInt(req.getParameter("month"));
				req.setAttribute("month",month);
				history = historyRepo.getLastSomeMonthHistory(AccountNo, month);
				break;
			case "from-only" : 
				String fromOnly = req.getParameter("only-from");
				req.setAttribute("fromOnly",fromOnly);
				history = historyRepo.getFromOnlyHistory(AccountNo, fromOnly);
				break;
			case "from-to":
				String from = req.getParameter("from");
				String to = req.getParameter("to");
				req.setAttribute("from",from);
				req.setAttribute("to",to);
				history = historyRepo.getFromToDateHistory(AccountNo, from, to);
				break;
			}
			req.setAttribute("type", typeOfHistory);
			req.setAttribute("history-list", history);
			req.getRequestDispatcher("transactionHistory.jsp").forward(req, resp);
		} catch (AccountNotFountException e) {
			status = e.getMessage();
			req.setAttribute("transaction-history-status", status);
			req.getRequestDispatcher("transactionHistoryStatus.jsp").forward(req, resp);
		} catch (NullInputException e) {
			status = e.getMessage();
			req.setAttribute("transaction-history-status", status);
			req.getRequestDispatcher("transactionHistoryStatus.jsp").forward(req, resp);
		}
	}
}
