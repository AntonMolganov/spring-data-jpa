/*
 * Copyright 2013-2022 the original author or authors.
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
package org.springframework.data.jpa.repository.query;

import static jakarta.persistence.TemporalType.*;
import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Method;
import java.util.Date;

import jakarta.persistence.TemporalType;

import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.jpa.repository.query.JpaParameters.JpaParameter;

/**
 * Unit tests for {@link JpaParameters}.
 *
 * @author Oliver Gierke
 * @author Jens Schauder
 */
class JpaParametersUnitTests {

	@Test
	void findsTemporalParameterConfiguration() throws Exception {

		Method method = SampleRepository.class.getMethod("foo", Date.class, String.class);

		JpaParameters parameters = new JpaParameters(method);

		JpaParameter parameter = parameters.getBindableParameter(0);
		assertThat(parameter.isSpecialParameter()).isFalse();
		assertThat(parameter.isTemporalParameter()).isTrue();
		assertThat(parameter.getTemporalType()).isEqualTo(TemporalType.TIMESTAMP);

		parameter = parameters.getBindableParameter(1);
		assertThat(parameter.isTemporalParameter()).isFalse();
	}

	interface SampleRepository {

		void foo(@Temporal(TIMESTAMP) Date date, String firstname);
	}
}
