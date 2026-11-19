
import com.mycompany.progpart3.MessageManager;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author itumelengteffo
 */
public class MessageManagerTest {
  
    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        MessageManager m = new MessageManager();
        m.populateTestData();
        assertEquals("Did you get the cake?", m.sentMessages.get(0).getMessageContent());
        assertEquals("It is dinner time !", m.sentMessages.get(1).getMessageContent());
        assertEquals(2, m.sentMessages.size());
    }

    @Test
    public void testDisplayLongestMessage() {
        MessageManager m = new MessageManager();
        m.populateTestData();
        String expected = "Where are you? You are late! I have asked you to be on time.";
        assertEquals(expected, m.displayLongestStoredMessage());
    }

    @Test
    public void testSearchForMessageID() {
        MessageManager m = new MessageManager();
        m.populateTestData();
        String expected = "It is dinner time !";
        assertEquals(expected, m.searchMessageTextByID("0838884567"));
    }

    @Test
    public void testSearchMessagesByRecipient() {
        MessageManager m = new MessageManager();
        m.populateTestData();
        String result = m.searchMessagesByRecipient("+27838884567");
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(result.contains("Ok, I am leaving without you."));
    }

    @Test
    public void testDeleteMessageUsingHash() {
        MessageManager m = new MessageManager();
        m.populateTestData();
        String hashToDelete = m.messageHashes.get(1);
        String result = m.deleteMessageByHash(hashToDelete);
        String expected = "Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.";
        assertEquals(expected, result);
    }

    @Test
    public void testDisplayReport() {
        MessageManager m = new MessageManager();
        m.populateTestData();
        String report = m.displayReport();
        assertTrue(report.contains("Message Hash"));
        assertTrue(report.contains("Recipient"));
        assertTrue(report.contains("Message"));
        assertTrue(report.contains("Did you get the cake?"));
        assertTrue(report.contains("It is dinner time !"));
    }
}
