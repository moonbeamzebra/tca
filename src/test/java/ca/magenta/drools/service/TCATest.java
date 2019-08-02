package ca.magenta.drools.service;

import ca.magenta.model.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TCATest {


    @Autowired
    CustomerService service;

    @Test
    public void testDiscount() {
        Customer customer1 = new Customer("Jojo");
        customer1.setAge(4);

        Customer customer2 = new Customer("JP");
        customer2.setAge(1);

        service.insertCustomer(customer1);
        service.insertCustomer(customer2);

        System.out.println("Allowed discount Jojo: " + customer1.getDiscount());
        System.out.println("Allowed discount JP: " +customer2.getDiscount());

        assertEquals(25, customer1.getDiscount());
        assertEquals(15, customer2.getDiscount());
    }

}