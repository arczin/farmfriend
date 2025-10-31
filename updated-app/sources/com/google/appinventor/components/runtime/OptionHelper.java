package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.Options;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.OptionList;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class OptionHelper {
    private static final Map<String, Map<String, Method>> componentMethods = new HashMap();

    public static <T> Object optionListFromValue(Object c, String func, T value) {
        Options annotation;
        Method calledFunc = getMethod(c, func);
        if (calledFunc == null || (annotation = (Options) calledFunc.getAnnotation(Options.class)) == null) {
            return value;
        }
        Class<? extends OptionList<?>> value2 = annotation.value();
        try {
            Object abstractVal = value2.getMethod("fromUnderlyingValue", new Class[]{value.getClass()}).invoke(value2, new Object[]{value});
            if (abstractVal != null) {
                return abstractVal;
            }
            return value;
        } catch (NoSuchMethodException e) {
            return value;
        } catch (IllegalAccessException e2) {
            return value;
        } catch (InvocationTargetException e3) {
            return value;
        }
    }

    public static Object[] optionListsFromValues(Component c, String func, Object... args) {
        Method calledFunc;
        Object[] objArr = args;
        if (objArr.length == 0 || (calledFunc = getMethod(c, func)) == null) {
            return objArr;
        }
        int i = 0;
        for (Annotation[] annotations : calledFunc.getParameterAnnotations()) {
            int length = annotations.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                Annotation annotation = annotations[i2];
                if (annotation.annotationType() == Options.class) {
                    Class<? extends OptionList<?>> value = ((Options) annotation).value();
                    try {
                        Object abstractVal = value.getMethod("fromUnderlyingValue", new Class[]{objArr[i].getClass()}).invoke(value, new Object[]{objArr[i]});
                        if (abstractVal != null) {
                            objArr[i] = abstractVal;
                        }
                    } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    }
                } else {
                    i2++;
                }
            }
            i++;
        }
        return objArr;
    }

    private static Method getMethod(Object c, String func) {
        Class<?> componentClass = c.getClass();
        String componentKey = componentClass.getSimpleName();
        Map<String, Method> methodMap = componentMethods.get(componentKey);
        if (methodMap == null) {
            methodMap = populateMap(componentClass);
            componentMethods.put(componentKey, methodMap);
        }
        return methodMap.get(func);
    }

    private static Map<String, Method> populateMap(Class<?> clazz) {
        Map<String, Method> methodMap = new HashMap<>();
        for (Method m : clazz.getMethods()) {
            if ((m.getModifiers() & 1) != 0) {
                String methodKey = m.getName();
                if (((SimpleEvent) m.getAnnotation(SimpleEvent.class)) != null) {
                    methodMap.put(methodKey, m);
                } else if (m.getReturnType() != Void.TYPE) {
                    if (((SimpleFunction) m.getAnnotation(SimpleFunction.class)) != null) {
                        methodMap.put(methodKey, m);
                    } else if (((SimpleProperty) m.getAnnotation(SimpleProperty.class)) != null) {
                        methodMap.put(methodKey, m);
                    }
                }
            }
        }
        return methodMap;
    }
}
