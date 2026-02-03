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
                                .append(Text.literal("VRAM Low! Entering Panic Mode (Anisotropy Disabled)").formatted(Formatting.YELLOW))
                        );
                    }
                } else if (panicMode && usedMb < config.recoveryThreshold) {
                    panicMode = false;
                    if (config.enableNotifications) {
                        client.inGameHud.getChatHud().addMessage(
                            Text.literal("[AnisoFix] ").formatted(Formatting.GREEN)
                                .append(Text.literal("VRAM Recovered. Exiting Panic Mode.").formatted(Formatting.WHITE))
                        );
                    }
                }
            }
        });

        HudRenderCallback.EVENT.register(new HardwareHud());
    }
}
