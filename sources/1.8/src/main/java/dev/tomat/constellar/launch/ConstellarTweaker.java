package dev.tomat.constellar.launch;

import dev.tomat.constellar.Constellar;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.launch.MixinTweaker;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ConstellarTweaker implements ITweaker {
    private final ArrayList<String> Arguments = new ArrayList<>();

    public static LoadContext Context;

    public MixinTweaker Mixin;

    public ConstellarTweaker() {
        Mixin = new MixinTweaker();
    }

    @Override
    public void acceptOptions(List<String> args, File gameDir, final File assetsDir, String profile) {
        Context = LoadContext.getLoadContext();

        if (!LoadContext.standalone(Context))
        {
            Mixin.acceptOptions(args, gameDir, assetsDir, profile);
            return;
        }

        Arguments.addAll(args);

        addArgument("gameDir", gameDir);
        addArgument("assetsDir", assetsDir);
        addArgument("version", profile);

        Mixin.acceptOptions(Arguments, gameDir, assetsDir, profile);
    }

    @Override
    public String getLaunchTarget() {
        return Mixin.getLaunchTarget();
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        Context = LoadContext.getLoadContext();

        Constellar.LOGGER.info("Tweaker", "Load context: " + Context);

        if (!LoadContext.standalone(Context)) {
            Mixin.injectIntoClassLoader(classLoader);
            return;
        }

        Constellar.LOGGER.info("Tweaker", "Initializing Bootstraps...");
        MixinBootstrap.init();

        Constellar.LOGGER.info("Tweaker", "Adding mixin configuration...");
        MixinEnvironment environment = MixinEnvironment.getDefaultEnvironment();
        Mixins.addConfiguration("mixins.constellar.json");

        if (Context == LoadContext.StandaloneOptiFine) {
            environment.setObfuscationContext("notch");
        }

        if (environment.getObfuscationContext() == null) {
            environment.setObfuscationContext("notch");
        }

        environment.setSide(MixinEnvironment.Side.CLIENT);
    }

    @Override
    public String[] getLaunchArguments() {
        if (!LoadContext.standalone(Context)) {
            return Mixin.getLaunchArguments();
        }

        return Arguments.toArray(new String[0]);
    }

    private void addArgument(String label, Object value) {
        Arguments.add("--" + label);
        Arguments.add(value instanceof String ? (String) value : value instanceof File ? ((File) value).getAbsolutePath() : ".");
    }

    public enum LoadContext {
        Standalone,
        StandaloneOptiFine,
        Forge,
        ForgeOptiFine;

        public static LoadContext getLoadContext() {
            boolean optiFine = Launch.classLoader.getTransformers().stream().anyMatch(
                    x -> x.getClass().getName().toLowerCase(Locale.ENGLISH).contains("optifine")
            );

            boolean forge = Launch.classLoader.getTransformers().stream().anyMatch(
                    x -> x.getClass().getName().toLowerCase(Locale.ENGLISH).contains("forge")
            );

            if (optiFine && !forge)
                return StandaloneOptiFine;

            if (!optiFine && !forge)
                return Standalone;

            if (!optiFine)
                return Forge;

            return ForgeOptiFine;
        }

        public static boolean standalone(LoadContext context) {
            return context == Standalone || context == StandaloneOptiFine;
        }
    }
}
