package cn.zb.storage;

import java.util.Map;

import javax.annotation.Resource;

import cn.zb.model.User;
import cn.zb.dao.UserDao;
import org.springframework.stereotype.Service;


/**
 * 
 * User storage
 *
 * @author zhulei
 * @version V1.0 created at: 2015-09-14
 */
@Service("userDbStorage")
public class UserDbStorageImpl implements UserDbStorage{

	@Resource
	private UserDao userDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean saveUser(User user){
		return userDao.insert(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean updateUser(long uid, User user){
		userDao.update(uid, user);
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public User getUser(long uid){
		User user = userDao.select(uid);
		
		return user;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Long, User> getUsers(Long[] uids){
		Map<Long, User> users = userDao.select(uids);
		
		return users;
	}

}
