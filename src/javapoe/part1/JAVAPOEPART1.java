
package javapoe.part1;
import java.util.Scanner;

public class JAVAPOEPART1 
{
    public static void main(String[] args) 
    {
      Scanner input=new Scanner(System.in); 
      
        System.out.println("Enter first name: ");
        String firstName=input.nextLine();
        System.out.println("Enter Surname: ");
        String surname=input.nextLine();
        
        System.out.println("Enter username: ");
        String username=input.nextLine();
        Login tempCheck = new Login (firstName ,surname ,username ,"" ,"");
        while (!tempCheck.checkUserName())
        {
            System.out.println("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters");
            System.out.println("Enter your username: ");
            username = input.nextLine();
            tempCheck = new Login(firstName ,surname ,username, "", "");
        }
            System.out.println("Username successfully captured!");
        
        System.out.println("Enter your password: ");
        String password=input.nextLine();
        tempCheck = new Login(firstName ,surname ,username , password, "");
        while (!tempCheck.checkPasswordComplexity())
        {
            System.out.println("Password is not correctly formatted, please ensure that your password  contains atleast eight characters, a capital letter , a number and a special character");
            System.out.println("Enter your password: ");
            password = input.nextLine();
            tempCheck = new Login(firstName ,surname ,username, password, "");
        }
        System.out.println("Password succesfully captured!!");
        
        System.out.println("Enter cellphone number:");
        String cell=input.nextLine();
        tempCheck = new Login(firstName ,surname ,username ,password ,cell);
        while (!tempCheck.checkCellPhoneNumber())
        {
            System.out.println("Cellphone is not correctly formatted or does not contain an international code ; please correct and try again.");
            System.out.println("Enter your cellphone: ");
            cell = input.nextLine();
            tempCheck = new Login(firstName ,surname ,username, password, cell);
        }
        
        System.out.println("Cell number successfully captured!");
        
        Login obj=new Login(firstName ,surname, username, password, cell);
        System.out.println("\nRegistration successful!");
        
        System.out.println("\n============");
        System.out.println("     LOGIN     ");
        System.out.println("\n=============");
        
        boolean loggedIn = false;
        while(!loggedIn)
        {
            System.out.println("Enter your username: ");
            String loginUser=input.nextLine();
            System.out.println("Enter your password: ");
            String loginPass=input.nextLine();
            
            String status=obj.returnLoginStatus(loginUser ,loginPass);
            System.out.println("\n" + status);
            
            if (status.startsWith("Welcome "))
            {
                loggedIn = true;
            }
            else
            {
                System.out.println("Please try again.\n");
            }
        } 
    }
   
}
