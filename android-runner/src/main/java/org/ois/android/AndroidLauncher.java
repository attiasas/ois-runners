package org.ois.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import org.ois.core.project.SimulationManifest;
import org.ois.core.runner.RunnerConfiguration;
import org.ois.core.runner.SimulationEngine;

import java.io.IOException;
import java.io.InputStream;

/** Launches the Android application. */
public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
        try {
            initialize(new SimulationEngine(convertToRunnerConfigAndSetParams(configuration)), configuration);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public RunnerConfiguration convertToRunnerConfigAndSetParams(AndroidApplicationConfiguration configuration) throws IOException {
        try (InputStream in = getAssets().open(SimulationManifest.DEFAULT_FILE_NAME)) {
            RunnerConfiguration runnerConfiguration =  AndroidSimulationConfig.getAndroidSimulationConfig(in);

            configuration.useImmersiveMode = true; // Recommended, but not required.

            return runnerConfiguration;
        }
    }
}
