package cn.hhu.aiams.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hhu.aiams.entity.User;
import cn.hhu.aiams.mapper.UserMapper;
import cn.hhu.aiams.util.JsonResult;

@RestController
public class UserController {
	
	@Autowired
	private UserMapper userMapper;


	
	@RequestMapping("test")
	public JsonResult test() {
		JsonResult jsonResult = new JsonResult();
		
		
		
		return jsonResult;
	}
	
}










