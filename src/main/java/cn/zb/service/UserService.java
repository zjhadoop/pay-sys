package cn.zb.service;

import cn.zb.model.User;
import java.util.Map;

/**
 *
 * 类说明
 *
 * @author zhulei
 * @version V1.0 created at: 2015-09-09
 */
public interface UserService {

    /**
     * save user
     * @param user
     * @return
     */
    boolean saveUser(User user);

    /**
     * update user
     * @param uid
     * @param user
     * @return
     */
    boolean updateUser(long uid, User user);

    /**
     * select user
     * @param uid
     * @return
     */
    User getUser(long uid);

    /**
     * select users
     * @param uids
     * @return
     */
    Map<Long, User> getUsers(Long[] uids);
}
