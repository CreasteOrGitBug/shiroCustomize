package cn.shiro.shiroservice.auth.service.impl;

import cn.shiro.shiroservice.auth.mapper.ISubjectDaoMapper;
import org.apache.shiro.mgt.SubjectDAO;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;


/**
 * &#064;Time 2023 十二月 星期六 14:08
 *
 * @author ShangGuan
 * 持久化存储Subject
 */
public class ISubjectDaoImpl implements SubjectDAO {


    private final ISubjectDaoMapper iSubjectDaoMapper;

    public ISubjectDaoImpl(ISubjectDaoMapper iSubjectDaoMapper) {
        this.iSubjectDaoMapper = iSubjectDaoMapper;
    }


    /**
     * 保存 只去保存当前的sessionId
     *
     * @param subject 主题
     * @return {@link Subject}
     */

    @Override
    public Subject save(Subject subject) {
        String userName = (String) subject.getPrincipal();
        Session session = subject.getSession();
        String sessionId = session.getId().toString();
        iSubjectDaoMapper.updateByUserName(sessionId,userName);
        return subject;
    }


    @Override
    public void delete(Subject subject) {
        clear((String) subject.getPrincipal());
    }

    private void clear(String userName){
        iSubjectDaoMapper.updateByUserName(null,userName);
    }
}
