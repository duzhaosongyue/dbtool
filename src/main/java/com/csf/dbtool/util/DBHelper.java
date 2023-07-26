package com.csf.dbtool.util;

import com.csf.dbtool.common.SystemConstant;
import com.csf.dbtool.model.DatabaseConnection;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class DBHelper<T> {
    // 全局变量
    private Connection conn = null;
    // 创建PreparedStatement对象
    private PreparedStatement psts = null;
    // 创建ResultSet对象
    private ResultSet rs = null;
    // 连接数据的驱动串

    // 连接数据库的URL
    private static String url;
    // 连接数据库的用户
    private static String user;
    // 连接数据库的密码
    private static String pwd;
    // 连接数据库的名称
    private static String databaseName;

    private String schema;

    public String getSchema() {
        return schema;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    //创建ThreadLocal类对象
    private static ThreadLocal<Connection> threadlocal = new ThreadLocal<>();


    public DBHelper(DatabaseConnection connection) {
        url = GetConnectionInfo.getConnectionUrl(connection);
        user = connection.getUser();
        pwd = connection.getPwd();
        databaseName = connection.getDatabaseName();
        schema = connection.getSchema();
        loadClassName(connection.getDatabase());
    }


    private void loadClassName(int database) {
        try {
            Class.forName(getClassName(database));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.error("数据库加载驱动出错！");
        }
    }

    private String getClassName(int database) {
        switch (database) {
            case 0:
                return SystemConstant.MYSQL_CLASS_NAME;
            case 1:
                return SystemConstant.ORACLE_CLASS_NAME;
            case 2:
                return SystemConstant.DM_CLASS_NAME;
            case 3:
                return SystemConstant.POSTGRESQL_CLASS_NAME;
            default:
                return "";
        }
    }


    /**
     * 建立数据库的连接
     */
    private void getConnection() throws SQLException {
        //一次请求一个连接对象
        conn = threadlocal.get();
        if (conn == null || conn.isClosed()) {
            DriverManager.setLoginTimeout(1);
            conn = DriverManager.getConnection(url, user, pwd);
            threadlocal.set(conn);
        }
    }


    @SneakyThrows
    public Boolean testConnection() {
        try {
            DriverManager.setLoginTimeout(1);
            conn = DriverManager.getConnection(url, user, pwd);
        } catch (Exception e) {
            return Boolean.FALSE;
        }
        if (conn != null && !conn.isClosed()) {
            close();
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


    /**
     * 返回带自增长列的主键值
     *
     * @param sql  SQL语句
     * @param objs SQL语句中的参数值,可变参数
     * @return 自增长的主键值
     */
    public long executeInsert(String sql, Object... objs) throws SQLException {
        // 取得连接
        getConnection();
        // 创建PreparedStatement对象赋值
        psts = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        // 遍历参数数组，并且为参数?赋值
        setParameters(objs);
        // 执行SQL语句返回影响行数
        psts.executeUpdate();
        // 返回带有主键值的结果集
        rs = psts.getGeneratedKeys();
        // 返回自增长主键值
        if (rs.next()) {
            return rs.getLong(1);
        } else {
            return -1;
        }
    }

    /**
     * 执行更新(添加、修改、删除)数据库
     *
     * @param sql  SQL语句
     * @param objs SQL语句中的参数值,可变参数
     * @return 影响行数
     * @throws SQLException 抛出的异常
     */
    public int executeUpdate(String sql, Object... objs) throws SQLException {
        // 取得连接
        getConnection();
        // 创建PreparedStatement对象赋值
        psts = conn.prepareStatement(sql);
        // 遍历参数数组，并且为参数?赋值
        setParameters(objs);
        // 执行SQL语句返回影响行数
        return psts.executeUpdate();
    }

    /**
     * 执行查询数据库
     *
     * @param sql  SQL语句
     * @param objs SQL语句中的参数值,可变参数
     * @return 查询结果集
     * @throws SQLException
     */
    public ResultSet executeQuery(String sql, Object... objs) throws SQLException {
        // 取得连接
        getConnection();

        // 创建PreparedStatement对象赋值
        psts = conn.prepareStatement(sql);
        // 遍历参数数组，并且为参数?赋值
        setParameters(objs);
        // 执行SQL语句返回结果集
        return psts.executeQuery();
    }

    /**
     * 查询后返回单值
     *
     * @param sql  SQL语句
     * @param objs SQL语句中的参数值,可变参数
     * @return 返回单值
     * @throws SQLException
     */
    public Object executeScalar(String sql, Object... objs) throws SQLException {
        // 取得连接
        getConnection();
        // 创建PreparedStatement对象赋值
        psts = conn.prepareStatement(sql);
        // 遍历参数数组，并且为参数?赋值
        setParameters(objs);
        // 执行SQL语句返回结果集
        rs = psts.executeQuery();

        // 结果集是否存在数据
        if (rs.next()) {
            return rs.getObject(1);
        } else {
            return null;
        }
    }

    /**
     * 返回实体集合(IRowMapper接口:多态)
     *
     * @param rowmapper
     * @param sql
     * @param objs
     * @return 返回实体集合
     * @throws SQLException
     */
    public List<T> getList(IRowMapper<T> rowmapper, String sql, Object... objs) throws SQLException {
        //取得查询结果集
        rs = executeQuery(sql, objs);

        //集合
        List<T> list = new ArrayList<>();
        //遍历结果集
        while (rs.next()) {
            T t = rowmapper.rowMapper(rs);
            list.add(t);
        }
        return list;
    }

    public List<T> getList(Class<T> tclass, String sql) {
        try {
            return getList(tclass, sql, null);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(2);
    }

    /**
     * 返回实体集合(反射技术)
     *
     * @param tclass
     * @param sql
     * @param objs
     * @return
     * @throws SQLException
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public List<T> getList(Class<T> tclass, String sql, Object... objs) throws SQLException, NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
        //取得查询结果集
        rs = executeQuery(sql, objs);
        // 创建一个对应的空的泛型集合
        List<T> list = new ArrayList<T>();
        // 反射出类类型（方便后续做操作)
        // 获得该类所有自己声明的字段，不问访问权限.所有。所有。所有  反射技术是破坏封装
        Field[] fs = tclass.getDeclaredFields();
        // 遍历结果集
        if (rs != null) {
            while (rs.next()) {
                // 创建实例
                T t = (T) tclass.newInstance();
                // 赋值
                for (int i = 0; i < fs.length; i++) {
                    /*
                     * fs[i].getName()：获得字段名
                     *
                     * f:获得的字段信息
                     */
                    Field f = t.getClass().getDeclaredField(fs[i].getName());
                    // 参数true 可跨越访问权限进行操作
                    f.setAccessible(true);
                    /*
                     * f.getType().getName()：获得字段类型的名字
                     */
                    // 判断其类型进行赋值操作
                    if (f.getType().getName().equals(String.class.getName())) {
                        f.set(t, rs.getString(fs[i].getName()));
                    } else if (f.getType().getName().equals(int.class.getName())) {
                        f.set(t, rs.getInt(fs[i].getName()));
                    } else if (f.getType().getName().equals(short.class.getName())) {
                        f.set(t, rs.getShort(fs[i].getName()));
                    } else if (f.getType().getName().equals(byte.class.getName())) {
                        f.set(t, rs.getByte(fs[i].getName()));
                    } else if (f.getType().getName().equals(long.class.getName())) {
                        f.set(t, rs.getLong(fs[i].getName()));
                    } else if (f.getType().getName().equals(double.class.getName())) {
                        f.set(t, rs.getDouble(fs[i].getName()));
                    } else if (f.getType().getName().equals(float.class.getName())) {
                        f.set(t, rs.getFloat(fs[i].getName()));
                    } else if (f.getType().getName().equals(Date.class.getName())) {
                        f.set(t, rs.getDate(fs[i].getName()));
                    } else if (f.getType().getName().equals(boolean.class.getName())) {
                        f.set(t, rs.getBoolean(fs[i].getName()));
                    }
                }
                list.add(t);
            }
        }
        // 返回结果
        return list;
    }


    /**
     * 设置SQL占位符
     *
     * @param objs
     * @throws SQLException
     */
    private void setParameters(Object... objs) throws SQLException {
        if (objs != null) {
            for (int i = 0; i < objs.length; i++) {
                psts.setObject(i + 1, objs[i]);
            }
        }
    }

    /**
     * 开启事务
     *
     * @throws SQLException
     */
    public void beginTransaction() throws SQLException {
        // 取得连接
        getConnection();
        conn.setAutoCommit(false);
    }

    /**
     * 提交事务
     *
     * @throws SQLException
     */
    public void commitTransaction() throws SQLException {
        conn.commit();
    }

    /**
     * 回滚事务
     *
     * @throws SQLException
     */
    public void rollbackTransaction() throws SQLException {
        conn.rollback();
    }

    // 8. 关闭数据库资源

    /**
     * 关闭数据库资源
     */
    public void close() {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            rs = null;
            e.printStackTrace();
        }

        try {
            if (psts != null) {
                psts.close();
            }
        } catch (SQLException e) {
            psts = null;
            e.printStackTrace();
        }
        if (conn != null) {
            conn = null;
        }
    }
}


interface IRowMapper<T> {
    public T rowMapper(ResultSet rs) throws SQLException;
}


