package com.csf.dbtool.model;

import lombok.*;

/****
 * 数据表字段属性
 * @author fuping
 */
@Getter
@Setter
public class Column {

    /****
     * 序号
     */
    private String orderNumber;

    /****
     * 字段名称
     */
    private String columnName;

    /****
     * 字段名称
     */
    private String columnType;

    /****
     * 字段描述
     */
    private String columnComment;

    /****
     * 允许空
     */
    private String isNullable;

    /****
     * 索引
     */
    private String indexName;

    /****
     * 表名称
     */
    private String tableName;


    public String getValue(Integer index) {
        switch (index) {
            case 0:
                return getOrderNumber();
            case 1:
                return getColumnName();
            case 2:
                return getColumnType();
            case 3:
                return getColumnComment();
            case 4:
                return getIsNullable();
            case 5:
                return getIndexName();
            default:
                return "";
        }
    }

}
