package org.ois.desktop;

import org.ois.core.project.SimulationManifest;
import org.ois.core.runner.RunnerConfiguration;
import org.ois.core.utils.io.data.formats.JsonFormat;
import org.ois.core.utils.log.ILogger;

import java.io.IOException;
import java.io.InputStream;

// Changing File location, name and content could break the plugin that inject values to it
public class DesktopSimulationConfig extends RunnerConfiguration {

    public static final String LOG_LEVEL = "info";
    public static final String[] LOG_TOPICS = new String[]{};

    public static final boolean DEBUG_MODE = false;
    public static final String DEV_MODE_DIR = "";

    public DesktopSimulationConfig() {
        super();
        setType(RunnerType.Desktop);

        setLogLevel(getInitialLogLevel());
        setLogTopics(getInitialLogTopic());

        setDebugMode(DEBUG_MODE);
        setDevModeDir(DEV_MODE_DIR);
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

    public static DesktopSimulationConfig getDesktopSimulationConfig(InputStream in) throws IOException {
        DesktopSimulationConfig config = new DesktopSimulationConfig();
        config.setSimulationManifest(JsonFormat.compact().load(new SimulationManifest(), in));
        return config;
    }
}
