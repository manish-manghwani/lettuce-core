/*
 * Copyright 2011-2024 the original author or authors.
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
package io.lettuce.core.cluster;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.cluster.models.partitions.RedisClusterNode;
import io.lettuce.core.protocol.ConnectionIntent;

/**
 * Static selection of nodes.
 *
 * @param <API> API type.
 * @param <CMD> Command command interface type to invoke multi-node operations.
 * @param <K> Key type.
 * @param <V> Value type.
 * @author Mark Paluch
 */
class StaticNodeSelection<API, CMD, K, V> extends AbstractNodeSelection<API, CMD, K, V> {

    private final ClusterDistributionChannelWriter writer;

    private final ConnectionIntent connectionIntent;

    private final List<RedisClusterNode> redisClusterNodes;

    private final Function<StatefulRedisConnection<K, V>, API> apiExtractor;

    public StaticNodeSelection(ClusterDistributionChannelWriter writer, Predicate<RedisClusterNode> selector,
                               ConnectionIntent connectionIntent, Function<StatefulRedisConnection<K, V>, API> apiExtractor) {

        this.writer = writer;
        this.connectionIntent = connectionIntent;
        this.apiExtractor = apiExtractor;

        this.redisClusterNodes = writer.getPartitions().stream().filter(selector).collect(Collectors.toList());
    }

    @Override
    protected CompletableFuture<StatefulRedisConnection<K, V>> getConnection(RedisClusterNode redisClusterNode) {

        RedisURI uri = redisClusterNode.getUri();

        AsyncClusterConnectionProvider async = (AsyncClusterConnectionProvider) writer.getClusterConnectionProvider();
        return async.getConnectionAsync(connectionIntent, uri.getHost(), uri.getPort());
    }

    @Override
    protected CompletableFuture<API> getApi(RedisClusterNode redisClusterNode) {
        return getConnection(redisClusterNode).thenApply(apiExtractor);
    }

    @Override
    protected List<RedisClusterNode> nodes() {
        return redisClusterNodes;
    }

}
