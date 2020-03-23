package com.csf.dbtool.util;

public class SQLUtil {

    /****
     * 拼接sql
     * @param parm
     * @return
     */
    public static String sqlForIn(String parm) {
        String[] p = parm.split(",");
        String result = "'";
        for (int i = 0; i < p.length; i++) {
            if (i == 0)
                result = new StringBuilder().append("'").append(p[i]).append("'").toString();
            else result = new StringBuilder().append(result).append(",'").append(p[i]).append("'").toString();
        }
        return result;
    }
}
