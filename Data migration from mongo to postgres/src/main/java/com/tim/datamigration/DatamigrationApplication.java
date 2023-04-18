package com.tim.datamigration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tim.datamigration.DB.TableWithBinarydataObj;
import com.tim.datamigration.MongoFunction.MongoConnectionObj;
import com.tim.datamigration.PostgresFunction.PostgresConnectionObj;

@SpringBootApplication
public class DatamigrationApplication {

	/**
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		// args[0] = mongoConnectionInfFilePath
		MongoConnectionObj mongoConnectionObj = new MongoConnectionObj(args[0]);
		mongoConnectionObj.iterateAllCollections();

		// args[1] = postgresConnectionInfFilePath
		PostgresConnectionObj postgresConnectionObj = new PostgresConnectionObj(args[1]);

		// set table with binary object
		TableWithBinarydataObj tableWithBinarydataObj1 = new TableWithBinarydataObj();
		String[] binaryDataColumns = { "svg" };
		tableWithBinarydataObj1.setTableName("DView8_PanelTemplate");
		tableWithBinarydataObj1.setBinaryDataColumns(binaryDataColumns);

		TableWithBinarydataObj tableWithBinarydataObj2 = new TableWithBinarydataObj();
		String[] binaryDataColumns2 = { "str1", "str2" };
		tableWithBinarydataObj2.setTableName("CreateCollection");
		tableWithBinarydataObj2.setBinaryDataColumns(binaryDataColumns2);

		// add object to list
		List<TableWithBinarydataObj> tableWithBinarydataObjList = new ArrayList<TableWithBinarydataObj>();
		tableWithBinarydataObjList.add(tableWithBinarydataObj1);
		tableWithBinarydataObjList.add(tableWithBinarydataObj2);

		// create all tables
		postgresConnectionObj.createAllTables(tableWithBinarydataObjList);

		// insert documents to table
		postgresConnectionObj.insertDocumentsToTable(tableWithBinarydataObjList);
	}

}