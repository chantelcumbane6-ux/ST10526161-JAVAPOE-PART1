package javapoepart1;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class StoredMessagesTest {
    
    private StoredMessages data;

    @Before
    public void setUp() {
        // We initialize the StoredMessages object
        data = new StoredMessages();
        
        // Test Data 1 (Sent)
        data.addMessage("1111111111", "11:1:DIDCAKE", "+27834557896", "Did you get the cake?", "Sent");
        
        // Test Data 2 (Stored)
        data.addMessage("2222222222", "22:2:WHETIME", "+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored");
        
        // Test Data 3 (Disregard)
        data.addMessage("3333333333", "33:3:YOGATE", "+27834484567", "Yohoooo, I am at your gate.", "Disregard");
        
        // Test Data 4 (Sent)
        data.addMessage("0838884567", "08:4:ISTIME", "0838884567", "It is dinner time!", "Sent");
        
        // Test Data 5 (Stored)
        data.addMessage("5555555555", "55:5:OKYOU", "+27838884567", "Ok, I am leaving without you.", "Stored");
    }

    // Test 1: Sent Messages array correctly populated
    @Test
    public void testSentMessagesArrayPopulated() {
        // The system should return messages 1 and 4
        assertEquals(2, data.sentMessages.size());
        assertEquals("Did you get the cake?", data.sentMessages.get(0));
        assertEquals("It is dinner time!", data.sentMessages.get(1));
    }

    // Test 2: Display the longest Message
    @Test
    public void testGetLongestMessage() {
        String expected = "Where are you? You are late! I have asked you to be on time.";
        String actual = data.getLongestMessage();
        assertEquals(expected, actual);
    }

    // Test 3: Search for messageID
    @Test
    public void testSearchByMessageID() {
        // Test Data: message 4 ("0838884567")
        // Expected: "It is dinner time!"
        String result = data.searchByMessageID("0838884567");
        
        // The method returns "Recipient: ... \nMessage: ..." so we check if it contains the text
        assertTrue(result.contains("It is dinner time!"));
    }

    // Test 4: Search all messages sent or stored regarding a particular recipient
    @Test
    public void testSearchByRecipient() {
        // Test Data: +27838884567
        ArrayList<String> results = data.searchByRecipient("+27838884567");
        
        // Expected 2 messages
        assertEquals(2, results.size());
        assertEquals("Where are you? You are late! I have asked you to be on time.", results.get(0));
        assertEquals("Ok, I am leaving without you.", results.get(1));
    }

    // Test 5: Delete a message using a message hash
    @Test
    public void testDeleteMessageByHash() {
        // Test Data: Test Message 2 (Hash: "22:2:WHETIME")
        String hashToDelete = "22:2:WHETIME";
        
        String result = data.deleteByHash(hashToDelete);
        
        // Expected System Return: successfully deleted.
        assertTrue(result.contains("successfully deleted"));
        
        // Verify it was actually removed by searching for the recipient again
        ArrayList<String> remainingMessages = data.searchByRecipient("+27838884567");
        assertEquals(1, remainingMessages.size()); // Should only be 1 message left for this recipient
        assertEquals("Ok, I am leaving without you.", remainingMessages.get(0));
    }

    // Test 6: Display Report (Testing the data state)
    @Test
    public void testDisplayReportData() {
       
        assertNotNull(data.messages);
        assertFalse(data.messages.isEmpty());
    }
}