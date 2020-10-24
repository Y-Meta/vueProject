package com.plmm.datasource.druid.filter;

import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.ConnectionProxy;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.alibaba.druid.sql.SQLUtils;


public class SQLFilter extends FilterEventAdapter {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SQLFilter.class);
	
	public SQLFilter(){}
	
	@Override
	protected void statementExecuteBatchAfter(StatementProxy statement, int[] result) {
	}

	@Override
	protected void statementExecuteUpdateAfter(StatementProxy statement, String sql, int updateCount) {
		if (updateCount > 0) {
			LOGGER.debug("statementExecuteUpdateAfter sql[{}]", SQLUtils.formatMySql(sql).replaceAll("[\t\n]", " "));
		}
	}
	
	@Override
	protected void statementExecuteBefore(StatementProxy statement, String sql) {
		LOGGER.info("statementExecuteBefore sql[{}]", SQLUtils.formatMySql(sql).replaceAll("[\t\n]", " "));
	}
	
	@Override
	protected void statementExecuteUpdateBefore(StatementProxy statement, String sql) {
		LOGGER.info("statementExecuteUpdateBefore sql[{}]", SQLUtils.formatMySql(sql).replaceAll("[\t\n]", " "));
	}
	
	@Override
	protected void statementExecuteAfter(StatementProxy statement, String sql, boolean result) {// 测试进此步骤说明sql已经执行成功
		if (null != sql) {
			sql = sql.trim();
			LOGGER.debug("statementExecuteAfter sql[{}]", SQLUtils.formatMySql(sql).replaceAll("[\t\n]", " "));
		} 
	}


	@Override
	public void connection_commit(FilterChain chain, ConnectionProxy connection) throws SQLException {
		try {
			super.connection_commit(chain, connection);
			if(LOGGER.isDebugEnabled()) {
				LOGGER.debug("conn {} commited", connection);
			}
		} catch (Exception e) {
			LOGGER.error("[SQL同步异常]事务提交时，处理SQL失败", e);
		} 
	}
	
	@Override
	public void connection_rollback(FilterChain chain, ConnectionProxy connection) throws SQLException {
		super.connection_rollback(chain, connection);
	}
}

