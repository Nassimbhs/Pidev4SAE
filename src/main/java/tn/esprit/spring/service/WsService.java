package tn.esprit.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.spring.entity.Employee;
import tn.esprit.spring.entity.Message;
import tn.esprit.spring.repository.MessageRepository;
import tn.esprit.spring.repository.EmployeeRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service
public class WsService implements IMessage {

    @Autowired
    MessageRepository messageRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    // Add msg and Detect bad words ---------------------------------------------------------------------
    public Message AddMessage(Message message, Employee employee) {
        List<String> content = new ArrayList<String>();
        // Bad Words Searcher
        BufferedReader reader;
        String line = null;
        try {
            reader = new BufferedReader(new FileReader(
                    "C:/Users/nassi/Desktop/4sae5-Grp4-Nassim-Benhassine/src/main/resources/bad_words.txt"));

            line = reader.readLine();

            while (line != null) {
                if (message.getMessage().contains(line)) {
                    content.add(line);
                    System.out.println("You are using bad words");
                    System.out.println(content);
                }

                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (content.size() > 0) {
            return null;
        } else {
            message.setDateMessage(new Date());
            message.setEmployee(employee);
            message = messageRepository.save(message);

            return message;
        }
    }

    public List<String> Autocomplete(String word){
        BufferedReader reader;
        String line = null;
        List<String> content = new ArrayList<String>();
        try {
            reader = new BufferedReader(new FileReader(
                    "C:/Users/nassi/Desktop/4sae5-Grp4-Nassim-Benhassine/src/main/resources/english_words.txt"));
            line = reader.readLine();

            while (line != null) {
                if (line.startsWith(word)) {
                    content.add(line);
                    System.out.println("This is line : " + line);
                }

                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
            return content;
    }

    // Delete msg
    @Override
    public void deletemessage(int id) {
        this.messageRepository.deleteById(id);
    }
}
