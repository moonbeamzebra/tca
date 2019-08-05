package ca.magenta.tca.common;


import ca.magenta.tca.fact.Fact;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jean-paul.laberge <jplaberge@magenta.ca>
 * @version 0.1
 * @since 2014-03-10
 */
public class KS  implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(KS.class.getPackage().getName());

    private final KieServices kieServices;
    private final KieSession kieSession;

    private volatile boolean doRun = true;
	private static long fireAllRulesPeriode;

	public KS(String name) {
		this(name, null);
	}

	public KS(String name, Long fireAllRulesPeriode) {
		super();

		// load up the knowledge base
		// KieServices is the factory for all KIE services
		kieServices = KieServices.Factory.get();




		// From the kie services, a container is created from the classpath
		KieContainer kc = kieServices.getKieClasspathContainer();


		// From the container, a session is created based on
		// its definition and configuration in the META-INF/kmodule.xml file
		kieSession = kc.newKieSession(name);

		if (fireAllRulesPeriode != null) {
			start(this, fireAllRulesPeriode);
		}

	}


	
	public FactHandle getFactHandle(Fact fact)
	{
		return kieSession.getFactHandle(fact);
	}
	
	public Fact getFact(FactHandle factHandle)
	{
		Fact r_fact = null;
		Object obj = kieSession.getObject(factHandle);
		if (obj instanceof Fact)
		{
			r_fact = (Fact) obj;
		}
		return r_fact;
	}
	

	public FactHandle insert(Fact fact) {
		FactHandle factHandle = kieSession.insert(fact);
		// TODO Try batching asynchronous fireAllRules
		kieSession.fireAllRules();

		return factHandle;
		
	}

	public void update(FactHandle oneFactHandle, Fact withAnotherFact) {
		kieSession.update(oneFactHandle, withAnotherFact);
		// TODO Try batching asynchronous fireAllRules
		kieSession.fireAllRules();
	}

	public void delete(Fact fact) {
		kieSession.delete(getFactHandle(fact));
		// TODO Try batching asynchronous fireAllRules
		kieSession.fireAllRules();
	}

	public static void start(KS ks, long a_fireAllRulesPeriode)
	{
		fireAllRulesPeriode = a_fireAllRulesPeriode;
    	Thread t = new Thread(ks);
    	t.start();
    	logger.trace(t.toString() + " started");
	}
	
    public void stopIt() {
        doRun = false;
    }

	@Override
	public void run() {

		while (doRun) {
			kieSession.fireAllRules();
			try {
				Thread.sleep(fireAllRulesPeriode);
			} catch (InterruptedException e) {
				logger.error("", e);
				e.printStackTrace();
				doRun = false;
			}
		}

	}

	public KieSession getKieSession() {
		return kieSession;
	}

    public KieServices getKieServices() {
        return kieServices;
    }
}


