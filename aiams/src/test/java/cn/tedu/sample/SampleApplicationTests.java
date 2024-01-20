package cn.tedu.sample;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.hhu.aiams.entity.User;
import cn.hhu.aiams.mapper.UserMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleApplicationTests {

	@Test
	public void contextLoads() {
	}
	
	@Autowired
	private DataSource dataSource;
	
	@Test
	public void getConnection() throws SQLException {
		Connection conn = dataSource.getConnection();
		System.err.println(conn);
	}
	
	@Autowired
	UserMapper userMapper;
	
	@Test
	public void addnew() {
		User user = new User();
		user.setUsername("xml");
		user.setPassword("888888");
		user.setAge(28);
		user.setPhone("13900139001");
		user.setEmail("xml@tedu.cn");
		Integer rows = userMapper.addnew(user);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void findByUsername() {
		String username = "mybatis";
		User user = userMapper.findByUsername(username);
		System.err.println(user);
	}
	
	@Test
	public void reg() {
		// 尝试注册的用户信息
		User user = new User();
		user.setUsername("newuser");
		user.setPassword("888888");
		user.setAge(28);
		user.setPhone("13900139008");
		user.setEmail("newuser@tedu.cn");
		// 检查用户名是否被注册
		User result = userMapper.findByUsername(user.getUsername());
		if (result == null) {
			// 查不到对应的用户数据，则未被注册，允许注册
			Integer rows = userMapper.addnew(user);
			System.err.println("rows=" + rows);
		} else {
			// 查到了对应的用户数据，则用户名已被占用，不执行注册，直接提示错误
			System.err.println("注册失败！用户名已经被占用！");
		}
	}

}






