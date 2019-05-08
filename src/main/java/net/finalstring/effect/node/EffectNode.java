package net.finalstring.effect.node;

import net.finalstring.effect.ConditionalEffect;
import net.finalstring.effect.Effect;
import net.finalstring.effect.misc.BlankEffect;
import net.finalstring.effect.misc.RunnableEffect;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public interface EffectNode extends Effect {
    EffectNode getNext();

    void setNext(EffectNode next);

    default void prepare() { }

    class Builder {
        private final Builder parent;

        private final List<String> branchDescriptions = new LinkedList<>();
        private final List<EffectNode> branches = new LinkedList<>();

        private EffectNode first = new SimpleEffectNode(new BlankEffect());
        private EffectNode current = first;

        public Builder() {
            this(null);
        }

        private Builder(Builder parent) {
            this.parent = parent;
        }

        public Builder effect(Effect effect) {
            current.setNext(new SimpleEffectNode(effect));
            current = current.getNext();
            return this;
        }

        public Builder effect(Runnable runnable) {
            return effect(new RunnableEffect(runnable));
        }

        public Builder effects(List<Effect> effects, boolean orderable) {
            if (orderable) {
                current.setNext(new OrderableEffectNode(effects));
                current = current.getNext();
            } else {
                for (Effect effect : effects) {
                    this.effect(effect);
                }
            }
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

        public Builder conditional(Supplier<Boolean> condition) {
            addNode(new SimpleEffectNode(new ConditionalEffect(condition)));
            return this;
        }

        public Builder dependentEffect(Supplier<Effect> dependentEffectSupplier) {
            addNode(new DependentEffectNode(dependentEffectSupplier));
            return this;
        }

        public Builder optional(String choiceText) {
            addNode(new OptionalEffectNode(choiceText));
            return this;
        }

        public EffectNode build() {
            if (!branches.isEmpty()) {
                addNode(new BranchingEffectNode(branchDescriptions, branches));
            }

            return parent == null ? first : first.getNext();
        }

        private void addNode(EffectNode next) {
            current.setNext(next);
            current = next;
        }
    }
}
