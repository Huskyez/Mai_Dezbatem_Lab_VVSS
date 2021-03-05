package pizzashop.service;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
    private List<Observer> observerList = new ArrayList<>();

    public void add(Observer observer) {
        this.observerList.add(observer);
    }

    public void remove(Observer observer) {
        this.observerList.remove(observer);
    }

    public void notifyAllObservers() {
        for(Observer observer: observerList) {
            observer.update();
        }
    }
}
