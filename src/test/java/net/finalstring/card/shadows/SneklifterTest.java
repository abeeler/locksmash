package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Artifact;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

// TODO: Convert the controlled artifact to shadows if not part of deck defined houses
public class SneklifterTest extends AbstractCardTest<Sneklifter> {
    @Test public void testSneklifterStealsArtifacts() {
        Artifact artifact = new SpeedSigil();
        artifact.setOwner(opponent);

        artifact.play(opponent);
        assertThat(opponent.getArtifacts().contains(artifact), is(true));
        assertThat(artifact.getInstance().getController(), is(opponent));

        play(underTest);
        assertThat(player.getArtifacts().contains(artifact), is(true));
        assertThat(opponent.getArtifacts().contains(artifact), is(false));
        assertThat(artifact.getInstance().getController(), is(player));
    }
}
