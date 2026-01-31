package com.rebrandloser.anisofix.client.hud;

import com.rebrandloser.anisofix.client.AnisofixClient;
import com.rebrandloser.anisofix.client.config.AnisofixConfig;
import com.rebrandloser.anisofix.client.monitor.HardwareMonitor;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

import java.util.ArrayList;
import java.util.List;

public class HardwareHud implements HudRenderCallback {
    @Override
    public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        AnisofixConfig config = AnisofixConfig.getInstance();
        if (!config.showHud) return;

        MinecraftClient client = MinecraftClient.getInstance();
        List<String> lines = new ArrayList<>();

        if (config.showGpuName) {
            lines.add("GPU: " + HardwareMonitor.getGpuName());
        }
        if (config.showGpuVendor) {
            lines.add("Vendor: " + HardwareMonitor.getGpuVendor());
        }
        if (config.showCpuName) {
            lines.add("CPU: " + HardwareMonitor.getCpuName());
        }
        if (config.showVram) {
            long total = HardwareMonitor.getTotalVideoMemory();
            long free = HardwareMonitor.getFreeVideoMemory();
            if (total > 0) {
                long used = total - free;
                lines.add(String.format("VRAM: %d / %d MB %s", used / 1024 / 1024, total / 1024 / 1024, AnisofixClient.panicMode ? "(PANIC)" : ""));
            }
        }

        if (lines.isEmpty()) return;

        int width = client.getWindow().getScaledWidth();
        int y = 2;
        int color = AnisofixClient.panicMode ? 0xFFFF5555 : 0xFFFFFFFF;

        for (String line : lines) {
            int textWidth = client.textRenderer.getWidth(line);
            context.drawTextWithShadow(client.textRenderer, line, width - textWidth - 2, y, color);
            y += 10;
        }
    }
}