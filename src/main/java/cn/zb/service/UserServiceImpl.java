package cn.zb.service;
import java.util.*;

import javax.annotation.Resource;

import cn.zb.model.User;
import cn.zb.storage.UserCacheStorage;
import cn.zb.storage.UserDbStorage;
import org.springframework.stereotype.Service;


/**
 * 
 * user service
 *
 * @author zhulei
 * @version V1.0 created at: 2015-09-14
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource(name="userDbStorage")
	private UserDbStorage userDbStorage;
	
	@Resource(name="userCacheStorage")
	private UserCacheStorage userCacheStorage;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean saveUser(User user){
		userDbStorage.saveUser(user);
		
		//注册过程中，user 信息不完整，先clean一下老的user
		userCacheStorage.cleanUserCache(user.getUid());
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean updateUser(long uid, User user){
		userDbStorage.updateUser(uid, user);
		
		userCacheStorage.cleanUserCache(uid);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User getUser(long uid){
		User user = userCacheStorage.getUser(uid);
		if(user == null){
			user = userDbStorage.getUser(uid);
			if(user != null){
				userCacheStorage.cacheUser(user);
			}
		}
		return user;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<Long, User> getUsers(Long[] uids){
		if(uids == null || uids.length == 0){
			return Collections.EMPTY_MAP;
		}
		Map<Long, User> users = new HashMap<Long, User>(uids.length);
		users.putAll(userCacheStorage.getUsers(uids));
		
		if(users.size() < uids.length){
			Long[] uidsForDb = getDiffIds(uids, users.keySet());
			Map<Long, User> usersFromDb = userDbStorage.getUsers(uidsForDb);
			if(usersFromDb.size() > 0){
				users.putAll(usersFromDb);
				userCacheStorage.cacheUsers(usersFromDb.values());
			}
		}
		return users;
	}

	/**
	 *
	 * @param allIds
	 * @param subIds
	 * @return
	 */
	private Long[] getDiffIds(Long[] allIds, Collection<Long> subIds) {
		if (allIds.length == subIds.size()) {
			return null;
		}
		List<Long> diffIds = new ArrayList<Long>();
		for (long id : allIds) {
			if (!subIds.contains(id)) {
				diffIds.add(id);
			}
		}
		return diffIds.toArray(new Long[diffIds.size()]);
	}

}
