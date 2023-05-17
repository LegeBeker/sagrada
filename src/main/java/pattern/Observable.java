package main.java.pattern;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Observable {
    private static Map<Class<?>, List<Observer>> observers = new HashMap<>();

    public static void addObserver(final Class<?> objectClass, final Observer observer) {
        if (!observers.containsKey(objectClass)) {
            observers.put(objectClass, new java.util.ArrayList<Observer>());
        }

        observers.get(objectClass).add(observer);
    }

    public void removeObserver(final Class<?> objectClass, final Observer observer) {
        if (!observers.containsKey(objectClass)) {
            return;
        }

        observers.get(objectClass).remove(observer);

        if (observers.get(objectClass).isEmpty()) {
            observers.remove(objectClass);
        }
    }

    public static void notifyObservers(final Class<?> objectClass) {
        for (Observer observer : observers.get(objectClass)) {
            observer.update();
        }
    }
}
