package ca.magenta.tca.drools.service;

import ca.magenta.tca.drools.config.DroolsConfiguration;
import ca.magenta.tca.model.Customer;
import ca.magenta.tca.fact.category.Message;
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