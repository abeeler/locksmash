package net.finalstring.effect.board;

import lombok.Getter;
import net.finalstring.card.Card;
import net.finalstring.card.Creature;
import net.finalstring.effect.AbstractEffect;

import java.util.List;

public class Heal extends AbstractEffect {
    private final int amount;
    private final List<Card> targets;

    @Getter
    private int totalHealed = 0;

    public Heal(int amount, List<Card> targets) {
        this.amount = amount;
        this.targets = targets;
    }

    @Override
    protected void affect() {
        for (Card target : targets) {
            totalHealed += ((Creature) target).getInstance().heal(amount);
        }
    }
}
