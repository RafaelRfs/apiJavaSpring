package com.siteapprfs.main.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class DatabaseConnection {
	
	Connection conn = null;                         
	private static final Logger logger = Logger.getLogger(DatabaseConnection.class);
	private static final String CONNECTION = "jdbc:mysql://localhost/siterfs?useUnicode=true&characterEncoding=utf-8&useTimezone=true&serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASS = "";
	private DataSource ds;
	private PreparedStatement ps;
	
	
	public DatabaseConnection(DataSource ds) {
		this.setDs(ds);
		this.getConn();
	}
	
	public DatabaseConnection() {
		this.getConn();
	}
	
	public PreparedStatement prepareStatement(String query) {
		try {
		if(getPs() == null) {
			return getConn().prepareStatement(query);
		}else {
			return getPs();
		}
		}catch(SQLException | NullPointerException e) {
			logger.error("Error prepareStatement >> "+e.toString());
			return null;
		}
	}
	
	public void setConn(Connection cn) {
		this.conn = cn;
	}
	
	public Connection getConn(){
	
	 if(this.getDs() ==  null) {
			
		if(this.conn == null) {
			try {	
			Properties prop = new Properties();
			prop.setProperty("user", USER);
			prop.setProperty("password", PASS);
			
			setConn( DriverManager.getConnection(CONNECTION, prop));
			}catch(SQLException e) {
				errorConn(e);
			}
			}
		}else {
			if(this.conn == null) {
				try {
					this.conn = this.getDs().getConnection();
				} catch (SQLException e) {
					errorConn(e);
				}
			}
		}	
		return conn;
	}
	
	
	public void closeConn(PreparedStatement ps) throws SQLException {
		if(ps != null) {
			ps.close();
		}
	}

	private void errorConn(Exception e) {
		logger.error("Error getConn >> "+e.getMessage());
	}
	
	public DataSource getDs() {
		return ds;
	}

	public void setDs(DataSource ds) {
		this.ds = ds;
	}
	
	public PreparedStatement getPs() {
		return ps;
	}

	public void setPs(PreparedStatement ps) {
		this.ps = ps;
	}

}
