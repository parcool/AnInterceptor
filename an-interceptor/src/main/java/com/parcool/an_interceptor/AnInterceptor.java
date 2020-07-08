package com.parcool.an_interceptor;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import com.parcool.an_interceptor.action.StartActivityForResultAction;
import com.parcool.annotation.Interceptor;
import com.parcool.internal.Chain;
import com.parcool.internal.HandlerInterceptor;
import com.parcool.internal.InterceptorManager;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

public class AnInterceptor {

    static final Map<Class<?>, Constructor<?>> BINDINGS = new LinkedHashMap<>();
    static final Map<String, Class<?>> TARGETS = new LinkedHashMap<>();

    public static InterceptorManager addChain(Chain chain) {
        return InterceptorManager.getInstance().addChain(chain);
    }

    public static void trigger() {
        InterceptorManager.getInstance().trigger();
    }

    public static void startActivityForResult(final Activity sourceActivity, final Intent intent, final int requestCode, final Bundle options) {
        ComponentName componentName = intent.getComponent();
        if (componentName == null) {
            return;
        }
        String targetClassPath = componentName.getClassName();
        try {
            Class<?> cls = TARGETS.get(targetClassPath);
            if (cls == null) {
                cls = sourceActivity.getClassLoader().loadClass(targetClassPath);
            }
            if (!cls.isAnnotationPresent(Interceptor.class)) {
                new StartActivityForResultAction(sourceActivity, intent, requestCode, options).call();
                return;
            }
            for (Annotation annotation : cls.getAnnotations()) {
                if (annotation instanceof Interceptor) {
                    Interceptor interceptor = (Interceptor) annotation;
                    Class<? extends HandlerInterceptor>[] handlerInterceptorClasses = interceptor.interceptors();
                    Chain chain = new Chain(targetClassPath);
                    chain.setAction(new StartActivityForResultAction(sourceActivity, intent, requestCode, options));
                    for (Class<? extends HandlerInterceptor> handlerInterceptorClass : handlerInterceptorClasses) {
                        Constructor<?> constructor = BINDINGS.get(handlerInterceptorClass);
                        if (constructor == null) {
                            constructor = handlerInterceptorClass.getConstructor(Activity.class);
                        }
                        chain.addInterceptor((HandlerInterceptor) constructor.newInstance(sourceActivity));
                    }
                    addChain(chain).trigger();
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
