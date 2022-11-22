import java.io.*;

import axe.Axe;
import connection.BddObject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class ListCritere extends HttpServlet {
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            Axe[] axes = Axe.convert(new Axe().getData(BddObject.getPostgreSQL(), null));
            request.setAttribute("axes", axes);
            RequestDispatcher dispat = request.getRequestDispatcher("critere.jsp");
            dispat.forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("error.jsp?error="+e.getMessage());
        }
    }
}