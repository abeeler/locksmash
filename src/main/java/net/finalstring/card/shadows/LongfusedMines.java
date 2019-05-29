package net.finalstring.card.shadows;

import net.finalstring.BoardState;
import net.finalstring.Player;
import net.finalstring.card.Artifact;
import net.finalstring.card.Creature;
import net.finalstring.card.House;
import net.finalstring.effect.TargetFilter;
import net.finalstring.effect.TargetSpecification;
import net.finalstring.effect.board.DamageMultiple;
import net.finalstring.effect.board.Destroy;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.usage.CardUsage;

import java.util.EnumSet;
import java.util.List;

public class LongfusedMines extends Artifact {
    public LongfusedMines() {
        super(287, House.Shadows);
    }

    @Override
    public int getAember() {
        return 1;
    }

    @Override
    public EnumSet<CardUsage> getOmniUsages() {
        return OMNI_ACTION_USAGE;
    }

    @Override
    protected void buildActionEffects(EffectNode.Builder builder, Player controller) {
        super.buildActionEffects(builder, controller);

        List<Creature> targets =
                new TargetSpecification(controller, BoardState::enemyCreatures, new TargetFilter().notOnFlank()).getValidTargets(Creature.class);

        builder
                .effect(new Destroy(this))
                .effect(new DamageMultiple(3, targets));
    }
}
