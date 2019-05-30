package net.finalstring.utility;

import java.util.*;
import java.util.function.BiFunction;

public class FrequencyEnumMap<K extends Enum<K>>{
    private final Map<K, Integer> wrappedMap;

    public FrequencyEnumMap(Class<K> clazz) {
        wrappedMap = new EnumMap<>(clazz);
    }

    public int get(K key) {
        return wrappedMap.getOrDefault(key, 0);
    }

    public boolean containsKey(K key) {
        return wrappedMap.containsKey(key);
    }

    public int increment(K key) {
        return add(key, 1);
    }

    public int add(K key, int amount) {
        return wrappedMap.merge(key, amount, Integer::sum);
    }

    public void addAll(FrequencyEnumMap<K> otherMap) {
        merge(otherMap, this::add);
    }

    public int decrement(K key) {
        return subtract(key, 1);
    }

    public int subtract(K key, int amount) {
        Integer result = wrappedMap.computeIfPresent(key, (k, value) -> value > amount ? value - amount : null);
        return result != null ? result : 0;
    }

    public void subtractAll(FrequencyEnumMap<K> otherMap) {
        merge(otherMap, this::subtract);
    }

    private void merge(FrequencyEnumMap<K> otherMap, BiFunction<K, Integer, Integer> mergeMethod) {
        if (otherMap == null) {
            return;
        }

        for (Map.Entry<K, Integer> pair : otherMap.wrappedMap.entrySet()) {
            mergeMethod.apply(pair.getKey(), pair.getValue());
        }
    }

    public void clear() {
        wrappedMap.clear();
    }

    public void incrementAll(Collection<K> keys) {
        keys.forEach(this::increment);
    }

    public void decrementAll(Collection<K> keys) {
        keys.forEach(this::decrement);
    }
}
