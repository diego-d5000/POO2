/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysys.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author diego-d
 */
public class SQLInterface {

    private static final String DB_NAME = "inventory.db";
    private static final String SQLITE_CLASS = "org.sqlite.JDBC";
    private static final SQLInterface INSTANCE = new SQLInterface();

    private SQLInterface() {
    }

    public static SQLInterface getInstance() {
        return INSTANCE;
    }

    public void createTable(String tablename, String cols) {
        Connection connection = null;
        try {
            try {
                Class.forName(SQLITE_CLASS);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SQLInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);

            // se ejecuta la accion a realizar
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS "
                    + tablename
                    + " ( " + cols + " );");

            statement.close();
            connection.commit();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void createTrigger(String name, String condition, String content) {
        Connection connection = null;
        try {
            try {
                Class.forName(SQLITE_CLASS);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SQLInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);

            // se ejecuta la accion a realizar
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE TRIGGER" + " " + name + " " + condition
                    + "\nBEGIN \n"
                    + content
                    + "\nEND;");

            statement.close();
            connection.commit();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet select(String cols, String from, String where, SQLAction sqlAction) {
        ResultSet rs = null;
        Connection connection = null;
        try {
            try {
                Class.forName(SQLITE_CLASS);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SQLInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);

            // se ejecuta la accion a realizar
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();

            rs = statement.executeQuery("SELECT "
                    + cols
                    + " FROM "
                    + from
                    + (where != null ? " WHERE " + where : "")
                    + ";");

            sqlAction.onResult(rs);

            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public int insert(String into, String cols, String values) {
        Connection connection = null;
        int lastIndex = 0;
        try {
            try {
                Class.forName(SQLITE_CLASS);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SQLInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);

            // se ejecuta la accion a realizar
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();

            statement.executeUpdate("INSERT INTO "
                    + into
                    + " ( " + cols + " ) "
                    + "VALUES ( " + values + " );");

            connection.commit();
            lastIndex = statement.getGeneratedKeys().getInt(1);
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lastIndex;
    }

    public void update(String into, String set, String where) {
        Connection connection = null;
        try {
            try {
                Class.forName(SQLITE_CLASS);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SQLInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);

            // se ejecuta la accion a realizar
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();

            statement.executeUpdate("UPDATE "
                    + into
                    + " SET "
                    + set
                    + " WHERE "
                    + where
                    + ";");

            connection.commit();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(String from, String where) {
        Connection connection = null;
        try {
            try {
                Class.forName(SQLITE_CLASS);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SQLInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);

            // se ejecuta la accion a realizar
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();

            statement.executeUpdate("DELETE FROM "
                    + from
                    + (where != null ? " WHERE " + where : "")
                    + ";");

            connection.commit();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static interface SQLAction {

        public void onResult(ResultSet rs);
    }
}
