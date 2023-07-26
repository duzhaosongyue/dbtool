package com.csf.dbtool.service;

import com.csf.dbtool.model.DBTable;
import com.csf.dbtool.model.DatabaseConnection;

import java.util.List;

/**
 * @author fuping
 */
public interface IDbTableService {

    /*****
     * 查询mysql数据库所有表
     * @param connection  连接信息
     * @return
     */
    List<DBTable> selectByTableName(DatabaseConnection connection);

    /*****
     * 根据表名查询数据库表列表
     * @param connection   连接信息
     * @param tableNames   表名
     * @return
     */
    List<DBTable> selectTableNameByTableNames(DatabaseConnection connection, String tableNames);
}
