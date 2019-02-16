package net.finalstring.effect.node;

import lombok.Setter;
import net.finalstring.effect.Effect;
import net.finalstring.effect.EffectParameter;

import java.util.List;
import java.util.Optional;

public class OrderableEffectNode implements EffectNode {
    private final EffectParameter<int[]> order = new EffectParameter<>("Decide the order these effects should resolve");

    private final List<Effect> effects;

    private int effectIndex = 0;

    @Setter
    private EffectNode next;

    public OrderableEffectNode(List<Effect> effects) {
        this.effects = effects;
    }

    @Override
    public Optional<EffectParameter> getNextUnsetParameter() {
        return order.isSet() ? Optional.empty() : Optional.of(order);
    }
    
    @Override
    public boolean trigger() {
        if (!order.isSet()) {
            int[] defaultOrder = new int[effects.size()];
            for (int i = 0; i < defaultOrder.length; i++) {
                defaultOrder[i] = i;
            }
            order.setValue(defaultOrder);
            return true;
        }

        return effects.get(order.getValue()[effectIndex++]).trigger();
    }

    @Override
    public EffectNode getNext() {
        return effectIndex >= effects.size() ? next : this;
    }
}
