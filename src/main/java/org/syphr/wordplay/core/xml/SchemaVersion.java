/*
 * Copyright © 2012-2022 Gregory P. Moyer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.syphr.wordplay.core.xml;

import java.text.MessageFormat;

public enum SchemaVersion
{
    _1;

    private static final String NS_TEMPLATE_COMMON = "http://syphr.org/wordplay/xsd/v{0}/common";
    private static final String NS_TEMPLATE_CONFIG = "http://syphr.org/wordplay/xsd/v{0}/configuration";
    private static final String NS_TEMPLATE_STATE = "http://syphr.org/wordplay/xsd/v{0}/state";

    private final String commonNamespace;
    private final String configurationNamespace;
    private final String stateNamespace;

    private SchemaVersion()
    {
        String versionNumber = name().substring(1);

        commonNamespace = MessageFormat.format(NS_TEMPLATE_COMMON, versionNumber);
        configurationNamespace = MessageFormat.format(NS_TEMPLATE_CONFIG, versionNumber);
        stateNamespace = MessageFormat.format(NS_TEMPLATE_STATE, versionNumber);
    }

    public String getCommonNamespace()
    {
        return commonNamespace;
    }

    public String getConfigurationNamespace()
    {
        return configurationNamespace;
    }

    public String getStateNamespace()
    {
        return stateNamespace;
    }
}
