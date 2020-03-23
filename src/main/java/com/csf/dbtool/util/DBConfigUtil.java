package com.csf.dbtool.util;
import com.csf.dbtool.common.SystemConstant;

public class DBConfigUtil {

    public static String MYSQL_CONNECTION_URL = "jdbc:mysql://MYSQL_IP_PORT/MYSQL_DATABASE_NAME?serverTimezone=UTC&useUnicode=true&charaterEncoding=utf-8&useSSL=false";

    public static String getMysqlConnectionUrl(String ip, Integer port, String databaseName){
        String ipPort = ip +":"+ port;
        return MYSQL_CONNECTION_URL.replace("MYSQL_IP_PORT", ipPort).replace("MYSQL_DATABASE_NAME", databaseName);
    }

    public static String getDefaultMysqlConnectionUrl(){
        return MYSQL_CONNECTION_URL.replace("MYSQL_IP_PORT", SystemConstant.MYSQL_IP_PORT).replace("MYSQL_DATABASE_NAME", SystemConstant.MYSQL_DATABASE_NAME);
    }

}
