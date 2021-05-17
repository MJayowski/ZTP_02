package Filters;

import model.User;
import Response.ServerResponse;
import model.StatusCode;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"/books/*"})
public class AuthFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        try {
            User user = (User) request.getSession().getAttribute("user");

            if (user == null) {
                ServerResponse.writeResponse(response, StatusCode.Unauthorized, "Nieuprawniony uzytkownik");
                return;
            }
            if (!checkForCookie(request.getCookies(), user)) {
                ServerResponse.writeResponse(response, StatusCode.Unauthorized, "Brak poprawnego ciasteczka");
                return;
            }
            chain.doFilter(request, response);

        } catch (Exception exc) {
            exc.printStackTrace();
            ServerResponse.writeResponse(response, StatusCode.InternalServerError, exc.getLocalizedMessage());
            return;
        }


    }

    private boolean checkForCookie(Cookie[] cookies, User user) {
        for (Cookie cookie : cookies) {
            if ("userId".equals(cookie.getName())) {
                return new String(Base64.getDecoder().decode(cookie.getValue().getBytes())).equals(user.getUserName());
            }
        }
        return false;
    }

    public void destroy() {
    }

}
