package com.csf.dbtool.model;

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

}
