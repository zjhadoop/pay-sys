package cn.zb.storage;

import cn.zb.model.User;

import java.util.Collection;
import java.util.Map;


/**
 * 
 * user cache storage
 * 
 * @author zhulei
 * @version V1.0 created at: 2015-09-14
 */

public interface UserCacheStorage {

	/**
	 * cache user
	 * @param user
	 * @return
	 */
	boolean cacheUser(User user);
	
	/**
	 * cache users
	 * @param users
	 * @return
	 */
	boolean cacheUsers(Collection<User> users);

	/**
	 * clean user cache with uid
	 * @param uid
	 * @return
	 */
	boolean cleanUserCache(long uid);
	
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
