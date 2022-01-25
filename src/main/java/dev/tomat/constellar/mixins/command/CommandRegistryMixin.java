package dev.tomat.constellar.mixins.command;

import dev.tomat.constellar.content.command.CommandNameHistory;
import dev.tomat.constellar.content.command.CommandVersion;
import net.minecraft.command.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerCommandManager.class)
public abstract class CommandRegistryMixin extends CommandHandler implements IAdminCommand {
    // injects into the ctor
    @Inject(method = "<init>", at = @At("RETURN"))
    public void constructorHead(CallbackInfo ci) {
        this.registerCommand(new CommandVersion());
        this.registerCommand(new CommandNameHistory());
    }
}
