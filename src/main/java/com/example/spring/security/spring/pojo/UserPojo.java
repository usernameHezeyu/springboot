package com.example.spring.security.spring.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class UserPojo  implements java.io.Serializable  {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String password;

    private String username;

    private String role;

}