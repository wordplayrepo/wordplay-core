package org.syphr.wordplay.core;

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
