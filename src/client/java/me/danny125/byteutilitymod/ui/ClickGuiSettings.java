package me.danny125.byteutilitymod.ui;

import me.danny125.byteutilitymod.BYTE;
import me.danny125.byteutilitymod.modules.Module;
import me.danny125.byteutilitymod.settings.Setting;

public class ClickGuiSettings {
    public static int screenWidth = BYTE.INSTANCE.screenWidth;
    public static int screenHeight = BYTE.INSTANCE.screenHeight;
    public static int rectangleWidth = (int)(screenWidth*0.300);
    public static int rectangleHeight = (int)(screenHeight*0.700);
    public static int titleBarHeight = (int)(screenHeight*0.03);
    public static int guiX = (screenWidth - rectangleWidth) / 2;
    public static int guiY = (screenHeight - rectangleHeight) / 2;
    public static int fart = 0;
    public static boolean isCollapsed = false;
    public static boolean shouldDrag = false;
    public static boolean canGoBack = false;
    public static boolean changing = false;
    public static double dragOffsetX;
    public static double dragOffsetY;
    public static ClickGui.CurrentScreenType currentScreen = ClickGui.CurrentScreenType.CATEGORY_LIST;
    public static me.danny125.byteutilitymod.modules.Module.CATEGORY currentCategory = null;
    public static Module currentModule = null;
    public static Setting currentSetting = null;

    public static void saveClickGui(){
        screenWidth = ClickGui.screenWidth;
        screenHeight = ClickGui.screenHeight;
        rectangleWidth = ClickGui.rectangleWidth;
        rectangleHeight = ClickGui.rectangleHeight;
        titleBarHeight = ClickGui.titleBarHeight;
        guiX = ClickGui.guiX;
        guiY = ClickGui.guiY;
        fart = ClickGui.fart;
        isCollapsed = ClickGui.isCollapsed;
        shouldDrag = ClickGui.shouldDrag;
        canGoBack = ClickGui.canGoBack;
        changing = ClickGui.changing;
        dragOffsetX = ClickGui.dragOffsetX;
        dragOffsetY = ClickGui.dragOffsetY;
        currentScreen = ClickGui.currentScreen;
        currentCategory = ClickGui.currentCategory;
        currentModule = ClickGui.currentModule;
        currentSetting = ClickGui.currentSetting;
    }
}
