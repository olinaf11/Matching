import java.io.*;
import java.sql.Date;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import match.Match;
import user.User;

public class InsertMatch extends HttpServlet {
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String idUser = request.getParameter("idUser");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("id");
        try {
            new Match(user.getIdUser(), idUser, new Date(System.currentTimeMillis())).insert(null);
            response.sendRedirect("liste");
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.println(e.getMessage());
        }
    }
}
