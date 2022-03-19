package tn.esprit.spring.service;

import tn.esprit.spring.entity.Employee;
import tn.esprit.spring.entity.Message;

import java.util.List;

public interface IMessage {

    Message AddMessage(Message message, Employee employee);
    void deletemessage(int id);
    List<String> Autocomplete(String word);

}
