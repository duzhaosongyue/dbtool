package com.csf.dbtool.util;

import com.csf.dbtool.model.DatabaseConnection;

/**
 * @author fuping
 */
public class GetConnectionInfo {

    /***
     * IP:PORT/MYSQL_DATABASE_NAME
     */
    public static String MYSQL_CONNECTION_URL = "jdbc:mysql://%s:%s/%s?serverTimezone=UTC&useUnicode=true&charaterEncoding=utf-8&useSSL=false";

    /***
     * IP:PORT/SERVICENAME
     */
    public static String ORACLE_CONNECTION_URL = "jdbc:oracle:thin:@//%s:%s/%s";

    /***
     * IP:PORT
     */
    public static String DA_MENG_IP_DA_MENG_URL = "jdbc:dm://%s:%s/%s?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=utf-8";

    /***
     * IP:PORT/DATABASE_NAME
     */
    public static String POSTGRESQL_IP_DA_MENG_URL = "jdbc:postgresql://%s:%s/%s?currentSchema=%s";

    /***
     * 获取mysql的连接地址
     * @param connection
     * @return
     */
    public static String getMysqlConnectionUrl(DatabaseConnection connection) {
        return String.format(MYSQL_CONNECTION_URL, connection.getIp(), connection.getPort(), connection.getDatabaseName());
    }

    /***
     * 获取ORACLE的连接地址
     * @param connection
     * @return
     */
    public static String getORACLEConnectionUrl(DatabaseConnection connection) {
        return String.format(ORACLE_CONNECTION_URL, connection.getIp(), connection.getPort(), connection.getDatabaseName());
    }

    /****
     * 获取达梦的连接地址
     * @param connection
     * @return
     */
    public static String getDAMENGConnectionUrl(DatabaseConnection connection) {
        return String.format(DA_MENG_IP_DA_MENG_URL, connection.getIp(), connection.getPort(), connection.getDatabaseName());
    }

    /****
     * 获取postgresql 连接地址
     * @param connection
     * @return
     */
    public static String getPostgresqlConnectionUrl(DatabaseConnection connection) {
        return String.format(POSTGRESQL_IP_DA_MENG_URL, connection.getIp(), connection.getPort(), connection.getDatabaseName(),connection.getSchema());
    }

    public static String getConnectionUrl(DatabaseConnection connection) {
        if (connection.getDatabase() == 0) {
            return getMysqlConnectionUrl(connection);
        } else if (connection.getDatabase() == 1) {
            return getORACLEConnectionUrl(connection);
        } else if (connection.getDatabase() == 2) {
            return getDAMENGConnectionUrl(connection);
        } else if (connection.getDatabase() == 3) {
            return getPostgresqlConnectionUrl(connection);
        }
        return "";
    }


}
