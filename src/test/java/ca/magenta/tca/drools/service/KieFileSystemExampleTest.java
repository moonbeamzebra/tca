package ca.magenta.tca.drools.service;

import ca.magenta.tca.fact.MappedMsg;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.Message.Level;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KieFileSystemExampleTest {

    @Autowired
    TcaService service;

    /*
    FROM example:
    https://github.com/kiegroup/drools/blob/master/drools-examples-api/kiefilesystem-example/src/main/java/org/drools/example/api/kiefilesystem/KieFileSystemExample.java

     */


    @Test
    public void KieFileSystemExample() {

        ObjectMapper mapper = new ObjectMapper();
        try {

            KieServices ks = KieServices.Factory.get();
            KieRepository kr = ks.getRepository();
            KieFileSystem kfs = ks.newKieFileSystem();

            kfs.write("src/main/resources/ca/magenta/tca/fact/MyTest.drl", getRule());

            KieBuilder kb = ks.newKieBuilder(kfs);

            kb.buildAll(); // kieModule is automatically deployed to KieRepository if successfully built.
            if (kb.getResults().hasMessages(Level.ERROR)) {
                throw new RuntimeException("Build Errors:\n" + kb.getResults().toString());
            }

            KieContainer kContainer = ks.newKieContainer(kr.getDefaultReleaseId());

            KieSession kSession = kContainer.newKieSession();

            MappedMsg mappedMsg1 = new MappedMsg(mapper, "{ \"mymsg\" : \"allo\" }");

            kSession.insert(mappedMsg1);
            kSession.fireAllRules();

            service.dumpFacts();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String getRule() {

        return "" +
                "package ca.magenta.tca.fact\n" +
                "\n" +
                "import ca.magenta.tca.drools.service.TcaService;\n" +
                "import ca.magenta.tca.fact.category.Message;\n" +
                "\n" +
                "declare Message\n" +
                "\t@role( event )\n" +
                "end\n" +
                "\n" +
                "declare MappedMsg\n" +
                "\t@role( event )\n" +
                "end\n" +
                "\n" +
                "rule \"Message\"\n" +
                "salience -999\n" +
                "    when\n" +
                "        message : Message(    )\n" +
                "    then\n" +
                "      TcaService.getLogger().info(\"Got Message: [{}]\",message.toString());\n" +
                "end\n";
    }

}