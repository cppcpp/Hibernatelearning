package com.cpp.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

	public class HibernateSessionFactory {
		
    //用的hibernate4
	//有了session对象，就可以以面向对象的方式保存，获取，更新和删除
	//session对象由工厂类的opensession方法获得
   
	//指定hibernate配置文件路径
    private static String CONFIG_FILE_LOCATION = "/hibernate.cfg.xml";
	//创建ThreadLocal对象
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	//创建Configuration对象
	private static Configuration configuration = new Configuration();
	//创建SessionFactory对象
    private static org.hibernate.SessionFactory sessionFactory;
	//定义configFile属性并赋值
    private static String configFile = CONFIG_FILE_LOCATION;

	static {
    	try {
    		//读取配置文件hibernate.cfg.xml
			configuration.configure();
			//生成一个注册机对象
			ServiceRegistry serviceRegistry=new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
			//使用注册机对象ServiceRegistry创建一个SessionFactory
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch (HibernateException e) {
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
    }
	
	//创建无参的HibernateSessionFactory构造方法
    private HibernateSessionFactory() {
    }
	
    
    //获得SessionFactory对象
   
	public static org.hibernate.SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	//重建SessionFactory
	
	public static void rebuildSessionFactory() {
		synchronized (sessionFactory) {
			try {
				configuration.configure(configFile);
				//hibernate4需要用到注册机创建工厂
				//生成一个注册机对象
				ServiceRegistry serviceRegistry=new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
				//使用注册机对象ServiceRegistry创建一个SessionFactory
				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			} catch (HibernateException e) {
				System.err.println("%%%% Error Creating SessionFactory %%%%");
				e.printStackTrace();
			}
		}
		
		
	}
	
	
	
	//获得session对象
    public static Session getSession() throws HibernateException {
    	//获得threadLocal对象管理的session对象
        Session session = (Session) threadLocal.get();
        try {
        	//判断session对象是否已经存在或者打开
        	if (session == null || !session.isOpen()) {
        		//如果session对象为空或未打开，再判断SessionFactory
    			if (sessionFactory == null) {
    				//如果为空，则重建
    				rebuildSessionFactory();
    			}
    			//不为空，则打开session
    			session = (sessionFactory != null) ? sessionFactory.openSession()
    					: null;
    			threadLocal.set(session);
    		}
		} catch (HibernateException e) {
			e.printStackTrace();
		}
        return session;
    }

	
    //关闭session对象
	/**
     *  Close the single hibernate session instance.
     *
     *  @throws HibernateException
     */
    public static void closeSession() throws HibernateException {
        Session session = (Session) threadLocal.get();
        threadLocal.set(null);
        try {
        	 if (session != null&&session.isOpen()) {
                 session.close();
             }
		} catch (HibernateException e) {
			e.printStackTrace();
		}
       
    }

	

	/**
     *  return session factory
     *
     *	session factory will be rebuilded in the next call
     */
    //configFile属性的set方法
	public static void setConfigFile(String configFile) {
		HibernateSessionFactory.configFile = configFile;
		sessionFactory = null;
	}
	/**
     *  return hibernate configuration
     *
     */
	public static Configuration getConfiguration() {
		return configuration;
	}

}
