package com.parcool.internal;

import java.util.ArrayDeque;
import java.util.Queue;

public class Chain {

    private String key;
    private Queue<Action> actionQueue = new ArrayDeque<>();
    private Queue<HandlerInterceptor> interceptorQueue = new ArrayDeque<>();

    public Chain(String key) {
        if (key == null || key.length() == 0) {
            throw new IllegalArgumentException("key must not be null");
        }
        this.key = key;
    }

    /**
     * 设置action
     *
     * @param action action接口
     * @return InterceptorManager
     */
    public Chain setAction(Action action) {
        actionQueue.clear();
        actionQueue.add(action);
        return this;
    }

    /**
     * 添加Validator
     *
     * @param handlerInterceptor 实现了IValidator的各种类
     * @return InterceptorManager
     */
    public Chain addInterceptor(HandlerInterceptor handlerInterceptor) {
        interceptorQueue.add(handlerInterceptor);
        return this;
    }

    public String getKey() {
        return key;
    }

    public Queue<Action> getActionQueue() {
        return actionQueue;
    }

    public Queue<HandlerInterceptor> getInterceptorQueue() {
        return interceptorQueue;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Chain)) {
            return false;
        }
        Chain chain = (Chain) obj;
        return chain.key.equals(key);
    }
}
