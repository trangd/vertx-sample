package beans;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



public class Message {

    private Integer id;

    private String content;

    private java.util.Date date;

    private Map<String, Boolean> hasConfirmed;

    public Message(Integer id, String content, Date date, Map<String, Boolean> hasConfirmed) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.hasConfirmed = hasConfirmed;
    }

    public Message() {
        super();
    }

    public Integer getId(){
        return this.id;
    }

    public String getContent(){
        return this.content;
    }

    public Date getDate(){
        return this.date;
    }

    public Map<String,Boolean> getHasConfirmed(){
        return this.hasConfirmed;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public void setHasConfirmed(Map<String, Boolean> hasConfirmed){
         this.hasConfirmed = hasConfirmed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (id != null ? !id.equals(message.id) : message.id != null) return false;
        if (content != null ? !content.equals(message.content) : message.content != null) return false;
        if (date != null ? !date.equals(message.date) : message.date != null) return false;
        return hasConfirmed != null ? hasConfirmed.equals(message.hasConfirmed) : message.hasConfirmed == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (hasConfirmed != null ? hasConfirmed.hashCode() : 0);
        return result;
    }
}
