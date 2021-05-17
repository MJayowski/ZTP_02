package Response;

import model.StatusCode;

public class ResponseHandler {

    private int status;
    private String[] messages;

    public ResponseHandler(StatusCode status, String message) {
        this.status = status.value;
        this.messages = new String[] { message };
    }

    public ResponseHandler(StatusCode status, String[] messages) {
        this.status = status.value;
        this.messages = messages;
    }


}
