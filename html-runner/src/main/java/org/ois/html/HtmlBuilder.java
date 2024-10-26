package org.ois.html;

import com.github.xpenatan.gdx.backends.teavm.config.AssetFileHandle;
import com.github.xpenatan.gdx.backends.teavm.config.TeaBuildConfiguration;
import com.github.xpenatan.gdx.backends.teavm.config.TeaBuilder;
import com.github.xpenatan.gdx.backends.teavm.config.plugins.TeaReflectionSupplier;
import com.github.xpenatan.gdx.backends.teavm.gen.SkipClass;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import org.ois.core.project.SimulationManifest;
import org.ois.core.utils.io.data.formats.JsonFormat;
import org.teavm.tooling.TeaVMTool;
import org.teavm.vm.TeaVMOptimizationLevel;

/** Builds the TeaVM/HTML application. */
@SkipClass
public class HtmlBuilder {
    public static void main(String[] args) throws IOException {
        TeaBuildConfiguration teaBuildConfiguration = new TeaBuildConfiguration();

        String assetsDirPathStr = "../../../resources";
        Path assetsDirPath = new File(assetsDirPathStr).toPath();

        teaBuildConfiguration.assetsPath.add(new AssetFileHandle(assetsDirPathStr));
        teaBuildConfiguration.webappPath = new File("build/dist").getCanonicalPath();

        // Register any extra classpath assets here:
        // teaBuildConfiguration.additionalAssetsClasspathFiles.add("org/ois/asset.extension");

        try (InputStream manifestInputStream = Files.newInputStream(assetsDirPath.resolve(SimulationManifest.DEFAULT_FILE_NAME))) {
            SimulationManifest manifest = JsonFormat.compact().load(new SimulationManifest(), manifestInputStream);

            // Set configurations
            teaBuildConfiguration.htmlTitle = SimulationConfig.TITLE;

            // Register any classes or packages that require reflection here:
            System.err.println("#################################################################\n" +
                    "|\n" +
                    "| OIS - Loading Reflections\n" +
                    "|\n" +
                    "#################################################################");
            // The engine uses Reflection for States, register them.
            Set<String> reflectionItems = new HashSet<>(manifest.getStates().values());
            // Get custom from project
            Path customReflectionItemsFilePath = assetsDirPath.resolve(SimulationConfig.REFLECTION_ITEMS_FILE_NAME);
            if (customReflectionItemsFilePath.toFile().exists()) {
                System.err.println("Loading custom reflection items from project");
                try (InputStream refelectionInputStream = Files.newInputStream(customReflectionItemsFilePath)) {
                    reflectionItems.addAll(Set.of(SimulationConfig.loadReflectionItems(JsonFormat.compact().load(refelectionInputStream))));
                }
            }
            for (String reflectionItem : reflectionItems) {
                System.err.println("* " + reflectionItem);
                TeaReflectionSupplier.addReflectionClass(reflectionItem);
            }
        }

        TeaVMTool tool = TeaBuilder.config(teaBuildConfiguration);
        tool.setMainClass(HtmlLauncher.class.getName());
        // For many (or most) applications, using the highest optimization won't add much to build time.
        // If your builds take too long, and runtime performance doesn't matter, you can change FULL to SIMPLE .
        tool.setOptimizationLevel(TeaVMOptimizationLevel.FULL);
        tool.setObfuscated(true);
        TeaBuilder.build(tool);
    }
}
