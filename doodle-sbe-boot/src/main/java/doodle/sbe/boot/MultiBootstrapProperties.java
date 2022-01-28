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

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(PREFIX)
public class MultiBootstrapProperties {

  private final List<ChildContextProperties> childContexts = new ArrayList<>();

  public List<ChildContextProperties> getChildContexts() {
    return childContexts;
  }

  public static class ChildContextProperties {

    private String rid; // relative application id;

    private RelativeType relativeType = RelativeType.NONE;

    private String mainClass;

    public final List<String> properties = new ArrayList<>();

    public String getRid() {
      return rid;
    }

    public void setRid(String rid) {
      this.rid = rid;
    }

    public RelativeType getRelativeType() {
      return relativeType;
    }

    public void setRelativeType(RelativeType relativeType) {
      this.relativeType = relativeType;
    }

    public String getMainClass() {
      return mainClass;
    }

    public void setMainClass(String mainClass) {
      this.mainClass = mainClass;
    }

    public List<String> getProperties() {
      return properties;
    }
  }

  public enum RelativeType {
    NONE,
    PARENT,
    CHILD,
    SIBLING;
  }
}
