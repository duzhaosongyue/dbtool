package com.csf.dbtool.util;

import com.csf.dbtool.dao.IDao;
import com.csf.dbtool.dao.impl.DmDao;
import com.csf.dbtool.dao.impl.MysqlDao;
import com.csf.dbtool.dao.impl.OracleDao;
import com.csf.dbtool.dao.impl.PostgresqlDao;
import com.csf.dbtool.model.Column;
import com.csf.dbtool.model.DBTable;
import com.csf.dbtool.model.DatabaseConnection;
import com.csf.dbtool.model.Index;

import java.util.List;

/**
 * @author fuping
 */
public class DataQueryTool {

    /****
     * 抽象出公共的根据连接查询所有表的工具类
     * @param connection
     * @return
     */
    public static List<DBTable> selectByTableName(DatabaseConnection connection) {
        DBHelper dbHelper = new DBHelper(connection);
        IDao dao = getDao(connection);
        return dao.selectTableNameAll(dbHelper);
    }


    private static IDao getDao(DatabaseConnection connection) {
        switch (connection.getDatabase()) {
            case 0:
                return new MysqlDao();
            case 1:
                return new OracleDao();
            case 2:
                return new DmDao();
            case 3:
                return new PostgresqlDao();
            default:
                return null;
        }
    }


    /*****
     * 抽象出公共根据表名查询数据的工具类
     * @param connection
     * @param tableNames
     * @return
     */
    public static List<DBTable> selectTableNameByTableNames(DatabaseConnection connection, String tableNames) {
        IDao dao = getDao(connection);
        DBHelper dbHelper = new DBHelper(connection);
        List<DBTable> tables = dao.selectTableNameByTableNames(dbHelper, tableNames);
        List<Column> columns = dao.selectColumnByTableName(dbHelper, tableNames);
        List<Index> indices = dao.selectIndexByTableName(dbHelper, tableNames);
        DataProcess.dataEncapsulation(tables, columns, indices);
        return tables;
    }

}
