package net.finalstring.effect;

public class SimpleEffectNode extends EffectNode {
    private final EffectNode next;

    public SimpleEffectNode(Effect effect, EffectNode next) {
        super(effect);

        this.next = next;
    }

    @Override
    public EffectNode getNext() {
        return next;
    }
}
