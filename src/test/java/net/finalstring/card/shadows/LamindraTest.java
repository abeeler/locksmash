package net.finalstring.card.shadows;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.Creature;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LamindraTest extends AbstractCardTest<Lamindra> {
    @Test public void testNeighborsGetElusive() {
        assertThat(friendly.hasElusive(), is(false));

        play(underTest, false);
        assertThat(friendly.hasElusive(), is(true));

        Creature neighbor = placeCreature();
        assertThat(neighbor.hasElusive(), is(true));
    }

    @Test public void testDeployStillGivesElusive() {
        Creature neighbor = placeCreature();
        assertThat(neighbor.hasElusive(), is(false));
        assertThat(friendly.hasElusive(), is(false));

        play(underTest, 1);
        assertThat(neighbor.hasElusive(), is(true));
        assertThat(friendly.hasElusive(), is(true));
    }

    @Test public void testNeighborsLoseElusiveWhenLamindraIsDestroyed() {
        Creature neighbor = placeCreature();
        play(underTest, 1);
        assertThat(neighbor.hasElusive(), is(true));
        assertThat(friendly.hasElusive(), is(true));

        destroy(underTest);
        assertThat(neighbor.hasElusive(), is(false));
        assertThat(friendly.hasElusive(), is(false));
    }

    @Test public void testNeighborsLoseElusiveWhenLamindraIsBounced() {
        Creature neighbor = placeCreature();
        play(underTest, 1);
        assertThat(neighbor.hasElusive(), is(true));
        assertThat(friendly.hasElusive(), is(true));

        underTest.bounce();
        assertThat(neighbor.hasElusive(), is(false));
        assertThat(friendly.hasElusive(), is(false));
    }

    @Test public void testNeighborsLoseElusiveWhenLamindraIsPurged() {
        Creature neighbor = placeCreature();
        play(underTest, 1);
        assertThat(neighbor.hasElusive(), is(true));
        assertThat(friendly.hasElusive(), is(true));

        underTest.purge();
        assertThat(neighbor.hasElusive(), is(false));
        assertThat(friendly.hasElusive(), is(false));
    }

    @Test public void testNeighborsLoseElusiveWhenLamindraChangesControllers() {
        Creature neighbor = placeCreature();
        play(underTest, 1);
        assertThat(neighbor.hasElusive(), is(true));
        assertThat(friendly.hasElusive(), is(true));

        changeControl(underTest);
        assertThat(neighbor.hasElusive(), is(false));
        assertThat(friendly.hasElusive(), is(false));
    }

    @Test public void testNeighborsWithElusiveKeepItAfterLamindraLeavesPlay() {
        Creature elusiveCreature = new MagdaTheRat();
        elusiveCreature.setOwner(player);
        play(elusiveCreature);

        assertThat(elusiveCreature.hasElusive(), is(true));
        play(underTest, 1);
        assertThat(elusiveCreature.hasElusive(), is(true));

        underTest.bounce();
        assertThat(elusiveCreature.hasElusive(), is(true));
    }

    @Test public void testNeighborLosesElusiveWhenNeighborLeavesPlay() {
        play(underTest);
        assertThat(friendly.hasElusive(), is(true));

        friendly.bounce();
        assertThat(friendly.hasElusive(), is(false));
    }
}
