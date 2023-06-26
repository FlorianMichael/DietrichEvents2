package de.florianmichael.dietrichevents2.type;

import de.florianmichael.dietrichevents2.core.AbstractEvent;

/**
 * This class represents an event that can be cancelled.
 * @param <T> The type of the listener.
 */
public abstract class CancellableEvent<T> extends AbstractEvent<T> {

    private boolean cancelled;

    public void cancel() {
        this.cancelled = true;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}
