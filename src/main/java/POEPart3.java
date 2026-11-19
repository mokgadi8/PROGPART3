
mport com.mycompany.progpart3.MessageManager;
import javax.swing.JOptionPane;

import javax.swing.JOptionPane;

import javax.swing.JOptionPane;

import javax.swing.JOptionPane;

import javax.swing.JOptionPane;

import com.mycompany.progpart3.Message;

import javax.swing.JOptionPane;

import com.mycompany.progpart3.Message;

import com.mycompany.progpart3.MessageManager;

import com.mycompany.progpart3.MessageManager;

import com.mycompany.progpart3.MessageManager;

import com.mycompany.progpart3.MessageManager;

import com.mycompany.progpart3.MessageManager;

import com.mycompany.progpart3.MessageManager;

import javax.swing.JOptionPane;

import com.mycompany.progpart3.Message;

import javax.swing.JOptionPane;

import javax.swing.JOptionPane;

import javax.swing.JOptionPane;

import com.mycompany.progpart3.MessageManager;

import javax.swing.JOptionPane;

import com.mycompany.progpart3.MessageManager;

import javax.swing.JOptionPane;

import javax.swing.JOptionPane;

import javax.swing.JOptionPane;

import com.mycompany.progpart3.MessageManager;

import com.mycompany.progpart3.MessageManager;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author itumelengteffo
 */
public class POEPart3 {
    public static void main(String[] args) 
    {
        MessageManager manager = new MessageManager();
        manager.loadStoredMessagesFromJSON();
        String sender = "0838884567";
        boolean running = true;
        while (running) {
            String choice = JOptionPane.showInputDialog(
                "Welcome to QuickChat\n"
                + "1) Send Messages\n"
                + "2) Show Recently Sent Messages\n"
                + "3) Quit\n"
                + "4) Stored Messages\n"
                + "Choose an option:");
            if (choice == null) {
                break;
            }
            switch (choice) {
                case "1" -> {
                    int num = Integer.parseInt(JOptionPane.showInputDialog("How many messages do you want to send?"));
                    for (int i = 1; i <= num; i++) {
                        String recipient = JOptionPane.showInputDialog("Enter recipient number (+27...):");
                        String message = JOptionPane.showInputDialog("Enter message " + i + ":");
                        if (!manager.checkRecipientCell(recipient)) {
                            JOptionPane.showMessageDialog(null, "Cell number incorrectly formatted.");
                            continue;
                        }
                        if (!manager.checkMessageLength(message)) {
                            JOptionPane.showMessageDialog(null, "Message exceeds 250 characters.");
                            continue;
                        }
                        String id = manager.generateMessageID();
                        String hash = manager.createMessageHash(id, i, message);
                        String flag = JOptionPane.showInputDialog("Send / Disregard / Store?");
                        Message m = new Message(sender, recipient, message, flag, id, hash);
                        manager.addMessage(m);
                        JOptionPane.showMessageDialog(null, "Message ID: " + id + "\nHash: " + hash + "\nStatus: " + flag);
                    }
                }
                case "2" -> {
                    StringBuilder sb = new StringBuilder("Sent Messages:\n");
                    for (Message m : manager.sentMessages) {
                        sb.append(m.getMessageContent()).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, sb.toString());
                }
                case "4" -> {
                    String sub = JOptionPane.showInputDialog(
                            "Stored Messages Menu\n"
                                    + "a) Display Sender and Recipient of Stored Messages\n"
                                    + "b) Display Longest Stored Message\n"
                                    + "c) Search by Message ID\n"
                                    + "d) Search Messages by Recipient\n"
                                    + "e) Delete Message by Hash\n"
                                    + "f) Display Report\n"
                                    + "Enter a-f:");
                    if (sub == null) {
                        break;
                    }
                    switch (sub.toLowerCase()) {
                        case "a":
                            JOptionPane.showMessageDialog(null, manager.displaySenderAndRecipientOfStoredMessages());
                            break;
                        case "b":
                            JOptionPane.showMessageDialog(null, "Longest Message:\n" + manager.displayLongestStoredMessage());
                            break;
                        case "c":
                            String searchID = JOptionPane.showInputDialog("Enter Message ID:");
                            JOptionPane.showMessageDialog(null, manager.searchMessageByID(searchID));
                            break;
                        case "d":
                            String rec = JOptionPane.showInputDialog("Enter Recipient:");
                            JOptionPane.showMessageDialog(null, manager.searchMessagesByRecipient(rec));
                            break;
                        case "e":
                            String hash = JOptionPane.showInputDialog("Enter Message Hash:");
                            JOptionPane.showMessageDialog(null, manager.deleteMessageByHash(hash));
                            break;
                        case "f":
                            JOptionPane.showMessageDialog(null, manager.displayReport());
                            break;
                        default:
                            break;
                    }
                }
                case "3" -> {
                    running = false;
                    JOptionPane.showMessageDialog(null, "Goodbye!");
                }
                default -> {
                }
            }
        }
    }
}
