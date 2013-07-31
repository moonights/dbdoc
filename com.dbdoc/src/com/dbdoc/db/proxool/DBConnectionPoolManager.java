package com.dbdoc.db.proxool;

import java.sql.*;
import java.util.logging.Logger;
import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;
import java.io.*;
/****
 * jdbc下的proxool连接池类实现 jdbc连接交由proxool进行管理
 * @author moonights
 * @version 1.0
 * @file DBConnectionPoolManager.java
 * @date Jun 24, 2011
 * @time 5:05:53 PM
 * @TODO TODO 
 */
public class DBConnectionPoolManager {  
	
	private static Logger log = Logger.getAnonymousLogger(); 
	private static DBConnectionPoolManager dbcpm = null;
    private Connection con = null;
    
    /***************************************************************************
	 * 构造函数
	 * 
	 * @return DBConnectionPoolManager
	 */
	private DBConnectionPoolManager() {
		log.info(getClass().getResource("/").getPath());
		InputStream is = getClass().getResourceAsStream("/jdbc_proxool.xml");
		try {
			JAXPConfigurator.configure(new InputStreamReader(is), false);
			log.info("<<<<<<proxool.xml配置文件加载成功!>>>>>>");
		} catch (Exception e) {
			log.info("<<<<<<proxool.xml配置文件加载失败!>>>>>>");
		} finally {
			try {
				is.close();
			} catch (Exception ex) {
			}
		}
	}
    
    /***************************************************************************
	 * 获取实例 单态
	 * 
	 * @return DBConnectionPoolManager
	 */
    public static synchronized DBConnectionPoolManager getInstance(){
	   if(null==dbcpm)
	        dbcpm =new DBConnectionPoolManager();
	    return dbcpm;
	}
    
    /***
     * 获取链接
     * @return
     */
    public Connection getConnection(){
        try{ 
        	con = DriverManager.getConnection("proxool.gxgljsyh");
        }catch(Exception e){
        	log.info("-<<<<<<数据库链接失败,请检查proxool.xml文件配置是否正确!>>>>>>");
         	log.info(e.getMessage());
        }
        return con;
    }
  
    public static void main(String[] args){
    	try{
    		if (DBConnectionPoolManager.getInstance().getConnection()!=null){
    			log.info("- <<<<<<链接成功！>>>>>>");
        	}else{
        		log.info("-<<<<<<链接失败！>>>>>>");
        	}
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    	
    }
} 

