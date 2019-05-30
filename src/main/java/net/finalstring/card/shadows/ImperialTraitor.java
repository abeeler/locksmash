package net.finalstring.card.shadows;

import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.effect.EffectListParameter;
import net.finalstring.effect.TargetFilter;
import net.finalstring.effect.board.Purge;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.Reveal;
import net.finalstring.effect.player.SelectCardsFromHand;

import java.util.stream.Collectors;

public class ImperialTraitor extends Card {
    public ImperialTraitor() {
        super(House.Shadows, 1);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);

        final Player opponent = player.getOpponent();

        SelectCardsFromHand selectedCards = new SelectCardsFromHand(
                player.getOpponent(),
                new EffectListParameter<>(
                        "Select up to one Sanctum creature to purge",
                        0,
                        1,
                        () -> opponent.getMatchingCardsInHand(new TargetFilter().isCreature().ofHouse(House.Sanctum))));
        effectBuilder
                .effect(new Reveal(player.getOpponent()))
                .effect(selectedCards)
                .dependentEffect(() -> new Purge(selectedCards.getSelectedCards().stream()
                        .map(handCard -> (Creature) handCard.getCard())
                        .collect(Collectors.toList())));
    }
}
