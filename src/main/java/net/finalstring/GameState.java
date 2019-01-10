package net.finalstring;

import lombok.experimental.UtilityClass;
import net.finalstring.card.Creature;
import net.finalstring.effect.Stateful;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class GameState {
    private final List<Stateful> activeConstantEffects = new ArrayList<>();

    public void registerConstantEffect(Stateful constantEffect) {
        activeConstantEffects.add(constantEffect);
    }

    public void deregisterConstantEffect(Stateful constantEffect) {
        activeConstantEffects.remove(constantEffect);
    }

    public void creaturePlaced(Creature placed) {
        activeConstantEffects.forEach(stateful -> stateful.onCreatureEnter(placed));
    }

    public void reset() {
        activeConstantEffects.clear();
    }
}
