package com.csf.dbtool.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author fuping
 */
@Getter
@Setter
public class DMTable {
    //数据表名
    private String TABLENAME;
    //数据表注释
    private String TABLECOMMENT;


    public DBTable getTable() {
        DBTable table = new DBTable();
        table.setTableName(this.TABLENAME);
        table.setTableComment(this.TABLECOMMENT);
        return table;
    }
}
