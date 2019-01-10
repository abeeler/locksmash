package net.finalstring.card;

import net.finalstring.Player;
import net.finalstring.effect.board.ArtifactPlace;
import net.finalstring.effect.Effect;
import net.finalstring.effect.board.RemoveArtifact;

import java.util.List;

public class Artifact extends Spawnable<Spawnable.Instance> {
    public Artifact(int id, House house) {
        super(id, house);
    }

    public void place(Player owner) {
        spawn(new Instance(owner));
        owner.addArtifact(getInstance());
    }

    @Override
    protected void generatePlayEffects(List<Effect> effects, Player player) {
        super.generatePlayEffects(effects, player);
        effects.add(new ArtifactPlace(player, this));
    }

    @Override
    protected void generateDestroyedEffects(List<Effect> effects, Player owner) {
        super.generateDestroyedEffects(effects, owner);
        effects.add(new RemoveArtifact(getInstance()));
    }
}
