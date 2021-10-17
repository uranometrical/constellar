package io.github.steviegt6.constellar.launch;

import io.github.steviegt6.constellar.ConstellarMain;
import net.minecraft.launchwrapper.IClassNameTransformer;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

// TODO: Configurable OptiFine
public class ConstellarTweaker implements ITweaker {
    private static final Logger LOGGER = LogManager.getLogger();

    public static boolean UseOptiFineLol = true;

    private final ArrayList<String> Arguments = new ArrayList<>();

    @Override
    public void acceptOptions(List<String> args, File gameDir, final File assetsDir, String profile) {
        Arguments.addAll(args);

        addArgument("gameDir", gameDir);
        addArgument("assetsDir", assetsDir);
        addArgument("version", profile);
    }

    @Override
    public String getLaunchTarget() {
        return "net.minecraft.client.main.Main";
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        if (UseOptiFineLol) {
            try {
                registerEmbeddedTransformer(classLoader, "libs/OptiFine.jar", "optifine.OptiFineClassTransformer");
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        LOGGER.info("Initializing Bootstraps...");
        MixinBootstrap.init();
        LOGGER.info("Adding mixin configuration...");
        MixinEnvironment environment = MixinEnvironment.getDefaultEnvironment();
        Mixins.addConfiguration("mixins.constellar.json");

        if (environment.getObfuscationContext() == null) {
            environment.setObfuscationContext("notch"); // Switch's to notch mappings
        }

        environment.setSide(MixinEnvironment.Side.CLIENT);
    }

    @Override
    public String[] getLaunchArguments() {
        return Arguments.toArray(new String[]{});
    }

    private void addArgument(String label, Object value) {
        Arguments.add("--" + label);
        Arguments.add(value instanceof String ? (String) value : value instanceof File ? ((File) value).getAbsolutePath() : ".");
    }

    // Registers a transformer from an embedded jar file.
    public void registerEmbeddedTransformer(LaunchClassLoader loader, String embeddedLocation, String tweakerClass) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        URL uri = ConstellarMain.class.getClassLoader().getResource(embeddedLocation);
        URLClassLoader streamLoader = new URLClassLoader(new URL[] { uri }, System.class.getClassLoader());
        Class<?> loadedClass = streamLoader.loadClass(tweakerClass);

        registerObjectTransformer(loader, loadedClass.newInstance());
    }

    // Helper method that uses reflection to register a new transformer with an existing object instance.
    @SuppressWarnings("unchecked")
    public void registerObjectTransformer(LaunchClassLoader loader, Object transformer) throws NoSuchFieldException, IllegalAccessException {
        // No try-catch here because we WANT this to fail if something goes wrong.

        Field transformers = LaunchClassLoader.class.getDeclaredField("transformers");
        Field renameTransformer = LaunchClassLoader.class.getDeclaredField("renameTransformer");

        ArrayList<IClassTransformer> transformerList = (ArrayList<IClassTransformer>) transformers.get(loader);
        transformerList.add((IClassTransformer) transformer);

        if (transformer instanceof IClassNameTransformer && renameTransformer.get(loader) == null)
            renameTransformer.set(loader, transformer);
    }
}
