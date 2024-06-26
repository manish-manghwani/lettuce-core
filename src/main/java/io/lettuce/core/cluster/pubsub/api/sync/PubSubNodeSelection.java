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

import io.lettuce.core.cluster.api.NodeSelectionSupport;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;

/**
 * Node selection with access to {@link RedisPubSubCommands}.
 *
 * @author Mark Paluch
 * @since 4.4
 */
public interface PubSubNodeSelection<K, V>
        extends NodeSelectionSupport<RedisPubSubCommands<K, V>, NodeSelectionPubSubCommands<K, V>> {

}
