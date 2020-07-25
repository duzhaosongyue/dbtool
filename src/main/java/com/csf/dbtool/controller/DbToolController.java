package com.csf.dbtool.controller;

import com.csf.dbtool.model.DBTable;
import com.csf.dbtool.model.DatabaseConnection;
import com.csf.dbtool.service.IDBTableService;
import com.csf.dbtool.util.DBHelper;
import com.csf.dbtool.util.DocUtil;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

@Controller
public class DbToolController {

    @Autowired
    private IDBTableService idbTableService;

    @RequestMapping("/")
    public String connectConfig() {
        return "connect_config";
    }


    @RequestMapping("list")
    public String list(DatabaseConnection connection, Model model) {
        model.addAttribute("bean", connection);
        return "export_word";
    }


    @RequestMapping("connection")
    @ResponseBody
    public Integer connection(DatabaseConnection connection) {
        DBHelper mysql = new DBHelper(connection);
        return mysql.testConnection() ? 1 : -1;
    }


    @ResponseBody
    @GetMapping("selectByTableName")
    public List<DBTable> selectByTableName(DatabaseConnection connection) {
        return idbTableService.selectByTableName(connection);
    }


    /****
     * 下载world文档
     * @param tableNames
     * @param response
     * @param connection
     */
    @RequestMapping("downloadDoc")
    @SneakyThrows
    public void downloadDoc(String tableNames, HttpServletResponse response, DatabaseConnection connection) {
        @Cleanup
        OutputStream outputStream = response.getOutputStream();
        List<DBTable> tables = idbTableService.selectTableNameByTableNames(connection, tableNames);
        response.setContentType("application/msword;charset=GB2312");
        response.setHeader("content-disposition", "attachment;filename=" + java.net.URLEncoder.encode(DocUtil.DOC_NAME, "UTF-8"));
        DocUtil.exportWord(tables, outputStream);

    }

}
