import Response.ResponseHandler;
import Response.ServerResponse;
import model.StatusCode;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", urlPatterns = "/logout/")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        try {
            HttpSession session = request.getSession();
            session.removeAttribute("user");
            ServerResponse.writeResponse(response, StatusCode.OK, null);
        }
        catch (Exception exc) {
            exc.printStackTrace();
            ServerResponse.writeResponse(response, StatusCode.InternalServerError, exc.getLocalizedMessage());
        }
    }
}
