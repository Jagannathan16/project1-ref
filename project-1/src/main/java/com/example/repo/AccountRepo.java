package com.example.repo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.example.entity.Account;
import com.example.factory.Factory;

public class AccountRepo {

	public void saveAccount(Account account) {
		EntityManager em = Factory.emf().createEntityManager();
		em.getTransaction().begin();
		em.persist(account);
		em.getTransaction().commit();
		em.close();
	}
}
