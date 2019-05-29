package net.finalstring.effect.board;

import net.finalstring.Player;
import net.finalstring.card.Artifact;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.EffectCardParameter;
import net.finalstring.effect.TargetSpecification;

public class UseArtifact extends AbstractEffect {
    private final Player user;
    private final EffectCardParameter<Artifact> target = EffectCardParameter.singleTarget("Select an artifact to use");

    public UseArtifact(Player user, TargetSpecification targetSpecification) {
        this.user = user;

        target.setTargetSpecification(targetSpecification);
        registerParameters(target);
    }

    @Override
    protected void affect() {
        target.getFirst().action(user);
    }
}
