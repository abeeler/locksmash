package net.finalstring.effect.node;

import net.finalstring.effect.parameter.ChoiceParameter;
import net.finalstring.effect.parameter.EffectParameter;

import java.util.List;
import java.util.Optional;

public class BranchingEffectNode implements EffectNode {
    private final EffectNode[] branches;
    private final EffectParameter<Integer> selectedBranch;

    public BranchingEffectNode(List<String> branchDescriptions, List<EffectNode> branches) {
        selectedBranch = new ChoiceParameter(branchDescriptions);
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

    @Override
    public boolean trigger() {
        return selectedBranch.isSet() && selectedBranch.getValue() >= 0 && selectedBranch.getValue() < branches.length;
    }

    @Override
    public void setNext(EffectNode next) {

    }
}
