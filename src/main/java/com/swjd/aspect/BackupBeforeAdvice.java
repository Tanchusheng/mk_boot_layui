package com.swjd.aspect;

import com.swjd.modules.system.service.LogService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * @program: mk_boot_layui
 * @description: 拦截器修改、删除方法，记录日志
 * @author: Tan.
 * @create: 2020-05-29 15:48
 **/
@Aspect
@Component
public class BackupBeforeAdvice implements MethodBeforeAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(BackupBeforeAdvice.class);

    private static List<String> backUpList = new ArrayList<String>();

    @Autowired
    private LogService logService;

    /** 需要记录日志的表的对象 */
    static {
        backUpList.add("Menu");
        backUpList.add("Role");
        backUpList.add("User");
    }

//    @Pointcut("@annotation(com.swjd.annotation.SysLog)")
//    public void TestAspect(){}
    /**
     *
     * @param method
     * @param args
     * @param target
     * @return void
     * @throw
     * @Author Tan
     * @Date: 2020/5/29 15:55
     */
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {

        try {

            String objName = args[0].getClass().toString();

            String backupObjectName = objName.substring(objName.indexOf(" ") + 1);

            String[] backupObjNameSections = backupObjectName.split("\\.");

            String backupNameSpace = backupObjNameSections[backupObjNameSections.length - 1];

            String methodName = method.getName().toLowerCase();

            if (args[0] instanceof List) {
                for (Object obj : (List) args[0]) {
                    String tempBackupNameSpace = obj.getClass().toString().substring(objName.indexOf(" ") + 1);
                    String[] tempBackupObjNameSections = tempBackupNameSpace.split("\\.");
                    backupNameSpace = tempBackupObjNameSections[tempBackupObjNameSections.length - 1];

                    backUpToLog(backupNameSpace, methodName, obj);
                }
            } else {
                backUpToLog(backupNameSpace, methodName, args[0]);
            }
        }catch(Exception e){
            LOGGER.error("[Error:backup log error]", e);
        }
    }

    /**
     *
     * @param backupNameSpace
     * @param methodName
     * @param args
     * @return void
     * @throw
     * @Author Tan
     * @Date: 2020/5/29 16:00
     */
    private void backUpToLog(String backupNameSpace,String methodName,Object args){
        try{
            LOGGER.info("backupNameSpace",backupNameSpace);
            LOGGER.info("methodName",methodName);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
