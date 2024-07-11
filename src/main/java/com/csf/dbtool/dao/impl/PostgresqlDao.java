package com.csf.dbtool.dao.impl;

import com.csf.dbtool.dao.IDao;
import com.csf.dbtool.model.Column;
import com.csf.dbtool.model.DBTable;
import com.csf.dbtool.model.Index;
import com.csf.dbtool.util.DBHelper;
import com.csf.dbtool.util.SQLUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

/**
 * @author fuping
 */
@Slf4j
public class PostgresqlDao implements IDao {
    @Override
    public List<DBTable> selectTableNameAll(DBHelper dbHelper) {
        String sql = "SELECT  c.relname AS tableName,  CAST(obj_description(c.oid, 'pg_class') AS VARCHAR) AS tableComment,NULL AS groupIndex FROM pg_class c  JOIN pg_namespace n ON c.relnamespace = n.oid WHERE c.relkind = 'r' AND n.nspname = '" + dbHelper.getSchema() + "' ORDER BY c.relname;";
        log.info("selectTableNameAll SQL:{}", sql);
        List<DBTable> tables = dbHelper.getList(DBTable.class, sql);
        dbHelper.close();
        return tables;
    }

    @Override
    public List<DBTable> selectTableNameByTableNames(DBHelper dbHelper, String tableNames) {
        String sql = "SELECT relname AS tableName,cast( obj_description ( relfilenode, 'pg_class' ) AS VARCHAR ) AS tableComment,null as groupIndex FROM pg_class c WHERE relkind = 'r' AND relname NOT LIKE 'pg_%' AND relname NOT LIKE 'sql_%' and relname in (" + SQLUtil.sqlForIn(tableNames) + ") and relname in ( SELECT tablename FROM pg_tables WHERE schemaname = '" + dbHelper.getSchema() + "' AND POSITION ( '_2' IN tablename ) = 0 ) ORDER BY relname";
        log.info("selectTableNameByTableNames SQL:{}", sql);
        List<DBTable> tables = dbHelper.getList(DBTable.class, sql);
        //dbHelper.close();
        return tables;
    }

    @Override
    public List<Column> selectColumnByTableName(DBHelper dbHelper, String tableNames) {
        String sql = "SELECT C.relname tableName, A.attnum orderNumber, A.attname columnName, null indexName, CASE WHEN A.attnotnull = 't' THEN 'NO' ELSE 'YES' END isNullable, d.description columnComment, concat_ws ( '', T.typname, SUBSTRING ( format_type ( A.atttypid, A.atttypmod ) FROM '\\(.*\\)' ) ) AS columnType FROM pg_class C, pg_attribute A, pg_type T, pg_description d WHERE A.attnum > 0 AND A.attrelid = C.OID AND A.atttypid = T.OID AND d.objoid = A.attrelid AND d.objsubid = A.attnum AND C.relname IN ( SELECT tablename FROM pg_tables WHERE schemaname = '" + dbHelper.getSchema() + "' AND POSITION ( '_2' IN tablename ) = 0 ) and c.relname in (" + SQLUtil.sqlForIn(tableNames) + ") ORDER BY C.relname, A.attnum;";
        log.info("selectTableNameByTableNames SQL:{}", sql);
        List<Column> columns = dbHelper.getList(Column.class, sql);
        dbHelper.close();
        return columns;
    }

    @Override
    public List<Index> selectIndexByTableName(DBHelper dbHelper, String tableNames) {
        return Collections.emptyList();
    }
}
