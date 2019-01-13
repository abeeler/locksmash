package net.finalstring.card.logos;

import net.finalstring.card.AbstractCardTest;
import net.finalstring.card.untamed.DustPixie;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class KnowledgeIsPowerTest extends AbstractCardTest<KnowledgeIsPower> {
    @Before public void handSetup() {
        player.addToHand(new DustPixie());
        player.archive(new DustPixie());
    }

    @Test public void testArchivingCard() {
        play(new Object[][] { { 0 }, { 0 } });

        assertThat(player.getHandSize(), is(0));
        assertThat(player.getArchive().size(), is(2));
    }

    @Test public void testGainingAemberFromArchive() {
        play(new Object[][] { { 1 } });

        assertThat(player.getAemberPool(), is(STARTING_AEMBER + 1));

        player.archiveFromHand(0);

        play(new Object[][] { { 1 } });

        assertThat(player.getAemberPool(), is(STARTING_AEMBER + 3));
    }

    @Override
    protected KnowledgeIsPower createInstance() {
        return new KnowledgeIsPower();
    }
}
