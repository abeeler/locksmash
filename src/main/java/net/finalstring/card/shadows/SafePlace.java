package net.finalstring.card.shadows;

import net.finalstring.card.House;
import net.finalstring.card.shared.AemberPoolArtifact;

public class SafePlace extends AemberPoolArtifact {
    public SafePlace() {
        super(289, House.Shadows);
    }

    @Override
    public int getAember() {
        return 1;
    }
}
