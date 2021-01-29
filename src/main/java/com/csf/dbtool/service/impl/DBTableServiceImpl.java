package com.csf.dbtool.service.impl;

import com.csf.dbtool.dao.IDao;
import com.csf.dbtool.dao.impl.MysqlDao;
import com.csf.dbtool.dao.impl.OracleDao;
import com.csf.dbtool.model.DBTable;
import com.csf.dbtool.model.DatabaseConnection;
import com.csf.dbtool.service.IDBTableService;
import com.csf.dbtool.util.DataQueryTool;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBTableServiceImpl implements IDBTableService{

    /*****
     * 查询mysql数据库所有表
     * @param connection  连接信息
     * @return
     */
    public List<DBTable> selectByTableName(DatabaseConnection connection) {
        return DataQueryTool.selectByTableName(connection);
    }


    /*****
     * 根据表名查询数据库表列表
     * @param connection   连接信息
     * @param tableNames   表名
     * @return
     */
    public List<DBTable> selectTableNameByTableNames(DatabaseConnection connection, String tableNames) {
        return DataQueryTool.selectTableNameByTableNames(connection, tableNames);
    }


}
