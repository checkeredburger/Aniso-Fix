package com.rebrandloser.anisofix.client;

import com.rebrandloser.anisofix.client.config.AnisofixConfig;
import com.rebrandloser.anisofix.client.hud.HardwareHud;
import com.rebrandloser.anisofix.client.monitor.HardwareMonitor;
import com.rebrandloser.anisofix.client.RealTimeTextureManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class AnisofixClient implements ClientModInitializer {
    public static boolean panicMode = false;
    public static Integer originalMipmapLevel = null;
    public static int mipmapPanicLevelTemp = 0;
    private int tickCounter = 0;
    private static MinecraftClient client;
    private static int afLevelChangeTicks = 0;
    private static float previousAFLevel = -1.0f;
    

    @Override
    public void onInitializeClient() {
        AnisofixConfig.load();

        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            client = minecraftClient;
            tickCounter++;
            if (tickCounter < 20) return;
            tickCounter = 0;

            HardwareMonitor.update();
            if (client.player == null) return;

            long freeMem = HardwareMonitor.getFreeVideoMemory();
            long totalMem = HardwareMonitor.getTotalVideoMemory();

            float currentAFLevel = RealTimeTextureManager.getCurrentAnisotropy();
            if (Math.abs(currentAFLevel - previousAFLevel) > 0.01f) {
                previousAFLevel = currentAFLevel;
                afLevelChangeTicks = 20;
            }

            if (afLevelChangeTicks > 0) {
                afLevelChangeTicks--;
                if (afLevelChangeTicks % 5 == 0) {
                    RealTimeTextureManager.updateCurrentTextureBinding();
                }
            }

            if (totalMem > 0) {
                long usedMem = totalMem - freeMem;
                long usedMb = usedMem / 1024 / 1024;
                AnisofixConfig config = AnisofixConfig.getInstance();

                if (!panicMode && usedMb > config.panicThreshold) {
                    panicMode = true;
                    if (config.enableNotifications) {
                        client.inGameHud.getChatHud().addMessage(
                            Text.literal("[AnisoFix] ").formatted(Formatting.RED)
                                .append(Text.literal("VRAM Low! Entering Panic Mode (Anisotropy Disabled").append(config.dynamicMipmapScaling ? " & Mipmaps Reduced" : "").append(")").formatted(Formatting.YELLOW))
                        );
                    }

                    RealTimeTextureManager.setInPanicMode(true);

                    if (RealTimeTextureManager.isAnisotropicFilteringSupported()) {
                        RealTimeTextureManager.setAnisotropicFilteringLevel(1.0f);
                        RealTimeTextureManager.updateCurrentTextureBinding();
                    }

                    if (config.dynamicMipmapScaling) {
                        originalMipmapLevel = client.options.getMipmapLevels().getValue();
                        mipmapPanicLevelTemp = config.mipmapPanicLevel;

                        client.options.getMipmapLevels().setValue(config.mipmapPanicLevel);
                        RealTimeTextureManager.setCurrentMipmapLevel(config.mipmapPanicLevel);
                    }
                } else if (panicMode && usedMb < config.recoveryThreshold) {
                    panicMode = false;
                    if (config.enableNotifications) {
                        client.inGameHud.getChatHud().addMessage(
                            Text.literal("[AnisoFix] ").formatted(Formatting.GREEN)
                                .append(Text.literal("VRAM Recovered. Exiting Panic Mode.").formatted(Formatting.WHITE))
                        );
                    }

                    RealTimeTextureManager.setInPanicMode(false);

                    if (RealTimeTextureManager.isAnisotropicFilteringSupported()) {
                        RealTimeTextureManager.setAnisotropicFilteringLevel(RealTimeTextureManager.getMaxAnisotropy());
                        RealTimeTextureManager.updateCurrentTextureBinding();
                    }

                    if (config.dynamicMipmapScaling && originalMipmapLevel != null) {
                        client.options.getMipmapLevels().setValue(originalMipmapLevel);
                        RealTimeTextureManager.setCurrentMipmapLevel(originalMipmapLevel);
                        originalMipmapLevel = null;
                        mipmapPanicLevelTemp = 0;
                    }
                }
            }
        });

        HudRenderCallback.EVENT.register(new HardwareHud());
    }
}
