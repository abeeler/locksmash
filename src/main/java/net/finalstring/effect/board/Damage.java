package net.finalstring.effect.board;

import lombok.Getter;
import net.finalstring.card.Card;
import net.finalstring.card.Creature;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.EffectCardParameter;
import net.finalstring.effect.TargetSpecification;

import java.util.List;

public class Damage extends AbstractEffect {
    @Getter
    private final EffectCardParameter<Creature> target;

    private final int amount;
    private final boolean isPoisonDamage;

    public Damage(Creature target, int amount) {
        this(amount);

        this.target.setSingleValue(target);
    }

    public Damage(TargetSpecification targetSpecification, int amount) {
        this(amount);
        target.setTargetSpecification(targetSpecification);
    }

    public Damage(int amount) {
        this.amount = amount;
        this.isPoisonDamage = false;

        target = EffectCardParameter.singleTarget("Select creature to damage");
        registerParameters(target);
    }

    public Damage(int amount, boolean isPoisonDamage, String description, List<Creature> targets) {
        this.amount = amount;
        this.isPoisonDamage = isPoisonDamage;
        this.target = EffectCardParameter.singleTarget(description);
        target.setTargetSpecification(new TargetSpecification(null, player -> targets));
        registerParameters(target);
    }

    @Override
    public void affect() {
        target.getFirst().getInstance().dealDamage(amount, isPoisonDamage);
    }

    public boolean targetDestroyed() {
        Creature actualTaret = target.getFirst();
        return actualTaret != null && actualTaret.getInstance() != null && !actualTaret.getInstance().isAlive();
    }
}
