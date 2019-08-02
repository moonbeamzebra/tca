package ca.magenta.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class MappedMsg implements Message {

    final private String payload;
    final private Map<String, Object> mapped;

    public MappedMsg(ObjectMapper mapper, String payload) throws IOException {
        this.payload = payload;
        this.mapped = mapper.readValue(payload, Map.class);
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
}
