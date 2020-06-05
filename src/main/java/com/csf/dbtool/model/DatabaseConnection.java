package com.csf.dbtool.model;

import com.csf.dbtool.common.SystemConstant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatabaseConnection {

    private Integer database;

    private String ip;

    private Integer port;

    private String user;

    private String pwd;

    private String databaseName;

    private String className;

    public String getClassName() {
        if (this.database == 0) {
            return SystemConstant.MYSQL_CLASS_NAME;
        } else if (this.database == 1) {
            return SystemConstant.ORACLE_CLASS_NAME;
        }
        return "";
    }

}
