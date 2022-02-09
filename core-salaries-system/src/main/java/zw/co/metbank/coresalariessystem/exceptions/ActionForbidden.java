package zw.co.metbank.coresalariessystem.exceptions;

public class ActionForbidden extends RuntimeException{
    public ActionForbidden(String message) {
        super(message);
    }
}
