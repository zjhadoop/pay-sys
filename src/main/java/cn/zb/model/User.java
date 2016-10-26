package cn.zb.model;


import cn.zb.commons.json.CostomSerializer;
import cn.zb.commons.json.JsonBuilder;
import cn.zb.commons.json.JsonSerializable;
import com.alibaba.fastjson.annotation.JSONType;

import java.io.Serializable;

/**
 *
 * 类说明
 *
 * @author zhulei
 * @version V1.0 created at: 2015-09-09
 */
@JSONType(serializer = CostomSerializer.class)
public class User implements JsonSerializable, Serializable {

    private static final long serialVersionUID = 1444788176106337274L;

    /** 用户唯一标识id */
    private Long uid;

    /** 用户昵称 */
    private String nickname;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toJsonString(int view) {
        String result;
        switch (view){
            case 1://注释
                result = new JsonBuilder("nickname", nickname).toJSON();
                break;
            case 2://注释
                result = new JsonBuilder("uid", uid).toJSON();
                break;
            default:
                result = new JsonBuilder("uid", uid).append("nickname", nickname).toJSON();
                break;
        }
        return result;
    }
}
