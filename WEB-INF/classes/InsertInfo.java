import java.io.*;
import java.util.List;
import annexe.Annexe;
import connection.BddObject;
import info.Information;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import user.User;

public class InsertInfo extends HttpServlet {
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            ServletContext context = this.getServletContext();
            List<BddObject> bdd = (List<BddObject>) context.getAttribute("bdd");
            Object[] annexes = new Annexe().getData(BddObject.getPostgreSQL(), null);
            for (Object object : annexes) {
                Annexe annexe = (Annexe) object;
                Information information = new Information(annexe.getIdAnnexe(), ((User) bdd.get(0)).getIdUser(), request.getParameter(annexe.getNom()));
                bdd.add(information);
            }
            response.sendRedirect("critere");
        } catch (Exception e) {
            response.sendRedirect("error.jsp?error="+e.getMessage());
        }
    }
}