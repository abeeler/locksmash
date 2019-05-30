package net.finalstring.effect.parameter;

import net.finalstring.Player;

public class CreaturePlacementParameter extends EffectParameter<Integer> {
    private final Player controller;
    private final boolean isDeployCreature;

    public CreaturePlacementParameter(Player controller, boolean isDeployCreature) {
        super("Select where to place the creature");

        this.controller = controller;
        this.isDeployCreature = isDeployCreature;
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof Boolean) {
            value = (Boolean) value ? 0 : controller.getBattleline().getCreatureCount();
        } else if (!isDeployCreature && (Integer) value > 0 && (Integer) value < controller.getBattleline().getCreatureCount()) {
            throw new IllegalArgumentException("A creature without deploy must go on a flank");
        }
        super.setValue(value);
    }
}
