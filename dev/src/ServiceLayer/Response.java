package ServiceLayer;

public class Response {

    private  String errorMessage;
    public Response() { }
    public Response(String msg)
    {
        this.errorMessage = msg;
    }
    public boolean ErrorOccured(){
        return errorMessage!=null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
