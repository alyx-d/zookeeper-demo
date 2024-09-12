package com.qt.zookeeperdemo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@TableName("user")
public class UserModel {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer deleted;
}
