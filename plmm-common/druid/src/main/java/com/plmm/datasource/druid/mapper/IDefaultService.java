package com.plmm.datasource.druid.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

import com.plmm.common.exception.plmmSqlException;

public abstract interface IDefaultService {
	public abstract int insert(String paramString, Object paramObject) throws plmmSqlException;

	public abstract int delete(String paramString, Object paramObject) throws plmmSqlException;

	public abstract int update(String paramString, Object paramObject) throws plmmSqlException;

	public abstract <E> List<E> selectList(String paramString) throws plmmSqlException;

	public abstract <E> List<E> selectList(String paramString, Object paramObject) throws plmmSqlException;

	public abstract <E> List<E> selectList(String paramString, Object paramObject, int paramInt1, int paramInt2)
			throws plmmSqlException;

	public abstract <T> T selectOne(String paramString, Object paramObject) throws plmmSqlException;

	public abstract Object[] getCommonPaged(String paramString1, String paramString2, Object paramObject, int paramInt1,
			int paramInt2) throws plmmSqlException;

	public abstract void batchUpdate(String paramString, List<Object[]> paramList, int[] paramArrayOfInt)
			throws plmmSqlException;

	public abstract int[] batchUpdate(String[] paramArrayOfString) throws plmmSqlException;

	public abstract void batchUpdate(String paramString, List<Object[]> paramList) throws plmmSqlException;

	public abstract void batchUpdate(String paramString, BatchPreparedStatementSetter paramBatchPreparedStatementSetter)
			throws plmmSqlException;

	public abstract <T> void batchUpdate(String paramString, Collection<T> paramCollection, int paramInt,
			ParameterizedPreparedStatementSetter<T> paramParameterizedPreparedStatementSetter)
			throws plmmSqlException;

	public abstract List<Map<String, Object>> queryList(String paramString, List<Object> paramList,
			int[] paramArrayOfInt) throws plmmSqlException;

	public abstract <T> List<T> queryList(String paramString, List<Object> paramList, int[] paramArrayOfInt,
			Class<T> paramClass) throws plmmSqlException;

	public abstract List<Map<String, Object>> queryList(String paramString) throws plmmSqlException;

	public abstract <T> List<T> queryList(String paramString, Class<T> paramClass) throws plmmSqlException;

	public abstract List<Map<String, Object>> queryList(String paramString, Object[] paramArrayOfObject)
			throws plmmSqlException;

	public abstract <T> List<T> queryList(String paramString, Class<T> paramClass, Object[] paramArrayOfObject)
			throws plmmSqlException;

	public abstract <T> List<T> queryList(String paramString, List<Object> paramList, Class<T> paramClass)
			throws plmmSqlException;

	public abstract <T> List<T> queryList(String paramString, List<Object> paramList, RowMapper<T> paramRowMapper)
			throws plmmSqlException;

	public abstract <T> T queryOneReocrd(String paramString, Class<T> paramClass) throws plmmSqlException;

	public abstract <T> T queryOneReocrd(String paramString, RowMapper<T> paramRowMapper) throws plmmSqlException;

	public abstract <T> T queryOneReocrd(String paramString, List<Object> paramList, Class<T> paramClass)
			throws plmmSqlException;

	public abstract <T> T queryOneReocrd(String paramString, List<Object> paramList, RowMapper<T> paramRowMapper)
			throws plmmSqlException;

	public abstract <T> T queryOneReocrd(String paramString, List<Object> paramList, int[] paramArrayOfInt,
			Class<T> paramClass) throws plmmSqlException;

	public abstract <T> T queryOneReocrd(String paramString, List<Object> paramList, int[] paramArrayOfInt,
			RowMapper<T> paramRowMapper) throws plmmSqlException;

	public abstract int update(String paramString) throws plmmSqlException;

	public abstract int updateJdbc(String paramString, Object[] paramArrayOfObject) throws plmmSqlException;

	public abstract int update(String paramString, List<Object> paramList) throws plmmSqlException;

	public abstract int update(String paramString, List<Object> paramList, int[] paramArrayOfInt)
			throws plmmSqlException;

	public abstract Map<String, Object> call(String paramString, boolean paramBoolean, Map<String, Object> paramMap,
			List<SqlParameter> paramList, List<SqlOutParameter> paramList1) throws plmmSqlException;

	public abstract Map<String, Object> call(String paramString, boolean paramBoolean, Map<String, Object> paramMap,
			List<SqlParameter> paramList) throws plmmSqlException;

	public abstract String keyCreate(String paramString, Character paramCharacter) throws plmmSqlException;

	public abstract long getSeqByName(String paramString) throws plmmSqlException;

	public abstract String getIdBySeq(Character paramCharacter, long paramLong) throws plmmSqlException;

	public abstract String keyCreatePad15Bit(String paramString, Character paramCharacter) throws plmmSqlException;
}