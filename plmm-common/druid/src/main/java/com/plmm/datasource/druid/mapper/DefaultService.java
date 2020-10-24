package com.plmm.datasource.druid.mapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import com.plmm.common.exception.plmmSqlException;

public class DefaultService implements IDefaultService {
	private SqlSession session = null;

	JdbcTemplate jdbcTemplate = null;
	public static final int BATCH_SIZE = 5000;

	private IDefaultService fallback = null;

	private List<Class<Throwable>> fallbackExceptions = null;
	public static final FastDateFormat df = FastDateFormat.getInstance("yyyyMMdd");

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public int insert(String statementName, Object param) throws plmmSqlException {
		try {
			return this.session.insert(statementName, param);
		} catch (Exception e) {
			throw new plmmSqlException("插入失败", e);
		}
	}

	public void batchUpdate(String sql, List<Object[]> batchArgs, int[] argTypes) throws plmmSqlException {
		try {
			int fromIndex = 0;
			int toIndex = 5000;
			while (fromIndex != batchArgs.size()) {
				if (toIndex > batchArgs.size())
					toIndex = batchArgs.size();
				this.jdbcTemplate.batchUpdate(sql, batchArgs.subList(fromIndex, toIndex), argTypes);
				fromIndex = toIndex;
				toIndex += 5000;
				if (toIndex <= batchArgs.size())
					continue;
				toIndex = batchArgs.size();
			}
		} catch (Exception e) {
			throw new plmmSqlException("批量插入失败", e);
		}
	}

	public int delete(String statementName, Object param) throws plmmSqlException {
		try {
			return this.session.delete(statementName, param);
		} catch (Exception e) {
			throw new plmmSqlException("删除失败", e);
		}
	}

	public int update(String statementName, Object param) throws plmmSqlException {
		try {
			return this.session.update(statementName, param);
		} catch (Exception e) {
			throw new plmmSqlException("更新失败", e);
		}
	}

	public <E> List<E> selectList(String statementName) throws plmmSqlException {
		try {
			return this.session.selectList(statementName);
		} catch (Exception e) {
			if (this.fallbackExceptions != null) {
				for (Class c : this.fallbackExceptions) {
					if (c.isAssignableFrom(e.getClass()))
						return this.fallback.selectList(statementName);
				}
			}
			throw new plmmSqlException("查询失败", e);
		}
	}

	public List<Map<String, Object>> queryList(String sql, List<Object> args, int[] types) {
		try {
			return this.jdbcTemplate.queryForList(sql, args != null ? args.toArray() : null, types);
		} catch (DataAccessException dex) {
			throw new plmmSqlException(" 数据库操作异常：", dex);
		}
	}

	public <T> List<T> queryList(String sql, List<Object> args, int[] types, Class<T> clazz) {
		try {
			return this.jdbcTemplate.queryForList(sql, args != null ? args.toArray() : null, types, clazz);
		} catch (DataAccessException dex) {
			throw new plmmSqlException(" 数据库操作异常：", dex);
		}
	}

	public List<Map<String, Object>> queryList(String sql) {
		try {
			return this.jdbcTemplate.queryForList(sql);
		} catch (DataAccessException dex) {
			throw new plmmSqlException(" 数据库操作异常：", dex);
		}
	}

	public <T> List<T> queryList(String sql, Class<T> clazz) {
		try {
			return this.jdbcTemplate.queryForList(sql, clazz);
		} catch (DataAccessException dex) {
			throw new plmmSqlException(" 数据库操作异常：", dex);
		}
	}

	public List<Map<String, Object>> queryList(String sql, Object[] args) {
		try {
			return this.jdbcTemplate.queryForList(sql, args);
		} catch (DataAccessException dex) {
			throw new plmmSqlException("数据库操作异常：", dex);
		}
	}

	public <T> List<T> queryList(String sql, Class<T> clazz, Object[] args) {
		try {
			return this.jdbcTemplate.queryForList(sql, clazz, args);
		} catch (DataAccessException dex) {
			throw new plmmSqlException("数据库操作异常：", dex);
		}
	}

	public <T> List<T> queryList(String sql, List<Object> args, Class<T> clazz) {
		try {
			return this.jdbcTemplate.queryForList(sql, args != null ? args.toArray() : null, clazz);
		} catch (DataAccessException dex) {
			throw new plmmSqlException("数据库操作异常：", dex);
		}
	}

	public <T> List<T> queryList(String sql, List<Object> args, RowMapper<T> rowMapper) throws plmmSqlException {
		try {
			return this.jdbcTemplate.query(sql, args != null ? args.toArray() : null, rowMapper);
		} catch (DataAccessException dex) {
			throw new plmmSqlException("数据库操作异常：", dex);
		}
	}

	public <T> T queryOneReocrd(String sql, Class<T> clazz) {
		try {
			return this.jdbcTemplate.queryForObject(sql, clazz);
		} catch (EmptyResultDataAccessException dex) {
			return null;
		} catch (DataAccessException dex) {
			throw new plmmSqlException("数据库操作异常：", dex);
		}
	}

	public <T> T queryOneReocrd(String sql, RowMapper<T> rowMapper) {
		try {
			return this.jdbcTemplate.queryForObject(sql, rowMapper);
		} catch (EmptyResultDataAccessException dex) {
			return null;
		} catch (DataAccessException dex) {
			throw new plmmSqlException("数据库操作异常：", dex);
		}
	}

	public <T> T queryOneReocrd(String sql, List<Object> args, Class<T> clazz) {
		try {
			return this.jdbcTemplate.queryForObject(sql, args != null ? args.toArray() : null, clazz);
		} catch (EmptyResultDataAccessException dex) {
			return null;
		} catch (DataAccessException dex) {
			throw new plmmSqlException("数据库操作异常：", dex);
		}
	}

	public <T> T queryOneReocrd(String sql, List<Object> args, RowMapper<T> rowMapper) {
		try {
			return this.jdbcTemplate.queryForObject(sql, args != null ? args.toArray() : null, rowMapper);
		} catch (EmptyResultDataAccessException dex) {
			return null;
		} catch (DataAccessException dex) {
			throw new plmmSqlException("数据库操作异常：", dex);
		}
	}

	public <T> T queryOneReocrd(String sql, List<Object> args, int[] argTypes, Class<T> clazz) {
		try {
			return this.jdbcTemplate.queryForObject(sql, args != null ? args.toArray() : null, argTypes, clazz);
		} catch (EmptyResultDataAccessException dex) {
			return null;
		} catch (DataAccessException dex) {
			throw new plmmSqlException("数据库操作异常：", dex);
		}
	}

	public <T> T queryOneReocrd(String sql, List<Object> args, int[] argTypes, RowMapper<T> rowMapper) {
		try {
			return this.jdbcTemplate.queryForObject(sql, args != null ? args.toArray() : null, argTypes, rowMapper);
		} catch (EmptyResultDataAccessException dex) {
			return null;
		} catch (DataAccessException dex) {
			throw new plmmSqlException("数据库操作异常：", dex);
		}
	}

	public int update(String sql) {
		try {
			int ret = this.jdbcTemplate.update(sql);
			return ret;
		} catch (DataAccessException dex) {
			throw new plmmSqlException("数据库操作异常：", dex);
		}
	}

	public int updateJdbc(String sql, Object[] args) {
		try {
			int ret = this.jdbcTemplate.update(sql, args);
			return ret;
		} catch (DataAccessException dex) {
			throw new plmmSqlException("数据库操作异常：", dex);
		}
	}

	public int update(String sql, List<Object> args) {
		try {
			int ret = this.jdbcTemplate.update(sql, args != null ? args.toArray() : null);
			return ret;
		} catch (DataAccessException dex) {
			throw new plmmSqlException("数据库操作异常：", dex);
		}
	}

	public int update(String sql, List<Object> args, int[] argTypes) {
		try {
			return this.jdbcTemplate.update(sql, args != null ? args.toArray() : null, argTypes);
		} catch (DataAccessException dex) {
			throw new plmmSqlException("数据库操作异常：", dex);
		}
	}

	public Map<String, Object> call(String name, boolean isFunction, Map<String, Object> procedureParamMap,
			List<SqlParameter> sqlInParameters, List<SqlOutParameter> sqlOutParameter) {
		try {
			StoredProcedure storedProcedure = new StoredProcedureHandler(this.jdbcTemplate, name);
			if ((sqlInParameters != null) && (!sqlInParameters.isEmpty())) {
				for (SqlParameter sqlParameter : sqlInParameters) {
					storedProcedure.declareParameter(sqlParameter);
				}
			}
			if ((sqlOutParameter != null) && (!sqlOutParameter.isEmpty())) {
				for (SqlOutParameter sqlParameter : sqlOutParameter) {
					storedProcedure.declareParameter(sqlParameter);
				}
			}
			storedProcedure.setFunction(isFunction);
			storedProcedure.compile();
			return storedProcedure.execute(procedureParamMap);
		} catch (DataAccessException dex) {
			throw new plmmSqlException("数据库存储过程调用异常：", dex);
		}
	}

	public Map<String, Object> call(String name, boolean isFunction, Map<String, Object> procedureParamMap,
			List<SqlParameter> sqlParameters) throws plmmSqlException {
		try {
			StoredProcedure storedProcedure = new StoredProcedureHandler(this.jdbcTemplate, name);
			if ((sqlParameters != null) && (!sqlParameters.isEmpty())) {
				for (SqlParameter sqlParameter : sqlParameters) {
					storedProcedure.declareParameter(sqlParameter);
				}
			}
			storedProcedure.setFunction(isFunction);
			storedProcedure.compile();
			return storedProcedure.execute(procedureParamMap);
		} catch (DataAccessException dex) {
			throw new plmmSqlException("数据库存储过程调用异常：", dex);
		}
	}

	public <E> List<E> selectList(String statementName, Object param, int offset, int limit)
			throws plmmSqlException {
		try {
			if (limit < 0) {
				return selectList(statementName, param);
			}
			return this.session.selectList(statementName, param, new RowBounds(offset, limit));
		} catch (Exception e) {
			if (this.fallbackExceptions != null) {
				for (Class c : this.fallbackExceptions) {
					if (c.isAssignableFrom(e.getClass())) {
						return this.fallback.selectList(statementName, param, offset, limit);
					}
				}
			}
			throw new plmmSqlException("查询失败", e);
		}
	}

	public <E> List<E> selectList(String statementName, Object param) throws plmmSqlException {
		try {
			return this.session.selectList(statementName, param);
		} catch (Exception e) {
			if (this.fallbackExceptions != null) {
				for (Class c : this.fallbackExceptions) {
					if (c.isAssignableFrom(e.getClass()))
						return this.fallback.selectList(statementName, param);
				}
			}
			throw new plmmSqlException("查询失败", e);
		}
	}

	public <T> T selectOne(String statementName, Object param) throws plmmSqlException {
		try {
			return this.session.selectOne(statementName, param);
		} catch (Exception e) {
			if (this.fallbackExceptions != null) {
				for (Class c : this.fallbackExceptions) {
					if (c.isAssignableFrom(e.getClass()))
						return this.fallback.selectOne(statementName, param);
				}
			}
			throw new plmmSqlException("查询失败", e);
		}
	}

	public Object[] getCommonPaged(String queryStatement, String countStatement, Object param, int offset, int limit)
			throws plmmSqlException {
		try {
			Object[] obj = new Object[2];
			obj[0] = selectList(queryStatement, param, offset, limit);
			obj[1] = selectOne(countStatement, param);
			return obj;
		} catch (Exception e) {
			if (this.fallbackExceptions != null) {
				for (Class c : this.fallbackExceptions) {
					if (c.isAssignableFrom(e.getClass())) {
						return this.fallback.getCommonPaged(queryStatement, countStatement, param, offset, limit);
					}
				}
			}
			throw new plmmSqlException("查询失败", e);
		}
	}

	public void setSession(SqlSession session) {
		this.session = session;
	}

	public void setFallback(IDefaultService fallback) {
		this.fallback = fallback;
	}

	public void setFallbackExceptions(List<String> fallbackExceptions) {
		this.fallbackExceptions = new ArrayList();
		for (String ex : fallbackExceptions)
			try {
				Class clazz = Class.forName(ex);
				this.fallbackExceptions.add(clazz);
			} catch (Exception e) {
			}
	}

	private void isNull(Object o) throws plmmSqlException {
		if (o == null)
			throw new plmmSqlException(new StringBuilder().append(o).append("未配置！该方法不可用").toString());
	}

	public int[] batchUpdate(String[] sql) throws plmmSqlException {
		try {
			return this.jdbcTemplate.batchUpdate(sql);
		} catch (Exception e) {
			throw new plmmSqlException("批量插入失败", e);
		}
	}

	public void batchUpdate(String sql, List<Object[]> batchArgs) throws plmmSqlException {
		try {
			int fromIndex = 0;
			int toIndex = 5000;
			while (fromIndex < batchArgs.size()) {
				if (toIndex > batchArgs.size())
					toIndex = batchArgs.size();
				this.jdbcTemplate.batchUpdate(sql, batchArgs.subList(fromIndex, toIndex));
				fromIndex = toIndex;
				toIndex += 5000;
			}
		} catch (Exception e) {
			throw new plmmSqlException("批量插入失败", e);
		}
	}

	public void batchUpdate(String sql, BatchPreparedStatementSetter pss) throws plmmSqlException {
		try {
			this.jdbcTemplate.batchUpdate(sql, pss);
		} catch (Exception e) {
			throw new plmmSqlException("批量插入失败", e);
		}
	}

	public <T> void batchUpdate(String sql, Collection<T> batchArgs, int batchSize,
			ParameterizedPreparedStatementSetter<T> ppss) throws plmmSqlException {
		try {
			this.jdbcTemplate.batchUpdate(sql, batchArgs, batchSize, ppss);
		} catch (Exception e) {
			throw new plmmSqlException("批量插入失败", e);
		}
	}

	public String keyCreate(String sequence, Character prefix) throws plmmSqlException {
		try {
			Long seqId = (Long) this.jdbcTemplate.queryForObject(
					new StringBuilder().append("select ").append(sequence).append(".nextval from dual").toString(),
					Long.class);
			return format20bit(prefix, seqId.longValue());
		} catch (Exception e) {
			throw new plmmSqlException("获取seq失败", e);
		}
	}

	public String keyCreatePad15Bit(String sequence, Character prefix) throws plmmSqlException {
		try {
			Long seqId = (Long) this.jdbcTemplate.queryForObject(
					new StringBuilder().append("select ").append(sequence).append(".nextval from dual").toString(),
					Long.class);
			return format15bit(prefix, seqId.longValue());
		} catch (Exception e) {
			throw new plmmSqlException("获取seq失败", e);
		}
	}

	private String format(long initval, long number) {
		return String.valueOf(initval + number).substring(1);
	}

	private String format20bit(Character prefix, long number) {
		return getIdBySeq(prefix == null ? "" : prefix.toString(), String.valueOf(number));
	}

	public static String getIdBySeq(String seqId) {
		StringBuffer seqBuf = new StringBuffer();

		if ((seqId != null) && (seqId.length() > 0) && (seqId.length() < 13)) {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			seqBuf.append(df.format(new Date()));

			for (int i = 0; i < 12 - seqId.length(); i++) {
				seqBuf.append("0");
			}
			seqBuf.append(seqId);
		} else if ((seqId != null) && (seqId.length() > 12)) {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			seqBuf.append(df.format(new Date()));
			seqBuf.append(seqId.substring(seqId.length() - 12));
		}

		return seqBuf.toString();
	}

	public static String getIdBySeq(String prefix, String seqId) {
		StringBuffer seqBuf = new StringBuffer(prefix);

		if ((seqId != null) && (seqId.length() > 0) && (seqId.length() + prefix.length() < 13)) {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			seqBuf.append(df.format(new Date()));

			for (int i = 0; i < 12 - prefix.length() - seqId.length(); i++) {
				seqBuf.append("0");
			}
			seqBuf.append(seqId);
		} else if ((seqId != null) && (seqId.length() + prefix.length() > 12)) {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			seqBuf.append(df.format(new Date()));
			seqBuf.append(seqId.substring(seqId.length() + prefix.length() - 12));
		}

		return seqBuf.toString();
	}

	private String format15bit(Character prefix, long number) {
		number %= (prefix == null ? 10000000L : 1000000L);
		String num = format(prefix == null ? 10000000L : 1000000L, number);
		String datestr = df.format(new Date());
		return new StringBuilder().append(prefix == null ? "" : prefix).append(datestr).append(num).toString();
	}

	public long getSeqByName(String sequence) throws plmmSqlException {
		try {
			return ((Long) this.jdbcTemplate.queryForObject(
					new StringBuilder().append("select ").append(sequence).append(".nextval from dual").toString(),
					Long.class)).longValue();
		} catch (Exception e) {
			throw new plmmSqlException("获取序号失败", e);
		}
	}

	public String getIdBySeq(Character prefix, long number) throws plmmSqlException {
		try {
			return format20bit(prefix, number);
		} catch (Exception e) {
			throw new plmmSqlException("获取序号失败", e);
		}
	}
}