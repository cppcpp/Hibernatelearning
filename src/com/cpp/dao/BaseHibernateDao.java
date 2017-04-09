package com.cpp.dao;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cpp.util.HibernateSessionFactory;

public class BaseHibernateDao {
	
	//增加数据
	protected void add(Object obj){
		Transaction tran=null;
		Session session=HibernateSessionFactory.getSession();
		
		try{
			tran=session.getTransaction();
			session.save(obj);
			tran.commit();
		}catch(Exception e){
			//出现异常，事务回滚
			if(tran!=null){
				tran.rollback();
			}
		}finally{
			//关闭session
			HibernateSessionFactory.closeSession();
		}
	}
	
	//加载数据
	protected Object get(Class c,Serializable id){
		Object object=null;
		Session session=HibernateSessionFactory.getSession();
		
		try{
			object=session.get(c, id);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HibernateSessionFactory.closeSession();
		}
		
		return object;
	}
	
	//修改数据
	protected void update(Object object){
		Session session=HibernateSessionFactory.getSession();
		Transaction tran=null;
		
		try{
			tran=session.beginTransaction();
			session.update(object);
			tran.commit();
			
		}catch(Exception e){
			if(tran!=null){
				tran.rollback();
			}
		}finally{
			HibernateSessionFactory.closeSession();
		}
	}

	//删除数据
	protected void delete(Object object){
		Session session=HibernateSessionFactory.getSession();
		Transaction tran=null;
		try{
			tran=session.beginTransaction();
			session.delete(object);
			tran.commit();
			
		}catch(Exception e){
			if(tran!=null){
				tran.rollback();
			}
		}finally{
			HibernateSessionFactory.closeSession();
		}
	}
	
	
	
}
