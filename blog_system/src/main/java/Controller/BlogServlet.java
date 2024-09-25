package Controller;

import Model.Blog;
import Model.BlogDao;
import Model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/blog")
public class BlogServlet extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 按照约定的接口格式返回数据

        resp.setContentType("application/json; charset=utf8");
        BlogDao blogDao = new BlogDao();

        String blogId = req.getParameter("blogId");
        if(blogId == null) {
            // 此时就说明该请求来自博客列表页
            List<Blog> blogs = blogDao.selectAll();
            // 将java对象转为json字符串格式
            resp.getWriter().write(objectMapper.writeValueAsString(blogs));
        } else {
            // 说明请求来自博客详情页
            Blog blog = blogDao.selectOne(Integer.parseInt(blogId));
            resp.getWriter().write(objectMapper.writeValueAsString(blog));
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 使用该方法来实现提交新博客的操作

        // 先检查用户的登录状态，获取到会话和用户信息
        HttpSession session = req.getSession(false);
        if(session == null) {
            resp.setStatus(403);
            resp.setContentType("text/html; charset=utf8");
            resp.getWriter().write("当前未登录，不能发布博客！");
            return;
        }
        User user = (User) session.getAttribute("user");
        if(user == null) {
            resp.setStatus(403);
            resp.setContentType("text/html; charset=utf8");
            resp.getWriter().write("当前未登录，不能发布博客！");
            return;
        }

        // 获取请求中的参数（博客的标题和正文）
        // 一定要设置编码方式
        req.setCharacterEncoding("utf8");
        // 参数是根据请求中的设定来写的
        String title = req.getParameter("title");
        String content = req.getParameter("content");

        // 构造Blog对象，并插入到数据库中
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setContent(content);
        blog.setUserId(user.getUserId());

        // blogId是自增主键，不用自己设置
        // postTime在数据库操作中已经设定，是now()
        BlogDao blogDao = new BlogDao();
        blogDao.insert(blog);

        // 重定向到博客列表页
        resp.sendRedirect("blog_list.html");
    }
}
