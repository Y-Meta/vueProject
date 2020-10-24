package com.plmm.datasource.druid.mapper;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.object.StoredProcedure;

public class StoredProcedureHandler extends StoredProcedure {
	public StoredProcedureHandler() {
	}

	public StoredProcedureHandler(DataSource ds) {
		this();
		setDataSource(ds);
	}

	public StoredProcedureHandler(DataSource ds, String sql) {
		super(ds, sql);
	}

	public StoredProcedureHandler(JdbcTemplate jdbcTemplate, String name) {
		super(jdbcTemplate, name);
	}
}
