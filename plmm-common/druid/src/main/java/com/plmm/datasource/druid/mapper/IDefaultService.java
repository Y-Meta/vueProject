package com.plmm.datasource.druid.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

import com.plmm.common.exception.PlmmSqlException;

public abstract interface IDefaultService {
	public abstract int insert(String paramString, Object paramObject) throws PlmmSqlException;

	public abstract int delete(String paramString, Object paramObject) throws PlmmSqlException;

	public abstract int update(String paramString, Object paramObject) throws PlmmSqlException;

	public abstract <E> List<E> selectList(String paramString) throws PlmmSqlException;

	public abstract <E> List<E> selectList(String paramString, Object paramObject) throws PlmmSqlException;

	public abstract <E> List<E> selectList(String paramString, Object paramObject, int paramInt1, int paramInt2)
			throws PlmmSqlException;

	public abstract <T> T selectOne(String paramString, Object paramObject) throws PlmmSqlException;

	public abstract Object[] getCommonPaged(String paramString1, String paramString2, Object paramObject, int paramInt1,
			int paramInt2) throws PlmmSqlException;

	public abstract void batchUpdate(String paramString, List<Object[]> paramList, int[] paramArrayOfInt)
			throws PlmmSqlException;

	public abstract int[] batchUpdate(String[] paramArrayOfString) throws PlmmSqlException;

	public abstract void batchUpdate(String paramString, List<Object[]> paramList) throws PlmmSqlException;

	public abstract void batchUpdate(String paramString, BatchPreparedStatementSetter paramBatchPreparedStatementSetter)
			throws PlmmSqlException;

	public abstract <T> void batchUpdate(String paramString, Collection<T> paramCollection, int paramInt,
			ParameterizedPreparedStatementSetter<T> paramParameterizedPreparedStatementSetter)
			throws PlmmSqlException;

	public abstract List<Map<String, Object>> queryList(String paramString, List<Object> paramList,
			int[] paramArrayOfInt) throws PlmmSqlException;

	public abstract <T> List<T> queryList(String paramString, List<Object> paramList, int[] paramArrayOfInt,
			Class<T> paramClass) throws PlmmSqlException;

	public abstract List<Map<String, Object>> queryList(String paramString) throws PlmmSqlException;

	public abstract <T> List<T> queryList(String paramString, Class<T> paramClass) throws PlmmSqlException;

	public abstract List<Map<String, Object>> queryList(String paramString, Object[] paramArrayOfObject)
			throws PlmmSqlException;

	public abstract <T> List<T> queryList(String paramString, Class<T> paramClass, Object[] paramArrayOfObject)
			throws PlmmSqlException;

	public abstract <T> List<T> queryList(String paramString, List<Object> paramList, Class<T> paramClass)
			throws PlmmSqlException;

	public abstract <T> List<T> queryList(String paramString, List<Object> paramList, RowMapper<T> paramRowMapper)
			throws PlmmSqlException;

	public abstract <T> T queryOneReocrd(String paramString, Class<T> paramClass) throws PlmmSqlException;

	public abstract <T> T queryOneReocrd(String paramString, RowMapper<T> paramRowMapper) throws PlmmSqlException;

	public abstract <T> T queryOneReocrd(String paramString, List<Object> paramList, Class<T> paramClass)
			throws PlmmSqlException;

	public abstract <T> T queryOneReocrd(String paramString, List<Object> paramList, RowMapper<T> paramRowMapper)
			throws PlmmSqlException;

	public abstract <T> T queryOneReocrd(String paramString, List<Object> paramList, int[] paramArrayOfInt,
			Class<T> paramClass) throws PlmmSqlException;

	public abstract <T> T queryOneReocrd(String paramString, List<Object> paramList, int[] paramArrayOfInt,
			RowMapper<T> paramRowMapper) throws PlmmSqlException;

	public abstract int update(String paramString) throws PlmmSqlException;

	public abstract int updateJdbc(String paramString, Object[] paramArrayOfObject) throws PlmmSqlException;

	public abstract int update(String paramString, List<Object> paramList) throws PlmmSqlException;

	public abstract int update(String paramString, List<Object> paramList, int[] paramArrayOfInt)
			throws PlmmSqlException;

	public abstract Map<String, Object> call(String paramString, boolean paramBoolean, Map<String, Object> paramMap,
			List<SqlParameter> paramList, List<SqlOutParameter> paramList1) throws PlmmSqlException;

	public abstract Map<String, Object> call(String paramString, boolean paramBoolean, Map<String, Object> paramMap,
			List<SqlParameter> paramList) throws PlmmSqlException;

	public abstract String keyCreate(String paramString, Character paramCharacter) throws PlmmSqlException;

	public abstract long getSeqByName(String paramString) throws PlmmSqlException;

	public abstract String getIdBySeq(Character paramCharacter, long paramLong) throws PlmmSqlException;

	public abstract String keyCreatePad15Bit(String paramString, Character paramCharacter) throws PlmmSqlException;
}