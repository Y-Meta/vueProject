package com.plmm.utils;

public class Constant {

	/* 数据文件基本路径 */
	public final static String FILE_SAVE = "datafiles";
	/* 模板下载路径 */
	public final static String DOWNLOAD = "downLoadMode";
	/* 数据导入文件模板名称 */
	public final static String FILE_DOWL_MODE_NAME = "数据导入模板";
	/* 账号绑定MAC地址模板文件名称 */
	public final static String FILE_MAC_DOWL_MODE_NAME = "绑定MAC地址模板";
	/* 数据文件临时上传路径 */
	public final static String FILE_UPLOAD_TEMP = "temp";
	/* 数据文件上传路径 */
	public final static String FILE_UPLOAD = "upload";
	/* mac文件上传路径 */
	public final static String FILE_MAC = "mac";
	/* 数据文件覆盖备份路径 */
	public final static String FILE_BAK = "fileBak";
	/* 数据文件退回备份路径 */
	public final static String RETURN_BAK = "returnBak";
	/* 数据文件删除备份路径 */
	public final static String FILE_DEL = "del";
	/* 默认日期格式 */
	public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	/* 默认分页数量 */
	public final static int DEFAULT_PAGE_SIZE = 10;
	/* 默认分隔字符 */
	public final static String SEPARATOR_11 = Character.toString((char) 11);
	public final static String SEPARATOR_12 = Character.toString((char) 12);
	public final static String SEPARATOR_14 = Character.toString((char) 14);
	public final static String SEPARATOR_15 = Character.toString((char) 15);
	// ASCII 码的不可见字符xml只支持 9,10,13，32,127 ；其中10、13会换行
	public final static String SEPARATOR_127 = Character.toString((char) 127);
	/* 门户系统系统标识 */
	public final static String DSP_CODE = "US";
	/* 补录系统系统标识 */
	public final static String dsrfSysCode = "SP";
	/*经营分析系统标识*/
	public final static String BaSysCode = "BA";
	/* 关联风险识别系统标识 */
	public final static String AriSysCode = "RS";
	/*补录任务状态-- 1 补录任务待发布*/
	public final static String ToBeReleased = "1";
	/*补录任务状态-- 2：发布待审核;*/
	public final static String ToBeApproval = "2";
	/*补录任务状态-- 3：待补录;*/
	public final static String ToBeSupplet = "3";
	/*补录任务状态-- 4：已补录待审核;*/
	public final static String SuppletedToApproval = "4";
	/*补录任务状态-- 5：已完成;*/
	public final static String TaskFinished = "5";
	/*关联风险识系统角色状态-- 1: 启用状态*/
	public final static String RoleOpeningStatue = "1";
	/*关联风险识系统角色状态-- 2：禁用状态;*/
	public final static String RoleCloseStatue = "2";



	
}
