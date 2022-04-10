package util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class JsonParser {

    private Map<String, String> settingsMap = new HashMap<>();

    public JsonParser() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            settingsMap = mapper.readValue(Paths.get("settings.json").toFile(), Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getToken() {
        return settingsMap.get("token");
    }
}
