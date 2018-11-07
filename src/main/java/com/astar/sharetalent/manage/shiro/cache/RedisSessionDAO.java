package com.astar.sharetalent.manage.shiro.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author: jujun chen
 * @description:
 * @date: 2018/7/1
 */
@Slf4j
@Component
public class RedisSessionDAO extends CachingSessionDAO {

    private static final ConcurrentHashMap<Serializable,Session> memorySession = new ConcurrentHashMap<>();


    @Override
    protected void doUpdate(Session session) {
    }

    @Override
    protected void doDelete(Session session) {
    }

    @Override
    protected Serializable doCreate(Session session) {
        if (session == null) {
            log.error("session is null");
            throw new UnknownSessionException("session is null");
        }
        Serializable sessionId = getRealSessionKey(this.generateSessionId(session));
        this.assignSessionId(session, sessionId);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        Cache cache = getCacheManager().getCache(getActiveSessionsCacheName());
        if (sessionId == null) {
            log.warn("session id is null");
            return null;
        }
        Session session = memorySession.get(sessionId);
        if (session != null) {
            return session;
        }
        log.debug("read session from redis");
        session = (Session) cache.get(sessionId);
        if (session != null){
            memorySession.put(sessionId,session);
        }
        return session;
    }


    private String getRealSessionKey(Serializable sessionId){
        return "session:" + sessionId;
    }


}
