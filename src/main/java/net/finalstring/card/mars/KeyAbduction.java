package net.finalstring.card.mars;

import net.finalstring.Player;
import net.finalstring.card.Card;
import net.finalstring.card.House;
import net.finalstring.card.Spawnable;
import net.finalstring.effect.board.Bounce;
import net.finalstring.effect.node.EffectNode;
import net.finalstring.effect.player.ForgeKey;

import java.util.List;
import java.util.stream.Collectors;

public class KeyAbduction extends Card {
    static final int BASE_KEY_COST_MODIFIER = 9;

    public KeyAbduction() {
        super(166, House.Mars);
    }

    @Override
    public int getAember() {
        return 1;
    }

    @Override
    protected void buildPlayEffects(EffectNode.Builder effectBuilder, Player player) {
        super.buildPlayEffects(effectBuilder, player);

        List<Spawnable> toBounce = player
                .getBattleline()
                .getCreatures()
                .stream()
                .filter(creature -> creature.getHouse() == House.Mars)
                .collect(Collectors.toList());

        toBounce.addAll(player
                .getOpponent()
                .getBattleline()
                .getCreatures()
                .stream()
                .filter(creature -> creature.getHouse() == House.Mars)
                .collect(Collectors.toList())
        );

        effectBuilder
                .effect(new Bounce(toBounce))
                .optional("Forge a key for +" + BASE_KEY_COST_MODIFIER + " subtracting cards in hand?")
                .dependentEffect(() -> new ForgeKey(player, BASE_KEY_COST_MODIFIER - player.getHandSize()));
    }
}
