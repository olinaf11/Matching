import java.io.*;
import java.sql.Connection;
import java.util.List;
import axe.Axe;
import connection.BddObject;
import info.Critere;
import info.Intervalle;
import info.Precision;
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
            User user = ((User) bdd.get(0));
            for (Object object : axes) {
                Axe axe = (Axe) object;
                axe.setIntervalles();
                bdd.add(new Critere(axe.getIdAxe(), user.getIdUser(), Integer.parseInt(request.getParameter(axe.getNom()))));
                for (Intervalle intervalle : axe.getIntervalles())
                    bdd.add(new Precision(intervalle.getIdIntervalle(), request.getParameter(intervalle.getIntervalle()), user.getIdUser()));
            }
            insertAll(bdd);
            response.sendRedirect("index");
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.println(e.getMessage());
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