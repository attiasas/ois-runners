package org.ois.html;

import org.ois.core.runner.RunnerConfiguration;
import org.ois.core.utils.io.data.DataNode;
import org.ois.core.utils.log.ILogger;

// Changing File location, name and content could break the plugin that inject values to it
public class HtmlSimulationConfig extends RunnerConfiguration {
    public static final String TITLE = "OIS";
    public static final int SCREEN_WIDTH = 0;
    public static final int SCREEN_HEIGHT = 0;

    public static final String LOG_LEVEL = "info";
    public static final String[] LOG_TOPICS = new String[]{};

    public static final boolean DEBUG_MODE = false;

    public static final String REFLECTION_ITEMS_FILE_NAME = "reflection.ois";

    public HtmlSimulationConfig() {
        super();
        setType(RunnerType.Html);

        setLogLevel(getInitialLogLevel());
        setLogTopics(getInitialLogTopic());

        setDebugMode(DEBUG_MODE);
    }

    private static ILogger.Level getInitialLogLevel()  {
        String logLevel = LOG_LEVEL;
        if (System.getenv(ILogger.ENV_LOG_LEVEL) != null) {
            logLevel = System.getenv(ILogger.ENV_LOG_LEVEL);
        }
        return ILogger.toLogLevel(logLevel);
    }

    private static String[] getInitialLogTopic()  {
        if(System.getenv(ILogger.ENV_LOG_TOPICS) != null) {
            return System.getenv(ILogger.ENV_LOG_TOPICS).split(";");
        }
        return LOG_TOPICS;
    }

    public static String[] loadReflectionItems(DataNode data) {
        if (data == null || !DataNode.Type.Collection.equals(data.getType())) {
            throw new RuntimeException("Can't load reflection items content from configuration file");
        }
        return data.toStringCollection();
    }
}