/*
 * This file is part of DietrichEvents2 - https://github.com/FlorianMichael/DietrichEvents2
 * Copyright (C) 2023-2024 FlorianMichael/EnZaXD <florian.michael07@gmail.com> and contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.florianmichael.dietrichevents2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GlobalTest {

    private static final List<Object> VALUES = new ArrayList<>();

    private static final TestListener ADDING_EVENT = event -> {
        System.out.println("TestEvent: " + event.something);
        VALUES.add(event.something);
    };

    @BeforeAll
    static void setUp() {
        DietrichEvents2.global().subscribe(TestListener.TestEvent.ID, ADDING_EVENT);
    }

    @Test
    void hasFunctions() {
        final DietrichEvents2 d = DietrichEvents2.global();

        Assertions.assertTrue(d.hasSubscriber(TestListener.TestEvent.ID));
        Assertions.assertNotEquals(0, d.getSubscribers(TestListener.TestEvent.ID).length);
        Assertions.assertTrue(d.isSubscriber(TestListener.TestEvent.ID, ADDING_EVENT));
    }

    @Test
    void fire() {
        DietrichEvents2.global().callUnsafe(TestListener.TestEvent.ID, new TestListener.TestEvent("Hello World"));
        DietrichEvents2.global().callUnsafe(TestListener.TestEvent.ID, new TestListener.TestEvent(10));

        Assertions.assertTrue(VALUES.contains("Hello World"));
        Assertions.assertTrue(VALUES.contains(10));
        Assertions.assertFalse(VALUES.contains(20));
    }

    @Test
    void unsubscribe() {
        final DietrichEvents2 d = DietrichEvents2.global();

        d.unsubscribe(TestListener.TestEvent.ID, ADDING_EVENT);
        Assertions.assertFalse(d.isSubscriber(TestListener.TestEvent.ID, ADDING_EVENT));
    }

}
