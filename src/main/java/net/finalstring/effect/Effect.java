package net.finalstring.effect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class Effect {
    public boolean trigger() {
        for (Field field : getClass().getDeclaredFields()) {
            boolean accessible = field.isAccessible();
            try {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Required.class) && field.get(this) == null) {
                    return false;
                }
            } catch (IllegalAccessException ignored) {
            } finally {
                field.setAccessible(accessible);
            }
        }

        affect();

        return true;
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

    public void set(Object param) {
        if (param == null) {
            throw new IllegalArgumentException("An effect's parameter must not be null");
        }

        for (Field field : getClass().getDeclaredFields()) {
            boolean accessible = field.isAccessible();
            try {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Required.class) &&
                        field.get(this) == null &&
                        field.getType().isAssignableFrom(param.getClass())) {
                    field.set(this, param);
                    return;
                }
            } catch (IllegalAccessException ignored) {
            } finally {
                field.setAccessible(accessible);
            }
        }
    }
}
