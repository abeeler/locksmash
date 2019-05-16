package net.finalstring.card.mars;

import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.EffectListParameter;
import net.finalstring.effect.TargetFilter;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.DrawCard;
import net.finalstring.effect.player.Reveal;
import net.finalstring.effect.player.SelectCardsFromHand;

public class BattleFleet extends Card {
    public BattleFleet() {
        super(161, House.Mars);
    }

    @Override
    public int getAember() {
        return 1;
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);

        final SelectCardsFromHand selectiveReveal =
                new SelectCardsFromHand(player, new EffectListParameter<>(
                        "Select cards to reveal",
                        0,
                        Integer.MAX_VALUE,
                        () -> player.getMatchingCardsInHand(new TargetFilter().ofHouse(House.Mars))));
        effectBuilder
                .effect(selectiveReveal)
                .dependentEffect(() -> new Reveal(selectiveReveal.getSelectedCards()))
                .dependentEffect(() -> new DrawCard(player, selectiveReveal.getSelectedCards().size()));
    }
}
