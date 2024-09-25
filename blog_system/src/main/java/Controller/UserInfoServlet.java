package Controller;

import Model.Blog;
import Model.BlogDao;
import Model.User;
import Model.UserDao;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/userInfo")
public class UserInfoServlet extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String blogId = req.getParameter("blogId");

        if(blogId == null) {
            // 说明此时为博客列表页，直接从session对象中获取信息就行
            getUserInfoFromSession(req,resp);
        } else {
            // 说明此时为博客详情页，查询数据库
            getUserInfoFromDB(req,resp,Integer.parseInt(blogId));
        }
    }

    // 获取到作者信息（详情页）
    private void getUserInfoFromDB(HttpServletRequest req, HttpServletResponse resp, int blogId) throws IOException {
        // 先根据blogId来查询Blog对象，获取到userId
        BlogDao blogDao = new BlogDao();
        Blog blog = blogDao.selectOne(blogId);
        if(blog == null) {
            // 如果blogId不存在，也就是在数据库中找不到该blog
            resp.setStatus(404);
            resp.setContentType("text/html; charset=utf8");
            resp.getWriter().write("blogId不存在！");
            return;
        }

        // 根据userId来查询对应的User对象就行
        UserDao userDao = new UserDao();
        User user = userDao.selectById(blog.getUserId());
        if(user == null) {
            // 如果blogId不存在，也就是在数据库中找不到该blog
            resp.setStatus(404);
            resp.setContentType("text/html; charset=utf8");
            resp.getWriter().write("user不存在！");
            return;
        }

        // 把user对象返回给浏览器
        user.setPassword("");
        resp.setContentType("application/json; charset=utf8");
        resp.getWriter().write(objectMapper.writeValueAsString(user));
    }

    // 获取当前登录用户的信息（列表页）
    private void getUserInfoFromSession(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //  没有会话则不创建 直接返回null
        HttpSession session = req.getSession(false);
        if(session == null) {
            resp.setStatus(403);
            resp.setContentType("text/html; charset=utf8");
            resp.getWriter().write("当前未登录！");
            return;
        }

        User user = (User) session.getAttribute("user");
        if(user == null) {
            resp.setStatus(403);
            resp.setContentType("text/html; charset=utf8");
            resp.getWriter().write("当前未登录！");
            return;
        }

        // 此时用户已登录，可以获取到用户的信息（但是包括密码，所以要去掉密码项）
        user.setPassword("");

        // 设置格式
        resp.setContentType("application/json; charset=utf8");
        resp.getWriter().write(objectMapper.writeValueAsString(user));
    }
}
