package cn.zb.resources;

import cn.zb.commons.util.StringUtils;
import cn.zb.model.User;
import cn.zb.commons.framework.auth.Access;
import cn.zb.commons.framework.context.RequestContext;
import cn.zb.exception.DemoSimpleException;
import cn.zb.resources.service.UserResourceService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 *
 * user resources
 *
 * @author zhulei
 * @version V1.0 created at: 2015-09-08
 */
@Controller
@RequestMapping("/v1/users")
public class UserResources {

    @Resource
    private UserResourceService userResourceService;

    /**
     * 增加用户信息
     *
     * 访问类型AccessType
     * 1.common（外网访问，且需mauth验证， 给客户端和h5提供的接口需此访问权限）
     * 2.internal (内网访问，内网调用接口访问权限)
     * 3.public(公开访问接口，无任何限制)
     *
     * @param nickname
     * @return
     */
    @RequestMapping(path = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Access(type = Access.AccessType.COMMON)
    public @ResponseBody
    String add(
            @RequestParam(name = "nickname", required = false, defaultValue = "") String nickname
    ) {
        RequestContext rc = RequestContext.getRequestContext();
        User user = new User();
        user.setUid(rc.getUid());
        user.setNickname(nickname != null ? nickname.trim() : null);

        return userResourceService.addUser(rc, user);
    }

    /**
     * 更新用户信息
     *
     * 访问类型AccessType
     * 1.common（外网访问，且需mauth验证， 给客户端和h5提供的接口需此访问权限）
     * 2.internal (内网访问，内网调用接口访问权限)
     * 3.public(公开访问接口，无任何限制)
     *
     * @param nickname
     * @return
     */
    @RequestMapping(path = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Access(type = Access.AccessType.COMMON)
    public @ResponseBody
    String update(
            @RequestParam(name = "nickname", required = false, defaultValue = "") String nickname
    ) {

        RequestContext rc = RequestContext.getRequestContext();
        User user = new User();
        user.setNickname(nickname != null ? nickname.trim() : null);

        return userResourceService.updateUser(rc, user);
    }


    /**
     * 获取用户信息
     *
     * 访问类型AccessType
     * 1.common（外网访问，且需mauth验证， 给客户端和h5提供的接口需此访问权限）
     * 2.internal (内网访问，内网调用接口访问权限)
     * 3.public(公开访问接口，无任何限制)
     *
     * @param uid
     * @return
     */
    @RequestMapping(path = "/show", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Access(type = Access.AccessType.COMMON)
    public @ResponseBody
    String getUser(@RequestParam(name = "uid", required = false, defaultValue = "0") long uid
    ) {
        RequestContext rc = RequestContext.getRequestContext();
        if (uid < 1){
            uid = rc.getUid();
        }
        return userResourceService.getUser(rc, uid);
    }
    @RequestMapping(path = "/show2", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Access(type = Access.AccessType.COMMON)
    public @ResponseBody
    String getUser2(@RequestParam(name = "uid", required = false, defaultValue = "0") long uid
    ) {
       throw DemoSimpleException.INVALID_PARAMETER_EXCEPTION;
    }
    /**
     * 批量获取用户信息
     *
     * 访问类型AccessType
     * 1.common（外网访问，且需mauth验证， 给客户端和h5提供的接口需此访问权限）
     * 2.internal (内网访问，内网调用接口访问权限)
     * 3.public(公开访问接口，无任何限制)
     *
     * @param uids
     * @return
     */
    @RequestMapping(path = "/showBatch", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Access(type = Access.AccessType.INTERNAL)
    public @ResponseBody
    String getUsers(@RequestParam(name = "uids", required = true) String uids) {

        RequestContext rc = RequestContext.getRequestContext();

        if (uids == null) {
            throw DemoSimpleException.INVALID_PARAMETER_EXCEPTION;
        }

        Long[] uidsArr = StringUtils.safeSplitAndParseLongParam(uids, ",", true);
        if(uidsArr == null || uidsArr.length == 0){
            throw DemoSimpleException.INVALID_PARAMETER_EXCEPTION;
        }

        return userResourceService.getUsers(rc, uidsArr);
    }
}
