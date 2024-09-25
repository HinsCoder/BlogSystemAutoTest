package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// blog表
// +----------+--------------+------+-----+---------+----------------+
// | Field    | Type         | Null | Key | Default | Extra          |
// +----------+--------------+------+-----+---------+----------------+
// | blogId   | int(11)      | NO   | PRI | NULL    | auto_increment |
// | title    | varchar(256) | YES  |     | NULL    |                |
// | content  | text         | YES  |     | NULL    |                |
// | postTime | datetime     | YES  |     | NULL    |                |
// | userId   | int(11)      | YES  |     | NULL    |                |

// 封装针对博客表的相关操作：根据需求来写操作
public class BlogDao {
    // 插入一个博客到数据库中 --发布博客
    public void insert(Blog blog) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            // 和数据库建立连接
            connection = DBUtil.getConnection();

            // 构造sql
            String sql = "insert into blog values(null,?,?,now(),?)";
            statement = connection.prepareStatement(sql);
            // 依次填充sql中的占位符
            statement.setString(1,blog.getTitle());
            statement.setString(2,blog.getContent());
            statement.setInt(3,blog.getUserId());

            // 执行sql
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // 释放对应的资源
            DBUtil.close(connection,statement,null);
        }
    }

    // 根据博客id来查询指定的博客 --博客详情页
    public Blog selectOne(int blogId) {
      Connection connection = null;
      PreparedStatement statement = null;
      ResultSet resultSet = null;
      try {
          // 和数据库建立连接
          connection = DBUtil.getConnection();
          // 构造sql
          String sql = "select * from blog where blogId = ?";
          statement = connection.prepareStatement(sql);
          statement.setInt(1,blogId);
          // 执行sql
          resultSet = statement.executeQuery();
          // 遍历结果集合(if)
          if (resultSet.next()) {
              Blog blog = new Blog();
              blog.setBlogId(resultSet.getInt("blogId"));
              blog.setTitle(resultSet.getString("title"));
              blog.setContent(resultSet.getString("content"));
              blog.setPostTime(resultSet.getTimestamp("postTime"));
              blog.setUserId(resultSet.getInt("userId"));
              return blog;
          }
      } catch (SQLException throwables) {
          throwables.printStackTrace();
      } finally {
          // 释放对应的资源
          DBUtil.close(connection,statement,resultSet);
      }
      return null;
    }

    // 直接查询博客列表 --博客列表页
    public List<Blog> selectAll() {
        // 链表用来存储blog
        List<Blog> blogs = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // 和数据库建立连接
            connection = DBUtil.getConnection();
            // 构造sql
            String sql = "select * from blog order by postTime desc";
            statement = connection.prepareStatement(sql);
            // 执行sql
            resultSet = statement.executeQuery();
            // 遍历结果集合拿到结果(while)
            while (resultSet.next()) {
                Blog blog = new Blog();
                blog.setBlogId(resultSet.getInt("blogId"));
                blog.setTitle(resultSet.getString("title"));

                // 进行内容截断作为摘要，避免博客列表页内容过长
                String content = resultSet.getString("content");
                if(content.length() > 100) {
                    content = content.substring(0,100) + " ...";
                }
                blog.setContent(content);

                blog.setPostTime(resultSet.getTimestamp("postTime"));
                blog.setUserId(resultSet.getInt("userId"));
                blogs.add(blog);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // 释放对应的资源
            DBUtil.close(connection,statement,null);
        }
        return blogs;
    }

    // 删除指定博客 --删除博客
    public void delete(int blogId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            // 和数据库建立连接
            connection = DBUtil.getConnection();

            // 构造sql
            String sql = "delete from blog where blogId = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1,blogId);

            // 执行sql
            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // 释放对应的资源
            DBUtil.close(connection,statement,null);
        }
    }

    // 获取文章数量 --博客列表页
    public int getBlogCount() {
        int count = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            // 和数据库建立连接
            connection = DBUtil.getConnection();

            // 构造sql
            String sql = "SELECT COUNT(*) FROM blog";   // 根据实际情况调整查询
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(); // 执行sql

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // 释放对应的资源
            DBUtil.close(connection,statement,null);
        }
        return count;
    }
}
