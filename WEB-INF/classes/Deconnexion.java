import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class Deconnexion extends HttpServlet {
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("id");
        response.sendRedirect("index");
    }
}
