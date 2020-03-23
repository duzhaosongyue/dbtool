package com.csf.dbtool.controller;

import com.csf.dbtool.model.DBTable;
import com.csf.dbtool.model.DatabaseConnection;
import com.csf.dbtool.model.ResultMode;
import com.csf.dbtool.service.IDBTableService;
import com.csf.dbtool.util.DBHelperMysql;
import com.csf.dbtool.util.DocUtil;
import com.csf.dbtool.util.HttpSessionUtil;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.util.List;

@RequestMapping("/")
@Controller
public class DbToolController {

    @Autowired
    private IDBTableService idbTableService;

    @RequestMapping("index")
    public String connectConfig(){
        return "connect_config.html";
    }


    @RequestMapping("list")
    public String list(){
        return "export_word.html";
    }

    @RequestMapping("connection")
    @ResponseBody
    public ResultMode connection(HttpSession session, DatabaseConnection connection){
        DBHelperMysql mysql = new DBHelperMysql(connection);
        if (mysql.testConnection()) {
            HttpSessionUtil.writSessionConnection(connection, session);
            return ResultMode.builder().code(200).build();
        } else {
            return ResultMode.builder().code(500).build();
        }
    }


    @RequestMapping("testConnection")
    @ResponseBody
    public ResultMode testConnection(DatabaseConnection connection) {
        DBHelperMysql mysql = new DBHelperMysql(connection);
        if (mysql.testConnection()) {
            return ResultMode.builder().code(200).build();
        } else {
            return ResultMode.builder().code(500).build();
        }
    }


    @ResponseBody
    @RequestMapping("selectByTableName")
    public ResultMode selectByTableName(HttpSession session){
        if (session != null) {
            DatabaseConnection connection = HttpSessionUtil.getSession(session);
            if (connection != null) {
                List<DBTable> dbTables = idbTableService.selectByTableName(connection);
                return ResultMode.builder().code(200).result(dbTables).build();
            }
        }
        return ResultMode.builder().code(500).build();
    }


    @RequestMapping("downloadDoc")
    @SneakyThrows
    public void downloadDoc(String tableNames, HttpServletResponse response, HttpSession session){
        @Cleanup
        OutputStream outputStream = response.getOutputStream();
        DatabaseConnection connection = HttpSessionUtil.getSession(session);
        List<DBTable> tables = idbTableService.selectTableNameByTableNames(connection,tableNames);
        response.setContentType("application/msword;charset=GB2312");
        response.setHeader("content-disposition", "attachment;filename=" + java.net.URLEncoder.encode(DocUtil.DOC_NAME, "UTF-8"));
        DocUtil.exportWord(tables, outputStream);

    }

}
