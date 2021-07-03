package com.csf.dbtool.model;

import com.csf.dbtool.common.SystemConstant;
import com.csf.dbtool.dao.IDao;
import com.csf.dbtool.dao.impl.MysqlDao;
import com.csf.dbtool.dao.impl.OracleDao;
import lombok.Getter;
import lombok.Setter;

/**
 * 数据库连接配置
 * @author fuping
 */
@Getter
@Setter
public class DatabaseConnection {

    /****
     * 数据库 0 mysql 1 oracle
     */
    private Integer database;

    /****
     * 数据库服务IP
     */
    private String ip;

    /****
     * 数据库服务端口
     */
    private Integer port;

    /****
     * 用户名称
     */
    private String user;

    /****
     * 密码
     */
    private String pwd;

    /****
     * 数据库名称
     */
    private String databaseName;

    /****
     * 数据库连接驱动
     */
    private String className;

    public String getClassName() {
        if (this.database == 0) {
            return SystemConstant.MYSQL_CLASS_NAME;
        } else if (this.database == 1) {
            return SystemConstant.ORACLE_CLASS_NAME;
        }
        return "";
    }


    public IDao getDao() {
        if (this.database == 0) {
            return new MysqlDao();
        } else if (this.database == 1) {
            return new OracleDao();
        }
        return null;
    }

}
