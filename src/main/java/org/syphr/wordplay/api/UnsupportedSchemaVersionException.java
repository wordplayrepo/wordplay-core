package org.syphr.wordplay.api;


public class UnsupportedSchemaVersionException extends Exception
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    private final SchemaVersion version;

    public UnsupportedSchemaVersionException(SchemaVersion version)
    {
        this.version = version;
    }

    public UnsupportedSchemaVersionException(SchemaVersion version, Throwable cause)
    {
        super(cause);
        this.version = version;
    }

    public SchemaVersion getVersion()
    {
        return version;
    }
}
