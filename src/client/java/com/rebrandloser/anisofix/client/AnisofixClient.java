package com.rebrandloser.anisofix.client;

import com.rebrandloser.anisofix.client.config.AnisofixConfig;
import com.rebrandloser.anisofix.client.hud.HardwareHud;
import com.rebrandloser.anisofix.client.monitor.HardwareMonitor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class AnisofixClient implements ClientModInitializer {
    public static boolean panicMode = false;

    @Override
    public void onInitializeClient() {
        AnisofixConfig.load();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            HardwareMonitor.update();
            if (client.player == null) return;
            
            long freeMem = HardwareMonitor.getFreeVideoMemory();
            long totalMem = HardwareMonitor.getTotalVideoMemory();
            
            if (totalMem > 0) {
                long usedMem = totalMem - freeMem;
                long usedMb = usedMem / 1024 / 1024;
                
                if (!panicMode && usedMb > AnisofixConfig.getInstance().panicThreshold) {
                    panicMode = true;
                } else if (panicMode && usedMb < AnisofixConfig.getInstance().recoveryThreshold) {
                    panicMode = false;
                }
            }
        });

        HudRenderCallback.EVENT.register(new HardwareHud());
    }
}
