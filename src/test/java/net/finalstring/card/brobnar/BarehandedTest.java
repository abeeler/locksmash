package net.finalstring.card.brobnar;

import net.finalstring.Player;
import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Artifact;
import net.finalstring.card.shadows.EvasionSigil;
import net.finalstring.effect.EffectStack;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BarehandedTest extends AbstractCardTest<Barehanded> {
    @Test public void testNothingHappensWithoutArtifactsInPlay() {
        play(underTest);

        assertThat(EffectStack.isEffectPending(), is(false));
        assertThat(player, hasAember(STARTING_AEMBER + 1));
    }

    @Test public void testAllArtifactsAreMovedToTopDeck() {
        Artifact firstFriendly = placeArtifact(player);
        Artifact secondFriendly = placeArtifact(player);
        Artifact firstEnemy = placeArtifact(opponent);

        assertThat(player.getArtifacts().size(), is(2));
        assertThat(opponent.getArtifacts().size(), is(1));

        play(underTest);

        assertThat(player.getArtifacts().size(), is(0));
        assertThat(opponent.getArtifacts().size(), is(0));

        assertThat(player.getDeck().contains(firstFriendly), is(true));
        assertThat(player.getDeck().contains(secondFriendly), is(true));
        assertThat(opponent.getDeck().contains(firstEnemy), is(true));
    }

    private Artifact placeArtifact(Player owner) {
        Artifact artifact = new EvasionSigil();
        artifact.setOwner(owner);
        artifact.play(owner);
        return artifact;
    }
}
