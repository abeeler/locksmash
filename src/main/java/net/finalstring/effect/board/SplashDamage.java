package net.finalstring.effect.board;

import net.finalstring.card.Creature;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.parameter.EffectCardParameter;
import net.finalstring.effect.TargetSpecification;

public class SplashDamage extends AbstractEffect {
    private final EffectCardParameter<Creature> target = EffectCardParameter.singleTarget("Select the primary target");

    private final int primaryAmount;
    private final int splashAmount;

    public SplashDamage(int primaryAmount, int splashAmount, TargetSpecification targetSpecification) {
        this.primaryAmount = primaryAmount;
        this.splashAmount = splashAmount;

        target.setTargetSpecification(targetSpecification);
        registerParameters(target);
    }

    @Override
    protected void affect() {
        Creature actualTarget = target.getFirst();
        actualTarget.getInstance().dealDamage(primaryAmount);
        for (Creature neighbor : actualTarget.getInstance().getController().getBattleline().getNeighbors(actualTarget)) {
            neighbor.getInstance().dealDamage(splashAmount);
        }
    }
}
