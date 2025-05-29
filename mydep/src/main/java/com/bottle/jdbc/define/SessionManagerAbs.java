package com.bottle.jdbc.define;




/**
 * @Author: leeping
 * @Date: 2019/8/16 9:25
 */
public abstract class SessionManagerAbs<S> implements SessionManagerI<S> {

    private final ThreadLocal<S> localSession = new ThreadLocal<>();

    @Override
    public void setSession(S session) {
        this.localSession.set(session);
    }

    @Override
    public S getSession() {
        return this.localSession.get();
    }

    @Override
    public void unInitialize() {
        this.localSession.remove();
    }
}
