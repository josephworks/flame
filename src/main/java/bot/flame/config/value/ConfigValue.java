package bot.flame.config.value;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConfigValue {

    private final String value;

    private boolean booleanValue() {
        return Boolean.parseBoolean(value);
    }

    private int intValue() {
        return Integer.parseInt(value);
    }

    private float floatValue() {
        return Float.parseFloat(value);
    }

    private double doubleValue() {
        return Double.parseDouble(value);
    }
}
