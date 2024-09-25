package Controller;

import Model.User;
import Model.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 从请求中获取用户名和密码
        req.setCharacterEncoding("utf8");
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        if(userName==null || userName.equals("") || password==null || password.equals("")) {
            resp.setContentType("text/html; charset=utf8");
            resp.getWriter().write("用户名或密码为空，登陆失败！");
            return;
        }

        // 查询数据库，验证用户名和密码是否正确
        UserDao userDao = new UserDao();
        User user = userDao.selectByName(userName);
        if(user==null || !user.getPassword().equals(password)) {
            resp.setContentType("text/html; charset=utf8");
            resp.getWriter().write("用户名或密码错误，登陆失败！");
            return;
        }

        // 如果正确，构造一个会话对象
        HttpSession session = req.getSession();
        // 在会话中保存user，后续访问其他页面时就可以通过会话获取user信息，当前页面就会知道是哪个用户在进行访问了
        session.setAttribute("user",user);

        // 重定向到博客列表页
        resp.sendRedirect("blog_list.html");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 使用该方法对当前登录状态进行判定
        HttpSession session = req.getSession(false);
        if(session == null) {
            // 没有会话 就是还没有存储用户信息
            resp.setStatus(403);
            return;
        }

        // 需要进行强转类型
        User user = (User) session.getAttribute("user");
        if(user == null) {
            // 虽然有会话，但是里面没有user对象，也认为是未登陆状态
            resp.setStatus(403);
            return;
        }
        // 已登录
        resp.setStatus(200);
    }
}
