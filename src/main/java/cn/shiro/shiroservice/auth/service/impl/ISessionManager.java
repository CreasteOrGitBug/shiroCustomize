package cn.shiro.shiroservice.auth.service.impl;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

/**
 * &#064;Time 2024 一月 星期三 18:25
 *
 * @author ShangGuan
 * 将SessionId放在缓存中
 */
public class ISessionManager extends AbstractSessionDAO {

    private final RedisTemplate<Serializable, Object> cache;

    public ISessionManager(RedisTemplate<Serializable, Object> cache) {
        this.cache = cache;
    }


    //序列化方法
    @Override
    protected Serializable doCreate(Session session) {
        final Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        cache.opsForValue().set(sessionId,session);
        return sessionId;
    }

    /**
     * 读取
     *
     * @param sessionId 会话id
     * @return {@link Session}
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        return (Session) cache.opsForValue().get(sessionId);
    }

    //缓存更新
    @Override
    public void update(Session session) throws UnknownSessionException {
        cache.opsForValue().set(generateSessionId(session), session);
    }

    //删除缓存
    @Override
    public void delete(Session session) {
        cache.delete(generateSessionId(session));
    }

    //读取缓存
    @Override
    public Collection<Session> getActiveSessions() {
        return Collections.emptySet();
    }
}
