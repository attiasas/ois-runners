package org.ois.html;

import org.ois.core.runner.RunnerConfiguration;
import org.ois.core.utils.io.data.DataNode;
import org.ois.core.utils.io.data.formats.JsonFormat;
import org.ois.core.utils.log.ILogger;

import java.util.ArrayList;

// Changing File location, name and content could break the plugin that inject values to it
public class SimulationConfig extends RunnerConfiguration {
    public static final String TITLE = "OIS";
    public static final int SCREEN_WIDTH = 0;
    public static final int SCREEN_HEIGHT = 0;

    public static final String LOG_LEVEL = "info";
    public static final String[] LOG_TOPICS = new String[]{};

    public static final String REFLECTION_ITEMS_FILE_NAME = "reflection.ois";

    public SimulationConfig() {
        super();
        setType(RunnerType.Html);

        setLogLevel(ILogger.toLogLevel(LOG_LEVEL));
        setLogTopics(LOG_TOPICS);
    }

    public static String[] loadReflectionItems(DataNode data) {
        if (data == null || !DataNode.Type.Collection.equals(data.getType())) {
            throw new RuntimeException("Can't load reflection items content from configuration file");
        }
        return data.toStringCollection();
    }
}
