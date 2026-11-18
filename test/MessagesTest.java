package javapoepart1;

import org.junit.Test;
import static org.junit.Assert.*;

public class MessagesTest {

    // ==========================================
    // Test 1: Message Length
    // ==========================================
    @Test
    public void testMessageLengthSuccess() {
        Messages msg = new Messages(1, "+277186930", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    @Test
    public void testMessageLengthFailure() {
        // 255 characters (exceeds by 5)
        String longMessage = "A".repeat(255); 
        Messages msg = new Messages(1, "+277186930", longMessage);
        assertEquals("Message exceeds 250 characters by 5; please reduce the size.", msg.checkMessageLength());
    }

    // ==========================================
    // Test 2: Recipient Number Format
    // ==========================================
    @Test
    public void testRecipientSuccess() {
        Messages msg = new Messages(1, "+277186930", "Hello");
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    @Test
    public void testRecipientFailure() {
        // Missing international code (+) and too long
        Messages msg = new Messages(1, "08575975889", "Hello"); 
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", msg.checkRecipientCell());
    }

    // ==========================================
    // Test 3: Message Hash
    // ==========================================
    @Test
    public void testMessageHash() {
        // Test Case 1 from your screenshots
        Messages msg = new Messages(1, "+277186930", "Hi Mike, can you join us for dinner tonight?");
        
        // The hash format is XX:N:FIRSTLAST. 
        // Because the ID (XX) is generated randomly, we check if it ends with ":1:HITONIGHT"
        assertTrue(msg.createMessageHash().endsWith(":1:HITONIGHT"));
    }

    // ==========================================
    // Test 4: Message ID Created
    // ==========================================
    @Test
    public void testMessageIDCreated() {
        Messages msg = new Messages(1, "+277186930", "Hello");
        assertNotNull(msg.generateMessageID());
        assertTrue(msg.checkMessageID());
    }

    // ==========================================
    // Test 5: Sent Message Actions 
    // ==========================================
    @Test
    public void testSentMessageAction1() {
        Messages msg = new Messages(1, "+277186930", "Hello");
        assertEquals("Message successfully sent.", msg.sentMessage(1));
    }

    @Test
    public void testSentMessageAction2() {
        Messages msg = new Messages(1, "+277186930", "Hello");
        assertEquals("Press 0 to delete the message.", msg.sentMessage(2)); // Note the period!
    }

    @Test
    public void testSentMessageAction3() {
        Messages msg = new Messages(1, "+277186930", "Hello");
        assertEquals("Message successfully stored.", msg.sentMessage(3));
    }
}