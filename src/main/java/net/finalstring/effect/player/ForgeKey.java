package net.finalstring.effect.player;

import net.finalstring.AemberPool;
import net.finalstring.Player;
import net.finalstring.effect.AbstractEffect;
import net.finalstring.effect.parameter.EffectParameter;

import java.util.List;
import java.util.stream.Collectors;

public class ForgeKey extends AbstractEffect {
    private final Player player;
    private final int modifier;

    private final EffectParameter<Integer[]> paymentDistribution =
            new EffectParameter<>("Select amount to pay for key from each aember pool");

    public ForgeKey(Player player) {
        this(player, 0);
    }

    public ForgeKey(Player player, int modifier) {
        this.player = player;
        this.modifier = modifier;

        int keyCost = player.getKeyCost(modifier);

        List<AemberPool> availablePools = player.getAemberPools().stream()
                .filter(pool -> pool.getHeldAember() > 0)
                .collect(Collectors.toList());

        if (availablePools.size() <= 1) {
            paymentDistribution.setValue(new Integer[] {
                    Math.min(
                            availablePools.size() == 1 ? availablePools.get(0).getHeldAember() : 0,
                            keyCost)
            });
        } else if (availablePools.stream().mapToInt(AemberPool::getHeldAember).sum() == keyCost) {
            Integer[] forcedPayment = new Integer[player.getAemberPools().size()];
            for (int i = 0; i < forcedPayment.length; i++) {
                forcedPayment[i] = player.getAemberPools().get(i).getHeldAember();
            }
            paymentDistribution.setValue(forcedPayment);
        } else {
            registerParameters(paymentDistribution);
        }
    }

    @Override
    protected void affect() {
        player.forgeKey(modifier, paymentDistribution.getValue());
    }
}
