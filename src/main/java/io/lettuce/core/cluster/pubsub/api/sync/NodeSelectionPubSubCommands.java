/*
 * Copyright 2016-2024 the original author or authors.
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
package io.lettuce.core.cluster.pubsub.api.sync;

import io.lettuce.core.cluster.api.sync.Executions;

/**
 * Synchronous executed commands on a node selection for Pub/Sub.
 *
 * @author Mark Paluch
 * @since 4.4
 */
public interface NodeSelectionPubSubCommands<K, V> {

    /**
     * Listen for messages published to channels matching the given patterns.
     *
     * @param patterns the patterns
     * @return Executions to synchronize {@code psubscribe} completion
     */
    Executions<Void> psubscribe(K... patterns);

    /**
     * Stop listening for messages posted to channels matching the given patterns.
     *
     * @param patterns the patterns
     * @return Executions Future to synchronize {@code punsubscribe} completion
     */
    Executions<Void> punsubscribe(K... patterns);

    /**
     * Listen for messages published to the given channels.
     *
     * @param channels the channels
     * @return Executions Future to synchronize {@code subscribe} completion
     */
    Executions<Void> subscribe(K... channels);

    /**
     * Stop listening for messages posted to the given channels.
     *
     * @param channels the channels
     * @return Executions Future to synchronize {@code unsubscribe} completion.
     */
    Executions<Void> unsubscribe(K... channels);

}
