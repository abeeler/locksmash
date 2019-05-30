package net.finalstring.effect.player;

import net.finalstring.AemberPool;
import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.parameter.EffectCardParameter;
import net.finalstring.effect.TargetFilter;
import net.finalstring.effect.TargetSpecification;

public class MoveAember extends AbstractEffect {
    private final EffectCardParameter<Card> aemberSource =
            EffectCardParameter.singleTarget("Select card to move aember from");

    private final AemberPool aemberTarget;
    private final int amount;

    public MoveAember(Player aemberTarget, int amount) {
        this.aemberTarget = aemberTarget;
        this.amount = amount;

        TargetFilter aemberPoolFilter = new TargetFilter()
                .hasInstance()
                .and(card -> card instanceof AemberPool && ((AemberPool) card).getHeldAember() >= amount);
        aemberSource.setTargetSpecification(new TargetSpecification(aemberTarget, BoardState::friendlySpawnables, aemberPoolFilter));
        registerParameters(aemberSource);
    }

    @Override
    protected void affect() {
        AemberPool source = (AemberPool) aemberSource.getFirst();

        if (source == null) {
            return;
        }

        source.removeAember(amount);
        aemberTarget.addAember(amount);
    }
}
