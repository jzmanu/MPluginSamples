package com.manu.mpluginsamples.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Desc: 反射辅助类
 * @Author: jzman
 */
public class ReflectHelper {

    public static Object createObject(String className) {
        Class<?>[] paramTypes = new Class[]{};
        Object[] paramValues = new Object[]{};
        return createObject(className, paramTypes, paramValues);
    }

    public static Object createObject(Class<?> clazz) {
        Class<?>[] paramTypes = new Class[]{};
        Object[] paramValues = new Object[]{};
        return createObject(clazz, paramTypes, paramValues);
    }


    public static Object createObject(String className, Class<?> paramType, Object paramValue) {
        Class<?>[] paramTypes = new Class[]{paramType};
        Object[] paramValues = new Object[]{paramValue};
        return createObject(className, paramTypes, paramValues);
    }

    public static Object invokeInstanceMethod(Object obj, String methodName) {
        Class<?>[] paramTypes = new Class[]{};
        Object[] paramValues = new Object[]{};
        return invokeInstanceMethod(obj, methodName, paramTypes, paramValues);
    }

    public static Object invokeInstanceMethod(Object obj, String methodName, Class<?> paramType, Object paramValue) {
        Class<?>[] paramTypes = new Class[]{paramType};
        Object[] paramValues = new Object[]{paramValue};
        return invokeInstanceMethod(obj, methodName, paramTypes, paramValues);
    }

    public static Object invokeStaticMethod(String className, String methodName) {
        Class<?>[] paramTypes = new Class[]{};
        Object[] paramValues = new Object[]{};
        return invokeStaticMethod(className, methodName, paramTypes, paramValues);
    }

    public static Object invokeStaticMethod(String className, String methodName, Class<?> paramType, Object paramValue) {
        Class<?>[] paramTypes = new Class[]{paramType};
        Object[] paramValues = new Object[]{paramValue};
        return invokeStaticMethod(className, methodName, paramTypes, paramValues);
    }

    public static Object invokeStaticMethod(Class<?> clazz, String methodName) {
        Class<?>[] paramTypes = new Class[]{};
        Object[] paramValues = new Object[]{};
        return invokeStaticMethod(clazz, methodName, paramTypes, paramValues);
    }

    public static Object invokeStaticMethod(Class<?> clazz, String methodName, Class<?> paramType, Object paramValue) {
        Class<?>[] paramTypes = new Class[]{paramType};
        Object[] paramValues = new Object[]{paramValue};
        return invokeStaticMethod(clazz, methodName, paramTypes, paramValues);
    }

    public static Object getStaticFieldObject(String className, String fieldName) {
        return getFieldObject(className, fieldName, null);
    }

    public static void setStaticFieldObject(String className, String fieldName, Object fieldValue) {
        setFieldObject(className, fieldName, null, fieldValue);
    }

    /**
     * 反射创建对象
     *
     * @param className   类的完全限定名称
     * @param paramTypes  参数类型
     * @param paramValues 参数值
     * @return 返回className对应的类创建的对象
     */
    private static Object createObject(String className, Class<?>[] paramTypes, Object[] paramValues) {
        try {
            Class<?> clazz = Class.forName(className);
            Constructor<?> constructor = clazz.getDeclaredConstructor(paramTypes);
            constructor.setAccessible(true);
            return constructor.newInstance(paramValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Object createObject(Class<?> clazz, Class<?>[] paramTypes, Object[] paramValues) {
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor(paramTypes);
            constructor.setAccessible(true);
            return constructor.newInstance(paramValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反射调用对象方法
     *
     * @param obj         某对象
     * @param methodName  方法名
     * @param paramTypes  参数类型
     * @param paramValues 参数值
     * @return 返回obj的方法methodName的返回值
     */
    public static Object invokeInstanceMethod(Object obj, String methodName, Class<?>[] paramTypes, Object[] paramValues) {
        if (obj == null) return null;
        try {
            Method method = obj.getClass().getDeclaredMethod(methodName, paramTypes);
            method.setAccessible(true);
            return method.invoke(obj, paramValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反射调用静态方法
     *
     * @param className   类的完全限定名称
     * @param methodName  方法名
     * @param paramTypes  参数类型
     * @param paramValues 参数值
     * @return 返回静态方法的返回值
     */
    public static Object invokeStaticMethod(String className, String methodName, Class<?>[] paramTypes, Object[] paramValues) {
        try {
            Class<?> clazz = Class.forName(className);
            Method method = clazz.getDeclaredMethod(methodName, paramTypes);
            method.setAccessible(true);
            return method.invoke(null, paramValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param clazz       目标类的Class类型
     * @param methodName  方法名
     * @param paramTypes  参数类型
     * @param paramValues 参数值
     * @return 返回静态方法的返回值
     */
    public static Object invokeStaticMethod(Class<?> clazz, String methodName, Class<?>[] paramTypes, Object[] paramValues) {
        try {
            Method method = clazz.getDeclaredMethod(methodName, paramTypes);
            method.setAccessible(true);
            return method.invoke(null, paramValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从某个对象中获取某个属性
     *
     * @param className 类的完全限定名称
     * @param fieldName 属性名称
     * @param obj       属性虽在的对象
     * @return 返回fieldName对应对象
     */
    public static Object getFieldObject(String className, String fieldName, Object obj) {
        try {
            Class<?> clazz = Class.forName(className);
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从某个对象中获取某个属性
     *
     * @param clazz     目标类的Class类型
     * @param fieldName 属性名称
     * @param obj       属性虽在的对象
     * @return 返回fieldName对应对象
     */
    public static Object getFieldObject(Class<?> clazz, String fieldName, Object obj) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getFieldObject(String fieldName, Object obj) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置某个属性的值
     *
     * @param className  类的完全限定名称
     * @param fieldName  属性名称
     * @param obj        属性所在对象
     * @param fieldValue 要设置的属性的值
     */
    public static void setFieldObject(String className, String fieldName, Object obj, Object fieldValue) {
        try {
            Class<?> clazz = Class.forName(className);
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, fieldValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置某个属性的值
     *
     * @param clazz      目标类的Class类型
     * @param fieldName  属性名称
     * @param obj        属性所在对象
     * @param fieldValue 要设置的属性的值
     */
    public static void setFieldObject(Class<?> clazz, String fieldName, Object obj, Object fieldValue) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, fieldValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
