package com.csf.dbtool.dao.impl;

import com.csf.dbtool.dao.IDao;
import com.csf.dbtool.model.*;
import com.csf.dbtool.util.DBHelper;
import com.csf.dbtool.util.SQLUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author fuping
 */
@Slf4j
public class DmDao implements IDao {
    @Override
    public List<DBTable> selectTableNameAll(DBHelper dbHelper) {
        String sql = "select TVNAME tableName,COMMENT$ as tableComment from SYSTABLECOMMENTS where SCHNAME='%s';";
        log.info("selectTableNameAll SQL:{}", String.format(sql, dbHelper.getDatabaseName()));
        return getDbTables(dbHelper, sql);
    }

    @Override
    public List<DBTable> selectTableNameByTableNames(DBHelper dbHelper, String tableNames) {
        String sql = "select TVNAME tableName,COMMENT$ as tableComment from SYSTABLECOMMENTS where SCHNAME='%s' and TVNAME in (" + SQLUtil.sqlForIn(tableNames) + ")";
        log.info("selectTableNameByTableNames SQL:{}", String.format(sql, dbHelper.getDatabaseName()));
        return getDbTables(dbHelper, sql);
    }

    private List<DBTable> getDbTables(DBHelper dbHelper, String sql) {
        List<DMTable> tables = dbHelper.getList(DMTable.class, String.format(sql, dbHelper.getDatabaseName()));
        List<DBTable> result = new ArrayList<>();
        tables.forEach(t -> result.add(t.getTable()));
        dbHelper.close();
        return result;
    }

    @Override
    public List<Column> selectColumnByTableName(DBHelper dbHelper, String tableNames) {
        String sql = "select " +
                "t1.TABLE_NAME," +
                "t1.COLUMN_NAME," +
                "t1.COLUMN_ID order_Number," +
                "CONCAT(t1.DATA_TYPE,'(',t1.DATA_LENGTH,')') as COLUMN_TYPE," +
                "t1.NULLABLE as IS_NULLABLE," +
                "t2.comments COLUMN_COMMENT," +
                "t3.index_name INDEX_NAME" +
                " from dba_tab_columns t1 " +
                "INNER JOIN  ALL_COL_COMMENTS t2 on t1.table_name = t2.table_name and t1.COLUMN_NAME=t2.COLUMN_NAME " +
                "left join (select TABLE_NAME,COLUMN_NAME, listagg(index_name ,',') index_name from DBA_IND_COLUMNS" +
                " group by TABLE_NAME,COLUMN_NAME) t3 on t1.table_name = t3.table_name and t1.COLUMN_NAME=t3.COLUMN_NAME " +
                "where t1.OWNER='%s' and t1.TABLE_NAME in (" + SQLUtil.sqlForIn(tableNames) + ")  order by order_Number";

        log.info("selectTableNameByTableNames SQL:{}", String.format(sql, dbHelper.getDatabaseName()));
        List<DmColumn> tables = dbHelper.getList(DmColumn.class, String.format(sql, dbHelper.getDatabaseName()));
        List<Column> result = new ArrayList<>();
        tables.forEach(t -> result.add(t.getColumn()));
        return result;
    }

    @Override
    public List<Index> selectIndexByTableName(DBHelper dbHelper, String tableNames) {
        return Collections.emptyList();
    }
}
