/*
 * This file is part of DietrichEvents2 - https://github.com/FlorianMichael/DietrichEvents2
 * Copyright (C) 2023 FlorianMichael/EnZaXD and contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.florianmichael.dietrichevents2;

import java.util.function.Consumer;

/**
 * This class is the main class of DietrichEvents2.
 */
public class DietrichEvents2 {

    /**
     * The global instance of DietrichEvents2.
     */
    private final static DietrichEvents2 GLOBAL = new DietrichEvents2(32, Throwable::printStackTrace);

    public static DietrichEvents2 global() {
        return GLOBAL;
    }

    /**
     * The subscribers of the event.
     */
    private Object[][] subscribers;
    private int[][] priorities;

    /**
     * The errorHandler consumer will be called when an exception is thrown in a subscriber.
     */
    public Consumer<Throwable> errorHandler;

    /**
     * Creates a new instance of DietrichEvents2. The eventCapacity parameter is the default size of the array that stores all subscribers.
     *
     * @param eventCapacity The default size of the array that stores all subscribers.
     */
    public DietrichEvents2(final int eventCapacity) {
        this(eventCapacity, Throwable::printStackTrace);
    }

    /**
     * Creates a new instance of DietrichEvents2. The maxEvents parameter is the default size of the array that stores all subscribers.
     *
     * @param eventCapacity The default size of the array that stores all subscribers.
     * @param errorHandler  The errorHandler consumer will be called when an exception is thrown in a subscriber.
     */
    public DietrichEvents2(final int eventCapacity, final Consumer<Throwable> errorHandler) {
        this.subscribers = new Object[eventCapacity][0];
        this.priorities = new int[eventCapacity][0];

        this.errorHandler = errorHandler;
    }

    /**
     * @param id The id of the event.
     * @return Whether the event has subscribers.
     */
    public boolean hasSubscriber(final int id) {
        return subscribers[id].length > 0;
    }

    /**
     * @param id The id of the event.
     * @return The subscribers of the event, if there are no subscribers null will be returned.
     */
    public Object[] getSubscribers(final int id) {
        if (!hasSubscriber(id)) {
            return null;
        }
        return subscribers[id];
    }

    /**
     * @param id     The id of the event.
     * @param object The object to check.
     * @return Whether the object is a subscriber of the event.
     */
    public boolean isSubscriber(final int id, final Object object) {
        final Object[] subscriberArr = subscribers[id];
        for (Object o : subscriberArr) {
            if (o == object) {
                return true;
            }
        }
        return false;
    }

    /**
     * Internal method that automatically resizes the array with all subscribers,
     * this method should never be called simply because the event system calls it itself.
     *
     * @param eventCapacity The new maximum length of the array.
     */
    public void setEventCapacity(final int eventCapacity) {
        final Object[][] subscribers = this.subscribers;
        final int[][] priorities = this.priorities;

        // Create new arrays
        this.subscribers = new Object[eventCapacity][0];
        this.priorities = new int[eventCapacity][0];

        // Fill old arrays into new arrays
        for (int i = 0; i < subscribers.length; i++) {
            this.subscribers[i] = subscribers[i];
            this.priorities[i] = priorities[i];
        }
    }

    /**
     * Subscribes a listener with all given IDs to the given class, can be called multiple times.
     *
     * @param object The object to subscribe.
     * @param ids    The ids of the events.
     */
    public void subscribe(final Object object, final int... ids) {
        subscribe(object, 0, ids);
    }

    /**
     * Subscribes a listener with all given IDs to the given class, can be called multiple times.
     *
     * @param object   The object to subscribe.
     * @param ids      The ids of the events.
     * @param priority The priority of the subscriber.
     */
    public void subscribe(final Object object, final int priority, final int... ids) {
        for (int id : ids) {
            subscribe(id, object, priority);
        }
    }

    /**
     * Subscribes a listener with the given ID to the given class, can be called multiple times.
     *
     * @param id     The id of the event.
     * @param object The object to subscribe.
     */
    public void subscribe(final int id, final Object object) {
        subscribe(id, object, 0);
    }

    /**
     * Subscribes a listener with the given ID to the given class, can be called multiple times.
     * For priorities see {@link Priorities}.
     * The higher a priority is, the earlier an event is called.
     *
     * @param id       The id of the event.
     * @param object   The object to subscribe.
     * @param priority The priority of the subscriber.
     */
    public void subscribe(final int id, final Object object, final int priority) {
        if (subscribers.length <= id) setEventCapacity(id + 1); // Resize event capacity if needed

        final Object[] subscriberArr = subscribers[id];
        final int[] priorityArr = priorities[id];

        int insertionIndex = subscriberArr.length;
        for (int i = 0; i < subscriberArr.length; i++) {
            if (priorityArr[i] > priority) {
                insertionIndex = i;
                break;
            }
        }

        final Object[] newSubscriberArr = new Object[subscriberArr.length + 1];
        final int[] newPriorityArr = new int[subscriberArr.length + 1];

        // before index
        System.arraycopy(priorityArr, 0, newPriorityArr, 0, insertionIndex);
        System.arraycopy(subscriberArr, 0, newSubscriberArr, 0, insertionIndex);

        // index
        newPriorityArr[insertionIndex] = priority;
        newSubscriberArr[insertionIndex] = object;

        // after index
        System.arraycopy(priorityArr, insertionIndex, newPriorityArr, insertionIndex + 1, priorityArr.length - insertionIndex);
        System.arraycopy(subscriberArr, insertionIndex, newSubscriberArr, insertionIndex + 1, subscriberArr.length - insertionIndex);

        subscribers[id] = newSubscriberArr;
        priorities[id] = newPriorityArr;
    }

    /**
     * Unsubscribes a listener with the given ID from the given class, can be called multiple times.
     *
     * @param id     The id of the event.
     * @param object The object to unsubscribe.
     */
    public void unsubscribe(final int id, final Object object) {
        Object[] subscriberArr = subscribers[id];
        int[] priorityArr = priorities[id];

        int removeIndex = -1;
        for (int i = 0; i < subscriberArr.length; i++) {
            if (subscriberArr[i] == object) {
                removeIndex = i;
                break;
            }
        }

        if (removeIndex == -1) return;

        Object[] newSubscriberArr = new Object[subscriberArr.length - 1];
        int[] newPriorityArr = new int[subscriberArr.length - 1];

        if (removeIndex > 0) {
            System.arraycopy(subscriberArr, 0, newSubscriberArr, 0, removeIndex);
            System.arraycopy(priorityArr, 0, newPriorityArr, 0, removeIndex);
        }
        System.arraycopy(subscriberArr, removeIndex + 1, newSubscriberArr, removeIndex, subscriberArr.length - removeIndex - 1);
        System.arraycopy(priorityArr, removeIndex + 1, newPriorityArr, removeIndex, subscriberArr.length - removeIndex - 1);

        subscribers[id] = newSubscriberArr;
        priorities[id] = newPriorityArr;
    }

    /**
     * Unsubscribes all listeners from the given type.
     *
     * @param id The id of the event.
     */
    public void unsubscribeAll(final int id) {
        subscribers[id] = new Object[0];
        priorities[id] = new int[0];
    }

    /**
     * This method is the recommended method for event calling, it calls the postInternal method but has some sanity checks and calls the errorHandler if an error occurs.
     *
     * @param id    The id of the event.
     * @param event The event to post.
     */
    public void post(final int id, final AbstractEvent event) {
        if (subscribers.length <= id) { // Resize event capacity if needed
            setEventCapacity(id + 1);
            return;
        }

        try {
            postInternal(id, event);
        } catch (final Throwable t) {
            this.errorHandler.accept(t);
        }
    }

    /**
     * This method is the internal method for event calling, it should never be called directly.
     *
     * @param id    The id of the event.
     * @param event The event to post.
     */
    public void postInternal(final int id, final AbstractEvent event) {
        final Object[] subscriber = subscribers[id];

        for (int i = 0; i < subscriber.length; i++) {
            event.call(subscriber[i]);
        }
    }
}
