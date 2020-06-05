package com.csf.dbtool.util;

import com.csf.dbtool.model.Column;
import com.csf.dbtool.model.DBTable;
import com.csf.dbtool.model.Index;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/****
 * 数据处理
 */
public class DataProcess {

    /*****
     * 将查询出来的列字段匹配到相应的表下面
     * @param tables     表列表
     * @param columns    字段列表
     * @param indices    索引列表
     */
    public static void dataEncapsulation(List<DBTable> tables, List<Column> columns, List<Index> indices) {
        Map<String, List<Column>> columnMap = columns.stream().collect(Collectors.groupingBy(Column::getTableName));
        Map<String, List<Index>> indexMap = indices.stream().collect(Collectors.groupingBy(Index::getTableName));
        for (DBTable table : tables) {
            String tableName = table.getTableName();
            List<Index> tableIndexes = indexMap.get(tableName);
            dataMatch(table, columnMap.get(tableName), tableIndexes);
        }
    }


    /****
     * 将查询的索引匹配到相应的字段信息
     * @param table
     * @param columns
     * @param indices
     */
    private static void dataMatch(DBTable table, List<Column> columns, List<Index> indices) {
        StringBuilder groupIndex = new StringBuilder("");
        if (indices != null) {
            for (Index index : indices) {
                String columnName = index.getColumnName();
                if (columnName.split(",").length > 1) {
                    groupIndex.append(index.getColumnName()).append(";");
                } else {
                    for (Column column : columns) {
                        if (column.getColumnName().equals(columnName)) {
                            column.setIndexName(index.getIndexName());
                        }
                    }
                }

            }
        }
        if (groupIndex.length() > 0) {
            //组合索引
            table.setGroupIndex(groupIndex.toString());
        }
        table.setColumns(columns);
    }

}
