package com.csf.dbtool.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

/**
 * @author fuping
 */
@Getter
@Setter
public class DmColumn {

    //字段名称
    private String COLUMN_NAME;
    //字段类型
    private String COLUMN_TYPE;
    //字段描述
    private String COLUMN_COMMENT;
    //允许空
    private String IS_NULLABLE;

    private String TABLE_NAME;

    private String ORDER_NUMBER;

    private String INDEX_NAME;

    public Column getColumn() {
        Column column = new Column();
        column.setColumnComment(this.COLUMN_COMMENT);
        column.setColumnName(this.COLUMN_NAME);
        column.setColumnType(this.COLUMN_TYPE);
        column.setIsNullable(this.IS_NULLABLE);
        column.setTableName(this.TABLE_NAME);
        column.setOrderNumber(this.ORDER_NUMBER);
        column.setIndexName(this.INDEX_NAME);
        return column;
    }

}
