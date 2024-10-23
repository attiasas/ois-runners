package org.ois.html;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.files.FileHandle;
import com.github.xpenatan.gdx.backends.teavm.filesystem.FileData;
import com.github.xpenatan.gdx.backends.teavm.TeaApplicationConfiguration;
import com.github.xpenatan.gdx.backends.teavm.TeaApplication;
import org.ois.core.project.SimulationManifest;
import org.ois.core.runner.RunnerConfiguration;
import org.ois.core.runner.SimulationEngine;
import org.ois.core.utils.log.ILogger;
import org.ois.core.utils.log.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Launches the TeaVM/HTML application.
 */
public class HtmlLauncher {
    public static void main(String[] args) throws IOException {
        TeaApplicationConfiguration config = new TeaApplicationConfiguration("canvas");
        RunnerConfiguration runnerConfiguration = convertToRunnerConfigAndSetParams(config);
        new TeaApplication(new SimulationEngine(runnerConfiguration), config);
    }

    public static RunnerConfiguration convertToRunnerConfigAndSetParams(TeaApplicationConfiguration configuration) throws IOException {
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(SimulationManifest.DEFAULT_FILE_NAME)) {
            System.err.println("TEST: " + in);
            byte[] file = new FileData(SimulationManifest.DEFAULT_FILE_NAME).getBytes();
            System.err.println("TEST FILE: " + Arrays.toString(file));

            System.err.println("TEST CONFIG: " + HtmlConfig.TestString);

//            Logger.setLogLevel(ILogger.Level.Debug);
//            Logger.setTopics("states", "bla");
            Logger.setTopics("bla");

//            System.err.println("TEST BUILDER: " + new String(file));
            RunnerConfiguration runnerConfiguration = RunnerConfiguration.getRunnerConfigurations(null);

            runnerConfiguration.setType(RunnerConfiguration.RunnerType.Html);

            //// If width and height are each greater than 0, then the app will use a fixed size.
            //configuration.width = 640;
            //configuration.height = 480;
            //// If width and height are both 0, then the app will use all available space.
            //configuration.width = 0;
            //configuration.height = 0;
            //// If width and height are both -1, then the app will fill the canvas size.
            configuration.width = -1;
            configuration.height = -1;

            return runnerConfiguration;
        }
    }
}
