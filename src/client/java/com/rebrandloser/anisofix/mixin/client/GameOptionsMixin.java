package com.rebrandloser.anisofix.mixin.client;

import com.rebrandloser.anisofix.client.AnisofixClient;
import com.rebrandloser.anisofix.client.RealTimeTextureManager;
import com.rebrandloser.anisofix.client.GameOptionsAccessor;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin {
    @Shadow public abstract SimpleOption<Integer> getMipmapLevels();

    @Inject(method = "write", at = @At("HEAD"))
    private void onWriteHead(CallbackInfo ci) {
        if (AnisofixClient.panicMode && AnisofixClient.originalMipmapLevel != null) {
            this.getMipmapLevels().setValue(AnisofixClient.originalMipmapLevel);
        }
    }

    @Inject(method = "write", at = @At("RETURN"))
    private void onWriteReturn(CallbackInfo ci) {
        if (AnisofixClient.panicMode && AnisofixClient.originalMipmapLevel != null) {
            this.getMipmapLevels().setValue(AnisofixClient.mipmapPanicLevelTemp);
            RealTimeTextureManager.setCurrentMipmapLevel(AnisofixClient.mipmapPanicLevelTemp);
        }
    }
}
