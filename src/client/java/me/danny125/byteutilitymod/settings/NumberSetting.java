package me.danny125.byteutilitymod.settings;

//Taken from old project of mine called "Peroxide"

public class NumberSetting extends Setting{

    public double value, min, max, increment;

    public NumberSetting(String name, double value, double min, double max, double increment) {
        this.name = name;
        this.value = value;
        this.min = min;
        this.max = max;
        this.increment = increment;
    }

    public double getValue() {
        return value;
    }

    public void increment(boolean positive) {
        setValue(getValue() + (positive ? 1 : -1) * increment);
    }

    public void setValue(double value) {
        double precision = 1 / increment;
        this.value = value / precision;
    }

    public double getMin() {
        return min;
    }


    public double getMax() {
        return max;
    }

}
