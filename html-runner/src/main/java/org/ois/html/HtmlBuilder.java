package org.ois.html;

import com.github.xpenatan.gdx.backends.teavm.config.AssetFileHandle;
import com.github.xpenatan.gdx.backends.teavm.config.TeaBuildConfiguration;
import com.github.xpenatan.gdx.backends.teavm.config.TeaBuilder;
import com.github.xpenatan.gdx.backends.teavm.config.plugins.TeaReflectionSupplier;
import com.github.xpenatan.gdx.backends.teavm.gen.SkipClass;
import java.io.File;
import java.io.IOException;

import org.ois.core.project.SimulationManifest;
import org.ois.core.runner.RunnerConfiguration;
import org.teavm.tooling.TeaVMTool;
import org.teavm.vm.TeaVMOptimizationLevel;

/** Builds the TeaVM/HTML application. */
@SkipClass
public class HtmlBuilder {
    public static void main(String[] args) throws IOException {
        TeaBuildConfiguration teaBuildConfiguration = new TeaBuildConfiguration();
        teaBuildConfiguration.assetsPath.add(new AssetFileHandle("../../../resources"));
        teaBuildConfiguration.webappPath = new File("build/dist").getCanonicalPath();

        // Register any extra classpath assets here:
        // teaBuildConfiguration.additionalAssetsClasspathFiles.add("org/ois/asset.extension");

        // Register any classes or packages that require reflection here:
        File reflectionsFile = new File("../../../resources/" + SimulationManifest.DEFAULT_FILE_NAME);
        System.err.println("Test file build: (exists: " + reflectionsFile.exists() + ") " + reflectionsFile.toPath());
         TeaReflectionSupplier.addReflectionClass("org.ois.example");
        TeaReflectionSupplier.addReflectionClass("java.io.File");

        TeaVMTool tool = TeaBuilder.config(teaBuildConfiguration);
        tool.setMainClass(HtmlLauncher.class.getName());
        // For many (or most) applications, using the highest optimization won't add much to build time.
        // If your builds take too long, and runtime performance doesn't matter, you can change FULL to SIMPLE .
        tool.setOptimizationLevel(TeaVMOptimizationLevel.FULL);
        tool.setObfuscated(true);
        TeaBuilder.build(tool);
    }
}
