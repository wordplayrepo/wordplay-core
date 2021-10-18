package org.syphr.wordplay.api;

public class PlacementException extends Exception
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    public PlacementException()
    {
        super();
    }

    public PlacementException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public PlacementException(String message)
    {
        super(message);
    }

    public PlacementException(Throwable cause)
    {
        super(cause);
    }
}
