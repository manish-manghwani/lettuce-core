/*
 * Copyright 2017-2024 the original author or authors.
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
package io.lettuce.core.dynamic.batch;

import java.util.Collections;
import java.util.List;

import io.lettuce.core.RedisCommandExecutionException;
import io.lettuce.core.protocol.RedisCommand;

/**
 * Batch exception to collect multiple errors from batched command execution.
 * <p>
 * Commands that fail during the batch cause a {@link BatchException} while non-failed commands remain executed successfully.
 *
 * @author Mark Paluch
 * @since 5.0
 * @see BatchExecutor
 * @see BatchSize
 * @see CommandBatching
 */
@SuppressWarnings("serial")
public class BatchException extends RedisCommandExecutionException {

    private final List<RedisCommand<?, ?, ?>> failedCommands;

    /**
     * Create a new {@link BatchException}.
     *
     * @param failedCommands {@link List} of failed {@link RedisCommand}s.
     */
    public BatchException(List<RedisCommand<?, ?, ?>> failedCommands) {
        super("Error during batch command execution");
        this.failedCommands = Collections.unmodifiableList(failedCommands);
    }

    /**
     * @return the failed commands.
     */
    public List<RedisCommand<?, ?, ?>> getFailedCommands() {
        return failedCommands;
    }

}
