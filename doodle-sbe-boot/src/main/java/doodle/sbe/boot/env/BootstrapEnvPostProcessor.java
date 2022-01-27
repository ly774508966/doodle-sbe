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
package doodle.sbe.boot.env;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;

/** bootstrap.yml 配置读取预处理器 */
public class BootstrapEnvPostProcessor implements EnvironmentPostProcessor, Ordered {

  private static final AtomicInteger count = new AtomicInteger(0);

  @Override
  public int getOrder() {
    return Ordered.LOWEST_PRECEDENCE;
  }

  @Override
  public void postProcessEnvironment(
      ConfigurableEnvironment environment, SpringApplication application) {
    PropertySource<?> propertySource =
        environment.getPropertySources().get("doodle.boot.config.name");
    // TODO: 1/27/22 支持多个context读取不同的bootstrap启动配置文件
    String property = environment.getProperty("doodle.boot.config.name");
    System.out.println(property);
    System.out.println(count.incrementAndGet());
  }
}
