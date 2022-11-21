import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import raikitra.Raikitra;
import user.User;

public class InsertRaikitra extends HttpServlet {
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String idUser = request.getParameter("idUser");
        String idMatch = request.getParameter("idMatch");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("id");
        try {
            Raikitra raikitra = new Raikitra(user.getIdUser(), idUser, idMatch);
            raikitra.insert(null);
            response.sendRedirect("liste-match");
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.println(e.getMessage());
        }
    }
}
