package com.qt.zookeeperdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qt.zookeeperdemo.model.UserModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserModel> {
}
