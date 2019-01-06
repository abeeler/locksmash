package net.finalstring.card;

import lombok.experimental.UtilityClass;
import net.finalstring.card.dis.Charette;
import net.finalstring.card.dis.DustImp;
import net.finalstring.card.dis.EmberImp;
import net.finalstring.card.dis.PitDemon;
import net.finalstring.card.logos.LibraryOfBabble;
import net.finalstring.card.sanctum.ChampionAnaphiel;
import net.finalstring.card.sanctum.TheVaultKeeper;
import net.finalstring.card.shadows.Dodger;
import net.finalstring.card.shadows.NoddyTheThief;
import net.finalstring.card.untamed.DustPixie;
import net.finalstring.card.untamed.Snufflegator;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class Registry {
    private final Map<Integer, Class<? extends Card>> cards;

    static {
        cards = new HashMap<>();

        // Brobnar

        // Dis
        cards.put(81, Charette.class);

        cards.put(83, DustImp.class);
        cards.put(84, EmberImp.class);

        cards.put(92, PitDemon.class);

        // Logos
        cards.put(129, LibraryOfBabble.class);

        // Sanctum
        cards.put(239, ChampionAnaphiel.class);

        cards.put(261, TheVaultKeeper.class);

        // Shadows
        cards.put(306, NoddyTheThief.class);

        cards.put(308, Dodger.class);

        // Untamed
        cards.put(351, DustPixie.class);

        cards.put(358, Snufflegator.class);
    }

    public Card getCard(int id) {
        try {
            return cards.get(id).newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new IllegalArgumentException("Invalid id provided: " + id);
        }
    }
}
