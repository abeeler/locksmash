package net.finalstring;

import lombok.experimental.UtilityClass;
import net.finalstring.card.Creature;
import net.finalstring.effect.EffectChain;
import net.finalstring.effect.Stateful;
import net.finalstring.effect.node.EffectNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

// TODO: Allow effects added to the stack at the same time to have their order specified

@UtilityClass
public class GameState {
    private final List<Stateful> activeConstantEffects = new ArrayList<>();
    private final List<Stateful> activeTurnEffects = new ArrayList<>();
    private final Deque<EffectChain> effectStack = new ArrayDeque<>();

    public void registerConstantEffect(Stateful constantEffect) {
        activeConstantEffects.add(constantEffect);
    }

    public void deregisterConstantEffect(Stateful constantEffect) {
        activeConstantEffects.remove(constantEffect);
    }

    public void registerTurnEffect(Stateful turnEffect) {
        activeTurnEffects.add(turnEffect);
    }

    public void endTurn() {
        activeTurnEffects.clear();
    }

    public void creaturePlaced(Creature placed) {
        activeTurnEffects.forEach(stateful -> stateful.onCreatureEnter(placed));
        activeConstantEffects.forEach(stateful -> stateful.onCreatureEnter(placed));
    }

    public void reset() {
        activeConstantEffects.clear();
        activeTurnEffects.clear();
        effectStack.clear();
    }

    public void pushEffect(EffectNode effectNode) {
        effectStack.push(new EffectChain(effectNode));

        if (effectStack.size() == 1) {
            triggerIfReady();
        }
    }

    public void setEffectParameter(Object value) {
        if (effectStack.isEmpty()) {
            throw new IllegalStateException("No effects on stack to set parameter of");
        }

        effectStack.peek().setParameter(value);

        triggerIfReady();
    }

    public boolean isEffectPending() {
        return !effectStack.isEmpty();
    }

    public void trigger() {
        if (effectStack.isEmpty()) {
            throw new IllegalStateException("No effects on stack to trigger");
        }

        effectStack.peek().trigger();

        while (!effectStack.isEmpty() && effectStack.peek().isFinished()) {
            effectStack.pop();
        }
    }

    private void triggerIfReady() {
        if (effectStack.isEmpty()) {
            throw new IllegalStateException("No effects on stack to trigger");
        }

        while (!effectStack.isEmpty() && effectStack.peek().isReadyToTrigger()) {
            trigger();
        }
    }
}
