package io.github.nevalackin.banaza.event.impl;

import io.github.nevalackin.banaza.event.CancellableEvent;
import io.github.nevalackin.banaza.event.Event;

public class KeyPressEvent extends CancellableEvent {

    private final int keyPress;

    public KeyPressEvent(int keyPress) {
        this.keyPress = keyPress;
    }

    public int getKeyPress() {
        return keyPress;
    }
}
