package com.cpp.daoImol;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import com.cpp.dao.ElectorDao;
import com.cpp.modle.Elector;
import com.cpp.util.HibernateSessionFactory;

public class ElectorDaoImpl implements ElectorDao{

	@Test
	public List<Elector> checkAllElector() {
			try{
			System.out.println("ElectorDaoImpl----");
			Session session=HibernateSessionFactory.getSession();
			String sql="from Elector";
			Query query= session.createQuery(sql);
			List<Elector> lists= query.list();
			
			/*for(Elector e:lists){
				System.out.println(e.toString());
			}*/
			
			return lists;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HibernateSessionFactory.closeSession();
		}
		
		return null;
	
	}

}
