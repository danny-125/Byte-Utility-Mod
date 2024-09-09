package me.danny125.byteutilitymod.settings;

//Taken from old project of mine called "Peroxide"

public class NumberSetting extends Setting{

    public double value, min, max, increment;
    public String units;

    public NumberSetting(String name, double value, double min, double max, double increment, String units) {
        this.name = name;
        this.value = value;
        this.min = min;
        this.max = max;
        this.increment = increment;
        this.units = units;
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
