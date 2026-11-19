/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.progpart3;

/**
 *
 * @author itumelengteffo
 */
package com.mycompany.poepart3;

import java.util.ArrayList;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import javax.swing.JOptionPane;

import java.io.BufferedReader;

import java.io.BufferedReader;

import java.io.BufferedReader;

import java.io.FileWriter;

import java.io.FileWriter;

public class MessageManager {

    // Requirement 1: Arrays
    public ArrayList<Message> sentMessages = new ArrayList<>();
    public ArrayList<Message> disregardedMessages = new ArrayList<>();
    public ArrayList<Message> storedMessages = new ArrayList<>();
    public ArrayList<String> messageHashes = new ArrayList<>();
    public ArrayList<String> messageIDs = new ArrayList<>();
    public ArrayList<Message> allMessages = new ArrayList<>();

    private static final String JSON_FILE = "storedMessages.json";

    public String generateMessageID() {
        Random rand = new Random();
       long num = 1000000000L + (long) (rand.nextDouble() * 9000000000L);
        return String.valueOf(num).substring(0, 10);
    }

    public String createMessageHash(String messageID, int num, String message) {
        String[] words = message.trim().split("\\s+");
        String first = words[0].toUpperCase().replaceAll("[^A-Z]", "");
        String last = words[words.length - 1].toUpperCase().replaceAll("[^A-Z!]", "");
        return messageID.substring(0, 2) + ":" + num + ":" + first + " " + last;
    }

    public boolean checkRecipientCell(String recipient) {
        return recipient != null && recipient.length() <= 13 && (recipient.startsWith("+27") || recipient.startsWith("0"));
    }

    public boolean checkMessageLength(String message) {
        return message != null && message.length() <= 250;
    }

    public void addMessage(Message m) {
        allMessages.add(m);
        messageIDs.add(m.getMessageId());
        messageHashes.add(m.getMessageHash());
        if (m.getFlag().equalsIgnoreCase("Sent")) {
            sentMessages.add(m);
        } else if (m.getFlag().equalsIgnoreCase("Disregard")) {
            disregardedMessages.add(m);
        } else if (m.getFlag().equalsIgnoreCase("Stored")) {
            storedMessages.add(m);
            storeMessageToJSON(m);
        }
    }

    public void clearAll() {
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();
        allMessages.clear();
    }

    // Requirement 3: Test Data from your assignment
    public void populateTestData() {
        clearAll();
        addMessage(new Message("0838884567", "+27834557896", "Did you get the cake?", "Sent", "1234567890", createMessageHash("1234567890", 1, "Did you get the cake?")));
        addMessage(new Message("0838884567", "+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored", "2345678901", createMessageHash("2345678901", 2, "Where are you? You are late! I have asked you to be on time.")));
        addMessage(new Message("0838884567", "+27834484567", "Yohooo, I am at your gate.", "Disregard", "3456789012", createMessageHash("3456789012", 3, "Yohooo, I am at your gate.")));
        addMessage(new Message("0838884567", "0838884567", "It is dinner time !", "Sent", "0838884567", createMessageHash("0838884567", 4, "It is dinner time !")));
        addMessage(new Message("0838884567", "+27838884567", "Ok, I am leaving without you.", "Stored", "4567890123", createMessageHash("4567890123", 5, "Ok, I am leaving without you.")));
    }

    // 2a. Display sender and recipient of all stored messages
    public String displaySenderAndRecipientOfStoredMessages() {
        if (storedMessages.isEmpty()) {
            return "No stored messages.";
        }
        StringBuilder sb = new StringBuilder();
        for (Message m : storedMessages) {
            sb.append("Sender: ").append(m.getSender()).append(", Recipient: ").append(m.getRecipient()).append("\n");
        }
        return sb.toString().trim();
    }

    // 2b. Display longest stored message
    public String displayLongestStoredMessage() {
        String longest = "";
        for (Message m : allMessages) {
            if (m.getMessageContent().length() > longest.length()) {
                longest = m.getMessageContent();
            }
        }
        return longest;
    }

    // 2c. Search for message ID and display recipient and message
    public String searchMessageByID(String searchID) {
        for (Message m : allMessages) {
            if (m.getMessageId().equals(searchID)) {
                return "Recipient: " + m.getRecipient() + "\nMessage: " + m.getMessageContent();
            }
        }
        return "Message ID not found.";
    }

    public String searchMessageTextByID(String searchID) {
        for (Message m : allMessages) {
            if (m.getMessageId().equals(searchID)) {
                return m.getMessageContent();
            }
        }
        return "Message ID not found.";
    }

    // 2d. Search all messages sent or stored for a particular recipient
    public String searchMessagesByRecipient(String recipient) {
        StringBuilder sb = new StringBuilder();
        for (Message m : allMessages) {
            if (m.getRecipient().equals(recipient) && (m.getFlag().equalsIgnoreCase("Sent") || m.getFlag().equalsIgnoreCase("Stored"))) {
                sb.append(m.getMessageContent()).append("\n");
            }
        }
        if (sb.length() == 0) {
            return "No messages found for this recipient.";
        }
        return sb.toString().trim();
    }

    // 2e. Delete a message using the message hash
    public String deleteMessageByHash(String hash) {
        for (int i = 0; i < allMessages.size(); i++) {
            Message m = allMessages.get(i);
            if (m.getMessageHash().equals(hash)) {
                String deletedMsg = m.getMessageContent();
                if (m.getFlag().equalsIgnoreCase("Sent")) {
                    sentMessages.remove(m);
                } else if (m.getFlag().equalsIgnoreCase("Stored")) {
                    storedMessages.remove(m);
                } else {
                    disregardedMessages.remove(m);
                }
                messageHashes.remove(m.getMessageHash());
                messageIDs.remove(m.getMessageId());
                allMessages.remove(i);
                return "Message: \"" + deletedMsg + "\" successfully deleted.";
            }
        }
        return "Message hash not found.";
    }

    // 2f. Display report
    public String displayReport() {
        if (sentMessages.isEmpty()) {
            return "No sent messages to report.";
        }
        StringBuilder sb = new StringBuilder("--- SENT MESSAGES REPORT ---\n");
        for (Message m : sentMessages) {
            sb.append("Message Hash: ").append(m.getMessageHash()).append("\n");
            sb.append("Recipient: ").append(m.getRecipient()).append("\n");
            sb.append("Message: ").append(m.getMessageContent()).append("\n");
            sb.append("------------------------\n");
        }
        return sb.toString();
    }

    // JSON - Store and Read
    public void storeMessageToJSON(Message m) {
        try (FileWriter fw = new FileWriter(JSON_FILE, true)) {
            String json = "{\"sender\":\"" + m.getSender() + "\",\"recipient\":\"" + m.getRecipient() + "\",\"message\":\"" + m.getMessageContent().replace("\"", "'") + "\",\"id\":\"" + m.getMessageId() + "\",\"hash\":\"" + m.getMessageHash() + "\"}\n";
            fw.write(json);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving JSON: " + e.getMessage());
        }
    }

    public ArrayList<String> loadStoredMessagesFromJSON() {
        ArrayList<String> loaded = new ArrayList<>();
        File file = new File(JSON_FILE);
        if (!file.exists()) {
            return loaded;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("\"message\":\"")) {
                    String msg = line.split("\"message\":\"")[1].split("\"")[0];
                    loaded.add(msg);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading JSON: " + e.getMessage());
        }
        return loaded;
    }
}
    
