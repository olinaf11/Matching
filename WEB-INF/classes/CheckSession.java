import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import user.User;

public class CheckSession extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/plain"); 
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("id");
        if (user == null) {
            response.sendRedirect("login.jsp");
        } else {
            response.sendRedirect("liste");
        }
    }
}
