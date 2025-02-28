package org.ois.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.ois.core.project.SimulationManifest;
import org.ois.core.runner.RunnerConfiguration;
import org.ois.core.runner.SimulationEngine;

import java.io.IOException;
import java.io.InputStream;

public class DesktopLauncher {
    public static void main(String[] args) throws IOException {
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
        createApplication();
    }

    private static Lwjgl3Application createApplication() throws IOException {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        return new Lwjgl3Application(new SimulationEngine(convertToRunnerConfigAndSetParams(config)), config);
    }

    public static RunnerConfiguration convertToRunnerConfigAndSetParams(Lwjgl3ApplicationConfiguration configuration) throws IOException {
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(SimulationManifest.DEFAULT_FILE_NAME)) {
            RunnerConfiguration runnerConfig = DesktopSimulationConfig.getDesktopSimulationConfig(in);
            configuration.setTitle(runnerConfig.getSimulationManifest().getTitle());

            if (!runnerConfig.getDebugMode()) {
                //// Vsync limits the frames per second to what your hardware can display, and helps eliminate
                //// screen tearing. This setting doesn't always work on Linux, so the line after is a safeguard.
                configuration.useVsync(true);
                //// Limits FPS to the refresh rate of the currently active monitor, plus 1 to try to match fractional
                //// refresh rates. The Vsync setting above should limit the actual FPS to match the monitor.
                configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
                //// If you remove the above line and set Vsync to false, you can get unlimited FPS, which can be
                //// useful for testing performance, but can also be very stressful to some hardware.
                //// You may also need to configure GPU drivers to fully disable Vsync; this can cause screen tearing.
            } else {
                configuration.useVsync(false);
            }
            if (runnerConfig.getSimulationManifest().getScreenWidth() > 0 || runnerConfig.getSimulationManifest().getScreenHeight() > 0) {
                configuration.setWindowedMode(
                        runnerConfig.getSimulationManifest().getScreenWidth(),
                        runnerConfig.getSimulationManifest().getScreenHeight()
                );
            }
            //// You can change these files; they are in lwjgl3/src/main/resources/ .
            configuration.setWindowIcon( "icons/icon128.png", "icons/icon32.png");

            return runnerConfig;
        }
    }
}
