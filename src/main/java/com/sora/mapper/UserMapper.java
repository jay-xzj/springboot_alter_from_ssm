package com.sora.mapper;



import com.sora.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
@Mapper
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
//	 Integer queryTotalCount();

	/**
	 * 分页查询用户
	 * 
	 * @param start
	 * @param rows
	 * @return
	 */
//	 List<User> queryUserListByPage(@Param("start") Integer start, @Param("rows") Integer rows);
	
	/**
	 * 查询全部用户
	 */
//	@Select("SELECT * FROM  tb_user;")
	List<User> queryUserList();

	/**
	 * 新增用户
	 * @param user
	 */
//	@Insert("INSERT INTO tb_user VALUES (null,#{userName},#{password},#{name},#{age},#{sex},#{birthday},now(),now());")
	Integer insertUser(User user);


	/**
	 * 查询单个用户
	 * @param name
	 * @return
	 */
//	@Select("SELECT * FROM  tb_user WHERE name = #{name};")
	User findUserByUsername(String name);

	Integer deleteUserById(Long id);

    Integer updateUserById(Long id);
}
