package main.java.pattern;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Observable {
    private static Map<Class<?>, List<Observer>> observers = new HashMap<>();

    public void addObserver(final Observer observer) {
        if (!observers.containsKey(this.getClass())) {
            observers.put(this.getClass(), new java.util.ArrayList<Observer>());
        }

        observers.get(this.getClass()).add(observer);
    }

    public void removeObserver(final Observer observer) {
        if (!observers.containsKey(this.getClass())) {
            return;
        }

        observers.get(this.getClass()).remove(observer);

        if (observers.get(this.getClass()).isEmpty()) {
            observers.remove(this.getClass());
        }
    }

    public void notifyObservers() {
        for (Observer observer : observers.get(this.getClass())) {
            observer.update();
        }
    }
}
