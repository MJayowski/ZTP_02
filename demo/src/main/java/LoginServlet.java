import Requests.LoginRequest;
import Response.ResultsOfValidation;
import Response.ServerResponse;
import com.google.gson.Gson;
import Response.ResponseHandler;
import model.*;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

@WebServlet(name = "LoginServlet", urlPatterns = "/login/")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            LoginRequest userRequest = parseRequestLogin(request, LoginRequest.class);

            String username = userRequest.getUserName();
            String password = userRequest.getPassword();

            ResultsOfValidation val = ValidateUser(username, password);

            if(!val.isValid()) {
                ServerResponse.writeResponse(response, StatusCode.BadRequest, val.getErrors());
                return;
            }

            User user = null;

            if ("admin".equals(username)) {
                user = AdminLogin(username, password);
            }
            else {
                user = UserLogin(username, password);
            }

            if(user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                String token = getBase64FromString(username);
                response.addCookie(new Cookie("userId", token));
                ServerResponse.writeResponse(response, StatusCode.OK, null);
            }
            else {
                ServerResponse.writeResponse(response, StatusCode.Unauthorized, new ResponseHandler(StatusCode.Unauthorized, "Niewlasciwe dane"));
            }
        }
        catch (Exception exc) {
            exc.printStackTrace();
            ServerResponse.writeResponse(response, StatusCode.InternalServerError, new ResponseHandler(StatusCode.InternalServerError, exc.getLocalizedMessage()));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/index.jsp").include(request, response);
    }

    public static ResultsOfValidation ValidateUser(String username, String password) {
        ArrayList<String> errors = new ArrayList<>();

        if(username == null || "".equals(username)) {
            errors.add("Login uzytkownika nie moze byc pusty");
        }

        if(password == null || "".equals(password)) {
            errors.add("Haslo uzytkownika nie moze byc puste.");
        }

        String[] arr = new String[errors.size()];
        return new ResultsOfValidation(errors.toArray(arr));
    }

    private User AdminLogin(String username, String password) {
        if ("admin".equals(username) && "admin".equals(password)) {
            return new User("admin", "admin", Role.ADMIN);
        }
        return null;
    }

    private User UserLogin(String username, String password) {
        ServletContext context = getServletContext();
        HashMap<String, User> users = (HashMap<String, User>)context.getAttribute("users");

        if(users.containsKey(username)) {
            User user = users.get(username);
            if(user.validatePassword(password)){
                return user;
            }
        }
        return null;
    }

    private String getBase64FromString(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    protected <T> T parseRequestLogin(HttpServletRequest request, Class<T> tClass) throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(request.getReader(), tClass);
    }
}
