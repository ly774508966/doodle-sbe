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
package doodle.sbe.boot;

import static doodle.sbe.boot.MultiBootstrapConstants.PREFIX;

import doodle.sbe.boot.MultiBootstrapProperties.ChildContextProperties;
import java.util.List;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ConfigurableApplicationContext;

public final class MultiSpringApplication {

  public static void run(Class<?> primarySource, String[] args) {
    ConfigurableApplicationContext context =
        new SpringApplicationBuilder().sources(primarySource).run(args);
    BindResult<MultiBootstrapProperties> bind =
        Binder.get(context.getEnvironment()).bind(PREFIX, MultiBootstrapProperties.class);
    if (bind.isBound()) {
      tryBootstrapMultiContext(context, bind.get(), args);
    }
  }

  private static void tryBootstrapMultiContext(
      ConfigurableApplicationContext parentContext,
      MultiBootstrapProperties properties,
      String[] args) {
    if (properties.getChildContexts().isEmpty()) {
      return;
    }

    List<ChildContextProperties> childContexts = properties.getChildContexts();

    childContexts.forEach((childContext) -> runChildContext(parentContext, childContext, args));
  }

  private static void runChildContext(
      ConfigurableApplicationContext parentContext,
      ChildContextProperties childProperties,
      String[] args) {
    SpringApplicationBuilder builder = new SpringApplicationBuilder();
    switch (childProperties.getRelativeType()) {
      case CHILD:
        builder.parent(parentContext).sources(buildMainClass(childProperties.getMainClass()));
        break;
      case SIBLING:
        break;
    }

    childProperties.getProperties().forEach(builder::properties);

    builder.run(args);
  }

  private static Class<?> buildMainClass(String mainClass) {
    try {
      return Class.forName(mainClass);
    } catch (ClassNotFoundException e) {
      return null;
    }
  }

  private MultiSpringApplication() {}
}
