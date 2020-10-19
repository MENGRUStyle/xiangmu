package com.kai.oasys.shiro;

import com.kai.oasys.pojo.SysMenu;
import com.kai.oasys.pojo.User;
import com.kai.oasys.service.SysMenuService;
import com.kai.oasys.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
* 自定义数据域
* */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private UserService userService;

    /*
    * 实现用户认证后的权限分配
    * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        //获取当前认证对象
        User user= (User) principals.getPrimaryPrincipal();
        Integer roleId=user.getRoleId();

        List<SysMenu> sysMenuList=sysMenuService.queryMenuByRoleId(roleId);

        //定义一个权限列表
        Set<String> permissions=new HashSet<>();

        for (SysMenu sysMenu : sysMenuList) {

            if(sysMenu.getPermission()!=null && !sysMenu.getPermission().equals("")){

                permissions.add(sysMenu.getPermission());
            }
        }

        SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissions);

        return simpleAuthorizationInfo;
    }

    /*
    * 身份认证
    * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken usernamePasswordToken= (UsernamePasswordToken) authenticationToken;
        //获取用户名
        String username=usernamePasswordToken.getUsername();
        String password=new String(usernamePasswordToken.getPassword());
        //System.out.println(token.getCredentials());

        System.out.println("username="+username+"  password="+password);

        User user=new User();
        user.setLoginName(username);
        user.setPwd(password);
        List<User> userList=userService.queryUserByLoginNameAndPwd(user);

        if(userList==null || userList.size()==0)
        {
            throw new IncorrectCredentialsException("账号或者密码不正确！");
        }

        //获取用户数据对象
        User u=userList.get(0);

        if(u.getStatus().equals("1"))
        {
            throw  new LockedAccountException("用户被锁定，不可用");
        }

        /*if(!name.equals(getName()))
        {
            System.out.println("CustomRealm AuthenticationException ");
            throw new AuthenticationException();
        }*/
        String realName=u.getName()+""+u.getPwd();
        SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo(u,password,realName);
        return simpleAuthenticationInfo;
    }
}
