package javapoepart1;

import java.util.Random;

public class Messages
{
    private String messageID;
    private int numMessagesSent;
    private String messageHash;
    private String messageText;
    private String recipient;
   
    private static int totalMessagesSent = 0;
    private static String[] sentMessagesArray = new String[100];
    
    public Messages(int numMessagesSent, String recipient, String messageText)
    {
        this.numMessagesSent = numMessagesSent;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }
    
    public String generateMessageID()
    {
       Random rand = new Random();
       long id = (long) (rand.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
       return String.valueOf(id);
    }
    
    public boolean checkMessageID()
    {
         return this.messageID.length() <= 10;
    }
    
    // FIXED: Renamed from checkRecipientCall to checkRecipientCell
    public String checkRecipientCell() 
    {
         // FIXED: Checks if it starts with '+' and is max 10 characters (per assignment instructions)
         if (this.recipient.startsWith("+") && this.recipient.length() <= 10) 
         {
             return "Cell phone number successfully captured.";
         }
         else
         {
             return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
         }
    }
    
    public String createMessageHash()
    {
          String firstTwoID = this.messageID.substring(0, 2);
          String[] words = this.messageText.trim().split("\\s+");
          String firstWord = words[0].replaceAll("[^a-zA-Z]", "").toUpperCase();
          String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z]", "").toUpperCase();
          
          return firstTwoID + ":" + this.numMessagesSent + ":" + firstWord + lastWord;
    }
    
    public String checkMessageLength()
    {
          if (this.messageText.length() <= 250)
          {
              return "Message ready to send."; // FIXED: Removed extra space before period
          }
          else
          {
              int excess = this.messageText.length() - 250;
              return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
          }
    }
    
    public String sentMessage(int choice)
    {
          switch (choice)
          {
              case 1:
                  sentMessagesArray[totalMessagesSent] = printMessages();
                  totalMessagesSent++;
                  return "Message successfully sent."; // FIXED: Removed extra space
              case 2:
                  return "Press 0 to delete the message";
              case 3:
                  storeMessage();
                  return "Message successfully stored.";
              default:
                  return "Invalid choice."; // FIXED: Capitalized 'I'
          }
    }
    
    public String printMessages()
    {
          return "Message ID: " + this.messageID + "\n" +
                  "Message Hash: " + this.messageHash + "\n" +
                  "Recipient: " + this.recipient + "\n" +
                  "Message: " + this.messageText;
    }
    
    public static int returnTotalMessages()
    {
          return totalMessagesSent;
    }
    
    public void storeMessage() 
    {
        // Simulating JSON format using standard Java String manipulation
        String jsonString = "{\n" +
                "  \"MessageID\": \"" + this.messageID + "\",\n" +
                "  \"MessageHash\": \"" + this.messageHash + "\",\n" +
                "  \"Recipient\": \"" + this.recipient + "\",\n" +
                "  \"Message\": \"" + this.messageText + "\"\n" + 
                "}";

        try {
            // This creates a file named "stored_messages.json" in your project folder
            java.io.FileWriter file = new java.io.FileWriter("stored_messages.json", true);
            file.write(jsonString + "\n\n");
            file.close();
            System.out.println("[System] Message stored to JSON file successfully."); // Added confirmation message
        } catch (Exception e) {
            System.out.println("Error storing message.");
        }
    }  
        
    public String getMessageID() { return this.messageID; }
    public String getMessageHash() { return this.messageHash; }
}