import Services.LiaisonService;
import beans.Message;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class LiaisonServiceTest {

    LiaisonService liaisonService;

    List<Message> messages;

    Date dateFromNow;

    @Before
    public void setUp() {
        messages = new ArrayList<>();
        liaisonService = LiaisonService.getInstance();
        dateFromNow = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Message message = new Message(1, "msg", dateFromNow, new HashMap<>());
        messages.add(message);
    }

    @Test
    public void should_set_id_and_date_when_comparing_with_actual_messages(){
        Message givenMessage = new Message(null, "msg", null, new HashMap<>());

        Message actualMessage = liaisonService.updateNewMessage(givenMessage, messages);

        Message expectedMessage = new Message(messages.size()+1, givenMessage.getContent(),
                dateFromNow, givenMessage.getHasConfirmed());

        Assert.assertEquals(expectedMessage, actualMessage);
    }
}
