package net.finalstring.card.logos;

import net.finalstring.Player;
import net.finalstring.card.Artifact;
import net.finalstring.card.House;
import net.finalstring.card.effect.player.DrawCard;
import net.finalstring.card.effect.Effect;

import java.util.List;

public class LibraryOfBabble extends Artifact {
    public LibraryOfBabble() {
        super(129, House.Logos);
    }

    @Override
    protected void generateActionEffects(List<Effect> effects, Player player) {
        super.generateActionEffects(effects, player);
        effects.add(new DrawCard(player));
    }
}
