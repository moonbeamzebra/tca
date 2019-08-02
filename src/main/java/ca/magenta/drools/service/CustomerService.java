package ca.magenta.drools.service;

import ca.magenta.drools.config.DroolsConfiguration;
import ca.magenta.model.Customer;
import ca.magenta.model.MappedMsg;
import ca.magenta.model.Message;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private KieSession kieSession=new DroolsConfiguration().getKieSession();

    public Customer insertCustomer(Customer customer){
        kieSession.insert(customer);
        kieSession.fireAllRules();
        return customer;

    }

    public Message insertMessage(Message message) {

        kieSession.insert(message);
        kieSession.fireAllRules();
        return message;

    }
}