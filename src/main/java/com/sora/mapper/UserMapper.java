package com.sora.mapper;



import com.sora.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserMapper {
	/**
	 * 根据ID来查询用户
	 * 
	 * @param id
	 * @return
	 */
	User queryUserById(@Param("id") Long id);

	/**
	 * 查询总条数
	 * 
	 * @return
	 */
	// Integer queryTotalCount();

	/**
	 * 分页查询用户
	 * 
	 * @param start
	 * @param rows
	 * @return
	 */
	// List<User> queryUserListByPage(@Param("start") Integer start, @Param("rows") Integer rows);
	
	/**
	 * 查询全部用户
	 */
	List<User> queryUserList();

	/**
	 * 新增用户
	 * @param user
	 */
	void insertUser(User user);


}
