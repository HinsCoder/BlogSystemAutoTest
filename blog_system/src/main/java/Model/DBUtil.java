package Model;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 通过该类来封装DataSource
public class DBUtil {
    private static volatile DataSource dataSource = null;   // 创建一个数据源对象


    // 通过单例模式的方式进行封装
    public static DataSource getDataSource() {
        if(dataSource == null) { // 是否需要加锁
            synchronized (DBUtil.class) {
                if(dataSource == null) { // 是否要创建对象
                    dataSource = new MysqlDataSource();

                    // MySQL数据库
                    ((MysqlDataSource)dataSource).setUrl("jdbc:mysql://127.0.0.1:3306/blog_system?characterEncoding=utf8&useSSL=false");
                    ((MysqlDataSource)dataSource).setUser("root");
                    ((MysqlDataSource)dataSource).setPassword("1111");
                }
            }
        }
        return dataSource;
    }

    // 建立连接
    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    // 释放连接
    public static void close(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        // 创建顺序和释放顺序正好相反
        if(resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if(statement != null) {
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
