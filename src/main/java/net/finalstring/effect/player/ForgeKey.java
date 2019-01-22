package net.finalstring.effect.player;

import net.finalstring.Player;
import net.finalstring.effect.AbstractEffect;

public class ForgeKey extends AbstractEffect {
    private final Player player;
    private final int modifier;

    public ForgeKey(Player player) {
        this(player, 0);
    }

    public ForgeKey(Player player, int modifier) {
        this.player = player;
        this.modifier = modifier;
    }

    @Override
    protected void affect() {
        player.forgeKey(modifier);
    }
}
