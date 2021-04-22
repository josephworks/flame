package bot.flame.config;

import bot.flame.config.value.ConfigValue;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Configuration {

    private final Map<String, ConfigValue> values = new HashMap<>();

    @SneakyThrows
    public Configuration() {
        Path path = Paths.get("config.json");
        if (!path.toFile().exists()) {
            InputStream inputStream = getClass().getResourceAsStream("/config.json");
            Files.copy(inputStream, path);
        }
        byte[] bytes = Files.readAllBytes(path);
        Gson gson = new Gson();
        Map<?, ?> map = gson.fromJson(new String(bytes), Map.class);
        for(Map.Entry<?, ?> entry : map.entrySet()) {
            values.put(entry.getKey().toString(), new ConfigValue(entry.getValue().toString()));
        }
    }

    public ConfigValue getValue(String key) {
        return values.get(key);
    }
}
