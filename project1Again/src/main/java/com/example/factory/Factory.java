package com.example.factory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Factory {

	private static EntityManagerFactory emf = null;
	static {
	    emf = Persistence.createEntityManagerFactory("my-pu");
	}
	public static EntityManagerFactory emf() {
		return emf;
	}
}
