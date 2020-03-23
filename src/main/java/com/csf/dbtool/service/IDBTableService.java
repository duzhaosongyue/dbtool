package com.csf.dbtool.service;

import com.csf.dbtool.model.DBTable;
import com.csf.dbtool.model.DatabaseConnection;

import java.util.List;

public interface IDBTableService {

    List<DBTable> selectByTableName(DatabaseConnection connection);

    List<DBTable> selectTableNameByTableNames(DatabaseConnection connection, String tableNames);
}
