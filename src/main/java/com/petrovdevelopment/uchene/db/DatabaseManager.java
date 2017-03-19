package com.petrovdevelopment.uchene.db;

import com.petrovdevelopment.uchene.db.converters.ResultSetConverterToInt;
import com.petrovdevelopment.uchene.db.converters.ResultSetConverterToModel;
import com.petrovdevelopment.uchene.db.converters.ResultSetConverterToModelList;
import com.petrovdevelopment.uchene.model.Model;
import com.sun.org.apache.xpath.internal.operations.Mod;

import java.sql.*;
import java.util.List;

/**
 * Created by Andrey Petrov on 17-01-07.
 */
public class DatabaseManager {
    public static final String DATABASE_CONNECTION = "jdbc:sqlite:mate.db";
    public static final int QUERY_TIMEOUT = 30;

    private static DatabaseManager instance;


    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public void initialize() {
        // load the sqlite-JDBC driver using the current class loader
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getDBConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE_CONNECTION);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;

    }

   /* public static String select(String selectQuery, ResultSetConverterToString resultSetConverterToString) {
        String result = null;
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection(DATABASE_CONNECTION);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(QUERY_TIMEOUT);  // set timeout to 30 sec.
            ResultSet resultSet = statement.executeQuery(selectQuery);
            result = resultSetConverterToString.convert(resultSet);
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
        return result;
    }

    public static String selectWithParameters(String selectQuery, int[] intInputParameters, ResultSetConverterToString resultSetConverterToString) {
        String result = null;
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection(DATABASE_CONNECTION);
            PreparedStatement statement = connection.prepareStatement(selectQuery);

            if (intInputParameters != null) {
                for (int i = 0; i < intInputParameters.length; i++) {
                    statement.setInt(i + 1, intInputParameters[i]);
                }
            }

            statement.setQueryTimeout(QUERY_TIMEOUT);  // set timeout to 30 sec.
            ResultSet resultSet = statement.executeQuery();
            result = resultSetConverterToString.convert(resultSet);
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
        return result;
    }*/

    public static int selectWithParameters(String selectQuery, int[] intInputParameters, ResultSetConverterToInt resultSetConverterToInt) {
        int result = -1;
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection(DATABASE_CONNECTION);
            PreparedStatement statement = connection.prepareStatement(selectQuery);

            if (intInputParameters != null) {
                for (int i = 0; i < intInputParameters.length; i++) {
                    statement.setInt(i + 1, intInputParameters[i]);
                }
            }

            statement.setQueryTimeout(QUERY_TIMEOUT);  // set timeout to 30 sec.
            ResultSet resultSet = statement.executeQuery();
            result = resultSetConverterToInt.convert(resultSet);
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
        return result;
    }


    public static <E extends Model> E select(String selectQuery, ResultSetConverterToModel<E> resultSetConverterToModel) {
        E result = null;
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection(DATABASE_CONNECTION);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(QUERY_TIMEOUT);  // set timeout to 30 sec.
            ResultSet resultSet = statement.executeQuery(selectQuery);
            result = resultSetConverterToModel.convert(resultSet);
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
        return result;
    }

    public static <E extends Model> E selectWithParameters(String selectQuery, int[] intInputParameters, ResultSetConverterToModel<E> resultSetConverterToModel) {
        E result = null;
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection(DATABASE_CONNECTION);
            PreparedStatement statement = connection.prepareStatement(selectQuery);

            if (intInputParameters != null) {
                for (int i = 0; i < intInputParameters.length; i++) {
                    statement.setInt(i + 1, intInputParameters[i]);
                }
            }

            statement.setQueryTimeout(QUERY_TIMEOUT);  // set timeout to 30 sec.
            ResultSet resultSet = statement.executeQuery();
            result = resultSetConverterToModel.convert(resultSet);
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
        return result;
    }

    public static <E extends Model> List<E> select(String selectQuery, ResultSetConverterToModelList<E> resultSetConverterToModelList) {
        List<E> result = null;
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection(DATABASE_CONNECTION);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(QUERY_TIMEOUT);  // set timeout to 30 sec.
            ResultSet resultSet = statement.executeQuery(selectQuery);
            result = resultSetConverterToModelList.convert(resultSet);
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
        return result;
    }

    public static <E extends Model> List<E> selectWithParameters(String selectQuery, int[] intInputParameters, ResultSetConverterToModelList<E> resultSetConverterToModelList) {
        List<E> result = null;
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection(DATABASE_CONNECTION);
            PreparedStatement statement = connection.prepareStatement(selectQuery);

            if (intInputParameters != null) {
                for (int i = 0; i < intInputParameters.length; i++) {
                    statement.setInt(i + 1, intInputParameters[i]);
                }
            }

            statement.setQueryTimeout(QUERY_TIMEOUT);  // set timeout to 30 sec.
            ResultSet resultSet = statement.executeQuery();
            result = resultSetConverterToModelList.convert(resultSet);
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
        return result;
    }

    public static int updateQuery(PreparedStatement updateStatement) {
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection(DATABASE_CONNECTION);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(QUERY_TIMEOUT);  // set timeout to 30 sec.
            return updateStatement.executeUpdate();
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
        return -1;
    }


    public static java.sql.Timestamp getCurrentTimeStamp() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());
    }
}


