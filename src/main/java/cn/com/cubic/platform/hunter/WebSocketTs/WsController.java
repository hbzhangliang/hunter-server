package cn.com.cubic.platform.hunter.WebSocketTs;

import cn.com.cubic.platform.hunter.mysql.entity.CoreUser;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.flaginfo.platform.api.common.base.BaseResponse;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpSession;

/**
 * Created by Liang.Zhang on 2018/7/4.
 **/

@Validated
@Controller
@RequestMapping(value = "/ws",produces = "application/json; charset=utf-8")
@ResponseBody
public class WsController {

    @RequestMapping(value = "/1")
    public Object test1(HttpSession session){
        session.setAttribute("user","zs");
        return new BaseResponse<>(null,"ddkjadlfkjdlaksfdlkaedfs");
    }

    @RequestMapping(value = "/2")
    public Object test2(HttpSession session){
        session.getAttribute("user");

        return new BaseResponse<>(null,session.getAttribute("user"));
    }

}
