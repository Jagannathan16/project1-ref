package com.example.check;

import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.example.entity.Account;
import com.example.exception.AccountNotFountException;
import com.example.exception.DuplicateAccountException;
import com.example.exception.FromToAccountEqualException;
import com.example.exception.InsufficientAmountException;
import com.example.exception.InvalidPhoneNo;
import com.example.exception.NullInputException;
import com.example.factory.Factory;

public class PhoneNoCheck {

	public void numberCheck(String phoneNo) throws InvalidPhoneNo {
		if(!Pattern.matches("[0-9]{10}", phoneNo)) {
			throw new InvalidPhoneNo("Enter valid phone number !!!");
		}
	}
	public void duplicateAccountCheck(String phoneNo) throws DuplicateAccountException {
		EntityManager em = Factory.emf().createEntityManager();
		em.getTransaction().begin();
		Query query = em.createQuery("select a from Account a where a.phoneNo = " + phoneNo);
	    List<Account> list = query.getResultList();
	    em.getTransaction().commit();
	    if(list.size() > 0) {
	    	throw new DuplicateAccountException("An account already created with this phone number !!!");
	    }
	    em.close();
	}
	public void transferAccountCheck(int accountNo) throws AccountNotFountException {
		EntityManager em = Factory.emf().createEntityManager();
		em.getTransaction().begin();
		Account account = em.find(Account.class, accountNo);
		em.getTransaction().commit();
	    if(account == null) {
	    	throw new AccountNotFountException("Account not found with Account number " + accountNo);
	    }
	    em.close();
	}
	public void balanceCheck(int accountNo, double amount) throws InsufficientAmountException {
		EntityManager em = Factory.emf().createEntityManager();
		em.getTransaction().begin();
		Account account = em.find(Account.class, accountNo);
		double balance = account.getBalance();
		em.getTransaction().commit();
	    if(balance<amount) {
	    	throw new InsufficientAmountException("\"INSUFFICIENT BALANCE\" your balance = " + balance);
	    }
	    em.close();
	}
	public void fromToEqualCheck(int from,int to) throws FromToAccountEqualException {
		if(from == to) {
			throw new FromToAccountEqualException("From and To account should not be equal !!!");
		}
	}
	public void nullCheck(String input) throws NullInputException {
		if(input.equals("")) {
			throw new NullInputException("Input should not be NULL");
		}
	}
}
