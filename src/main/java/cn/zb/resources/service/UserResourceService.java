package cn.zb.resources.service;

import cn.zb.model.User;
import cn.zb.commons.framework.context.RequestContext;

/**
 * 
 * 类说明
 *
 * @author zhulei
 * @version V1.0 created at: 2015-09-14
 */

public interface UserResourceService {

	/**
	 * add user
	 * @param context
	 * @param user
	 * @return
	 */
	String addUser(RequestContext context, User user);
	
	/**
	 * update user
	 * @param context
	 * @param user
	 * @return
	 */
	String updateUser(RequestContext context, User user);

	/**
	 * get user
	 * @param context
	 * @param uid
	 * @return
	 */
	String getUser(RequestContext context, long uid);
	
	/**
	 * get users
	 * @param context
	 * @param uids
	 * @return
	 */
	String getUsers(RequestContext context, Long[] uids);
	
}
