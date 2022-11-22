import java.io.*;
import java.sql.Connection;
import java.util.List;

import axe.Axe;
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
            Object[] axes = new Axe().getData(BddObject.getPostgreSQL(), "idAxe");
            for (Object object : axes) {
                Axe Axe = (Axe) object;
                Critere critere = new Critere(Axe.getIdAxe(), ((User) bdd.get(0)).getIdUser(), Integer.parseInt(request.getParameter(Axe.getNom())));
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