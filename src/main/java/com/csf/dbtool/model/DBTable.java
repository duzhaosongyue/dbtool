package com.csf.dbtool.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/****
 * 数据表数据类
 * @author fuping
 */
@Setter
@Getter
public class DBTable {

    /****
     * 数据表名
     */
    private String tableName;

    /****
     * 数据名表注释
     */
    private String tableComment;

    /****
     * 表的数据列
     */
    private List<Column> columns;

    /****
     * 组合索引
     */
    private String groupIndex;
}
