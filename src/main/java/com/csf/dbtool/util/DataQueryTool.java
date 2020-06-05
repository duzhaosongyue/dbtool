package com.csf.dbtool.util;

import com.csf.dbtool.dao.IDao;
import com.csf.dbtool.dao.impl.MysqlDao;
import com.csf.dbtool.model.Column;
import com.csf.dbtool.model.DBTable;
import com.csf.dbtool.model.DatabaseConnection;
import com.csf.dbtool.model.Index;

import java.util.List;

public class DataQueryTool {

    /****
     * 抽象出公共的根据连接查询所有表的工具类
     * @param dao
     * @param connection
     * @return
     */
    public static List<DBTable> selectByTableName(IDao dao, DatabaseConnection connection) {
        DBHelper dbHelper = new DBHelper(connection);
        return dao.selectTableNameAll(dbHelper);
    }

    /*****
     * 抽象出公共根据表名查询数据的工具类
     * @param dao
     * @param connection
     * @param tableNames
     * @return
     */
    public static List<DBTable> selectTableNameByTableNames(IDao dao, DatabaseConnection connection, String tableNames) {
        DBHelper dbHelper = new DBHelper(connection);
        List<DBTable> tables = dao.selectTableNameByTableNames(dbHelper, tableNames);
        List<Column> columns = dao.selectColumnByTableName(dbHelper, tableNames);
        List<Index> indices = dao.selectIndexByTableName(dbHelper, tableNames);
        DataProcess.dataEncapsulation(tables, columns, indices);
        return tables;
    }

}
