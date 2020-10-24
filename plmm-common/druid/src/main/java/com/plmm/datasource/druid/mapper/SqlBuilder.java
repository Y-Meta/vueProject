package com.plmm.datasource.druid.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class SqlBuilder {
	private StringBuffer outSQL;
	private List<Object> argList;
	private List<Integer> argTypeList;
	private boolean isOK;
	private boolean inIf;

	public SqlBuilder() {
		this.outSQL = new StringBuffer();
		init();
	}

	public SqlBuilder(int bufferSize) {
		this.outSQL = new StringBuffer(bufferSize);
		init();
	}

	public SqlBuilder(String ssql) {
		this.outSQL = new StringBuffer(ssql);
		init();
	}

	private void init() {
		this.argList = new ArrayList();
		this.argTypeList = new ArrayList();
		this.isOK = false;
		this.inIf = false;
	}

	public String getSQL() {
		return this.outSQL.toString();
	}

	public String toString() {
		return getSQL();
	}

	public List<Object> getSQLArgs() {
		return this.argList;
	}

	public int[] getArgTypes() {
		int[] args = new int[this.argTypeList.size()];
		for (int i = 0; i < this.argTypeList.size(); i++) {
			args[i] = ((Integer) this.argTypeList.get(i)).intValue();
		}
		return args;
	}

	public SqlBuilder append(Object ssql) {
		if ((!this.inIf) || (this.isOK)) {
			this.outSQL.append(ssql);
		}
		return this;
	}

	public SqlBuilder addType(int argType) {
		if ((!this.inIf) || (this.isOK)) {
			this.argTypeList.add(new Integer(argType));
		}
		return this;
	}

	public SqlBuilder addArg(Object sqlArg) {
		if ((!this.inIf) || (this.isOK)) {
			if (sqlArg == null) {
				sqlArg = "";
			}
			this.argList.add(sqlArg);
		}
		return this;
	}

	public SqlBuilder addArg(long sqlArg) {
		if ((!this.inIf) || (this.isOK)) {
			this.argList.add(new Long(sqlArg));
		}
		return this;
	}

	public SqlBuilder addArg(double sqlArg) {
		if ((!this.inIf) || (this.isOK)) {
			this.argList.add(new Double(sqlArg));
		}
		return this;
	}

	public SqlBuilder elseC() {
		this.isOK = (!this.isOK);
		return this;
	}

	public void endIf() {
		this.inIf = false;
	}

	public SqlBuilder ifNull(Object cObj) {
		this.inIf = true;
		this.isOK = (cObj == null);
		return this;
	}

	public SqlBuilder ifNotNull(Object cObj) {
		this.inIf = true;
		ifNull(cObj);
		this.isOK = (!this.isOK);
		return this;
	}

	public SqlBuilder ifEmpty(Object cObj) {
		this.inIf = true;
		if (cObj == null)
			this.isOK = true;
		else if ((cObj instanceof String))
			this.isOK = (((String) cObj).length() == 0);
		else if ((cObj instanceof Collection))
			this.isOK = ((Collection) cObj).isEmpty();
		else if ((cObj instanceof Map))
			this.isOK = ((Map) cObj).isEmpty();
		else {
			this.isOK = (String.valueOf(cObj).length() == 0);
		}

		return this;
	}

	public SqlBuilder ifNotEmpty(Object cObj) {
		this.inIf = true;
		ifEmpty(cObj);
		this.isOK = (!this.isOK);
		return this;
	}

	public SqlBuilder ifEquals(Object cObj, Object cObj2) {
		this.inIf = true;
		this.isOK = ((cObj == cObj2) || (cObj2.equals(cObj)));
		return this;
	}

	public SqlBuilder ifNotEquals(Object cObj, Object cObj2) {
		this.inIf = true;
		ifEquals(cObj, cObj2);
		this.isOK = (!this.isOK);
		return this;
	}

	public SqlBuilder ifEquals(long cObj, long cObj2) {
		this.inIf = true;
		this.isOK = (cObj == cObj2);
		return this;
	}

	public SqlBuilder ifNotEquals(long cObj, long cObj2) {
		this.inIf = true;
		ifEquals(cObj, cObj2);
		this.isOK = (!this.isOK);
		return this;
	}

	public SqlBuilder ifEquals(double cObj, double cObj2) {
		this.inIf = true;
		this.isOK = (cObj == cObj2);
		return this;
	}

	public SqlBuilder ifNotEquals(double cObj, double cObj2) {
		this.inIf = true;
		ifEquals(cObj, cObj2);
		this.isOK = (!this.isOK);
		return this;
	}

	public SqlBuilder ifLess(long cObj, long cObj2) {
		this.inIf = true;
		this.isOK = (cObj < cObj2);
		return this;
	}

	public SqlBuilder ifGreat(long cObj, long cObj2) {
		this.inIf = true;
		this.isOK = (cObj > cObj2);
		return this;
	}

	public SqlBuilder ifLessEq(long cObj, long cObj2) {
		this.inIf = true;
		this.isOK = (cObj <= cObj2);
		return this;
	}

	public SqlBuilder ifGreatEq(long cObj, long cObj2) {
		this.inIf = true;
		this.isOK = (cObj >= cObj2);
		return this;
	}

	public SqlBuilder ifLess(double cObj, double cObj2) {
		this.inIf = true;
		this.isOK = (cObj < cObj2);
		return this;
	}

	public SqlBuilder ifGreat(double cObj, double cObj2) {
		this.inIf = true;
		this.isOK = (cObj > cObj2);
		return this;
	}

	public SqlBuilder ifLessEq(double cObj, double cObj2) {
		this.inIf = true;
		this.isOK = (cObj <= cObj2);
		return this;
	}

	public SqlBuilder ifGreatEq(double cObj, double cObj2) {
		this.inIf = true;
		this.isOK = (cObj >= cObj2);
		return this;
	}

	public SqlBuilder ifLess(String cObj, String cObj2) {
		this.inIf = true;
		this.isOK = (cObj.compareTo(cObj2) < 0);
		return this;
	}

	public SqlBuilder ifGreat(String cObj, String cObj2) {
		this.inIf = true;
		this.isOK = (cObj.compareTo(cObj2) > 0);
		return this;
	}

	public SqlBuilder ifLessEq(String cObj, String cObj2) {
		this.inIf = true;
		this.isOK = (cObj.compareTo(cObj2) <= 0);
		return this;
	}

	public SqlBuilder ifGreatEq(String cObj, String cObj2) {
		this.inIf = true;
		this.isOK = (cObj.compareTo(cObj2) >= 0);
		return this;
	}

	public void setOutSQL(StringBuffer outSQL) {
		this.outSQL = outSQL;
	}

	public List<Object> getArgList() {
		return this.argList;
	}

	public void setArgList(List<Object> argList) {
		this.argList = argList;
	}

	public boolean isOK() {
		return this.isOK;
	}

	public void setOK(boolean isOK) {
		this.isOK = isOK;
	}
}