package com.gaoxin.toy4j.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author gaoxin.wei
 */
public class DatabaseHelper {

    public static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    private static ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<Connection>();


    public static void closeConnection() {
        Connection conn = CONNECTION_HOLDER.get();
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                LOGGER.error("close connection error", e);
                throw new RuntimeException(e);
            }
        }
    }

    public static Connection getConnection() {
        Connection conn = CONNECTION_HOLDER.get();
        if (conn == null) {
            String driver = ConfigHelper.getJdbcDriver();
            String url = ConfigHelper.getJdbcUrl();
            String userName = ConfigHelper.getJdbcUserName();
            String passwd = ConfigHelper.getJdbcPassword();
            try {
                Class.forName(driver);
                conn = DriverManager.getConnection(url, userName, passwd);
            } catch (Exception e) {
                LOGGER.error("get connection error", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
        return conn;
    }

    public static void beginTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.setAutoCommit(false);
            } catch (Exception e) {
                LOGGER.error("set auto commit error", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
    }

    public static void commitTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.commit();
                conn.close();
            } catch (Exception e) {
                LOGGER.error("commit transaction error", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }

        }
    }

    public static void rollbackTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.rollback();
                conn.close();
            } catch (Exception e) {
                LOGGER.error("roll back error", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }

        }
    }
}
