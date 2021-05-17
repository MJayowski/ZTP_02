package Response;

import com.google.gson.Gson;
import model.StatusCode;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServerResponse {

    public static void writeResponse(HttpServletResponse response, StatusCode status, Object value) throws IOException {

        Gson gson = new Gson();
        response.setStatus(status.value);
        response.setContentType("application/json;charset=UTF-8");
        if (value != null) {
            gson.toJson(value, response.getWriter());
        }
    }
/*
    public static void writeResponse(HttpServletResponse response, StatusCode status, Object value) throws IOException {


            Gson gson = new Gson();
            ResponseHandler res = new ResponseHandler(status, (String) value);
            response.setStatus(status.value);
            response.setContentType("application/json;charset=UTF-8");
        if (value != null) {
            gson.toJson(res, response.getWriter());
        }
    }
*/

}
