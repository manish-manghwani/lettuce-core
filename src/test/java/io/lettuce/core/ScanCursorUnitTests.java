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
package io.lettuce.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

/**
 * @author Mark Paluch
 */
class ScanCursorUnitTests {

    @Test
    void testFactory() {
        ScanCursor scanCursor = ScanCursor.of("dummy");
        assertThat(scanCursor.getCursor()).isEqualTo("dummy");
        assertThat(scanCursor.isFinished()).isFalse();
    }

    @Test
    void setCursorOnImmutableInstance() {
        assertThatThrownBy(() -> ScanCursor.INITIAL.setCursor("")).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void setFinishedOnImmutableInstance() {
        assertThatThrownBy(() -> ScanCursor.INITIAL.setFinished(false)).isInstanceOf(UnsupportedOperationException.class);
    }
}
