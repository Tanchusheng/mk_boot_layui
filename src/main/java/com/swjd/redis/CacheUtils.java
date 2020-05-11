package com.swjd.redis;

import com.swjd.base.SysUser;
import com.swjd.modules.system.entity.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;

/**
 * @Description:缓存工具类
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/9 8:37
 * @Version：1.0
 */
public class CacheUtils {
    /**
     * 清除当前用户redis缓存
     */
    @Caching(evict = {
            @CacheEvict(value = "user", key = "'user_id_'+T(String).valueOf(#result.id)",condition = "#result.id != null and #result.id != 0"),
            @CacheEvict(value = "user", key = "'user_name_'+#result.loginName", condition = "#result.loginName !=null and #result.loginName != ''"),
            @CacheEvict(value = "user", key = "'user_email_'+#result.email", condition = "#result.email != null and #result.email != ''"),
            @CacheEvict(value = "user", key = "'user_tel_'+#result.tel", condition = "#result.tel != null and #result.tel != ''" ),
    })
    public User clearUserCache(){
        User user = new User();
        user.setId(SysUser.id());
        return user.selectById();
    }
}
