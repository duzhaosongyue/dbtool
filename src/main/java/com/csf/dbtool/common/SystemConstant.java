package com.csf.dbtool.common;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author fuping
 */
public class SystemConstant {

    public static String MYSQL_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    public static String ORACLE_CLASS_NAME = "oracle.jdbc.OracleDriver";

    public static String DM_CLASS_NAME = "dm.jdbc.driver.DmDriver";

    public static String POSTGRESQL_CLASS_NAME = "org.postgresql.Driver";


    @Value("${db.config-driver.mysql}")
    public void setMysqlClassName(String mysqlClassName) {
        MYSQL_CLASS_NAME = mysqlClassName;
    }


    @Value("${db.config-driver.oracle}")
    public void setOracleClassName(String oracleClassName) {
        ORACLE_CLASS_NAME = oracleClassName;
    }

    @Value("${db.config-driver.dm}")
    public void setDmClassName(String dmClassName) {
        DM_CLASS_NAME = dmClassName;
    }

    @Value("${db.config-driver.postgresql}")
    public void setPostgresqlClassName(String postgresqlClassName) {
        POSTGRESQL_CLASS_NAME = postgresqlClassName;
    }
}
