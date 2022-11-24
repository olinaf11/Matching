import jakarta.servlet.http.HttpServlet;
import raikitra.Indisponible;
import user.User;
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class InsertIndisponible extends HttpServlet {
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            String idUser = request.getParameter("idUser");
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("id");
            try {
                new Indisponible(user.getIdUser(), idUser).insert(null);
                response.sendRedirect("liste");
            } catch (Exception e) {
                PrintWriter out = response.getWriter();
                out.println(e.getMessage());
            }
    }
}
