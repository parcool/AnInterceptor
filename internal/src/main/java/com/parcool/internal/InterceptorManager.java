package com.parcool.internal;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

public class InterceptorManager {
    private static InterceptorManager instance;

    private InterceptorManager() {
    }

    public static synchronized InterceptorManager getInstance() {
        if (instance == null) {
            instance = new InterceptorManager();
        }
        return instance;
    }

    private Queue<Chain> queue = new ArrayDeque<>();

    public InterceptorManager addChain(Chain chain) {
        Iterator<Chain> iterable = queue.iterator();
        while (iterable.hasNext()) {
            if (iterable.next().equals(chain)) {
                iterable.remove();
            }
        }
        queue.add(chain);
        return this;
    }

    public void trigger() {
        if (queue.size() == 0) {
            return;
        }
        Chain chain = queue.peek();
        if (chain == null) {
            trigger();
            return;
        }
        HandlerInterceptor head = chain.getInterceptorQueue().peek();
        if (head == null) {
            Action action = chain.getActionQueue().poll();
            if (action != null) {
                queue.remove(chain);
                action.call();
            }
        } else {
            if (head.preHandle()) {
                if (chain.getInterceptorQueue().poll() != null) {
                    trigger();
                } else {
                    Action action = chain.getActionQueue().poll();
                    if (action != null) {
                        action.call();
                    }
                }
            } else {
                head.handle();
            }
        }
    }

}
