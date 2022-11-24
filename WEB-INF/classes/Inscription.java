import java.io.*;
import java.util.ArrayList;
import java.util.List;
import connection.BddObject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import user.User;

public class Inscription extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        List<BddObject> bdd = new ArrayList<BddObject>();
        String name = request.getParameter("nom");
        String password = request.getParameter("password");
        String genre = request.getParameter("genre");
        try {
            bdd.add(new User(name, password, genre));
            ServletContext context = this.getServletContext();
            context.setAttribute("bdd", bdd);
            response.sendRedirect("information.html");
        } catch (Exception e) {
            response.sendRedirect("error.jsp?error=" + e.getMessage());
        }
    }
}