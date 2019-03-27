package net.finalstring.effect.board;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Creature;
import net.finalstring.card.Upgrade;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.EffectCardParameter;
import net.finalstring.effect.TargetSpecification;

public class UpgradeAttach extends AbstractEffect {
    private final Upgrade upgrade;

    private final EffectCardParameter<Creature> creature =
            EffectCardParameter.singleTarget("Select creature to attach upgrade to");

    public UpgradeAttach(Player player, Upgrade upgrade) {
        this.upgrade = upgrade;
        creature.setTargetSpecification(new TargetSpecification(player, BoardState::allCreatures));
        registerParameters(creature);
    }

    @Override
    public void affect() {
        creature.getFirst().attachUpgrade(upgrade);
    }
}
