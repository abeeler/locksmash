package net.finalstring.card.shadows;

import lombok.Getter;
import net.finalstring.GameState;
import net.finalstring.Player;
import net.finalstring.card.Artifact;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.effect.board.Destroy;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.RemoveCardFromHand;
import net.finalstring.usage.CardUsage;
import net.finalstring.usage.UsagePredicate;

import java.util.EnumSet;

public class Masterplan extends Artifact {
    @Getter
    private Card heldCard;

    public Masterplan() {
        super(House.Shadows, 1);
    }

    @Override
    public EnumSet<CardUsage> getOmniUsages() {
        return OMNI_ACTION_USAGE;
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder builder, Player player) {
        super.buildPlayEffects(builder, player);
        final RemoveCardFromHand removeCardFromHand = new RemoveCardFromHand(player);
        builder
                .effect(removeCardFromHand)
                .effect(() -> heldCard = removeCardFromHand.getRemovedCard());
    }

    @Override
    protected void buildActionEffects(EffectNode.Builder builder, Player controller) {
        super.buildActionEffects(builder, controller);
        builder
                .effect(() -> {
                    if (heldCard != null) {
                        UsagePredicate temporaryUsage = (usage, card) -> usage == CardUsage.Play && card == heldCard;
                        try {
                            GameState.getInstance().getCurrentTurn().getUsageManager().addAllowance(temporaryUsage);
                            if (heldCard.canPlay()) {
                                heldCard.play(controller);
                                heldCard = null;
                            }
                        } finally {
                            GameState.getInstance().getCurrentTurn().getUsageManager().removeAllowance(temporaryUsage);
                        }
                    }
                })
                .effect(new Destroy(this));
    }

    @Override
    protected void leavePlay() {
        if (heldCard != null) {
            heldCard.getOwner().discard(heldCard);
            heldCard = null;
        }
        super.leavePlay();
    }
}
