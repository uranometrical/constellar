package dev.tomat.constellar.mixins.renderer;

import dev.tomat.constellar.Constellar;
import dev.tomat.constellar.modules.IModule;
import dev.tomat.constellar.modules.ModuleNotFoundException;
import dev.tomat.constellar.modules.ModuleType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.awt.*;


@Mixin(RenderGlobal.class)
public class BlockOutlineMixin {
    @Shadow private WorldClient theWorld;


}
