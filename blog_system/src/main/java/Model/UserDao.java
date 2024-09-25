package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 封装针对用户表的相关操作
public class UserDao {
    // 根据用户名来查询用户详情 --登录
    public User selectByName(String userName) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // 和数据库建立连接
            connection = DBUtil.getConnection();
            // 构造sql
            String sql = "select * from user where userName = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,userName);
            // 执行sql
            resultSet = statement.executeQuery();
            // 遍历结果集合(if)
            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setUserName(resultSet.getString("userName"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // 释放对应的资源
            DBUtil.close(connection,statement,resultSet);
        }
        return null;
    }

    // 根据用户id来查询用户详情 --博客详情页
    public User selectById(int userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // 和数据库建立连接
            connection = DBUtil.getConnection();
            // 构造sql
            String sql = "select * from user where userId = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1,userId);
            // 执行sql
            resultSet = statement.executeQuery();
            // 遍历结果集合(if)
            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setUserName(resultSet.getString("userName"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // 释放对应的资源
            DBUtil.close(connection,statement,resultSet);
        }
        return null;
    }
}
