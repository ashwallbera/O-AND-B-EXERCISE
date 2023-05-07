package com.orangeandbronze.enlistment.init;

import org.dbunit.database.*;
import org.dbunit.dataset.*;
import org.dbunit.operation.*;

import com.orangeandbronze.enlistment.dao.jdbc.*;

public class InitializeDB {

	public static void main(String[] args) throws Exception {
		String dataset = "DefaultDataset.xml";
		IDataSet dataSet = DbUnitUtil.createFlatXmlDataSet(dataset);
		IDatabaseConnection connection = DbUnitUtil.getIDatabaseConnection();
		try {
			DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
		} finally {
			connection.close();
		}
		System.out.println("Database initialized with '" + dataset + "'");
	}

}
