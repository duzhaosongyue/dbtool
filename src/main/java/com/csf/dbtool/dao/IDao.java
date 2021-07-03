package com.csf.dbtool.dao;

import com.csf.dbtool.model.Column;
import com.csf.dbtool.model.DBTable;
import com.csf.dbtool.model.Index;
import com.csf.dbtool.util.DBHelper;

import java.util.List;

/**
 * 数据库查询接口
 * @author fuping
 */
public interface IDao {

    /****
     * 查询所有的表名称
     * @param dbHelper
     * @return
     */
    List<DBTable> selectTableNameAll(DBHelper dbHelper);

    /****
     * 根据表名称查询表名称
     * @param dbHelper
     * @param tableNames
     * @return
     */
    List<DBTable> selectTableNameByTableNames(DBHelper dbHelper, String tableNames);

    /*****
     * 根据表名称查询列
     * @param dbHelper
     * @param tableName
     * @return
     */
    List<Column> selectColumnByTableName(DBHelper dbHelper, String tableName);

    /****
     * 根据表名称查询索引
     * @param dbHelper
     * @param tableNames
     * @return
     */
    List<Index> selectIndexByTableName(DBHelper dbHelper, String tableNames);
}
