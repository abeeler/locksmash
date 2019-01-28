package net.finalstring.effect;

import net.finalstring.effect.node.EffectNode;

public class EffectChain {
    private EffectNode currentNode;

    public EffectChain(EffectNode first) {
        currentNode = first;
    }

    public boolean isFinished() {
        return currentNode == null;
    }

    public boolean isReadyToTrigger() {
        return !currentNode.getNextAssignableParameter().isPresent();
    }

    public void trigger() {
        if (!currentNode.trigger()) {
            currentNode = null;
        } else if ((currentNode = currentNode.getNext()) != null) {
            currentNode.prepare();
        }
    }

    public void setParameter(Object value) {
        currentNode.getNextUnsetParameter().ifPresent(param -> param.setValue(value));
    }
}
