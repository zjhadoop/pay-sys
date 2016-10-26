package cn.zb.resources.service;

import cn.zb.commons.json.JsonBuilder;
import cn.zb.model.User;
import cn.zb.service.UserService;
import cn.zb.commons.framework.context.RequestContext;
import cn.zb.exception.DemoSimpleException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *
 * user resource service
 *
 * @author zhulei
 * @version V1.0 created at: 2015-09-14
 */
@Service("userResourceService")
public class UserResourceServiceImpl implements UserResourceService{

	@Resource
	private UserService userService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String addUser(RequestContext context, User user) {

		userService.saveUser(user);
		User usernew = userService.getUser(context.getUid());
		return usernew.toJsonString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String updateUser(RequestContext context, User user) {

		user.setUid(context.getUid());
		userService.updateUser(context.getUid(), user);
		User usernew = userService.getUser(context.getUid());
		return usernew.toJsonString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getUser(RequestContext context, long uid) {

		User u = userService.getUser(uid);
		if(u != null){
			JsonBuilder jb = new JsonBuilder();
			jb.append("uid", String.valueOf(u.getUid()));
			jb.append("nickname", u.getNickname());
			return jb.toString();
		}
		throw DemoSimpleException.INVALID_UID_EXCEPTION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getUsers(RequestContext context, Long[] uids) {
		Map<Long, User> users = userService.getUsers(uids);
		if(users == null || users.size() == 0){
			return JsonBuilder.buildEmptyArray("users");
		}

		List<User> userJsons = new ArrayList<User>();
		for(long uid : uids){
			User u = users.get(uid);
			if(u == null){
				continue;
			}
			userJsons.add(u);

		}
		return JsonBuilder.toJSON(userJsons);
	}
}
