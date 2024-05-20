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
import org.junit.jupiter.api.Test;

public class CancellableEventTest {

    @Test
    void fire() {
        final DietrichEvents2 d = DietrichEvents2.global();
        d.subscribe(CancellableTestListener.CancellableTestEvent.ID, (CancellableTestListener) CancellableEvent::cancel);

        CancellableTestListener.CancellableTestEvent event = new CancellableTestListener.CancellableTestEvent();
        d.post(CancellableTestListener.CancellableTestEvent.ID, event);
        Assertions.assertTrue(event.isCancelled());
    }

}
