package javapoepart1;

import java.util.ArrayList;
import java.util.Scanner;

public class QuickChat {
    
    // --- THE LINK TO PART 3 ---
    // This creates the Part 3 object. It will automatically read the JSON file.
    private StoredMessages storedData = new StoredMessages();

    public void startQuickChat(Scanner input) {
        System.out.println("Welcome to QuickChat");
        
        // Populate the arrays with the Test Data required by the assignment rubric
        populateTestData();

        boolean running = true;
        while (running) {
            System.out.println("\n--- QuickChat Menu ---");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Stored Messages"); // NEW: Part 3 Option
            System.out.println("4) Quit");             // Shifted Quit to 4
            System.out.print("Choose an option: ");
            
            String choice = input.nextLine();
            
            switch(choice) {
                case "1":
                    sendMessagesFlow(input);
                    break;
                case "2":
                    System.out.println("Coming Soon.");
                    break;
                case "3":
                    storedMessagesMenu(input); // NEW: Part 3 Menu logic
                    break;
                case "4":
                    System.out.println("Exiting QuickChat. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1, 2, 3, or 4."); 
            }
        }
    }

        // This method handles the message creation loop (PART 2)
    private void sendMessagesFlow(Scanner input) {
        System.out.print("How many messages do you wish to enter? ");
        int numMessages = 0;
        try {
            numMessages = Integer.parseInt(input.nextLine());
        } catch(NumberFormatException e) {
            System.out.println("Invalid number. Returning to menu.");
            return;
        }
        
        for (int i = 1; i <= numMessages; i++) {
            System.out.println("\n--- Message " + i + " of " + numMessages + " ---"); 
            
            System.out.print("Enter recipient cell number (e.g., +277186930): ");
            String recipient = input.nextLine();
            
            System.out.print("Enter message: ");
            String messageText = input.nextLine();
            
            Messages msg = new Messages(i, recipient, messageText);
            
            System.out.println(msg.checkRecipientCell());
            String lengthCheck = msg.checkMessageLength();
            System.out.println(lengthCheck);
            
            if (lengthCheck.startsWith("Message exceeds")) {
                continue; 
            }
            
            System.out.println("\n--- Message Details ---");
            System.out.println(msg.printMessages());
            
            System.out.println("\nWhat would you like to do?");
            System.out.println("1) Send Message");
            System.out.println("2) Disregard Message");
            System.out.println("3) Store Message to send later");
            System.out.print("Choice: ");
            
            int action = 0;
            try {
                action = Integer.parseInt(input.nextLine());
            } catch(NumberFormatException e) {
                System.out.println("Invalid choice. Defaulting to Disregard.");
                action = 2; 
            }
            
            String result = msg.sentMessage(action); 
            System.out.println(result);
            
            
            if (action == 2) {
                System.out.print("Enter 0 to confirm deletion : ");
                String deleteConfirm = input.nextLine();
                
                if (deleteConfirm.equals("0")) {
                    System.out.println("Message successfully deleted.");
                    continue; // Skips the rest of the loop and goes to the next message
                }
            }
            // --------------------------------------------
            
            // --- THE BRIDGE: SENDING DATA TO PART 3 ---
            String status = "";
            if (action == 1) status = "Sent";
            else if (action == 2) status = "Disregard";
            else if (action == 3) status = "Stored";
            
            storedData.addMessage(msg.getMessageID(), msg.getMessageHash(), recipient, messageText, status);
        }
        
        System.out.println("\nTotal messages successfully sent: " + Messages.returnTotalMessages());
    }
    // --- NEW: PART 3 STORED MESSAGES MENU ---
    private void storedMessagesMenu(Scanner input) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Stored Messages Menu ---");
            System.out.println("a. Display sender and recipient of all stored messages");
            System.out.println("b. Display the longest stored message");
            System.out.println("c. Search for a message ID");
            System.out.println("d. Search for messages by recipient");
            System.out.println("e. Delete a message using message hash");
            System.out.println("f. Display full report");
            System.out.println("g. Back to main menu");
            System.out.print("Choice: ");
            
            String choice = input.nextLine().toLowerCase();
            switch (choice) {
                case "a": 
                    storedData.displaySendersAndRecipients(); 
                    break;
                case "b": 
                    System.out.println("Longest Message: " + storedData.getLongestMessage()); 
                    break;
                case "c": 
                    System.out.print("Enter Message ID: ");
                    System.out.println(storedData.searchByMessageID(input.nextLine())); 
                    break;
                case "d": 
                    System.out.print("Enter Recipient (e.g., +27838884567): ");
                    ArrayList<String> results = storedData.searchByRecipient(input.nextLine());
                    if (results.isEmpty()) System.out.println("No messages found.");
                    else for (String r : results) System.out.println("- " + r);
                    break;
                case "e": 
                    System.out.print("Enter Message Hash to delete: ");
                    System.out.println(storedData.deleteByHash(input.nextLine())); 
                    break;
                case "f": 
                    storedData.displayReport(); 
                    break;
                case "g": 
                    back = true; 
                    break;
                default: 
                    System.out.println("Invalid choice.");
            }
        }
    }

    
    private void populateTestData() {
        storedData.addMessage("1111111111", "11:1:DIDCAKE", "+27834557896", "Did you get the cake?", "Sent");
        storedData.addMessage("2222222222", "22:2:WHETIME", "+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored");
        storedData.addMessage("3333333333", "33:3:YOGATE", "+27834484567", "Yohoooo, I am at your gate.", "Disregard");
        storedData.addMessage("0838884567", "08:4:ISTIME", "0838884567", "It is dinner time!", "Sent");
        storedData.addMessage("5555555555", "55:5:OKYOU", "+27838884567", "Ok, I am leaving without you.", "Stored");
    }
}