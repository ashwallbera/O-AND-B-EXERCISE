package com.orangeandbronze.enlistment.init;

import java.io.*;
import java.sql.*;

import org.dbunit.database.*;
import org.dbunit.dataset.xml.*;

import com.orangeandbronze.enlistment.dao.jdbc.*;

public class GenerateDatasetDTD {

	public static void main(String[] args) throws Exception {
		// database connection
        Connection jdbcConnection = DataSourceManager.getDataSource().getConnection();
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

        // write DTD file
        FlatDtdDataSet.write(connection.createDataSet(), new FileOutputStream("src/test/resources/EnlistmentSchema.dtd"));

	}

}
