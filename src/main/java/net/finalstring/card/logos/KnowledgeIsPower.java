package net.finalstring.card.logos;

import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.EffectNode;
import net.finalstring.effect.player.ArchiveFromHand;
import net.finalstring.effect.player.GainAember;

public class KnowledgeIsPower extends Card {
    public KnowledgeIsPower() {
        super(113, House.Logos);
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);
        effectBuilder
                .branch("Archive a card")
                    .effect(new ArchiveFromHand(player))
                    .unbranch()
                .branch("Gain one for each archived card")
                    .effect(new GainAember(player, player.getArchive().size()))
                    .unbranch();
    }
}
