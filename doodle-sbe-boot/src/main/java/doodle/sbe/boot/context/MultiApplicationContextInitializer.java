/*
 * Copyright (c) 2022-present Doodle. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package doodle.sbe.boot.context;

import java.util.Objects;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;

public class MultiApplicationContextInitializer
    implements ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {

  public static final int DEFAULT_ORDER = Ordered.LOWEST_PRECEDENCE - 9;

  @Override
  public int getOrder() {
    return DEFAULT_ORDER;
  }

  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    ApplicationContext parent = applicationContext.getParent();
    MultiApplicationContextHolder contextHolder =
        Objects.nonNull(parent)
            ? parent.getBean(MultiApplicationContextHolder.class)
            : new MultiApplicationContextHolder();

    contextHolder.registerContext(applicationContext);

    if (Objects.isNull(parent)) {
      applicationContext
          .getBeanFactory()
          .registerSingleton(MultiApplicationContextHolder.class.getName(), contextHolder);
    }
  }
}
