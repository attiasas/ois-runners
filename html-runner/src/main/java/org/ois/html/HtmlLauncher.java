package org.ois.html;

import com.github.xpenatan.gdx.backends.teavm.TeaApplicationConfiguration;
import com.github.xpenatan.gdx.backends.teavm.TeaApplication;
import org.ois.core.runner.SimulationEngine;

/**
 * Launches the TeaVM/HTML application.
 */
public class HtmlLauncher {
    public static void main(String[] args) {
        TeaApplicationConfiguration config = new TeaApplicationConfiguration("canvas");
        new TeaApplication(new SimulationEngine(convertToRunnerConfigAndSetParams(config)), config);
    }

    public static SimulationConfig convertToRunnerConfigAndSetParams(TeaApplicationConfiguration configuration) {
        //// If width and height are each greater than 0, then the app will use a fixed size.
        //configuration.width = 640;
        //configuration.height = 480;
        //// If width and height are both 0, then the app will use all available space.
        //configuration.width = 0;
        //configuration.height = 0;
        //// If width and height are both -1, then the app will fill the canvas size.
        configuration.width = SimulationConfig.SCREEN_WIDTH;
        configuration.height = SimulationConfig.SCREEN_HEIGHT;

        return new SimulationConfig();
    }
}
