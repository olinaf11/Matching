import java.io.*;

import annexe.Annexe;
import connection.BddObject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class ListCritere extends HttpServlet {
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            Annexe[] annexes = Annexe.convert(new Annexe().getData(BddObject.getPostgreSQL(), null));
            request.setAttribute("axes", annexes);
            RequestDispatcher dispat = request.getRequestDispatcher("critere.jsp");
            dispat.forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("error.jsp?error="+e.getMessage());
        }
    }
}