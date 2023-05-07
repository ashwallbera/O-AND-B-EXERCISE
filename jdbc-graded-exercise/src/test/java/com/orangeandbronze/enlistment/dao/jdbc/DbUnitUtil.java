package com.orangeandbronze.enlistment.dao.jdbc;

import java.io.*;
import java.sql.*;

import org.dbunit.*;
import org.dbunit.database.*;
import org.dbunit.dataset.*;
import org.dbunit.dataset.xml.*;
import org.dbunit.ext.h2.*;
import org.dbunit.ext.postgresql.*;
import org.dbunit.operation.*;

import com.orangeandbronze.enlistment.dao.*;

public class DbUnitUtil {

	public static IDataSet createFlatXmlDataSet(String datasetFilename)
			throws DataSetException, IOException {
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		builder.setDtdMetadata(false);
		// builder.setMetaDataSetFromDtd(DbUnitUtil.class.getClassLoader()
		// .getResourceAsStream("EnlistmentSchema.dtd"));
		builder.setColumnSensing(true);
		IDataSet dataSet = builder.build(DbUnitUtil.class.getClassLoader()
				.getResourceAsStream(datasetFilename));
		return dataSet;
	}

	public static IDatabaseConnection getIDatabaseConnection()
			throws SQLException, DatabaseUnitException {
		Connection jdbcConnection = DataSourceManager.getDataSource()
				.getConnection();
		// turn off referential integrity to make it easy to insert and delete
		// test data
		String sqlH2 = "SET REFERENTIAL_INTEGRITY FALSE";
		String sqlPG = "SET CONSTRAINTS ALL DEFERRED";
		jdbcConnection.createStatement().execute(sqlPG);
		DatabaseConnection dbUnitConn = new DatabaseConnection(jdbcConnection);
		// dbUnitConn.getConfig().setProperty(
		// DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
		// new H2DataTypeFactory());
		dbUnitConn.getConfig().setProperty(
				DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
				new PostgresqlDataTypeFactory());
		return dbUnitConn;
	}

	public static StudentDAO cleanInsertAndCreateDao(String datasetFilename)
			throws Exception {
		initData(datasetFilename);
		return new StudentDaoJdbc(DataSourceManager.getDataSource());
	}

	public static void initData(String datasetFilename) throws SQLException,
			DatabaseUnitException, DataSetException, IOException {
		IDataSet dataSet = createFlatXmlDataSet(datasetFilename);
		IDatabaseConnection connection = null;
		try {
			connection = getIDatabaseConnection();
			DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
		} finally {
			connection.close();
		}
	}

}
