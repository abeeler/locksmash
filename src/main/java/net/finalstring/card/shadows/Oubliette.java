package net.finalstring.card.shadows;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.TargetFilter;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.board.Purge;
import net.finalstring.effect.node.EffectNode;

public class Oubliette extends Card {
    private static final TargetFilter POWER_CONDITION = new TargetFilter().withPower(power -> power <= 3);

    public Oubliette() {
        super(278, House.Shadows);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);

        effectBuilder.effect(new Purge(new TargetSpecification(player, BoardState::allCreatures, POWER_CONDITION)));
    }
}
