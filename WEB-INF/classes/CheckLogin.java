import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import user.User;

public class CheckLogin extends HttpServlet {
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("Content-Type: plain/text");
        String name = request.getParameter("nom");
        String password = request.getParameter("password");
        try {
            User user = new User(name, password);
            User[] users = user.find();
            if (users.length > 0) {
                HttpSession session = request.getSession();
                session.setAttribute("id", users[0]);
                response.sendRedirect("liste");
            } else {
                response.sendRedirect("login.jsp");
            }
        } catch (Exception e) {
            response.sendRedirect("error.jsp?error="+e.getMessage());
        }
    }
}
