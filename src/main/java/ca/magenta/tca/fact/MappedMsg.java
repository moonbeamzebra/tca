package ca.magenta.tca.fact;

import ca.magenta.tca.fact.category.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class MappedMsg implements Message, Fact {

    final private String payload;
    final private Map<String, Object> mapped;

    public MappedMsg(ObjectMapper mapper, String payload) throws IOException {
        this.payload = payload;
        this.mapped = mapper.readValue(payload, Map.class);
    }

    public boolean payloadContains(String s)
    {
        return payload.contains(s);

    }

    public String getPayload() {
        return payload;
    }

    public Map<String, Object> getMapped() {
        return mapped;
    }

    @Override
    public String toString() {
        return "MappedMsg{" +
                "payload='" + payload + '\'' +
                ", mapped=" + mapped +
                '}';
    }

    @Override
    public String toString(boolean pretty) {
        return toString();
    }
}
