package algvis.helper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionHelper {
    private static Class<?> getClass(String classFullName) {
        Class<?> cls = null;
        try {
            cls = Class.forName(classFullName);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return cls;
    }

    public static Object getFieldValue(Object object, String fieldName) {
        if (object == null) {
            return null;
        }

        Field field = null;

        try {
            field = object.getClass().getDeclaredField(fieldName);
        } catch (SecurityException | NoSuchFieldException e) {
            System.out.println(e.getMessage());
        }

        if (field == null) {
            Class<?> superClass = object.getClass().getSuperclass();

            while (superClass != null) {
                try {
                    field = superClass.getDeclaredField(fieldName);
                } catch (SecurityException | NoSuchFieldException e) {
                    System.out.println(e.getMessage());
                }

                if (field != null) {
                    break;
                }
                superClass = superClass.getSuperclass();
            }
        }

        if (field == null) {
            return null;
        }

        boolean isAccessible = field.isAccessible();
        field.setAccessible(true);

        Object value = null;
        try {
            value = field.get(object);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            System.out.println(e.getMessage());
        }

        field.setAccessible(isAccessible);

        return value;
    }

    public static Object createObject(String classFullName) {
        Class<?> cls = getClass(classFullName);

        if (cls == null) {
            return null;
        }

        Constructor<?> constructor = null;
        try {
            constructor = cls.getConstructor();
        } catch (SecurityException | NoSuchMethodException e) {
            System.out.println(e.getMessage());
        }

        Object object = null;

        if (constructor == null) {
            try {
                object = cls.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                System.out.println(e.getMessage());
            }
        } else {
            boolean constructorIsAccessible = constructor.isAccessible();
            constructor.setAccessible(true);
            try {
                object = constructor.newInstance();
            } catch (IllegalArgumentException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                System.out.println(e.getMessage());
            }
            constructor.setAccessible(constructorIsAccessible);
        }

        return object;
    }

    public static Method getMethod(String classFullName, String methodName, Class<?>... parameterTypes) {
        Class<?> cls = getClass(classFullName);

        if (cls == null || "".equals(methodName)) {
            return null;
        }

        Method method = null;

        try {
            method = cls.getDeclaredMethod(methodName, parameterTypes);
        } catch (SecurityException | NoSuchMethodException e) {
            System.out.println(e.getMessage());
        }

        return method; 
    }
}
