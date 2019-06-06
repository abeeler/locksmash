package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Artifact;
import net.finalstring.effect.EffectStack;
import org.junit.Test;

import static net.finalstring.matchers.shared.SharedMatchers.hasAember;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class NexusTest extends AbstractCardTest<Nexus> {
    @Test public void testNothingHappensWithoutOpponentArtifact() {
        play(underTest);
        reap(underTest);

        assertThat(EffectStack.isEffectPending(), is(false));
    }

    @Test public void testFriendlyArtifactIsNotUsed() {
        Artifact friendlyArtifact = new SkeletonKey();
        friendlyArtifact.setOwner(player);
        play(friendlyArtifact);
        friendlyArtifact.getInstance().ready();

        play(underTest);
        reap(underTest);

        assertThat(friendlyArtifact.getInstance().isReady(), is(true));
    }

    @Test public void testOpponentArtifactGetsUsed() {
        Artifact artifact = giveOpponentArtifact(new SkeletonKey());

        play(underTest);
        reap(underTest, friendly);

        assertThat(friendly, hasAember(1));
        assertThat(opponent, hasAember(STARTING_AEMBER - 1));
        assertThat(artifact.getInstance().isReady(), is(false));
    }

    @Test public void testNonActionArtifactsCanBeUsed() {
        Artifact artifact = giveOpponentArtifact(new SkeletonKey());
        Artifact uselessArtifact = giveOpponentArtifact(new EvasionSigil());

        play(underTest);
        reap(underTest, uselessArtifact);

        assertThat(opponent, hasAember(STARTING_AEMBER + 1));
        assertThat(artifact.getInstance().isReady(), is(true));
        assertThat(uselessArtifact.getInstance().isReady(), is(false));
    }

    private Artifact giveOpponentArtifact(Artifact artifact) {
        artifact.setOwner(opponent);
        artifact.play(opponent);
        artifact.getInstance().ready();
        return artifact;
    }
}
