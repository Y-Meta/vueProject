package com.plmm.generate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.plmm.generate.model.Field;
import com.plmm.generate.util.DBUtil;
import com.plmm.generate.util.DateUtils;
import com.plmm.generate.util.VelocityUtils;

public class Generate {

	boolean isMyBatis = true;//是否使用mybatis配置
	/*自定义区*/
	private final static String directory = "c:\\generate_src";//文件生成路径
	private final static String company = "";//公司名字，以组包名tp_sys_share
	private final static String project = "plmm";//项目名字
	private final static String module = "customer";//子模块名字
	private final static String business = "customermanage";//业务编码
	private final static String PRIMARY_KEY = "id";//定义主键。暂时不支持联合主键
	private final static String QUERY_FIELD = "id,code";//定义查询字段
	private final static String author = "admin";
	private final static String serviceId = "service-common";
	/*固定区*/
	private final static String COM = "com";
	private final static String WEB = "web";
	private final static String CONFIG = "config";
	private final static String CONTROLLER = "controller";
	private final static String SERVICE = "service";
	private final static String SERVICE4NAME = "Service";
	private final static String IDUBBOSERVICE4NAME = "DubboService";
	private final static String MAPPER4NAME = "Mapper";
	private final static String DUBBOSERVICE4NAME = "DubboServiceImpl";
	private final static String RESTCONTROLLER4NAME = "RestController";
	private final static String IMPL = "impl";
	private final static String MAPPER = "mapper";
	private final static String MODEL = "model";
	private final static String POINT = ".";
	private final static String LINE = "/";
	private final static String packagepath = COM + POINT + company + POINT +project + POINT + module + POINT + business + POINT;//请在此外定义包路径
	private final static String rootpackagepath = COM + POINT + company + POINT +project + POINT ;//请在此外定义包路径
	private final static String controllerpath = WEB + LINE + module + LINE + business;//controller路径
	private final static String javacodepath = directory + File.separator + COM + File.separator + company + File.separator + project
			+ File.separator + module + File.separator + business + File.separator;
	private final static String vopath =  javacodepath + MODEL;
	private final static String interfacepath = javacodepath + SERVICE;
	private final static String servicepath = interfacepath + File.separator;
	private final static String mapperpath = javacodepath + File.separator + MAPPER;
	private final static String configpath = javacodepath + CONFIG;
	private final static String controllerjavapath = directory + File.separator + COM + File.separator + company + File.separator +project + File.separator + CONTROLLER;
	private final static String servicejavapath = directory + File.separator + COM + File.separator + company + File.separator +project + File.separator + SERVICE;
	private final static String jsppath = directory + File.separator + WEB + File.separator + module + File.separator + business;
	/**
	 * //定义模板，有common,tree两种t
	 * common: 常见的增删改查界面，上下布局，上是查询区，下为GRID，自带有detail面版
	 * tree:常见的树形管理界面，左右布局，左边为树形菜单，右为detail面版
	 */
	private final static String TEMPLATE = "common";
	
	private static Logger logger = LoggerFactory.getLogger(Generate.class);

	public static void main(String[] args) throws Exception {
		logger.info("\r\n请输入要生成的表名:");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String biaoming = null;
		if ((biaoming = br.readLine()) != null) {
			generate(biaoming.trim());
		}
		logger.info("生成完毕!目录:" + directory);
	}
	
	public static void generate(String tableName){
		File dir = new File(directory);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		Map<String, Object> metaMap = getMapFromDatabase(tableName);
		logger.info("map:{}", metaMap);
		createVO(metaMap);
		createMybatis(metaMap);
		createRestController(metaMap);
		createInterface(metaMap);
		createWebDubboService(metaMap);
		createDubboService(metaMap);
		createMapper(metaMap);
		
		
	}
	
	/**
	 * 
	 * @param tableName
	 * @return
	 */
	private static Map<String, Object> getMapFromDatabase(String tableName) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("packagepath", packagepath);
		retMap.put("rootpackagepath", rootpackagepath);
		retMap.put("webpath", controllerpath);
		retMap.put("tableName", tableName);
		retMap.put("primaryKeyColumn", PRIMARY_KEY);
		retMap.put("primaryKeyField", toCcccase(PRIMARY_KEY));
		retMap.put("queryFieldString", QUERY_FIELD);
		retMap.put("author", author);
		retMap.put("nowdatetime", DateUtils.getCurrentDateTimeAsString());
		retMap.put("serviceId", serviceId);
		Map<String, Field> map = new HashMap<String, Field>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			String dbName = con.getMetaData().getDatabaseProductName();
			String sql = null;
			if ("MySql".equalsIgnoreCase(dbName)) {
				sql = "show full fields  from  "
						+ tableName.toUpperCase().trim();
			} else {
				sql = "SELECT * FROM USER_COL_COMMENTS WHERE TABLE_NAME = ?";
			}
			pstmt = con.prepareStatement(sql);
			if (!"MySql".equalsIgnoreCase(dbName)) {
				pstmt.setString(1, tableName.toUpperCase().trim());
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String colName = null;
				String commmmmmmment = null;
				if ("MySql".equalsIgnoreCase(dbName)) {
					colName = rs.getString("Field");
					commmmmmmment = rs.getString("COMMENT");
				} else {
					colName = rs.getString("COLUMN_NAME");
					commmmmmmment = rs.getString("COMMENTS");
				}

				String colNamec = toCcccase(colName.toLowerCase());
				commmmmmmment = commmmmmmment == null ? colName : commmmmmmment;
				Field field = new Field();
				field.setColumnName(colName);
				field.setLabel(commmmmmmment);
				map.put(colNamec, field);
			}
			retMap.put("columns", map);
			String ssssss = toCcccase(tableName.toLowerCase());
			String name = ssssss.substring(0, 1).toUpperCase() + ssssss.substring(1);
			retMap.put("name", name);
			retMap.put("lowName", ssssss);
		} catch (SQLException e) {
			logger.info("ERROR" + e);
		} catch (SecurityException e) {
			logger.info("ERROR" + e);
		} catch (IllegalArgumentException e) {
			logger.info("ERROR" + e);
		} catch (Exception e) {
			logger.info("ERROR" + e);
		} finally {
			// 关闭rs...
			DBUtil.closePreparedStatement(pstmt);
			DBUtil.closeConnection(con);
		}
		return retMap;
	}
	
	private static void createVO(Map<String, Object> map) {
		File directoryVO = new File(vopath);
		if(!directoryVO.exists()) {
			directoryVO.mkdirs();
		}
		File destFile = new File(vopath + File.separator + map.get("name").toString() + "VO.java");
		create(map, destFile, "java.txt");
	}
	
	private static void createWebDubboService(Map<String, Object> map) {
		File directoryService = new File(servicejavapath);
		if(!directoryService.exists()) {
			directoryService.mkdirs();
		}
		String serviceName =  map.get("name").toString() + SERVICE4NAME;
		File destFile = new File(servicejavapath + File.separator + serviceName + ".java");
		create(map, destFile, "web_dubboservice.txt");
	}
	
	private static void createDubboService(Map<String, Object> map) {
		File directoryController = new File(servicepath);
		if(!directoryController.exists()) {
			directoryController.mkdirs();
		}
		String controllerName = map.get("name").toString() + DUBBOSERVICE4NAME;
		File destFile = new File(servicepath + File.separator + controllerName + ".java");
		create(map, destFile, "dubboservice.txt");
	}
	
	private static void createMapper(Map<String, Object> map) {
		File directoryService = new File(mapperpath);
		if(!directoryService.exists()) {
			directoryService.mkdirs();
		}
		String serviceName =  map.get("name").toString() + MAPPER4NAME;
		File destFile = new File(mapperpath + File.separator + serviceName + ".java");
		create(map, destFile, "mapper.txt");
	}
	
	private static void createInterface(Map<String, Object> map) {
		File directoryInterface = new File(interfacepath);
		if(!directoryInterface.exists()) {
			directoryInterface.mkdirs();
		}
		String interfaceName = "I" + map.get("name").toString() + IDUBBOSERVICE4NAME;
		File destFile = new File(interfacepath + File.separator + interfaceName + ".java");
		create(map, destFile, "interface.txt");
	}
	
	
	private static void createRestController(Map<String, Object> map) {
		File directoryController = new File(controllerjavapath);
		if(!directoryController.exists()) {
			directoryController.mkdirs();
		}
		String controllerName = map.get("name").toString() + RESTCONTROLLER4NAME;
		File destFile = new File(controllerjavapath + File.separator + controllerName + ".java");
		create(map, destFile, "restcontroller.txt");
	}
	
	private static void createMybatis(Map<String, Object> map) {
		File directoryMybatis = new File(configpath);
		if(!directoryMybatis.exists()) {
			directoryMybatis.mkdirs();
		}
		File destFile = new File(configpath + File.separator + map.get("lowName").toString() + "-sqlmap.xml");
		create(map, destFile, "mybatis.xml");
	}

	private static void create(Map<String, Object> map, File destFile, String templateName){
		FileOutputStream fos = null;
		try{
			fos = new FileOutputStream(destFile);
			String string = loadStringFromTemplate(templateName);
			logger.info("template:" + string);
			logger.info("convert:" + VelocityUtils.convert(string, map));
			fos.write(VelocityUtils.convert(string, map).getBytes("utf-8"));
		} catch (IOException e){
			logger.info("ERROR" + e);
		} catch (Exception e) {
			logger.info("ERROR" + e);
		} finally {
			if(fos != null)
				try {
					fos.close();
				} catch (IOException e) {}
		}
	}

	private static String loadStringFromTemplate(String filename) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		br = new BufferedReader(new InputStreamReader(Generate.class
				.getClassLoader().getResourceAsStream("template/"+filename), "utf-8"));
		String line = null;
		while ((line = br.readLine()) != null) {
			sb.append(line).append("\r\n");
		}
		return sb.toString();
	}

	private static String toCcccase(String colName) {
		int idx = colName.indexOf("_");
		if (idx != -1) {
			return toCcccase(colName.substring(0, idx)
					+ colName.substring(idx + 1, idx + 2).toUpperCase()
					+ colName.substring(idx + 2));
		}
		return colName;
	}
}
