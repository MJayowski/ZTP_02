package Response;

public class ResultsOfValidation {
    public String[] errors;

    public ResultsOfValidation(String[] errors) {
        this.errors = errors != null ? errors : new String[0];
    }

    public String[] getErrors() {
        return errors;
    }

    public boolean isValid() {
        return errors == null || errors.length == 0;
    }
}
