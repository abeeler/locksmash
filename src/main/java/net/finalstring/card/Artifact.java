package net.finalstring.card;

import net.finalstring.Player;
import net.finalstring.card.effect.ArtifactPlace;
import net.finalstring.card.effect.Effect;
import java.util.List;

public class Artifact extends Spawnable<Artifact.ArtifactInstance> {
    public Artifact(int id, House house) {
        super(id, house);
    }

    @Override
    public List<Effect> getPlayEffects(Player player) {
        List<Effect> playEffects = super.getPlayEffects(player);
        playEffects.add(new ArtifactPlace(player, this));
        return playEffects;
    }

    public void place(Player owner) {
        spawn(new ArtifactInstance(owner));
        owner.addArtifact(getInstance());
    }

    public class ArtifactInstance extends Spawnable.Instance {
        ArtifactInstance(Player owner) {
            super(owner);
        }

        @Override
        public void destroy(Card parentCard) {
            getOwner().removeArtifact(this);
            super.destroy(parentCard);
        }
    }
}
