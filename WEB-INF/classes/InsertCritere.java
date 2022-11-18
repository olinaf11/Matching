import java.io.*;
import java.sql.Connection;
import java.util.List;
import annexe.Annexe;
import connection.BddObject;
import info.Critere;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import user.User;

public class InsertCritere extends HttpServlet {
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            ServletContext context = this.getServletContext();
            List<BddObject> bdd = (List<BddObject>) context.getAttribute("bdd");
            Object[] annexes = new Annexe().getData(BddObject.getPostgreSQL(), "idannexe");
            for (Object object : annexes) {
                Annexe annexe = (Annexe) object;
                Critere critere = new Critere(annexe.getIdAnnexe(), ((User) bdd.get(0)).getIdUser(), Integer.parseInt(request.getParameter(annexe.getNom())));
                bdd.add(critere);
            }
            insertAll(bdd);
            response.sendRedirect("index");
        } catch (Exception e) {
            response.sendRedirect("error.jsp?error=" + e.getMessage());
        }
    }

    public void insertAll(List<BddObject> bdd) throws Exception {
        Connection connection = null;
        try {
            connection = BddObject.getPostgreSQL();
            for (BddObject data : bdd)
                data.insert(connection);
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
    }
}