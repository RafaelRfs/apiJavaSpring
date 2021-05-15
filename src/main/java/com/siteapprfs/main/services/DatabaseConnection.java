package com.siteapprfs.main.services;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseConnection {
	private Connection conn = null;
	private String url;
	private String user;
	private String pass;
	private DataSource ds;
	private PreparedStatement ps;
	private static final Logger logger = LoggerFactory.getLogger(AppServices.class);

	public DatabaseConnection(DataSource ds) {
		this.setDs(ds);
		this.getConn();
	}

	public DatabaseConnection() {
		this.getConn();
	}

	public PreparedStatement prepareStatement(String query) {
		try {
			if (getPs() == null) {
				return getConn().prepareStatement(query);
			} else {
				return getPs();
			}
		} catch (SQLException | NullPointerException e) {
			logger.error("Error prepareStatement >> ", e);
			return null;
		}
	}

	public void setConn(Connection cn) {
		this.conn = cn;
	}

	public Connection getConn() {
		try {
			if(this.conn == null) {
			Properties props = new Properties();	
			if(System.getenv("DATABASE_URL2") != null) {	
			URI dbUri = new URI(System.getenv("DATABASE_URL"));
			user = dbUri.getUserInfo().split(":")[0];
			pass = dbUri.getUserInfo().split(":")[1];
			url = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
			}else {
				url = "jdbc:mysql://127.0.0.1:3306/testSystems?useUnicode=true&characterEncoding=utf-8&useTimezone=true&serverTimezone=UTC";
				user = "root";
				pass =  "1234";
			}
			props.setProperty("user", user);
			props.setProperty("password", pass);
			setConn(DriverManager.getConnection(url, props));
			}

		} catch (Exception e) {
			errorConn(e);
		}

		return conn;
	}

	public void closeConn(PreparedStatement ps) throws SQLException {
		if (ps != null) {
			ps.close();
		}
	}

	private void errorConn(Exception e) {
		logger.error("Error getConn >> ", e);
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
