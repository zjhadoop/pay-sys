package cn.zb.storage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import cn.zb.commons.memcached.MemcacheClient;
import cn.zb.commons.util.storage.McClientUtil;
import cn.zb.model.User;
import cn.zb.commons.util.ApiLogger;
import org.springframework.stereotype.Service;


/**
 * 
 * user cache
 *
 * @author zhulei
 * @version V1.0 created at: 2015-09-14
 */

@Service("userCacheStorage")
public class UserCacheStorageImpl implements UserCacheStorage{

	@Resource(name="userMemcache")
	private MemcacheClient userCache;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean cacheUser(User user){
		return userCache.set(McClientUtil.getKey(user.getUid(), KeySuffix.uid_user.value), user);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean cacheUsers(Collection<User> users){
		for(User u : users){
			cacheUser(u);
		}
		return true;
	}

	/**
	 * {@inheritDoc}`
	 */
	@Override
	public boolean cleanUserCache(long uid){
		return userCache.delete(McClientUtil.getKey(uid, KeySuffix.uid_user.value));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public User getUser(long uid){
		try{
			User u = (User)userCache.get(McClientUtil.getKey(uid, KeySuffix.uid_user.value));
			return u;
		}catch(Exception e){
			ApiLogger.error("get user from cache error: uid=" + uid, e);
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Long, User> getUsers(Long[] uids){
		String[] keys = McClientUtil.getKeys(uids, KeySuffix.uid_user.value);
		Map<String, Object> userPbs = userCache.getMulti(keys);
		Map<Long, User> users = new HashMap<Long, User>();
		
		for(Object pb : userPbs.values()){
			User user = (User)pb;
			if(user != null){
				users.put(user.getUid(), user);
			}
		}
		return users;
	}
	
	private static enum KeySuffix{
		uid_user("uiu");
		
		private String value;
		private KeySuffix(String v){
			this.value = v;
		}
		
	}
}
