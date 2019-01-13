package net.finalstring.effect;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import net.finalstring.effect.misc.BlankEffect;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
public abstract class EffectNode implements Effect {
    @Delegate
    protected final Effect effect;

    public abstract EffectNode getNext();

    public static class Builder {
        private final Builder parent;
        private final Deque<Effect> effects = new LinkedList<>();

        private final List<String> branchDescriptions = new LinkedList<>();
        private final List<EffectNode> branches = new LinkedList<>();

        public Builder() {
            this(null);
        }

        private Builder(Builder parent) {
            this.parent = parent;
        }

        public Builder effect(Effect effect) {
            effects.push(effect);
            return this;
        }

        public Builder branch(String branchDescription) {
            branchDescriptions.add(branchDescription);
            return new Builder(this);
        }

        public Builder unbranch() {
            parent.branches.add(build());
            return parent;
        }

        public EffectNode build() {
            EffectNode current = !branches.isEmpty() ?
                    new BranchingEffectNode(branchDescriptions, branches) :
                    null;

            for (Effect effect : effects) {

                current = new SimpleEffectNode(effect, current);
            }

            if (parent == null) {
                current = new SimpleEffectNode(new BlankEffect(), current);
            }

            return current;
        }
    }
}
