/*
 * Copyright (c) 2018, Cardinal Operations and/or its affiliates. All rights reserved.
 * CARDINAL OPERATIONS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.shanshu.ai.common;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Frank Huang (runping@shanshu.ai)
 * @date 2018/06/05
 */
public class DBUtils {
    private static Connection conn = null;
    private static String user;

    /**
     * default initialization
     * */
    public static void initConn(){
        initConn("postgres");
    }

    /**
     * should be called before changing database
     * */
    public static void initConn(String database) {
        if (conn!=null){
            closeConn();
        }
        try{
            Class.forName("org.postgresql.Driver");
            ConfUtils conf = new ConfUtils();
            String url = conf.getProperty("URL") + database;
            user = conf.getProperty("USER");
            String password = conf.getProperty("PASSWORD");
            DBUtils.setConn(DriverManager.getConnection(url, user, password));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void closeConn() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setConn(Connection conn) {
        DBUtils.conn = conn;
    }

    /**
     * show all databases in current database
    * */
    public static List<String> showDatabases() throws SQLException{
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT * FROM pg_database where datistemplate=false");
        List<String> result = new ArrayList<String>();
        while (resultSet.next()){
            result.add(resultSet.getString("datname"));
        }
        st.close();
        return result;
    }

    public static List<String> showTables(String database) throws SQLException {
        initConn(database);
        return showTables();
    }

    /**
     * show all tables in the current database of postgresql
    * */
    public static List<String> showTables() throws SQLException {
        Statement st = conn.createStatement();
        ResultSet result = st.executeQuery(String.format("SELECT * FROM pg_catalog.pg_tables WHERE tableowner=\'%s\'", user));
        List<String> list = new ArrayList<String>();

        while (result.next()){
            list.add(result.getString("tablename"));
        }
        st.close();
        return list;
    }

    /**
     * sample data for 50 entry
    * */
    public static List<String> sampling(String tableName) throws SQLException {
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT * FROM "+tableName+" LIMIT 50");
        int columns = resultSet.getMetaData().getColumnCount();
        List<String> result = new ArrayList<String>();
        while (resultSet.next()){
            StringBuilder row = new StringBuilder();
            for (int i=1;i<=columns;i++){
                row.append(resultSet.getString(i)).append("|");
            }
            result.add(row.toString());
        }
        return result;
    }
}
