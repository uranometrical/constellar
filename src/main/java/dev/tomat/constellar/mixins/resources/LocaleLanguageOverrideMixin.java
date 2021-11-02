package dev.tomat.constellar.mixins.resources;

import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.Locale;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Mixin(Locale.class)
public abstract class LocaleLanguageOverrideMixin {
    @Shadow
    Map<String, String> properties;

    @Shadow
    protected abstract void loadLocaleData(List<IResource> p_loadLocaleData_1_) throws IOException;

    @Shadow
    protected abstract void checkUnicode();

    @Inject(method = "loadLocaleDataFiles", at = @At("HEAD"))
    public synchronized void loadLocaleDataFiles(IResourceManager resourceManager, List<String> locales, CallbackInfo ci) {
        properties.clear();

        for (String locale : locales) {
            locale = String.format("lang/%s.lang", locale);

            for (String domain : resourceManager.getResourceDomains()) {
                try {
                    loadLocaleData(resourceManager.getAllResources(new ResourceLocation(domain, locale)));
                } catch (IOException ignored) {
                }
            }
        }

        checkUnicode();
    }
}
