package ca.magenta.tca.drools.service;

import ca.magenta.tca.common.KS;
import ca.magenta.tca.fact.Fact;
import org.kie.api.runtime.rule.FactHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TcaService {

    private static final Logger logger = LoggerFactory.getLogger(KS.class.getPackage().getName());

    private KS ks = new KS("StreamKS");


    FactHandle insertFact(Fact fact){
        return ks.insert(fact);
    }

    void dumpFacts() {

        for (FactHandle handle : ks.getKieSession().getFactHandles())
        {
            Object fact = ks.getKieSession().getObject(handle);

            if (fact instanceof Fact)
            {
                logger.info("[{}]", ((Fact) fact).toString(true));
            }
        }
    }

    public static Logger getLogger() {
        return logger;
    }

}