package com.ez.test.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.File;


/** 
 * <pre>
 * Hibernate测试基类
 * </pre>
 *  
 * @author 李贵庆2015年10月14日
 * @version 1.0
 **/
public abstract class AbstractHibernateTest extends AbstractRepositoryTest {
	protected SessionFactory sessionFactory;
	
	protected Session session;
	
	protected void init(String hbmFileRealtivePath) {
		String path = this.getClass().getResource("/").getPath();
		File f = new File(path);
		File[] fs = f.getParentFile().listFiles();
		for(File file:fs) {
			if(file.getName().equals("classes")) {
				String[] hbmFiles =getHbmFiles(file.getAbsolutePath()+"/"+hbmFileRealtivePath,hbmFileRealtivePath +"/");
				try {
					initHibernate(hbmFiles);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	protected String[] getHbmFiles(String path,String prefix) {
		File file = new File(path);

		String[]  fileNames = null;
		if(file.isDirectory()) {
			String[] hbmFileNames = file.list();
			fileNames = new String[hbmFileNames.length];
			for(int i = 0;i < hbmFileNames.length;i++) {
				fileNames[i] = prefix + hbmFileNames[i];
			}
		}
		
		return fileNames == null?new String[0]:fileNames;
	}
	
	protected void initHibernate(String... hbmFiles) throws Exception{
		Configuration conf = new Configuration();
		conf.configure("/hibernate.cfg.xml");
		
		//if(hbmFiles == null)
		//	throw new IllegalArgumentException();
		if(hbmFiles != null && hbmFiles.length >0) {		
			for(String file : hbmFiles) {
				conf.addResource(file);
		    }
		}
		
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
		sessionFactory = conf.buildSessionFactory(serviceRegistry);
		session = sessionFactory.openSession();
//		/session = sessionFactory.getCurrentSession();
	}
	
	protected void release()throws Exception{
		session.clear();
		session.close();
	}
	
	protected Session getSession(){
		if(this.session == null || !this.session.isOpen()){
			this.session = this.sessionFactory.openSession();
		}
		return this.session;
	}
	
	protected void beginTransaction(){
		this.getSession().beginTransaction();
	}
	
	protected void commit(){
		this.session.getTransaction().commit();
	}
	
	protected void rollback(){
		this.session.getTransaction().rollback();
	}
	
	protected void saveOrUpdate(Object o){
		this.session.save(o);
	}
	
	protected void clear(Object o){
		//this.session = this.sessionFactory.openSession();
		this.session.getTransaction().begin();
		this.session.delete(o);
		this.session.getTransaction().commit();
	}
}
