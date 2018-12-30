package net.finalstring.card;

import lombok.Getter;

public abstract class CreatureCard extends Card implements CreatureStatistics {
    @Getter
    private final int power;

    public CreatureCard(int id, House house, int power) {
        super(id, house);

        this.power = power;
    }
}
