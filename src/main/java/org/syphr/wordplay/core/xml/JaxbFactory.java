package org.syphr.wordplay.core.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JaxbFactory<T>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(JaxbFactory.class);

    private final Class<T> type;

    private final boolean root;

    private final QName tag;

    public static <T> JaxbFactory<T> create(Class<T> type)
    {
        return new JaxbFactory<T>(type);
    }

    public static <T> JaxbFactory<T> create(Class<T> type, QName tag)
    {
        return new JaxbFactory<T>(type, tag);
    }

    public JaxbFactory(Class<T> type)
    {
        this(type, null);
    }

    public JaxbFactory(Class<T> type, QName tag)
    {
        this.type = type;
        this.root = tag == null;
        this.tag = tag;
    }

    public T read(File file) throws IOException
    {
        LOGGER.trace("Reading JAXB object from a file at \"{}\"", file.getAbsolutePath());

        InputStream in = new FileInputStream(file);
        try
        {
            return read(in);
        }
        finally
        {
            in.close();
        }
    }

    public T read(String xml) throws IOException
    {
        LOGGER.trace("Reading JAXB object from a XML string");
        return read(new ByteArrayInputStream(xml.getBytes()));
    }

    public T read(InputStream in) throws IOException
    {
        LOGGER.trace("Reading JAXB object from a stream");

        try
        {
            JAXBContext jc = getContext();
            JAXBElement<T> object = jc.createUnmarshaller().unmarshal(new StreamSource(in), type);

            return object.getValue();
        }
        catch (JAXBException e)
        {
            throw new IOException(e);
        }
    }

    public void write(T object, File file) throws IOException
    {
        LOGGER.trace("Writing JAXB object to \"{}\"", file.getAbsolutePath());

        File parent = file.getParentFile();
        if (!parent.exists() && !parent.mkdirs())
        {
            throw new IOException("Parent directory does not exist and cannot be created at \""
                    + parent.getAbsolutePath()
                    + "\"");
        }

        OutputStream out = new FileOutputStream(file);
        try
        {
            write(object, out);
        }
        finally
        {
            out.close();
        }
    }

    public String write(T object) throws IOException
    {
        LOGGER.trace("Writing JAXB object to a XML string");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        write(object, out);

        return out.toString();
    }

    public void write(T object, OutputStream out) throws IOException
    {
        LOGGER.trace("Writing JAXB object to a stream");

        try
        {
            JAXBContext jc = getContext();
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            if (root)
            {
                marshaller.marshal(object, out);
            }
            else
            {
                marshaller.marshal(new JAXBElement<T>(tag, type, object), out);
            }
        }
        catch (PropertyException e)
        {
            throw new IOException(e);
        }
        catch (JAXBException e)
        {
            throw new IOException(e);
        }
    }

    protected JAXBContext getContext() throws JAXBException
    {
        return JAXBContext.newInstance(type);
    }
}
