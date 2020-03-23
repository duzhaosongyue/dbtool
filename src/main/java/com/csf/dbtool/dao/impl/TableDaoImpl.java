package com.csf.dbtool.dao.impl;

import com.csf.dbtool.dao.ITableDao;
import com.csf.dbtool.model.Column;
import com.csf.dbtool.model.DBTable;
import com.csf.dbtool.model.Index;
import com.csf.dbtool.util.DBHelperMysql;
import com.csf.dbtool.util.SQLUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class TableDaoImpl implements ITableDao {

    public List<DBTable> selectTableNameByTableNames(DBHelperMysql dbHelper, String tableNames) {

        String sql = "SELECT table_name as tableName,table_comment as tableComment,null as groupIndex FROM information_schema.TABLES WHERE" +
                " table_schema = '" + dbHelper.getDatabaseName() + "' and table_name in (" + SQLUtil.sqlForIn(tableNames) + ")";

        log.info("selectTableNameByTableNames SQL:{}", sql);
        List<DBTable> tables = dbHelper.getList(DBTable.class, sql);
        dbHelper.close();
        return tables;
    }


    public List<DBTable> selectTableNameAll(DBHelperMysql dbHelper) {

        String sql = "SELECT table_name as tableName,table_comment as tableComment,null as groupIndex FROM information_schema.TABLES WHERE" +
                " table_schema = '" + dbHelper.getDatabaseName() + "'";

        log.info("selectTableNameAll SQL:{}", sql);
        List<DBTable> tables = dbHelper.getList(DBTable.class, sql);
        dbHelper.close();
        return tables;
    }


    public List<Column> selectColumnByTableName(DBHelperMysql dbHelper, String tableNames){

        String sql = "SELECT table_name as tableName,ordinal_position AS orderNumber,column_name AS columnName,column_type AS columnType,column_comment " +
                "AS columnComment,is_nullable AS isNullable, null as indexName " +
                "FROM information_schema.COLUMNS WHERE table_schema = '" + dbHelper.getDatabaseName() + "' AND table_name in ("+SQLUtil.sqlForIn(tableNames) +")";

        log.info("selectColumnByTableName SQL:{}", sql);
        List<Column> columns = dbHelper.getList(Column.class, sql);
        dbHelper.close();
        return columns;
    }


    public List<Index> selectIndexByTableName(DBHelperMysql dbHelper, String tableNames){

        String sql = "SELECT a.table_name as tableName,a.index_name as indexName,a.stat_description as columnName FROM " +
                "mysql.`innodb_index_stats` a WHERE a.`database_name` = '" + dbHelper.getDatabaseName() + "' " +
                "and a.stat_value = 0 AND a.table_name in (" + SQLUtil.sqlForIn(tableNames) + ")";

        log.info("selectIndexByTableName SQL:{}", sql);
        List<Index> indices = dbHelper.getList(Index.class, sql);
        dbHelper.close();
        return indices;
    }

}
