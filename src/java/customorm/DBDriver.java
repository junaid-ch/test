/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm;

import customorm.model.BaseModel;
import customorm.model.ModelFactory;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author junaid.ahmad
 */
public class DBDriver {

    Connection conn = null;

    private DBDriver() {
    }

    public static DBDriver getInstance() {
        return DBDriverHolder.INSTANCE;
    }

    public Connection getConnection() {

        try {
            if (conn != null && !conn.isClosed()) {
                return conn;
            }

            //STEP 2: Register JDBC driver
            Class.forName(Configurations.getJDBC_DRIVER());

            //STEP 3: Open a connection
            conn = DriverManager.getConnection(Configurations.getDB_URL(), Configurations.getUSER_NAME(), Configurations.getPASSWORD());

        } catch (SQLException | ClassNotFoundException se) {
            //Handle errors for JDBC
            Logger.getLogger(DBDriver.class.getName()).log(Level.SEVERE, null, se);
        }

        return conn;
    }

    public void closeConnection() {
        try {
            if (conn != null || !conn.isClosed()) {
                this.conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int executeInsert(String query, BaseModel model) {
        PreparedStatement preparedStmt = null;
        int rowsAffected = 0;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            String[] columns = query
                    .substring(query.indexOf("(") + 1, query.indexOf(")"))
                    .split(",");
            List<String> columnNames = Arrays.asList(columns);
            // create the mysql insert preparedstatement
            preparedStmt = conn.prepareStatement(query);

            for (Class<?> c = model.getClass(); c != null; c = c.getSuperclass()) {

                Field[] fields = c.getDeclaredFields();
                for (Field classField : fields) {

                    if (classField.getType().isAssignableFrom(java.lang.String.class)) {
                        preparedStmt.setString(columnNames.indexOf(classField.getName()) + 1,
                                (String) model.getField(model, classField));
                    } else if (classField.getType().isAssignableFrom(int.class)
                            && !classField.getName().equalsIgnoreCase("id")) {
                        preparedStmt.setInt(columnNames.indexOf(classField.getName()) + 1,
                                (int) model.getField(model, classField));
                    }
                }
            }
            rowsAffected = preparedStmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DBDriver.class.getName()).log(Level.SEVERE, null, ex);
            rowsAffected = 0;
            if (conn != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    conn.rollback();
                } catch (SQLException excep) {
                    Logger.getLogger(DBDriver.class.getName()).log(Level.SEVERE, null, excep);
                }
            }
        } catch (SecurityException ex) {
            Logger.getLogger(DBDriver.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (preparedStmt != null) {
                    preparedStmt.close();
                }

                closeConnection();

            } catch (SQLException se) {
                Logger.getLogger(DBDriver.class.getName()).log(Level.SEVERE, null, se);
            }//end finally try//end finally try
        }
        return rowsAffected;
    }

    public int executeDelete(String query, int id) {

        PreparedStatement preparedStmt = null;
        int rowsAffected = 0;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, id);

            // execute the preparedstatement
            rowsAffected = preparedStmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            rowsAffected = 0;
            Logger.getLogger(DBDriver.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    conn.rollback();
                } catch (SQLException excep) {
                    Logger.getLogger(DBDriver.class.getName()).log(Level.SEVERE, null, excep);
                }
            }
        } finally {
            try {
                if (preparedStmt != null) {
                    preparedStmt.close();
                }
                closeConnection();
            } catch (SQLException se) {
                Logger.getLogger(DBDriver.class.getName()).log(Level.SEVERE, null, se);
            }//end finally try//end finally try
        }
        return rowsAffected;
    }

    public int executeUpdate(String query, BaseModel model) {
        PreparedStatement preparedStmt = null;
        int rowsAffected = 0;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            preparedStmt = conn.prepareStatement(query);

            int i = 1;
            int id = 0;
            for (Class<?> c = model.getClass(); c != null; c = c.getSuperclass()) {
                Field[] fields = c.getDeclaredFields();
                for (Field classField : fields) {

                    try {
                        if (classField.getType().isAssignableFrom(java.lang.String.class)) {
                            preparedStmt.setString(i, (String) model.getField(model, classField));
                            i++;
                        } else if (classField.getType().isAssignableFrom(int.class)
                                && classField.getName().equalsIgnoreCase("id")) {
                            id = (int) model.getField(model, classField);
                        } else if (classField.getType().isAssignableFrom(int.class)) {
                            preparedStmt.setInt(i, (int) model.getField(model, classField));
                            i++;
                        }

                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(DBDriver.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            preparedStmt.setInt(i, id);
            // execute the preparedstatement
            rowsAffected = preparedStmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DBDriver.class.getName()).log(Level.SEVERE, null, ex);
            rowsAffected = 0;
            if (conn != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    conn.rollback();
                } catch (SQLException excep) {
                    Logger.getLogger(DBDriver.class.getName()).log(Level.SEVERE, null, excep);
                }
            }
        } finally {
            try {
                if (preparedStmt != null) {
                    preparedStmt.close();
                }
                closeConnection();
            } catch (SQLException se) {
                Logger.getLogger(DBDriver.class.getName()).log(Level.SEVERE, null, se);
            }//end finally try//end finally try
        }
        return rowsAffected;
    }

    public BaseModel executeSelect(String query, String modelName, int id) {
        BaseModel model = new ModelFactory().getModel(modelName);
        PreparedStatement preparedStmt = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, id);

            // execute the preparedstatement
            ResultSet rs = preparedStmt.executeQuery();
            conn.commit();
            if (!rs.next()) {
                return model;
            }

            for (Class<?> c = model.getClass(); c != null; c = c.getSuperclass()) {
                Field[] fields = c.getDeclaredFields();
                for (Field classField : fields) {

                    try {
                        if (classField.getType().isAssignableFrom(java.lang.String.class)) {
                            model.setField(model, classField, rs.getString(classField.getName()));
                        } else if (classField.getType().isAssignableFrom(int.class)) {
                            model.setField(model, classField, rs.getInt(classField.getName()));
                        }
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(DBDriver.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBDriver.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    conn.rollback();
                } catch (SQLException excep) {
                    Logger.getLogger(DBDriver.class.getName()).log(Level.SEVERE, null, excep);
                }
            }
        } finally {
            try {
                if (preparedStmt != null) {
                    preparedStmt.close();
                }
                closeConnection();
            } catch (SQLException se) {
                Logger.getLogger(DBDriver.class.getName()).log(Level.SEVERE, null, se);
            }//end finally try//end finally try
        }
        return model;
    }

    private static class DBDriverHolder {

        private static final DBDriver INSTANCE = new DBDriver();
    }
}
