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
 * @ClassName UserController
 * @Description TODO
 * @Author Tan
 * @Date 2020/5/17 16:43
 * @Version 1.0
 */
@Controller
@RequestMapping("api/system/user")
public class UserController extends BaseController {

    /**
     * 功能描述:获取用户所拥有的菜单列表
     * @Param: []
     * @return: com.swjd.util.RestResponse
     **/
    @RequestMapping("/getUserMenu")
    @ResponseBody
    public RestResponse getUserMenu(){
        Long userId = SysUser.id();
        List<ShowMenu> menulist = menuService.getShowMenuByUser(userId);
        return RestResponse.success().setData(menulist);
    }
}
