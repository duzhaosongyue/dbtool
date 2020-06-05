package com.csf.dbtool.util;

import com.csf.dbtool.model.Column;
import com.csf.dbtool.model.DBTable;
import com.lowagie.text.*;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.style.RtfParagraphStyle;
import lombok.SneakyThrows;

import java.awt.*;
import java.io.OutputStream;
import java.util.List;

public class DocUtil {

    private final static Integer COLUMN_NUM = 6;

    private final static Integer LINE_NUM = 3;

    private final static String[] COLUMN_NAME = new String[]{"序号", "字段名称", "字段类型", "字段描述", "允许空", "索引"};

    private final static int[] TABLE_COLUMN_WIDTH = new int[]{8, 20, 18, 30, 18, 16};

    private final static String DOC_TITLE = "数据库结构文档";

    public final static String DOC_NAME = "数据库结构文档.doc";


    @SneakyThrows
    public static void exportWord(List<DBTable> dBTables, OutputStream outputStream) {
        // 创建word文档,并设置纸张的大小
        Document doc = new Document(PageSize.A4);
        //建立一个书写器与document对象关联，通过书写器可以将文档写入到输出流中
        RtfWriter2.getInstance(doc, outputStream);
        doc.open();

        printDocTitle(doc);
        printDocContent(dBTables, doc);
        defaultPrint(dBTables, doc);

        doc.close();
    }

    @SneakyThrows
    private static void printDocTitle(Document doc) {
        Paragraph title = new Paragraph(DOC_TITLE);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingBefore(15);
        doc.add(title);
    }


    private static void printDocContent(List<DBTable> dBTables, Document doc) {
        for (int i = 0; i < dBTables.size(); i++) {
            printTable(i, dBTables.get(i), doc);
        }
    }


    @SneakyThrows
    private static void defaultPrint(List<DBTable> dbTables, Document doc) {
        if (dbTables.isEmpty()) {
            Paragraph context = new Paragraph("查询不到数据");
            // 正文格式对齐方式
            context.setAlignment(Element.ALIGN_LEFT);
            // 与上一段落（标题）的行距
            context.setSpacingBefore(10);
            doc.add(context);
        }
    }


    @SneakyThrows
    private static Table initTableStyle() {
        //表格设置（列、行）
        Table table = new Table(COLUMN_NUM, LINE_NUM);
        //设置每列所占比例
        table.setWidths(TABLE_COLUMN_WIDTH);
        //表格所占页面宽度
        table.setWidth(100);
        //居中显示
        table.setAlignment(Element.ALIGN_CENTER);
        //自动填满
        table.setBorderWidth(5); // 边框宽度
        table.setBorderColor(new Color(0, 125, 255)); // 边框颜色
        table.setPadding(12);// 衬距，看效果就知道什么意思了
        table.setSpacing(0);// 即单元格之间的间距
        table.setBorder(5);// 边框
        table.setSpacing(0f);
        return table;
    }

    @SneakyThrows
    private static void printTableTitle(Integer index, DBTable dbTable, Document doc) {
        String comment = (index + 1) + "." + dbTable.getTableName();
        if (dbTable.getTableComment() != null && dbTable.getTableComment().length() > 0) {
            comment = comment + "(" + dbTable.getTableComment() + ")";
        }
        Paragraph paragraphComment = new Paragraph(comment, RtfParagraphStyle.STYLE_HEADING_2);
        paragraphComment.setAlignment(Element.ALIGN_LEFT);
        doc.add(paragraphComment);
        doc.add(new Paragraph(""));
    }

    @SneakyThrows
    private static void printGroupIndex(DBTable dbTable, Document doc) {
        String groupIndex = dbTable.getGroupIndex();
        if (groupIndex != null && groupIndex.length() > 0) {
            doc.add(new Paragraph("组合索引: " + dbTable.getGroupIndex()));
        }
    }


    private static void printTableHeader(Table table) {
        for (int i = 0; i < COLUMN_NUM; i++) {
            Cell cell = new Cell(COLUMN_NAME[i]);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
    }


    private static void printTableContent(Table table, List<Column> columns) {
        if (columns != null) {
            for (int j = 0; j < columns.size(); j++) {
                Column column = columns.get(j);
                for (int index = 0; index < COLUMN_NUM; index++) {
                    Cell cell = new Cell(column.getValue(index));
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                }
            }
        }
    }


    @SneakyThrows
    private static void printTable(Integer index, DBTable dbTable, Document doc) {
        printTableTitle(index, dbTable, doc);
        Table table = initTableStyle();
        printTableHeader(table);
        printTableContent(table, dbTable.getColumns());
        printGroupIndex(dbTable, doc);
        doc.add(table);
    }

}
