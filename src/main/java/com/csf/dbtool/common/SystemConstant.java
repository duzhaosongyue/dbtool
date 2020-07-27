package com.csf.dbtool.common;

import org.springframework.beans.factory.annotation.Value;

public class SystemConstant {

    public static String MYSQL_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    public static String ORACLE_CLASS_NAME = "oracle.jdbc.OracleDriver";


    @Value("${db.config-driver.mysql}")
    public void setMysqlClassName(String mysqlClassName) {
        MYSQL_CLASS_NAME = mysqlClassName;
    }


    @Value("${db.config-driver.oracle}")
    public void setOracleClassName(String oracleClassName) {
        ORACLE_CLASS_NAME = oracleClassName;
    }
}
