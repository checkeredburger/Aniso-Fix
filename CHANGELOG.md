# Aniso Fix 0.0.4

## Changes
- **Config UI Rewrite:** Grouped everything into "General" and "HUD" tabs. It’s much less cluttered now. Also added tooltips for the settings that weren't self-explanatory.
- **HUD Placement:** You can finally move the Hardware HUD. 
    - Added anchors for all four corners.
    - Added X/Y offset sliders for pixel-perfect positioning.
- **Panic Mode Toggle:** Added chat-based notifications for when Panic Mode kicks in or shuts off. Use this if you want to know exactly when the mod is downscaling Anisotropic Filtering. (Can be toggled in config).

## Performance & Internal Logic
- **Reduced Polling Rate:** Changed VRAM monitoring to check once per second (20 ticks) rather than every single frame. This cuts down on unnecessary CPU usage.
- **Stabilized Thresholds:** Tweaked the "Panic" logic to prevent it from flickering on and off when memory usage is right on the line.

## Versioning
- **Version Change:** Updated versioning scheme.