package com.rebrandloser.anisofix.mixin.client;

import com.rebrandloser.anisofix.client.AnisofixClient;
import net.minecraft.client.gl.SamplerCache;
import com.mojang.blaze3d.textures.AddressMode;
import com.mojang.blaze3d.textures.FilterMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SamplerCache.class)
public class SamplerCacheMixin {
    @ModifyVariable(
        method = "get(Lcom/mojang/blaze3d/textures/AddressMode;Lcom/mojang/blaze3d/textures/AddressMode;Lcom/mojang/blaze3d/textures/FilterMode;Lcom/mojang/blaze3d/textures/FilterMode;Z)Lnet/minecraft/client/gl/GpuSampler;",
        at = @At("HEAD"),
        argsOnly = true,
        ordinal = 0
    )
    private boolean modifyAnisotropy(boolean useAnisotropy) {
        if (AnisofixClient.panicMode) {
            return false;
        }
        return useAnisotropy;
    }
}
