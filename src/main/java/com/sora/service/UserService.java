package com.sora.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sora.mapper.UserMapper;
import com.sora.pojo.User;
import com.sora.vo.DataGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	public DataGridResult queryUserList(Integer page, Integer rows) {
		// 准备总条数信息
		// Integer total = userMapper.queryTotalCount();
		// 准备分页的数据信息
		// List<User> list = userMapper.queryUserListByPage((page - 1) * rows,rows);
		
		// 开启分页,后面紧跟的Sql就会被自动分页
		PageHelper.startPage(page, rows);
		List<User> list = this.userMapper.queryUserList();
		// 封装分页对象
		PageInfo<User> pageInfo = new PageInfo<>(list);
		// 返回结果
		return new DataGridResult(pageInfo.getTotal(), pageInfo.getList());
	}

	public Integer insertUser(User user) {
		Integer status = this.userMapper.insertUser(user);
		System.out.println(status);
		return status;
	}

	public User findUserByUsername(String name) {
		User user = userMapper.findUserByUsername(name);
		return user;
	}

	public int deleteUserByIds(List<Long> ids) {
		int size = ids.size();
		int count = 0;
		//1:完全成功;2:部分成功;0:完全不成功
		for (Long id :
				ids) {
			this.userMapper.deleteUserById(id);
			count++;
		}
		if (count != 0){
			return size==count?1:2;
		}else{
			return 0;
		}

	}
}
