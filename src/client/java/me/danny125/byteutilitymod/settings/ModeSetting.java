package me.danny125.byteutilitymod.settings;

import java.util.Arrays;
import java.util.List;

// Taken from old project of mine called "Peroxide"

public class ModeSetting extends Setting{

    public int index;

    public List<String>modes;

    public String mode;

    public ModeSetting(String name, String mode, String... modes) {
        this.name = name;
        this.modes = Arrays.asList(modes);
        this.mode = mode;
        index = this.modes.indexOf(mode);
    }

    public void cycle() {
        if(index < modes.size() - 1) {
            index++;
        }else {
            index = 0;
        }
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean is(String mode) {
        return index == modes.indexOf(mode);
    }

    public String getMode() {
        return this.mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public List<String> getModes() {
        return modes;
    }

    public void setModes(List<String> modes) {
        this.modes = modes;
    }
}
