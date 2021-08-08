package com.example.repo;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.example.entity.*;
import com.example.factory.Factory;
public class HistoryRepo {

	public List<History> getTop10History(int accountNo){
		
		EntityManager em = Factory.emf().createEntityManager();
		em.getTransaction().begin();
		Query query = em.createQuery("select t from History t where t.fromAccountNo = '"+accountNo+"'" +" order by id desc");
		query.setMaxResults(10);
		List<History> list = query.getResultList();
		return list;
	}
	
	public List<History> getLastSomeMonthHistory(int accountNo,int month){
		EntityManager em = Factory.emf().createEntityManager();
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -3);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date beforeMonth = calendar.getTime();
		String newDate = sdf.format(beforeMonth);
		Query query = em.createQuery("select h from History h where h.fromAccountNo = " + accountNo + " and h.date >= '"+ newDate + "'"+"order by date");
		List<History> list = query.getResultList();
		return list;
	}
	public List<History> getFromToDateHistory (int accountNo, String from, String to){
		EntityManager em = Factory.emf().createEntityManager();
		Query query = em.createQuery("select h from History h where h.fromAccountNo = '"+ accountNo + "' and h.date between '"+ from +"' and '"+ to + "'order by date");
		List<History> list = query.getResultList();
		return list;
	}
		
		
}
