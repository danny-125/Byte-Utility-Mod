package me.danny125.byteutilitymod.ui;

import me.danny125.byteutilitymod.ByteUtilityMod;
import me.danny125.byteutilitymod.Initialize;
import me.danny125.byteutilitymod.modules.Module;
import me.danny125.byteutilitymod.settings.*;
import me.danny125.byteutilitymod.util.MathUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;

import java.util.concurrent.CopyOnWriteArrayList;

public class ClickGui extends Screen {
    public ClickGui() {
        super(Text.literal("ClickGUI"));
    }

    @Override
    protected void init() {
    }

    public enum CurrentScreenType{
        CATEGORY_LIST,
        MODULE_LIST,
        SETTINGS_LIST,
        SETTING
    }

    public static ClickGui INSTANCE = new ClickGui();

    public int screenWidth = Initialize.INSTANCE.screenWidth;
    public int screenHeight = Initialize.INSTANCE.screenHeight;
    public int rectangleWidth = (int)(screenWidth*0.300);
    public int rectangleHeight = (int)(screenHeight*0.700);
    public int titleBarHeight = (int)(screenHeight*0.03);
    public int guiX = (screenWidth - rectangleWidth) / 2;
    public int guiY = (screenHeight - rectangleHeight) / 2;
    public int fart = 0;
    public boolean isCollapsed = false;
    public boolean shouldDrag = false;
    public boolean canGoBack = false;
    public static boolean changing = false;
    private double dragOffsetX;
    private double dragOffsetY;
    CurrentScreenType currentScreen = CurrentScreenType.CATEGORY_LIST;
    Module.CATEGORY currentCategory = null;
    Module currentModule = null;
    public static Setting currentSetting = null;

    public CurrentScreenType getParentScreen(){
        switch(currentScreen){
            case CATEGORY_LIST:
                return CurrentScreenType.CATEGORY_LIST;
            case MODULE_LIST:
                return CurrentScreenType.CATEGORY_LIST;
            case SETTINGS_LIST:
                return CurrentScreenType.MODULE_LIST;
            case SETTING:
                return CurrentScreenType.SETTINGS_LIST;
        }
        return CurrentScreenType.CATEGORY_LIST;
    }

    public String getGuiTitle(CurrentScreenType currentScreen, Module currentModule){
        switch (currentScreen){
            case CATEGORY_LIST:
                return "CATEGORIES";
            case MODULE_LIST:
                return currentCategory.name().toUpperCase();
            case SETTINGS_LIST:
                if(currentModule != null){
                    return currentModule.getName().toUpperCase() + " SETTINGS";
                }else{
                    return "INVALID";
                }
            case SETTING:
                if(currentModule != null && currentSetting != null){
                    return currentModule.getName().toUpperCase() + " " + currentSetting.name.toUpperCase();
                }else{
                    return "INVALID";
                }
        }
        return "INVALID";
    }

    public int getTextColor(boolean isHovered){
        if(isHovered){
            return 0xffc8c8c8;
        }else{
            return 0xffffffff;
        }
    }

    public int getRectColor(boolean isToggled, boolean isHovered){
        if(!isToggled && !isHovered){
            return 0xffc8171f;
        }
        if(isToggled && !isHovered){
            return 0xff00fc68;
        }
        if(!isToggled && isHovered){
            return 0xffad1018;
        }
        if(isToggled && isHovered){
            return 0xff00e45b;
        }
        return 0xffffffff;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        String title = getGuiTitle(currentScreen, currentModule);
        int titleTextWidth = client.textRenderer.getWidth(title);
        int titleTextHeight = client.textRenderer.fontHeight;
        context.getMatrices().push();
        context.drawText(client.textRenderer, title, guiX + 3, guiY+4, getTextColor(false), true);
        context.getMatrices().pop();
        if(!isCollapsed) {
            canGoBack = currentScreen != CurrentScreenType.CATEGORY_LIST;
            context.fill(guiX, guiY+titleBarHeight, guiX + rectangleWidth, guiY + rectangleHeight, 0x99454545);
            if(canGoBack){
                String backText = "<-";
                int backWidth = client.textRenderer.getWidth(backText);
                int backHeight = client.textRenderer.fontHeight;
                context.getMatrices().push();
                context.drawText(client.textRenderer, backText, guiX + 3, guiY+rectangleHeight-10, getTextColor(MathUtil.isBetween(mouseX,mouseY,guiX+3,guiY+rectangleHeight-10, guiX+3+backWidth,guiY+backHeight-10+rectangleHeight)),true);
            }

            if(currentScreen.equals(CurrentScreenType.CATEGORY_LIST)) {
                int CATEGORY_COUNT = 0;
                for(Module.CATEGORY category : Module.CATEGORY.values()){
                        String text = category.name();

                        int textWidth = client.textRenderer.getWidth(text);
                        int textHeight = client.textRenderer.fontHeight;

                        context.getMatrices().push();
                        context.drawText(client.textRenderer, text, guiX + 3, guiY+titleBarHeight+(CATEGORY_COUNT*textHeight+1)+3, getTextColor(MathUtil.isBetween(mouseX,mouseY,guiX+3,guiY+titleBarHeight+(CATEGORY_COUNT*textHeight+1)+3,guiX+3+textWidth,guiY+textHeight+titleBarHeight+(CATEGORY_COUNT*textHeight+1)+3)), true);
                        context.getMatrices().pop();
                        CATEGORY_COUNT++;
                }
            }
            if(currentScreen.equals(CurrentScreenType.SETTINGS_LIST)){
                int SETTINGS_COUNT = 0;
                if(!currentModule.settings.isEmpty()) {
                    for (Setting setting : currentModule.settings) {
                        String text = "";
                        if(setting instanceof KeyBindSetting){
                            text = setting.name + ": " + GLFW.glfwGetKeyName(currentModule.getKey(),GLFW.glfwGetKeyScancode(currentModule.getKey()));
                        }
                        if(setting instanceof NumberSetting){
                            NumberSetting numberSetting = (NumberSetting) setting;
                            text = setting.name + ": " + numberSetting.getValue() + " " + numberSetting.units;
                        }
                        if(setting instanceof BooleanSetting){
                            BooleanSetting booleanSetting = (BooleanSetting) setting;
                            if(booleanSetting.isToggled()) {
                                text = setting.name + ": true";
                            }else{
                                text = setting.name + ": false";
                            }
                        }
                        if(setting instanceof ModeSetting){
                            ModeSetting modeSetting = (ModeSetting) setting;
                            text = setting.name + ": " + modeSetting.getMode();
                        }

                        int textWidth = client.textRenderer.getWidth(text);
                        int textHeight = client.textRenderer.fontHeight;

                        context.getMatrices().push();
                        context.drawText(client.textRenderer, text, guiX + 3, guiY+titleBarHeight+(SETTINGS_COUNT*textHeight+1)+3, getTextColor(MathUtil.isBetween(mouseX,mouseY,guiX+3,guiY+titleBarHeight+(SETTINGS_COUNT*textHeight+1)+3,guiX+3+textWidth,guiY+textHeight+titleBarHeight+(SETTINGS_COUNT*textHeight+1)+3)), true);
                        context.getMatrices().pop();
                        SETTINGS_COUNT++;
                    }
                }
            }
            if(currentScreen.equals(CurrentScreenType.SETTING)){
                if(currentSetting instanceof BooleanSetting){
                    BooleanSetting boolSetting = (BooleanSetting)currentSetting;
                    String text = boolSetting.name;

                    int textWidth = client.textRenderer.getWidth(text);
                    int textHeight = client.textRenderer.fontHeight;

                    boolean buttonHovered = MathUtil.isBetween(mouseX,mouseY,guiX+2,guiY + titleBarHeight + 1,guiX+12,guiY + titleBarHeight + 12);

                    context.drawText(
                            client.textRenderer,
                            text,
                            guiX + 15,
                            guiY + titleBarHeight + 3,
                            getTextColor(MathUtil.isBetween(mouseX, mouseY, guiX + 15, guiY + titleBarHeight + 3, guiX + 15 + textWidth, guiY + textHeight + titleBarHeight + 3)),
                            true
                    );

                    context.fill(
                            guiX + 2,
                            guiY + titleBarHeight + 1,
                            guiX + 12,
                            guiY + titleBarHeight + 12,
                            getRectColor(boolSetting.isToggled(),buttonHovered)
                    );
                }
                if(currentSetting instanceof KeyBindSetting){
                    KeyBindSetting keyBindSetting = (KeyBindSetting)currentSetting;

                    String text = "Press key you want to set as the key bind...";

                    if(!changing && fart == 1){
                        fart = 0;
                        this.currentScreen = getParentScreen();
                        return;
                    }

                    if(fart == 0) {
                        changing = true;
                    }
                    fart = 1;
                    int textWidth = client.textRenderer.getWidth(text);
                    int textHeight = client.textRenderer.fontHeight;

                    context.getMatrices().push();
                    context.drawText(client.textRenderer, text, guiX + 3, guiY+titleBarHeight+3, getTextColor(false), true);
                    context.getMatrices().pop();
                }
                if(currentSetting instanceof ModeSetting){
                    ModeSetting modeSetting = (ModeSetting)currentSetting;
                    int MODE_COUNT = 0;
                    for(String mode : modeSetting.modes){
                        String text = mode;

                        int textWidth = client.textRenderer.getWidth(text);
                        int textHeight = client.textRenderer.fontHeight;

                        context.getMatrices().push();
                        context.drawText(client.textRenderer, text, guiX + 3, guiY+titleBarHeight+(MODE_COUNT*textHeight+1)+3, getTextColor(MathUtil.isBetween(mouseX,mouseY,guiX+3,guiY+titleBarHeight+(MODE_COUNT*textHeight+1)+3,guiX+3+textWidth,guiY+textHeight+titleBarHeight+(MODE_COUNT*textHeight+1)+3)), true);
                        context.getMatrices().pop();
                        MODE_COUNT++;
                    }
                }
                if(currentSetting instanceof NumberSetting){
                    NumberSetting numberSetting = (NumberSetting)currentSetting;
                }
            }
            if(currentScreen.equals(CurrentScreenType.MODULE_LIST)){
                int MODULE_COUNT = 0;
                for (Module module : Initialize.INSTANCE.modules) {
                    if (module.getCategory() == currentCategory) {
                        String text = module.getName();

                        int textWidth = client.textRenderer.getWidth(text);
                        int textHeight = client.textRenderer.fontHeight;

                        context.getMatrices().push();
                        context.drawText(
                                client.textRenderer,
                                text,
                                guiX + 15,
                                guiY + titleBarHeight + (MODULE_COUNT * (textHeight + 10)) + 3,
                                getTextColor(MathUtil.isBetween(mouseX, mouseY, guiX + 15, guiY + titleBarHeight + (MODULE_COUNT * (textHeight + 10)) + 3, guiX + 15 + textWidth, guiY + textHeight + titleBarHeight + (MODULE_COUNT * (textHeight + 10)) + 3)),
                                true
                        );
                        context.getMatrices().pop();

                        boolean buttonHovered = MathUtil.isBetween(mouseX,mouseY,guiX+2,guiY + titleBarHeight + (MODULE_COUNT * (textHeight + 10)) + 1,guiX+12,guiY + titleBarHeight + (MODULE_COUNT * (textHeight + 10)) + 12);

                        context.fill(
                                guiX + 2,
                                guiY + titleBarHeight + (MODULE_COUNT * (textHeight + 10)) + 1,
                                guiX + 12,
                                guiY + titleBarHeight + (MODULE_COUNT * (textHeight + 10)) + 12,
                                getRectColor(module.isToggled(),buttonHovered)
                        );

                        MODULE_COUNT++;
                    }
                }
            }
        }
        context.fill(guiX, guiY, guiX + rectangleWidth, guiY + titleBarHeight, 0x80c8171f);
        String collapseButtonText = "-";
        int collapseButtonWidth = client.textRenderer.getWidth(collapseButtonText);
        int collapseButtonHeight = client.textRenderer.fontHeight;
        context.getMatrices().push();
        context.drawText(client.textRenderer, collapseButtonText,guiX+rectangleWidth-3-collapseButtonWidth,guiY+collapseButtonHeight,getTextColor(MathUtil.isBetween(mouseX,mouseY,guiX+rectangleWidth-3-collapseButtonWidth,guiY+collapseButtonHeight,guiX+rectangleWidth-3,guiY+collapseButtonHeight+collapseButtonHeight)),true);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(MathUtil.isBetween(mouseX,mouseY,guiX,guiY,guiX+rectangleWidth,guiY+titleBarHeight)) {
            shouldDrag = true;
            dragOffsetX = mouseX - guiX;
            dragOffsetY = mouseY - guiY;
        }
        if(!isCollapsed) {
            if (currentScreen.equals(CurrentScreenType.CATEGORY_LIST)) {
                int CATEGORY_COUNT = 0;
                for (Module.CATEGORY category : Module.CATEGORY.values()) {
                    String text = category.name();

                    int textWidth = client.textRenderer.getWidth(text);
                    int textHeight = client.textRenderer.fontHeight;

                    if (MathUtil.isBetween(mouseX, mouseY, guiX + 3, guiY + titleBarHeight + (CATEGORY_COUNT * textHeight + 1) + 3, guiX + 3 + textWidth, guiY + textHeight + titleBarHeight + (CATEGORY_COUNT * textHeight + 1) + 3)) {
                        currentCategory = category;
                        currentScreen = CurrentScreenType.MODULE_LIST;
                        return true;
                    }

                    CATEGORY_COUNT++;
                }
            }
            if(currentScreen.equals(CurrentScreenType.MODULE_LIST)){
                int MODULE_COUNT = 0;
                for (Module module : Initialize.INSTANCE.modules) {
                    if (module.getCategory() == currentCategory) {
                        String text = module.getName();

                        int textWidth = client.textRenderer.getWidth(text);
                        int textHeight = client.textRenderer.fontHeight;

                        if(MathUtil.isBetween(mouseX, mouseY, guiX + 15, guiY + titleBarHeight + (MODULE_COUNT * (textHeight + 10)) + 3, guiX + 15 + textWidth, guiY + textHeight + titleBarHeight + (MODULE_COUNT * (textHeight + 10)) + 3)) {
                            currentModule = module;
                            currentScreen = CurrentScreenType.SETTINGS_LIST;
                            return true;
                        }

                        boolean buttonHovered = MathUtil.isBetween(mouseX,mouseY,guiX+2,guiY + titleBarHeight + (MODULE_COUNT * (textHeight + 10)) + 1,guiX+12,guiY + titleBarHeight + (MODULE_COUNT * (textHeight + 10)) + 12);

                        if(buttonHovered){
                            module.toggle();
                            if(module.isToggled()){
                                module.onEnable();
                            }else{
                                module.onDisable();
                            }
                            return true;
                        }

                        MODULE_COUNT++;
                    }
                }
            }
            if(currentScreen.equals(CurrentScreenType.SETTING)){
                if(currentSetting instanceof BooleanSetting){
                    BooleanSetting boolSetting = (BooleanSetting)currentSetting;
                    String text = boolSetting.name;

                    int textWidth = client.textRenderer.getWidth(text);
                    int textHeight = client.textRenderer.fontHeight;

                    boolean buttonHovered = MathUtil.isBetween(mouseX,mouseY,guiX+2,guiY + titleBarHeight + 1,guiX+12,guiY + titleBarHeight + 12);
                    if(buttonHovered){
                        boolSetting.toggle();
                        return true;
                    }
                }
                if(currentSetting instanceof ModeSetting){
                    ModeSetting modeSetting = (ModeSetting)currentSetting;
                    int MODE_COUNT = 0;
                    for(String mode : modeSetting.modes){
                        String text = mode;

                        int textWidth = client.textRenderer.getWidth(text);
                        int textHeight = client.textRenderer.fontHeight;

                        if(MathUtil.isBetween(mouseX,mouseY,guiX+3,guiY+titleBarHeight+(MODE_COUNT*textHeight+1)+3,guiX+3+textWidth,guiY+textHeight+titleBarHeight+(MODE_COUNT*textHeight+1)+3)){
                            modeSetting.setMode(mode);
                            currentScreen = getParentScreen();
                            return true;
                        }
                        MODE_COUNT++;
                    }
                }
                if(currentSetting instanceof NumberSetting){
                    NumberSetting numberSetting = (NumberSetting)currentSetting;
                }
            }
            if(currentScreen.equals(CurrentScreenType.SETTINGS_LIST)){
                int SETTINGS_COUNT = 0;
                if(!currentModule.settings.isEmpty()) {
                    for (Setting setting : currentModule.settings) {
                        String text = "";

                        if(setting instanceof KeyBindSetting){
                            text = setting.name + ": " + GLFW.glfwGetKeyName(currentModule.getKey(),GLFW.glfwGetKeyScancode(currentModule.getKey()));
                        }
                        if(setting instanceof NumberSetting){
                            NumberSetting numberSetting = (NumberSetting) setting;
                            text = setting.name + ": " + numberSetting.getValue() + " " + numberSetting.units;
                        }
                        if(setting instanceof BooleanSetting){
                            BooleanSetting booleanSetting = (BooleanSetting) setting;
                            if(booleanSetting.isToggled()) {
                                text = setting.name + ": true";
                            }else{
                                text = setting.name + ": false";
                            }
                        }
                        if(setting instanceof ModeSetting){
                            ModeSetting modeSetting = (ModeSetting) setting;
                            text = setting.name + ": " + modeSetting.getMode();
                        }

                        int textWidth = client.textRenderer.getWidth(text);
                        int textHeight = client.textRenderer.fontHeight;

                        if(MathUtil.isBetween(mouseX,mouseY,guiX+3,guiY+titleBarHeight+(SETTINGS_COUNT*textHeight+1)+3,guiX+3+textWidth,guiY+textHeight+titleBarHeight+(SETTINGS_COUNT*textHeight+1)+3)){
                            currentSetting = setting;
                            currentScreen = CurrentScreenType.SETTING;
                            return true;
                        }
                        SETTINGS_COUNT++;
                    }
                }
            }
            if (canGoBack) {
                String backText = "<-";
                int backWidth = client.textRenderer.getWidth(backText);
                int backHeight = client.textRenderer.fontHeight;
                if (MathUtil.isBetween(mouseX, mouseY, guiX + 3, guiY + rectangleHeight - 10, guiX + 3 + backWidth, guiY + backHeight - 10 + rectangleHeight)) {
                    currentScreen = getParentScreen();
                    changing = false;
                    fart = 0;
                    return true;
                }
            }
        }
        String collapseButtonText = "-";
        int collapseButtonWidth = client.textRenderer.getWidth(collapseButtonText);
        int collapseButtonHeight = client.textRenderer.fontHeight;
        if (MathUtil.isBetween(mouseX, mouseY, guiX + rectangleWidth - 3 - collapseButtonWidth, guiY + collapseButtonHeight, guiX + rectangleWidth - 3, guiY + collapseButtonHeight + collapseButtonHeight)) {
            isCollapsed = !isCollapsed;
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        shouldDrag = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if(shouldDrag) {
            guiX = (int)(mouseX - dragOffsetX);
            guiY = (int)(mouseY - dragOffsetY);
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}