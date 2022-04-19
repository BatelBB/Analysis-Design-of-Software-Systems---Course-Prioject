package assignment1.PresentationLayer;

public class UserOutput {
    private static UserOutput instance = null;
    UserOutput(){

    }

    public static UserOutput getInstance(){
        if(instance==null){
            instance = new UserOutput();
        }
        return instance;
    }

    void println(String args){
        System.out.println(args);
    }

}
