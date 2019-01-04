package net.finalstring.card.logos;

import net.finalstring.Player;
import net.finalstring.card.Artifact;
import net.finalstring.card.House;
import net.finalstring.card.effect.DrawCard;
import net.finalstring.card.effect.Effect;

import java.util.Collections;
import java.util.List;

public class LibraryOfBabble extends Artifact {
    public LibraryOfBabble() {
        super(129, House.Logos);
    }

    @Override
    public List<Effect> getActionEffects(Player player) {
        return Collections.singletonList(new DrawCard(player));
    }
}
