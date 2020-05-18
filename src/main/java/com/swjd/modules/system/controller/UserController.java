package com.swjd.modules.system.controller;

import com.swjd.base.BaseController;
import com.swjd.base.SysUser;
import com.swjd.modules.system.entity.vo.ShowMenu;
import com.swjd.util.RestResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description:
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/18 10:29
 * @Version：1.0
 */
@Controller
@RequestMapping("api/system/user")
public class UserController extends BaseController {
    /**
     * 功能描述：获取用户所拥有的菜单列表
     * @return
     */
    @RequestMapping("/getUserMenu")
    @ResponseBody
    public RestResponse getUserMenu(){
        Long userId = SysUser.id();
        List<ShowMenu> list = menuService.getShowMenuByUser(userId);
        return RestResponse.success().setData(list);
    }
}
