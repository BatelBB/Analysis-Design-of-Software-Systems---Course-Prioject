package BusinessLayer;

import java.util.Date;

public class Password {
    private String password;
    private final int MIN_PASSWORD_LENGTH = 6;
    private final int MAX_PASSWORD_LENGTH = 12;

    public Password(String password) throws Exception {
        setPassword(password);
    }

    public synchronized boolean setPassword(String password) throws Exception {
        if (password == null)
            return false;
        if(password.length() < MIN_PASSWORD_LENGTH)
            throw new IllegalArgumentException("The minimum password length should be at least 6 characters");
        if(password.length() > MAX_PASSWORD_LENGTH)
            throw new IllegalArgumentException("The maximum password length should be up to 12 characters");
        boolean digitFlag = false;
        boolean capitalLetterFlag = false;
        boolean lowercaseLetterFlag = false;
        for(int i = 0 ; i < password.length(); i++) {
            char curr = password.charAt(i);
            if (Character.isDigit(curr))
                digitFlag = true;
            else if (Character.isUpperCase(curr))
                capitalLetterFlag = true;
            else if (Character.isLowerCase(curr))
                lowercaseLetterFlag = true;
        }
        if(!digitFlag)
            throw new IllegalArgumentException("The password must contain at least one digit");
        if(!capitalLetterFlag)
            throw new IllegalArgumentException("The password must contain at least one capital letter");
        if(!lowercaseLetterFlag)
            throw new IllegalArgumentException("The password must contain at least one lowercase letter");
        return true;
    }

    public synchronized boolean checkPassword(String passwordToCheck) {
        if (passwordToCheck == null)
            return false;
        return passwordToCheck.equals(password);
    }
}
