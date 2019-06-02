package net.finalstring.usage;

public enum CardUsage {
    Play, PostPlay, Act,  Reap, PreFight, Fight, PostFight, Unstun, Destroy;

    public boolean isUse() {
        return this == Act || this == Fight || this == Reap;
    }
}
