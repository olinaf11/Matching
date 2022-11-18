import java.io.*;
import user.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class Liste extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("id");
        try {
            User[] proposition = user.getProposition();
            request.setAttribute("proposition", proposition);
            request.setAttribute("user", user);
            RequestDispatcher dispat = request.getRequestDispatcher("liste.jsp");
            dispat.forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("error.jsp?error="+e.getMessage());
        }
    }
}