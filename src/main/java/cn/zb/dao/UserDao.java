package cn.zb.dao;

import cn.zb.model.User;
import java.util.Map;
/**
 *
 * use dao interfaces
 *
 * @author zhulei
 *
 */
public interface UserDao {

    /**
     * insert user
     * @param user
     * @return
     */
    boolean insert(User user);

    /**
     * update user
     * @param uid
     * @param user
     * @return
     */
    boolean update(long uid, User user);

    /**
     * select user
     * @param uid
     * @return
     */
    User select(long uid);

    /**
     * select users
     * @param uids
     * @return
     */
    Map<Long, User> select(Long[] uids);
}
