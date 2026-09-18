//commit 4 - ST10526161 - checkUserName

package javapoe.part1;


public class Login 
{
    String username;
    String password;
    String cellNumber;
    String firstName;
    String Surname;
    
    public Login(String firstName ,String Surname ,String username ,String password ,String cellNumber)
    {
        this.firstName=firstName;
        this.Surname=Surname;
        this.username=username;
        this.password=password;
        this.cellNumber=cellNumber;
    }
    
    public boolean checkUserName()
    {
        return username.contains("_") && username.length() <=5;
    }
    
    public boolean checkPasswordComplexity()
    {
        if (password.length()<8) return false;
        
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;
        
        for (char c: password.toCharArray())
        {
           if (Character.isUpperCase(c)) hasCapital = true;
           if (Character.isDigit(c)) hasNumber = true;
           if (!Character.isLetterOrDigit(c))hasSpecial = true;
        }
        return hasCapital && hasNumber && hasSpecial;
    }
    
    public boolean checkCellPhoneNumber()
    {
        return cellNumber.matches("^\\+27\\d{9}$");
        
    }
    public boolean loginUser(String enteredUsername ,String enteredPassword)
    {
       return enteredUsername.equals(username) && enteredPassword.equals(password);
       
    }
    
    public String returnLoginStatus(String enteredUsername ,String enteredPassword)
    {
        if (loginUser(enteredUsername, enteredPassword))
        {
            return "Welcome " + firstName + "" + Surname + " it is great to see you again.";
        }
        else
        {
            return "Username or password is incorrect, please try again.";
        }
    }
    public String registerUser()
    {
        if (!checkUserName())
        {
            return "Username is not correctly formatted ,please ensure that your username contains an underscore and is no more than 5 characters in length.";
            
        }
        if (!checkPasswordComplexity())
        {
            return "Password is not correctly formatted, please ensure that the password contains atleast 8 characters , a capital letter, a number and a special character.";
            
        }
        if (checkCellPhoneNumber())
        {
            return "Cell phone number incorretly formatted or does not contain international code.";
        }
            return "Welcome " + firstName + " " + Surname + "it is great to see you";
    }
}

