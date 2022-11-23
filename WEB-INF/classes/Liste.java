import java.io.*;
import java.lang.reflect.InvocationTargetException;

import axe.Axe;
import connection.BddObject;
import user.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class Liste extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("id");
        boolean check = (request.getParameter("check") != null) ? Boolean.parseBoolean(request.getParameter("check")) : true;
        try {
            User[] proposition = user.getProposition(check);
            request.setAttribute("proposition", proposition);
            request.setAttribute("user", user);
            Axe[] axes = Axe.convert(new Axe().getData(BddObject.getPostgreSQL(), "idAxe"));
            request.setAttribute("axe", axes);
            RequestDispatcher dispat = request.getRequestDispatcher("liste.jsp");
            dispat.forward(request, response);
        } catch (InvocationTargetException e) {
            PrintWriter out = response.getWriter();
            out.println(e.getCause().getMessage());
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.println(e.getMessage());
        }
    }
}