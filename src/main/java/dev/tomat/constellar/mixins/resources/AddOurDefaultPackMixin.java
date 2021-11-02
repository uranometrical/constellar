package dev.tomat.constellar.mixins.resources;

import com.google.common.collect.Lists;
import dev.tomat.constellar.resources.ConstellarPack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.resources.*;
import net.minecraft.client.settings.GameSettings;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.List;

@Mixin(Minecraft.class)
public abstract class AddOurDefaultPackMixin {
    @Shadow
    @Final
    private DefaultResourcePack mcDefaultResourcePack;

    @Shadow
    @Final
    private List<IResourcePack> defaultResourcePacks;

    @Shadow
    private ResourcePackRepository mcResourcePackRepository;

    @Shadow
    private IReloadableResourceManager mcResourceManager;

    @Shadow
    public GameSettings gameSettings;

    @Shadow
    @Final
    private static Logger logger;

    @Shadow
    private LanguageManager mcLanguageManager;

    @Shadow
    public RenderGlobal renderGlobal;

    @Inject(method = "startGame", at = @At("HEAD"))
    public void startGame(CallbackInfo ci) {
        defaultResourcePacks.add(new ConstellarPack());
    }

    @Inject(method = "refreshResources", at = @At("HEAD"), cancellable = true)
    public void refreshResources(CallbackInfo ci) {
        List<IResourcePack> list = Lists.newArrayList(this.defaultResourcePacks);

        for (ResourcePackRepository.Entry resourcePackRepositoryEntry : mcResourcePackRepository.getRepositoryEntries()) {
            list.add(resourcePackRepositoryEntry.getResourcePack());
        }

        if (mcResourcePackRepository.getResourcePackInstance() != null) {
            list.add(this.mcResourcePackRepository.getResourcePackInstance());
        }

        try {
            mcResourceManager.reloadResources(list);
        } catch (RuntimeException runtimeexception) {
            logger.info("Caught error stitching, removing all assigned resource packs", runtimeexception);
            list.clear();
            list.addAll(this.defaultResourcePacks);
            mcResourcePackRepository.setRepositories(Collections.emptyList());
            mcResourceManager.reloadResources(list);
            gameSettings.resourcePacks.clear();
            gameSettings.incompatibleResourcePacks.clear();
            gameSettings.saveOptions();
        }

        mcLanguageManager.parseLanguageMetadata(list);

        if (renderGlobal != null) {
            renderGlobal.loadRenderers();
        }

        ci.cancel();
    }
}
