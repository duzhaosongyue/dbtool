package com.csf.dbtool.model;

import lombok.Getter;
import lombok.Setter;

/****
 * 表索引属性
 */
@Getter
@Setter
public class Index {

    private String tableName;

    private String indexName;

    private String columnName;
}
