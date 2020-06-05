package com.csf.dbtool.util;

import com.csf.dbtool.model.DatabaseConnection;

public class DBConfigUtil {

    public static String MYSQL_CONNECTION_URL = "jdbc:mysql://MYSQL_IP_PORT/MYSQL_DATABASE_NAME?serverTimezone=UTC&useUnicode=true&charaterEncoding=utf-8&useSSL=false";

    public static String ORACLE_CONNECTION_URL = "jdbc:oracle:thin:@//ORACLE_IP_PORT/SERVICENAME";

    public static String getMysqlConnectionUrl(DatabaseConnection connection) {
        String ipPort = connection.getIp() + ":" + connection.getPort();
        return MYSQL_CONNECTION_URL.replace("MYSQL_IP_PORT", ipPort).replace("MYSQL_DATABASE_NAME", connection.getDatabaseName());
    }

    public static String getORACLEConnectionUrl(DatabaseConnection connection) {
        String ipPort = connection.getIp() + ":" + connection.getPort();
        return ORACLE_CONNECTION_URL.replace("ORACLE_IP_PORT", ipPort).replace("SERVICENAME", connection.getDatabaseName());
    }

    public static String getConnectionUrl(DatabaseConnection connection) {
        if (connection.getDatabase() == 0) {
            return getMysqlConnectionUrl(connection);
        } else if (connection.getDatabase() == 1) {
            return getORACLEConnectionUrl(connection);
        }
        return "";
    }

}
