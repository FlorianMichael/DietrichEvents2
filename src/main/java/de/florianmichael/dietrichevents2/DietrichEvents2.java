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

import de.florianmichael.dietrichevents2.core.AbstractEvent;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * This class is the main class of DietrichEvents2.
 */
public class DietrichEvents2 {

    /**
     * The global instance of DietrichEvents2.
     */
    private final static DietrichEvents2 GLOBAL = new DietrichEvents2(Throwable::printStackTrace);

    public static DietrichEvents2 global() {
        return GLOBAL;
    }

    /**
     * The subscribers of the event.
     */
    private Object[][] subscribers = new Object[32][0];
    private int[][] priorities = new int[32][0];

    /**
     * The errorHandler consumer will be called when an exception is thrown in a subscriber.
     */
    public Consumer<Throwable> errorHandler;

    public DietrichEvents2(final Consumer<Throwable> errorHandler) {
        this.errorHandler = errorHandler;
    }

    public boolean hasSubscriber(final int id) {
        return subscribers[id].length > 0;
    }

    public void subscribe(final int id, final Object object) {
        subscribe(id, object, 0);
    }

    protected void resizeArrays(final int maxLength) {
        final Object[][] subscribers = Arrays.copyOf(this.subscribers, this.subscribers.length);
        final int[][] priorities = Arrays.copyOf(this.priorities, this.priorities.length);

        this.subscribers = new Object[maxLength][0];
        this.priorities = new int[maxLength][0];

        for (int i = 0; i < subscribers.length; i++) {
            this.subscribers[i] = subscribers[i];
            this.priorities[i] = priorities[i];
        }
    }

    public void subscribe(final int id, final Object object, final int priority) {
        if (subscribers.length < id) {
            resizeArrays(id + 1);
        }
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
     * Posts an event to all subscribers.
     * @param id The id of the event.
     * @param event The event to post.
     */
    public void post(final int id, final AbstractEvent event) {
        try {
            postInternal(id, event);
        } catch (final Throwable t) {
            this.errorHandler.accept(t);
        }
    }

    /**
     * Posts an event to all subscribers.
     * @param id The id of the event.
     * @param event The event to post.
     */
    public void postInternal(final int id, final AbstractEvent event) {
        final Object[] subscriber = subscribers[id];

        for (int i = 0; i < subscriber.length; i++) {
            event.call(subscriber[i]);
        }
    }
}
