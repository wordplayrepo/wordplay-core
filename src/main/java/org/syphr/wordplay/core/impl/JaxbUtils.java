package org.syphr.wordplay.core.impl;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.syphr.wordplay.core.space.Dimension;
import org.syphr.wordplay.core.space.Location;
import org.syphr.wordplay.xsd.v1.ClassType;
import org.syphr.wordplay.xsd.v1.DimensionType;
import org.syphr.wordplay.xsd.v1.ExtensionType;
import org.syphr.wordplay.xsd.v1.ExtensionsType;
import org.syphr.wordplay.xsd.v1.LocationType;
import org.syphr.wordplay.xsd.v1.MethodType;
import org.syphr.wordplay.xsd.v1.ParameterType;
import org.syphr.wordplay.xsd.v1.PrimitiveType;

public class JaxbUtils
{
    public static Location getLocation(LocationType locationType)
    {
        int x = locationType.getX();
        int y = locationType.getY();
        int z = locationType.getZ() == null ? 0 : locationType.getZ();

        return Location.at(x, y, z);
    }

    public static LocationType toLocation(Location location)
    {
        LocationType locationType = new LocationType();
        locationType.setX(location.getX());
        locationType.setY(location.getY());

        int z = location.getZ();
        if (z != 0)
        {
            locationType.setZ(z);
        }

        return locationType;
    }

    public static Dimension getDimension(DimensionType dimensionType)
    {
        int width = dimensionType.getWidth();
        int height = dimensionType.getHeight();
        int depth = dimensionType.getDepth() == null ? 1 : dimensionType.getDepth();

        return Dimension.of(width, height, depth);
    }

    public static DimensionType toDimension(Dimension dimension)
    {
        DimensionType dimensionType = new DimensionType();
        dimensionType.setWidth(dimension.getWidth());
        dimensionType.setHeight(dimension.getHeight());

        int depth = dimension.getDepth();
        if (depth != 1)
        {
            dimensionType.setDepth(depth);
        }

        return dimensionType;
    }

    public static Map<String, Object> getExtensions(ExtensionsType extensionsType)
    {
        Map<String, Object> extensions = new HashMap<String, Object>();

        if (extensionsType != null)
        {
            for (ExtensionType extensionType : extensionsType.getExtensions())
            {
                extensions.put(extensionType.getId(), extensionType.getAny());
            }
        }

        return extensions;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getInstance(ClassType config)
    {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        String className = config.getType();

        try
        {
            Class<?> clazz = cl.loadClass(className);

            MethodType methodType = config.getMethod();
            if (methodType == null)
            {
                return (T)clazz.newInstance();
            }

            String methodName = methodType.getName();

            Map<Class<?>, Object> parameters = getParameterMap(methodType.getParameters(), cl);

            Method method = clazz.getMethod(methodName,
                                            parameters.keySet().toArray(new Class<?>[parameters.size()]));
            Object instance = null;
            if (!Modifier.isStatic(method.getModifiers()))
            {
                instance = clazz.newInstance();
            }

            return (T)method.invoke(instance, parameters.values().toArray());
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("Invalid class configuration: " + className, e);
        }
    }

    public static Map<Class<?>, Object> getParameterMap(List<ParameterType> parameters,
                                                        ClassLoader cl) throws Exception
    {
        Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();

        for (ParameterType parameter : parameters)
        {
            String valueStr = parameter.getValue();

            PrimitiveType primitive = parameter.getPrimitive();
            if (primitive != null)
            {
                switch (primitive)
                {
                    case BYTE:
                        map.put(Byte.TYPE, Byte.parseByte(valueStr));
                        break;

                    case SHORT:
                        map.put(Short.TYPE, Short.parseShort(valueStr));
                        break;

                    case INT:
                        map.put(Integer.TYPE, Integer.parseInt(valueStr));
                        break;

                    case LONG:
                        map.put(Long.TYPE, Long.parseLong(valueStr));
                        break;

                    case FLOAT:
                        map.put(Float.TYPE, Float.parseFloat(valueStr));
                        break;

                    case DOUBLE:
                        map.put(Double.TYPE, Double.parseDouble(valueStr));
                        break;

                    case BOOLEAN:
                        map.put(Boolean.TYPE, Boolean.parseBoolean(valueStr));
                        break;

                    case CHAR:
                        map.put(Character.TYPE, valueStr.charAt(0));
                        break;
                }
            }
            else
            {
                Class<?> clazz = cl.loadClass(parameter.getType());
                Object value = clazz.getConstructor(String.class).newInstance(valueStr);
                map.put(clazz, value);
            }
        }

        return map;
    }

    private JaxbUtils()
    {
        /*
         * Static utilities
         */
    }
}
