package cn.zb.storage;

import java.util.Map;

import cn.zb.model.User;

/**
 * 
 * user db storage
 * 
 * @author zhulei
 * @version V1.0 created at: 2015-09-14
 */

public interface UserDbStorage {

	/**
	 * save user
	 * @param user
	 * @return
	 */
	boolean saveUser(User user);

	/**
	 * udpate user
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
