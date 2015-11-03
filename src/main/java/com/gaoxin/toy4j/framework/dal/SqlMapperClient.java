package com.gaoxin.toy4j.framework.dal;

import com.gaoxin.toy4j.framework.helper.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author gaoxin.wei
 * sql mapper代理
 */
public class SqlMapperClient {

    public static SqlMapperClient INSTANCE = new SqlMapperClient();

    private SqlMapperClient() {}

    public Object execute() {
        System.out.println("sql mapper client");

        try {
            Connection conn = DatabaseHelper.getConnection();
            PreparedStatement stmt = conn.prepareStatement("insert into Customer(CustomerName) values(?)");
            stmt.setString(1, "test for sql mapper client");
            stmt.executeUpdate();
            throw new Exception("i am exception");
        } catch (Exception e) {
            System.out.println("hello");
        } finally {
            System.out.println("hahahahah");
        }
        return null;
    }




}
