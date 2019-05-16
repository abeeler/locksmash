package net.finalstring.card;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class HandCard {
    private final Card card;

    private boolean revealed = false;

    public void reveal() {
        revealed = true;
    }
}
