package cn.zb.exception;

import cn.zb.commons.framework.exception.CommonException;
import cn.zb.commons.framework.exception.ExceptionFactor;

import javax.servlet.http.HttpServletResponse;

/**
 * DemoSimpleException
 * 注意：code部分需要单独找钟杰申请 邮箱：zhongjie@17shihui.com, 并备注：系统名
 * @author zj
 * @date 2016/9/5
 */
public class DemoSimpleException extends ExceptionFactor{

	private static int DEMO_SYSTEM_CODE=0;
    /**
     * 非法参数
     */
    public static final CommonException INVALID_PARAMETER_EXCEPTION = new CommonException(
            CommonException.ERROR_LEVEL_SYSTEM, DEMO_SYSTEM_CODE, 0, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "invalid parameter!", "非法参数!");

    /**
     * 非法用户
     */
    public static final CommonException INVALID_UID_EXCEPTION = new CommonException(
            CommonException.ERROR_LEVEL_SERVICE, DEMO_SYSTEM_CODE, 1, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "invalid uid!", "非法用户id!");

}
