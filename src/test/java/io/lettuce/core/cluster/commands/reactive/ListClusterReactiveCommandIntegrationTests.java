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
package io.lettuce.core.cluster.commands.reactive;

import static org.assertj.core.api.Assertions.*;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisClusterCommands;
import io.lettuce.core.commands.ListCommandIntegrationTests;
import io.lettuce.test.ReactiveSyncInvocationHandler;
import io.lettuce.test.condition.EnabledOnCommand;

/**
 * @author Mark Paluch
 */
class ListClusterReactiveCommandIntegrationTests extends ListCommandIntegrationTests {

    private final RedisClusterCommands<String, String> redis;

    @Inject
    ListClusterReactiveCommandIntegrationTests(StatefulRedisClusterConnection<String, String> connection) {
        super(ReactiveSyncInvocationHandler.sync(connection));
        this.redis = ReactiveSyncInvocationHandler.sync(connection);
    }

    // re-implementation because keys have to be on the same slot
    @Test
    void brpoplpush() {

        redis.rpush("UKPDHs8Zlp", "1", "2");
        redis.rpush("br7EPz9bbj", "3", "4");
        assertThat(redis.brpoplpush(1, "UKPDHs8Zlp", "br7EPz9bbj")).isEqualTo("2");
        assertThat(redis.lrange("UKPDHs8Zlp", 0, -1)).isEqualTo(list("1"));
        assertThat(redis.lrange("br7EPz9bbj", 0, -1)).isEqualTo(list("2", "3", "4"));
    }

    @Test
    void brpoplpushTimeout() {
        assertThat(redis.brpoplpush(1, "UKPDHs8Zlp", "br7EPz9bbj")).isNull();
    }

    @Test
    @EnabledOnCommand("BLMOVE") // Redis 6.2
    void brpoplpushDoubleTimeout() {
        redis.rpush("br7EPz9bbj", "1", "2");
        redis.rpush("UKPDHs8Zlp", "3", "4");
        assertThat(redis.brpoplpush(0.5, "br7EPz9bbj", "UKPDHs8Zlp")).isEqualTo("2");
        assertThat(redis.lrange("br7EPz9bbj", 0, -1)).isEqualTo(list("1"));
        assertThat(redis.lrange("UKPDHs8Zlp", 0, -1)).isEqualTo(list("2", "3", "4"));
    }

    @Test
    void blpop() {
        redis.rpush("br7EPz9bbj", "2", "3");
        assertThat(redis.blpop(1, "UKPDHs8Zlp", "br7EPz9bbj")).isEqualTo(kv("br7EPz9bbj", "2"));
    }

    @Test
    @EnabledOnCommand("BLMOVE") // Redis 6.2
    void blpopDoubleTimeout() {
        redis.rpush("br7EPz9bbj", "2", "3");
        assertThat(redis.blpop(0.5, "UKPDHs8Zlp", "br7EPz9bbj")).isEqualTo(kv("br7EPz9bbj", "2"));
    }

    @Test
    void brpop() {
        redis.rpush("br7EPz9bbj", "2", "3");
        assertThat(redis.brpop(1, "UKPDHs8Zlp", "br7EPz9bbj")).isEqualTo(kv("br7EPz9bbj", "3"));
    }

    @Test
    @EnabledOnCommand("BLMOVE") // Redis 6.2
    void brpopDoubleTimeout() {
        redis.rpush("UKPDHs8Zlp", "2", "3");
        assertThat(redis.brpop(0.5, "br7EPz9bbj", "UKPDHs8Zlp")).isEqualTo(kv("UKPDHs8Zlp", "3"));
    }

    @Test
    void rpoplpush() {
        assertThat(redis.rpoplpush("UKPDHs8Zlp", "br7EPz9bbj")).isNull();
        redis.rpush("UKPDHs8Zlp", "1", "2");
        redis.rpush("br7EPz9bbj", "3", "4");
        assertThat(redis.rpoplpush("UKPDHs8Zlp", "br7EPz9bbj")).isEqualTo("2");
        assertThat(redis.lrange("UKPDHs8Zlp", 0, -1)).isEqualTo(list("1"));
        assertThat(redis.lrange("br7EPz9bbj", 0, -1)).isEqualTo(list("2", "3", "4"));
    }

}
