/**
 * Copyright (C), 2015-2021, 开度
 * FileName: PermissionService
 * Author:   ASUS
 * Date:     2021/4/14 14:14
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Hezeyu           2021/4/14           1.0
 */
package com.example.spring.security.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

import static sun.security.util.SecurityConstants.ALL_PERMISSION;

/**
 * 〈〉
 *
 * @author ASUS
 * @create 2021/4/14
 * @since 1.0.0
 */
@Service("permission")
@Slf4j
public class    PermissionService {

    Set<String> set=new HashSet<>();

    /**
     * 验证用户是否具备某权限
     *
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    public boolean hasPermi(String permission) {
        set.add("list");
        set.add("helloUser");
        if (StringUtils.isEmpty(permission))
        {
            return false;
        }
        if ( CollectionUtils.isEmpty(set))
        {
            return false;
        }
        return hasPermissions(set, permission);
    }

    private boolean hasPermissions(Set<String> permissions, String permission) {
        log.info(StringUtils.trim(permission));
        log.info(permissions.contains(StringUtils.trim(permission))+"1");

        return permissions.contains(ALL_PERMISSION) || permissions.contains(StringUtils.trim(permission));
    }
}
