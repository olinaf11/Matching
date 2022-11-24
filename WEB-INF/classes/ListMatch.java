import jakarta.servlet.http.HttpServlet;
import match.Match;
import java.io.*;
import connection.BddObject;
import user.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class ListMatch extends HttpServlet {
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("id");
        try {
            Match match = new Match();
            match.setIdUserMatch(user.getIdUser());
            request.setAttribute("matchs", Match.convert(match.getData(BddObject.getPostgreSQL(), null, "idUserMatch")));
            request.setAttribute("user", user);
            RequestDispatcher dispat = request.getRequestDispatcher("listeMatch.jsp");
            dispat.forward(request, response);
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.println(e.getMessage());
        }
    }
}
