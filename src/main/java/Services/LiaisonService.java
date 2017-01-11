package Services;


import beans.Message;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.ext.web.RoutingContext;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LiaisonService {

    private static LiaisonService instance;

    public static LiaisonService getInstance() {
        if (null == instance) {
            instance = new LiaisonService();
        }
        return instance;
    }

    public String[] getAllParents() {
        String[] parents = {"Martin", "Dubois", "Watson", "Holmes"};
        return parents;
    }

    public void saveToJson(List<Message> messages) {
        try {
            File file = new File("src/main/resources/data" + "/" + "message.json");
            ObjectMapper objectMapper = new ObjectMapper();
            messages.sort((message1, message2) -> message1.getId().compareTo(message2.getId()));
            objectMapper.writeValue(file, messages);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Message> readJson(File file) {
        List<Message> messages = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            messages = objectMapper.readValue(file, new TypeReference<List<Message>>() {});
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }

    public List<Message> getMessages() {
        List<Message> messages = new ArrayList<>();
        File file = new File("src/main/resources/data" + "/" + "message.json");
        if (file.exists() && !file.isDirectory()){
            messages = readJson(file);
        }
        return messages;
    }

    public List<Message> retrieveParentMessages(String parent) {
        List<Message> messages = getMessages();
        return messages.stream().filter(message -> {
            Map<String, Boolean> hasConfirmed = message.getHasConfirmed();
            return hasConfirmed.containsKey(parent);
        }).collect(Collectors.toList());
    }

    public List<Message> updateMessages(List<Message> messagesOfParent) {
        List<Message> messages = getInstance().getMessages();
        for (Message messageOfParent : messagesOfParent){
            messages.removeIf(message -> message.getId().equals(messageOfParent.getId()));
            messages.add(messageOfParent);
        }
        return messages;
    }

    public Message updateNewMessage(Message message, List<Message> messages) {
        Message updatedNewMessage = new Message();
        updatedNewMessage.setContent(message.getContent());
        updatedNewMessage.setHasConfirmed(message.getHasConfirmed());

        if (null == messages || messages.isEmpty()){
            updatedNewMessage.setId(1);
        }else {
            Integer id = messages.get(messages.size() - 1).getId();
            updatedNewMessage.setId(id+1);
        }
        updatedNewMessage.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return updatedNewMessage;
    }

    public List<Message> jsonToListMessage(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, new TypeReference<List<Message>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}

