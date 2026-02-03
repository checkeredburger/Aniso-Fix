package com.rebrandloser.anisofix.client;

import com.rebrandloser.anisofix.client.config.AnisofixConfig;
import com.rebrandloser.anisofix.client.hud.HardwareHud;
import com.rebrandloser.anisofix.client.monitor.HardwareMonitor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class AnisofixClient implements ClientModInitializer {
    public static boolean panicMode = false;
    public static Integer originalMipmapLevel = null;
    public static int mipmapPanicLevelTemp = 0;
    private int tickCounter = 0;

    @Override
    public void onInitializeClient() {
        AnisofixConfig.load();
        
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            tickCounter++;
            if (tickCounter < 20) return;
            tickCounter = 0;

            HardwareMonitor.update();
            if (client.player == null) return;
            
            long freeMem = HardwareMonitor.getFreeVideoMemory();
            long totalMem = HardwareMonitor.getTotalVideoMemory();
            
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
                    if (config.dynamicMipmapScaling) {
                        originalMipmapLevel = client.options.getMipmapLevels().getValue();
                        mipmapPanicLevelTemp = config.mipmapPanicLevel;
                        client.options.getMipmapLevels().setValue(config.mipmapPanicLevel);
                        client.reloadResources();
                    }
                } else if (panicMode && usedMb < config.recoveryThreshold) {
                    panicMode = false;
                    if (config.enableNotifications) {
                        client.inGameHud.getChatHud().addMessage(
                            Text.literal("[AnisoFix] ").formatted(Formatting.GREEN)
                                .append(Text.literal("VRAM Recovered. Exiting Panic Mode.").formatted(Formatting.WHITE))
                        );
                    }
                    if (config.dynamicMipmapScaling && originalMipmapLevel != null) {
                        client.options.getMipmapLevels().setValue(originalMipmapLevel);
                        client.reloadResources();
                        originalMipmapLevel = null;
                        mipmapPanicLevelTemp = 0;
                    }
                }
            }
        });

        HudRenderCallback.EVENT.register(new HardwareHud());
    }
}
