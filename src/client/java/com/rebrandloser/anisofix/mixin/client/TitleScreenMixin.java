package com.rebrandloser.anisofix.mixin.client;

import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    public int adjustRealmsHeight(int height) {
        return height;
    }
}
