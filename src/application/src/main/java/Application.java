import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    private static final String CONFIGJSON = "config.json";
    public static void main(String[] args) {
        Path configPath = Path.of(CONFIGJSON);
    }
}
