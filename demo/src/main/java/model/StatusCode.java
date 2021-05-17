package model;

public enum StatusCode {

    OK(200),
    Created(201),
    BadRequest(400),
    Unauthorized(401),
    NotFound(404),
    InternalServerError(500);

    public final int value;

    StatusCode(int value) {
        this.value = value;
    }
}
