package net.finalstring.effect;

import java.util.Optional;

public interface Effect {
    Optional<EffectParameter> getNextUnsetParameter();

    boolean trigger();
}
