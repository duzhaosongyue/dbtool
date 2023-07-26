package com.csf.dbtool.dao.impl;

import com.csf.dbtool.dao.IDao;
import com.csf.dbtool.model.Column;
import com.csf.dbtool.model.DBTable;
import com.csf.dbtool.model.Index;
import com.csf.dbtool.util.DBHelper;
import com.csf.dbtool.util.SQLUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 基于oracle的实现
 * @author fuping
 */
@Repository
@Slf4j
public class OracleDao implements IDao {


    @Override
    public List<DBTable> selectTableNameAll(DBHelper dbHelper) {
        String sql = "SELECT A.TABLE_NAME as tableName,A.COMMENTS as tableComment, null as groupIndex FROM USER_TAB_COMMENTS A";
        log.info("oracle selectTableNameAll SQL:{}", sql);
        List<DBTable> tables = dbHelper.getList(DBTable.class, sql);
        dbHelper.close();
        return tables;
    }

    @Override
    public List<DBTable> selectTableNameByTableNames(DBHelper dbHelper, String tableNames) {
        String sql = "SELECT A.TABLE_NAME as tableName,A.COMMENTS as tableComment, null as groupIndex FROM USER_TAB_COMMENTS A  " +
                " where A.TABLE_NAME in (" + SQLUtil.sqlForIn(tableNames) + ")";
        log.info("oracle selectTableNameByTableNames SQL:{}", sql);
        List<DBTable> tables = dbHelper.getList(DBTable.class, sql);
        dbHelper.close();
        return tables;
    }



    @Override
    public List<Column> selectColumnByTableName(DBHelper dbHelper, String tableNames) {
        String sql = "select A.COLUMN_ID as orderNumber, A.COLUMN_NAME as columnName,CONCAT(concat(A.DATA_TYPE, '('),CONCAT(A.DATA_LENGTH, ')')) as columnType,A.NULLABLE as  isNullable,B.COMMENTS as columnComment,A.TABLE_NAME as tableName,null as indexName from user_tab_columns A " +
                "LEFT JOIN user_col_comments B on B.TABLE_NAME = A.TABLE_NAME and A.COLUMN_NAME = B.COLUMN_NAME" +
                " where A.TABLE_NAME in (" + SQLUtil.sqlForIn(tableNames) + ") order by orderNumber";
        log.info("oracle selectColumnByTableName SQL:{}", sql);
        List<Column> columns = dbHelper.getList(Column.class, sql);
        dbHelper.close();
        return columns;
    }


    @Override
    public List<Index> selectIndexByTableName(DBHelper dbHelper, String tableNames) {
        String sql = "SELECT user_ind_columns.table_name as tableName, user_ind_columns.column_name as  " +
                "columnName, user_indexes.uniqueness as  indexName FROM user_ind_columns, user_indexes " +
                "WHERE user_ind_columns.index_name = user_indexes.index_name and user_ind_columns.table_name" +
                " in (" + SQLUtil.sqlForIn(tableNames) + ")";
        log.info("oracle listIndex SQL:{}", sql);
        List<Index> index = dbHelper.getList(Index.class, sql);
        dbHelper.close();
        return index;
    }


}
