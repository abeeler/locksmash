package net.finalstring.card.effect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class Effect {
    /*
     * TODO:
     * Have all effect arguments be non-final
     * Throw error if trigger goes off without a value being set
     * Return all required arguments as enum (source card could set all or none of base effect)
     * Have interface to set specific arguments
     */
    public void trigger() {
        for (Field field : getClass().getDeclaredFields()) {
            try {
                if (field.isAnnotationPresent(Required.class) && field.get(this) == null) {
                    throw new IllegalStateException("Effect is missing a required parameter: " +
                            field.getType().getSimpleName());
                }
            } catch (IllegalAccessException ignored) {
            }
        }

        affect();
    }

    public abstract void affect();

    public List<Class> getRequiredParameters() {
        List<Class> requiredClasses = new ArrayList<>();
        for (Field field : getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Required.class)) {
                requiredClasses.add(field.getType());
            }
        }

        return requiredClasses;
    }

    public boolean needs(Class clazz) {
        for (Field field : getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Required.class) && field.getType() == clazz) {
                try {
                    return field.get(this) == null;
                } catch (IllegalAccessException ignored) {
                }
            }
        }

        return false;
    }

    public <T> void set(Class<T> clazz, T param) {
        if (param == null) {
            throw new IllegalArgumentException("An effect's parameter must not be null");
        }

        for (Field field : getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Required.class) && field.getType() == clazz) {
                try {
                    field.set(this, param);
                } catch (IllegalAccessException ignored) {
                }
            }
        }
    }
}
