/*
 * Copyright 2020-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.lettuce.core.masterreplica;

import java.util.concurrent.TimeUnit;

/**
 * Value object to represent a timeout.
 *
 * @author Mark Paluch
 * @since 4.2
 */
class Timeout {

    private final long expiresMs;

    public Timeout(long timeout, TimeUnit timeUnit) {
        this.expiresMs = System.currentTimeMillis() + timeUnit.toMillis(timeout);
    }

    public boolean isExpired() {
        return expiresMs < System.currentTimeMillis();
    }

    public long remaining() {

        long diff = expiresMs - System.currentTimeMillis();
        if (diff > 0) {
            return diff;
        }
        return 0;
    }

}
