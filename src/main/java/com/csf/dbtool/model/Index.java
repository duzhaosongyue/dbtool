package com.csf.dbtool.model;

import lombok.Getter;
import lombok.Setter;

/****
 * 表索引属性
 * @author fuping
 */
@Getter
@Setter
public class Index {

    /*****
     * 所属表
     */
    private String tableName;

    /****
     * 索引名称
     */
    private String indexName;

    /****
     * 绑定的列
     */
    private String columnName;
}
