package com.rebrandloser.anisofix.client.monitor;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

public class HardwareMonitor {
    private static final int GL_GPU_MEMORY_INFO_TOTAL_AVAILABLE_MEMORY_NVX = 0x9048;
    private static final int GL_GPU_MEMORY_INFO_CURRENT_AVAILABLE_VIDMEM_NVX = 0x9049;
    private static final int GL_VBO_FREE_MEMORY_ATI = 0x87FC;

    private static long totalVideoMemory = 0;
    private static long freeVideoMemory = 0;
    private static String gpuName = "Unknown";
    private static String gpuVendor = "Unknown";
    private static String cpuName = "Unknown";
    private static boolean initialized = false;
    private static boolean isNvidia = false;
    private static boolean isAmd = false;

    private static void init() {
        try {
            GLCapabilities caps = GL.getCapabilities();
            isNvidia = caps.GL_NVX_gpu_memory_info;
            isAmd = caps.GL_ATI_meminfo;
            
            gpuName = GL11.glGetString(GL11.GL_RENDERER);
            gpuVendor = GL11.glGetString(GL11.GL_VENDOR);
            
            cpuName = System.getenv("PROCESSOR_IDENTIFIER");
            if (cpuName == null) {
                cpuName = System.getProperty("os.arch");
            }
            
            initialized = true;
        } catch (Exception e) {
            initialized = false;
        }
    }

    public static void update() {
        if (!initialized) init();
        if (!initialized) return;

        try {
            if (isNvidia) {
                int totalKb = GL11.glGetInteger(GL_GPU_MEMORY_INFO_TOTAL_AVAILABLE_MEMORY_NVX);
                int freeKb = GL11.glGetInteger(GL_GPU_MEMORY_INFO_CURRENT_AVAILABLE_VIDMEM_NVX);
                if (totalKb > 0) totalVideoMemory = (long) totalKb * 1024;
                if (freeKb > 0) freeVideoMemory = (long) freeKb * 1024;
            } else if (isAmd) {
                int[] params = new int[4];
                GL11.glGetIntegerv(GL_VBO_FREE_MEMORY_ATI, params);
                if (params[0] > 0) freeVideoMemory = (long) params[0] * 1024;
                if (totalVideoMemory <= 0 && freeVideoMemory > 0) {
                    totalVideoMemory = freeVideoMemory + (512L * 1024 * 1024);
                }
            }
            if (totalVideoMemory <= 0 && freeVideoMemory > 0) {
                totalVideoMemory = freeVideoMemory;
            }
        } catch (Exception ignored) {}
    }

    public static long getTotalVideoMemory() { return totalVideoMemory; }
    public static long getFreeVideoMemory() { return freeVideoMemory; }
    public static String getGpuName() { return gpuName; }
    public static String getGpuVendor() { return gpuVendor; }
    public static String getCpuName() { return cpuName; }
}
