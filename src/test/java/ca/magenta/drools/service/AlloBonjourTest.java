package ca.magenta.drools.service;

import ca.magenta.model.MappedMsg;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlloBonjourTest {


    @Autowired
    CustomerService service;

    @Test
    public void testAlloBonjour() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            MappedMsg mappedMsg1 = new MappedMsg(mapper, "{ \"mymsg\" : \"allo\" }");
            MappedMsg mappedMsg2 = new MappedMsg(mapper, "{ \"mymsg\" : \"bonjour\" }");

            service.insertMessage(mappedMsg1);
            service.insertMessage(mappedMsg2);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}