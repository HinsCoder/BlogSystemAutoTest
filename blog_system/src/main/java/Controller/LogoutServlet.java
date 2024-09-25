package Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取会话
        HttpSession session = req.getSession();
        if(session == null) {
            resp.setStatus(403);
            return;
        }
        // 直接将session中的user对象给删除就行
        session.removeAttribute("user");
        // 重定向到登录页面
        resp.sendRedirect("login.html");
    }
}
