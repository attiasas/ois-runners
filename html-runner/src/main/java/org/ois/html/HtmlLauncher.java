package org.ois.html;

import com.github.xpenatan.gdx.backends.teavm.TeaApplicationConfiguration;
import com.github.xpenatan.gdx.backends.teavm.TeaApplication;
import org.ois.core.runner.RunnerConfiguration;
import org.ois.core.runner.SimulationEngine;

import java.io.IOException;

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
