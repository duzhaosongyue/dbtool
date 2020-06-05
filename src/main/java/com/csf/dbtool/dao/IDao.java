package com.csf.dbtool.dao;

import com.csf.dbtool.model.Column;
import com.csf.dbtool.model.DBTable;
import com.csf.dbtool.model.Index;
import com.csf.dbtool.util.DBHelper;

import java.util.List;

public interface IDao {

    List<DBTable> selectTableNameAll(DBHelper dbHelper);

    List<DBTable> selectTableNameByTableNames(DBHelper dbHelper, String tableNames);

    List<Column> selectColumnByTableName(DBHelper dbHelper, String tableName);

    List<Index> selectIndexByTableName(DBHelper dbHelper, String tableNames);
}
