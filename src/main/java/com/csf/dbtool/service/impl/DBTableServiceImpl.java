package com.csf.dbtool.service.impl;

import com.csf.dbtool.dao.ITableDao;
import com.csf.dbtool.model.Column;
import com.csf.dbtool.model.DBTable;
import com.csf.dbtool.model.DatabaseConnection;
import com.csf.dbtool.model.Index;
import com.csf.dbtool.service.IDBTableService;
import com.csf.dbtool.util.DBHelperMysql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DBTableServiceImpl implements IDBTableService {

    @Autowired
    private ITableDao iTableDao;



    @Override
    public List<DBTable> selectByTableName(DatabaseConnection connection) {
        DBHelperMysql dbHelper = new DBHelperMysql(connection);
        return iTableDao.selectTableNameAll(dbHelper);
    }

    @Override
    public List<DBTable> selectTableNameByTableNames(DatabaseConnection connection, String tableNames) {
        DBHelperMysql dbHelper = new DBHelperMysql(connection);
        List<DBTable> tables = iTableDao.selectTableNameByTableNames(dbHelper, tableNames);
        List<Column> columns = iTableDao.selectColumnByTableName(dbHelper, tableNames);
        List<Index> indices = iTableDao.selectIndexByTableName(dbHelper, tableNames);
        dataEncapsulation(tables, columns, indices);
        return tables;
    }


    private static void dataEncapsulation(List<DBTable> tables, List<Column> columns, List<Index> indices){
        Map<String, List<Column>> columnMap = columns.stream().collect(Collectors.groupingBy(Column::getTableName));
        Map<String, List<Index>> indexMap = indices.stream().collect(Collectors.groupingBy(Index::getTableName));
        for (DBTable table: tables) {
            String tableName = table.getTableName();
            List<Index> tableIndexes = indexMap.get(tableName);
            dataMatch(table, columnMap.get(tableName), tableIndexes);
        }
    }


    private static void dataMatch(DBTable table, List<Column> columns, List<Index> indices){
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
            table.setGroupIndex(groupIndex.toString());
        }
        table.setColumns(columns);
    }

}
