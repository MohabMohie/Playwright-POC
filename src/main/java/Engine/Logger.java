package Engine;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;

public class Logger {
    @Getter
    private static final
    org.apache.logging.log4j.Logger logger = LogManager.getLogger("logger");
}
