package com.plmm.generate.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBUtil {
	static Logger logger = LoggerFactory.getLogger(DBUtil.class);

	public static Connection getConnection() {
		Connection con = null;
		try {
			String driverClass = ConfigReader.getInstance().getProp(
					"driverClass");
			Class.forName(driverClass);
//			String conType = ConfigReader.getInstance().getProp("conType");
			String url = ConfigReader.getInstance().getProp("url");
//			String ssid = ConfigReader.getInstance().getProp("ssid");
			String user = ConfigReader.getInstance().getProp("user");
			String passwd = ConfigReader.getInstance().getProp("passwd");
//			if(ssid != null && !"".equals(ssid)){
//				con = DriverManager.getConnection(
//						conType + ":@" + url + ":" + ssid, user, passwd);
//			}else{
				con = DriverManager.getConnection(url, user, passwd);
//			}
			logger.info("url:{},user:{},passwd:{}",  url,  user, passwd);
		} catch (ClassNotFoundException e) {
			logger.error("", e);
		} catch (SQLException e) {
			logger.error("", e);
		}
		return con;
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}
	}

	public static void closeStatement(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
			}
		}
	}

	public static void closePreparedStatement(PreparedStatement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
			}
		}
	}

	public static void closeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * truncate��
	 * @param tabName
	 */
	public static void truncateTalbe(String tabName) {
		Connection con = null;
		Statement stmt = null;
		String sql = "TRUNCATE TABLE " + tabName;
		try{
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			stmt.execute(sql);
		}catch (Exception e) {
			logger.error("", e);
		}finally{
			DBUtil.closeStatement(stmt);
			DBUtil.closeConnection(con);
		}
	}
	
	/**
	 * ��¼������ʷ
	 * @param result
	 * @param path
	 * @param batNo
	 */
	public static void saveLog(String result, String path, String batNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO LOG_LIST_IMP_RES (  RES_ID, LIST_BATCH_NO, FILE_PATH,   STATUS, CREATE_USER)VALUES ( SQ_LOG_LIST_IMP_RES.nextval,? ,? ,? , ?)";
		try{
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			int idx = 1;
			pstmt.setString(idx++, batNo);
			pstmt.setString(idx++, path);
			pstmt.setString(idx++, result);
			pstmt.setString(idx++, "system");
			pstmt.executeUpdate();
		}catch (Exception e) {
			logger.error("", e);
		}finally{
			DBUtil.closePreparedStatement(pstmt);
			DBUtil.closeConnection(con);
		}
	}
}
