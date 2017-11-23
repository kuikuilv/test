package com.gta.chapter2.helper;

import com.gta.chapter2.util.CollectionUtil;
import com.gta.chapter2.util.PropsUtil;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by kui.lv on 2017/11/20.
 */
public class DatabaseHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    /*private static final String DRIVER;

    private static final String URL;

    private static final String USERNAME;

    private static final String PASSWORD;*/

    //private static final QueryRunner QUERY_RUNNER= new QueryRunner();

    //private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<Connection>();
    private static final ThreadLocal<Connection> CONNECTION_HOLDER;

    private static final QueryRunner QUERY_RUNNER;

    private static final BasicDataSource DATA_SOURCE;



    static {
        CONNECTION_HOLDER = new ThreadLocal<Connection>();
        QUERY_RUNNER= new QueryRunner();
        Properties conf = PropsUtil.loadProps("config.properties");
        String driver = conf.getProperty("jdbc.driver");
        String url = conf.getProperty("jdbc.url");
        String userName = conf.getProperty("jdbc.userName");
        String passWord = conf.getProperty("jdbc.passWord");

        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(driver);
        DATA_SOURCE.setUrl(url);
        DATA_SOURCE.setUsername(userName);
        DATA_SOURCE.setPassword(passWord);

      /*  try {
            Class.forName("DRIVER");
        } catch (ClassNotFoundException e) {
            LOGGER.error("can not load jdbc driver",e);
        }*/
    }

    /**
     * 获取数据库连接
     * @return
     */
    public static Connection getConnection(){
        Connection conn =CONNECTION_HOLDER.get();
        try {
            conn = DATA_SOURCE.getConnection();
        } catch (SQLException e) {
            LOGGER.error("connection get failure",e);
            throw  new RuntimeException(e);
        }finally {
            CONNECTION_HOLDER.set(conn);
        }
        return conn;
    }

    /**
     * 关闭连接
     * @param
     */
    public static void closeConnection(){
        Connection conn = CONNECTION_HOLDER.get();
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
               LOGGER.error("close connection  failure",e);
               throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * 查询实体列表
     * @param entityClass
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass,String sql,Object... params){
        List<T> entityList ;
        try {
            Connection conn = getConnection();
            entityList = QUERY_RUNNER.query(conn,sql,new BeanListHandler<T>(entityClass),params);
        } catch (SQLException e) {
            LOGGER.error("query entity list failure",e);
            throw  new RuntimeException(e);
        }finally {
            closeConnection();
        }
        return  entityList;
    }

    /**
     * 查询实体类
     * @param entityClass
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public static <T> T queryEntity(Class<T>entityClass,String sql,Object...params){
        T entity;
        try {
            Connection conn = getConnection();
            entity = QUERY_RUNNER.query(conn,sql,new BeanHandler<T>(entityClass),params);
        } catch (SQLException e) {
            LOGGER.error("query entity failure",e);
            throw new RuntimeException(e);
        }finally {
            closeConnection();
        }
        return entity;

    }

    /**
     * 执行查询语句
     * @param sql
     * @param params
     * @return
     */
    public static List<Map<String,Object>> executeQuery(String sql,Object...params){
        List<Map<String,Object>> result;
        try {
            Connection conn = getConnection();
            result = QUERY_RUNNER.query(conn,sql,new MapListHandler(),params);
        } catch (SQLException e) {
            LOGGER.error("execute query failure",e);
            throw new RuntimeException(e);
        }finally {
            closeConnection();
        }
        return result;
    }

    /**
     * 执行更新语句（insert,update,delete）
     * @param sql
     * @param params
     * @return
     */
    public static int executeUpdate(String sql ,Object...params){
        int rows = 0;
        try {
            Connection conn = getConnection();
            rows = QUERY_RUNNER.update(conn,sql,params);
        } catch (SQLException e) {
            LOGGER.error("execute update failure",e);
            throw  new RuntimeException(e);
        }finally {
            closeConnection();
        }
        return rows;
    }

    /**
     * 插入实体
     * @param entityClass
     * @param fieldMap
     * @param <T>
     * @return
     */
    public  static <T> boolean insertEntity(Class<T> entityClass,Map<String,Object> fieldMap){
        if(CollectionUtil.isEmpty(fieldMap)){
            LOGGER.error("can not insert entity:fieldMap is empty");
            return false;
        }

        String sql =" INSERT INTO "+getTableName(entityClass);
        StringBuffer columns = new StringBuffer("(");
        StringBuffer values = new StringBuffer("(");

        for (String fieldName : fieldMap.keySet()){
            columns.append(fieldName).append(", ");
            values.append("?, ");
        }

        columns.replace(columns.lastIndexOf(", "),columns.length(),")");
        values.replace(values.lastIndexOf(", "),values.length(),")");
        sql += columns + "VALUES" + values;

        Object[] params = fieldMap.values().toArray();

        return  executeUpdate(sql,params) ==1 ;

    }

    /**
     * 更新实体
     * @param entityClass
     * @param id
     * @param filedMap
     * @param <T>
     * @return
     */
    public static <T> boolean updateEntity(Class<T> entityClass,long id,Map<String,Object>filedMap){
        if(CollectionUtil.isEmpty(filedMap)){
            LOGGER.error("can not update entity:fieldMap is empty");
            return false;
        }
        String sql ="UPDATE "+getTableName(entityClass)+ " SET ";
        StringBuffer columns = new StringBuffer();
        for (String fieldName : filedMap.keySet()){
            columns.append(fieldName).append("=?, ");
        }
        sql += columns.substring(0,columns.lastIndexOf(", "))+" WHERE ID=?";
        List<Object> paramList =  new ArrayList<Object>();
        paramList.addAll(filedMap.values());
        paramList.add(id);
        Object[] params = paramList.toArray();

        return executeUpdate(sql,params)==1 ;

    }

    /**
     * 删除实体
     * @param entityClass
     * @param id
     * @param <T>
     * @return
     */
    public static <T> boolean deleteEntity(Class<T>entityClass,long id){
        String sql = " DELETE FROM " + getTableName(entityClass) +" WHERE ID=?";
        return executeUpdate(sql,id)==1 ;
    }

    private static String getTableName(Class<?> entityClass){

        return entityClass.getSimpleName();
    }

    /**
     * 执行SQL 文件
     * @param filePath
     */
    public static void exeuteSqlFile(String filePath){
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            String sql;

            while ( (sql=reader.readLine())!=null){
                executeUpdate(sql);
            }
        } catch (IOException e) {
            LOGGER.error("execute sql file failure",e);
            throw new  RuntimeException(e);
        }
    }


}
