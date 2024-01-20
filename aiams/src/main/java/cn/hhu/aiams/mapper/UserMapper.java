package cn.hhu.aiams.mapper;

import cn.hhu.aiams.entity.User;

public interface UserMapper {
	
	Integer addnew(User user);
	
	User findByUsername(String username);
	
}








