package net.finalstring.usage;

public enum CardUsage {
    Play, Act, Fight, Reap;

    public boolean isUse() {
        return this == Act || this == Fight || this == Reap;
    }
}
