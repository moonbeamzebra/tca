package ca.magenta.tca.drools.service;

import ca.magenta.tca.common.KS;
import ca.magenta.tca.fact.Fact;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.stereotype.Service;

@Service
public class TcaService {

    private KS ks = new KS("StreamKS");

    public FactHandle insertFact(Fact fact){
        return ks.insert(fact);
    }

}