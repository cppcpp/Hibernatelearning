package com.cpp.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

	public class HibernateSessionFactory {
		
    //�õ�hibernate4
	//����session���󣬾Ϳ������������ķ�ʽ���棬��ȡ�����º�ɾ��
	//session�����ɹ������opensession�������
   
	//ָ��hibernate�����ļ�·��
    private static String CONFIG_FILE_LOCATION = "/hibernate.cfg.xml";
	//����ThreadLocal����
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	//����Configuration����
	private static Configuration configuration = new Configuration();
	//����SessionFactory����
    private static org.hibernate.SessionFactory sessionFactory;
	//����configFile���Բ���ֵ
    private static String configFile = CONFIG_FILE_LOCATION;

	static {
    	try {
    		//��ȡ�����ļ�hibernate.cfg.xml
			configuration.configure();
			//����һ��ע�������
			ServiceRegistry serviceRegistry=new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
			//ʹ��ע�������ServiceRegistry����һ��SessionFactory
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch (HibernateException e) {
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
    }
	
	//�����޲ε�HibernateSessionFactory���췽��
    private HibernateSessionFactory() {
    }
	
    
    //���SessionFactory����
   
	public static org.hibernate.SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	//�ؽ�SessionFactory
	
	public static void rebuildSessionFactory() {
		synchronized (sessionFactory) {
			try {
				configuration.configure(configFile);
				//hibernate4��Ҫ�õ�ע�����������
				//����һ��ע�������
				ServiceRegistry serviceRegistry=new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
				//ʹ��ע�������ServiceRegistry����һ��SessionFactory
				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			} catch (HibernateException e) {
				System.err.println("%%%% Error Creating SessionFactory %%%%");
				e.printStackTrace();
			}
		}
		
		
	}
	
	
	
	//���session����
    public static Session getSession() throws HibernateException {
    	//���threadLocal��������session����
        Session session = (Session) threadLocal.get();
        try {
        	//�ж�session�����Ƿ��Ѿ����ڻ��ߴ�
        	if (session == null || !session.isOpen()) {
        		//���session����Ϊ�ջ�δ�򿪣����ж�SessionFactory
    			if (sessionFactory == null) {
    				//���Ϊ�գ����ؽ�
    				rebuildSessionFactory();
    			}
    			//��Ϊ�գ����session
    			session = (sessionFactory != null) ? sessionFactory.openSession()
    					: null;
    			threadLocal.set(session);
    		}
		} catch (HibernateException e) {
			e.printStackTrace();
		}
        return session;
    }

	
    //�ر�session����
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
    //configFile���Ե�set����
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
