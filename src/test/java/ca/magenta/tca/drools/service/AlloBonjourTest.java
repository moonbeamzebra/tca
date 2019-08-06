package ca.magenta.tca.drools.service;

import ca.magenta.tca.fact.MappedMsg;
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


//    @Autowired
//    CustomerService service;
    @Autowired
    TcaService service;

    @Test
    public void testAlloBonjour() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            MappedMsg mappedMsg1 = new MappedMsg(mapper, "{ \"mymsg\" : \"allo\" }");
            MappedMsg mappedMsg2 = new MappedMsg(mapper, "{ \"mymsg\" : \"bonjour\" }");

            service.insertFact(mappedMsg1);
            service.insertFact(mappedMsg2);

            service.dumpFacts();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    @Test
//    public void testDiscount() {
//        Customer customer1 = new Customer("Jojo");
//        customer1.setAge(4);
//
//        Customer customer2 = new Customer("JP");
//        customer2.setAge(1);
//
//        service.insertCustomer(customer1);
//        service.insertCustomer(customer2);
//
//        System.out.println("Allowed discount Jojo: " + customer1.getDiscount());
//        System.out.println("Allowed discount JP: " +customer2.getDiscount());
//
//        assertEquals(25, customer1.getDiscount());
//        assertEquals(15, customer2.getDiscount());
//    }


}