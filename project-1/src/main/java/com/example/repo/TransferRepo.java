package com.example.repo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.example.entity.Account;
import com.example.entity.History;
import com.example.factory.Factory;

public class TransferRepo {
	
	public Account loadAccount(int accountNo) {
		
		EntityManager em = Factory.emf().createEntityManager();
		em.getTransaction().begin();
		Account account = em.find(Account.class, accountNo);
		em.getTransaction().commit();
		em.close();
		return account;
	}
	
	public void updateAccount(Account account) {
		
		EntityManager em = Factory.emf().createEntityManager();
		em.getTransaction().begin();
		em.merge(account);
		em.getTransaction().commit();
		em.close();
	}
	public void createHistory(History history) {
		
		EntityManager em = Factory.emf().createEntityManager();
		em.getTransaction().begin();
		em.persist(history);
		em.getTransaction().commit();
		em.close();
	}
}
