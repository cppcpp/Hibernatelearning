package com.cpp.daoImol;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.cpp.dao.UserDao;
import com.cpp.modle.User;
import com.cpp.util.HibernateSessionFactory;

public class UserDaoImpl implements UserDao{

	public List<User> checkAllUser() {
		try{
			
			Session session=HibernateSessionFactory.getSession();
			String sql="from user";
			Query query= session.createQuery(sql);
			List<User> lists= query.list();
			
			Iterator it=lists.iterator();
			while(it.hasNext()){
				System.out.println(it.next());
			}
			
			return lists;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HibernateSessionFactory.closeSession();
		}
		
		return null;
	}

}
