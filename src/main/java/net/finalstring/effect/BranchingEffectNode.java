package net.finalstring.effect;

import net.finalstring.effect.misc.BlankEffect;

import java.util.List;
import java.util.Optional;

public class BranchingEffectNode extends EffectNode {
    private final EffectNode[] branches;
    private final EffectParameter<Integer> selectedBranch;

    public BranchingEffectNode(List<String> branchDescriptoins, List<EffectNode> branches) {
        super(new BlankEffect());

        selectedBranch = new ChoiceParameter(branchDescriptoins);
        this.branches = branches.toArray(new EffectNode[0]);
    }

    @Override
    public Optional<EffectParameter> getNextUnsetParameter() {
        return Optional.ofNullable(selectedBranch.isSet() ? null : selectedBranch);
    }

    @Override
    public EffectNode getNext() {
        return branches[selectedBranch.getValue()];
    }
}
