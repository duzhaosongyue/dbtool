package com.csf.dbtool.dao;

import com.csf.dbtool.model.Column;
import com.csf.dbtool.model.DBTable;
import com.csf.dbtool.model.Index;
import com.csf.dbtool.util.DBHelperMysql;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITableDao {

    List<DBTable> selectTableNameAll(DBHelperMysql dbHelper);

    List<DBTable> selectTableNameByTableNames(DBHelperMysql dbHelper, String tableNames);

    List<Column> selectColumnByTableName(DBHelperMysql dbHelper, String tableName);

    List<Index> selectIndexByTableName(DBHelperMysql dbHelper, String tableNames);
}
