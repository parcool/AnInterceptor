package com.parcool.internal;

public interface HandlerInterceptor {

    /**
     * 在handle之前，用来判断是否需要处理
     *
     * @return true:需要处理，false:直接返回，不调用handle
     */
    boolean preHandle();

    /**
     * 如果preHandle返回true，则调用该方法。
     *
     */
    void handle();
}
