package com.csf.dbtool.service.impl;

import com.csf.dbtool.model.DBTable;
import com.csf.dbtool.model.DatabaseConnection;
import com.csf.dbtool.service.IDBTableService;
import com.csf.dbtool.util.DataQueryTool;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fuping
 */
@Service
public class DBTableServiceImpl implements IDBTableService{

    /*****
     * 查询mysql数据库所有表
     * @param connection  连接信息
     * @return
     */
    @Override
    public List<DBTable> selectByTableName(DatabaseConnection connection) {
        return DataQueryTool.selectByTableName(connection);
    }


    /*****
     * 根据表名查询数据库表列表
     * @param connection   连接信息
     * @param tableNames   表名
     * @return
     */
    @Override
    public List<DBTable> selectTableNameByTableNames(DatabaseConnection connection, String tableNames) {
        return DataQueryTool.selectTableNameByTableNames(connection, tableNames);
    }


}
