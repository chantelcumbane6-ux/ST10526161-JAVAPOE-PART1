package javapoepart1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StoredMessages {
    
    private final String jsonFile;
    
    // Using ArrayLists makes it much easier to add and delete items dynamically
    public ArrayList<String> sentMessages = new ArrayList<>();
    public ArrayList<String> disregardedMessages = new ArrayList<>();
    public ArrayList<String> storedMessages = new ArrayList<>();
    public ArrayList<String> messageHash = new ArrayList<>();
    public ArrayList<String> messageID = new ArrayList<>();
    
    // Parallel arrays for searching
    public ArrayList<String> recipients = new ArrayList<>();
    public ArrayList<String> senders = new ArrayList<>();
    
    public ArrayList<MessageRecord> messages = new ArrayList<>();

    // Default constructor
    public StoredMessages() {
        this("stored_messages.json"); // Matches the file name used in your Part 2 Messages class
    }

    // Parameterized constructor
    public StoredMessages(String jsonFile) {
        this.jsonFile = jsonFile;
        loadStoredMessagesFromJSON();
        populateArrays();
    }

    public static class MessageRecord {
        String id, hash, recipient, text, status, sender;

        public MessageRecord(String id, String hash, String recipient, String text, String status, String sender) {
            this.id = id;
            this.hash = hash;
            this.recipient = recipient;
            this.text = text;
            this.status = status;
            this.sender = sender;
        }
    }

    // Add a new message to the list and update arrays
    public void addMessage(String id, String hash, String recipient, String text, String status) {
        messages.add(new MessageRecord(id, hash, recipient, text, status, "Developer"));
        populateArrays(); // Refresh the arrays
    }

    // --- PART 3 REQUIREMENT: Populate Arrays ---
    public void populateArrays() {
        // Clear existing arrays to prevent duplicates
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageHash.clear();
        messageID.clear();
        recipients.clear();
        senders.clear();

        for (MessageRecord message : messages) {
            messageHash.add(message.hash);
            messageID.add(message.id);
            recipients.add(message.recipient);
            senders.add(message.sender);

            if ("Sent".equalsIgnoreCase(message.status)) {
                sentMessages.add(message.text);
            } else if ("Disregard".equalsIgnoreCase(message.status) || "Disregarded".equalsIgnoreCase(message.status)) {
                disregardedMessages.add(message.text);
            } else if ("Stored".equalsIgnoreCase(message.status)) {
                storedMessages.add(message.text);
            }
        }
    }

    // --- PART 3 REQUIREMENTS: MENU METHODS ---

    // a. Display the sender and recipient of all stored messages
    public void displaySendersAndRecipients() {
        System.out.println("\n--- Senders and Recipients ---");
        boolean found = false;
        for (MessageRecord msg : messages) {
            if ("Stored".equalsIgnoreCase(msg.status)) {
                System.out.println("Sender: " + msg.sender + " | Recipient: " + msg.recipient);
                found = true;
            }
        }
        if (!found) System.out.println("No stored messages found.");
    }

    // b. Display the longest stored message
    public String getLongestMessage() {
        String longest = "";
        for (MessageRecord msg : messages) {
            if ("Stored".equalsIgnoreCase(msg.status)) {
                if (msg.text.length() > longest.length()) {
                    longest = msg.text;
                }
            }
        }
        return longest.isEmpty() ? "No stored messages found." : longest;
    }

    // c. Search for a message ID and display the corresponding recipient and message
    public String searchByMessageID(String id) {
        for (MessageRecord msg : messages) {
            if (msg.id.equals(id)) {
                return "Recipient: " + msg.recipient + "\nMessage: " + msg.text;
            }
        }
        return "Message ID not found.";
    }

    // d. Search for all messages stored for a particular recipient
    public ArrayList<String> searchByRecipient(String recipient) {
        ArrayList<String> found = new ArrayList<>();
        for (MessageRecord msg : messages) {
            if (msg.recipient.equals(recipient)) {
                found.add(msg.text);
            }
        }
        return found;
    }

    // e. Delete a message using the message hash
    public String deleteByHash(String hash) {
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).hash.equals(hash)) {
                String deletedText = messages.get(i).text;
                messages.remove(i);
                populateArrays(); // Refresh arrays after deletion
                return "Message: \"" + deletedText + "\" successfully deleted.";
            }
        }
        return "Message hash not found.";
    }

    // f. Display a report that lists the full details of all the stored messages
    public void displayReport() {
        System.out.println("\n--- Full Stored Messages Report ---");
        boolean found = false;
        for (MessageRecord msg : messages) {
            if ("Stored".equalsIgnoreCase(msg.status)) {
                System.out.println("Message Hash: " + msg.hash);
                System.out.println("Recipient: " + msg.recipient);
                System.out.println("Message: " + msg.text);
                System.out.println("-------------------------");
                found = true;
            }
        }
        if (!found) System.out.println("No stored messages available.");
    }

    // --- JSON FILE READER ---
    private void loadStoredMessagesFromJSON() {
        File file = new File(jsonFile);
        if (!file.exists()) return; // File doesn't exist yet

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line.trim());
            }
            reader.close();

            // Using Regex to find the JSON objects created by Part 2
            String json = jsonContent.toString();
            Matcher m = Pattern.compile("\\{[^}]*\\}").matcher(json);
            
            while (m.find()) {
                String obj = m.group();
                String id = extractField(obj, "MessageID");
                String hash = extractField(obj, "MessageHash");
                String rec = extractField(obj, "Recipient");
                String text = extractField(obj, "Message");
                
                messages.add(new MessageRecord(id, hash, rec, text, "Stored", "Developer"));
            }
        } catch (Exception e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
        }
    }

    private String extractField(String jsonObj, String key) {
        Matcher m = Pattern.compile("\"" + key + "\":\\s*\"([^\"]*)\"").matcher(jsonObj);
        return m.find() ? m.group(1) : "";
    }
}