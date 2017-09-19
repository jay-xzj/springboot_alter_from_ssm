package com.sora.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sora.mapper.UserMapper;
import com.sora.pojo.User;
import com.sora.vo.DataGridResult;
import org.springframework.beans.factory.annotation.Autowired;
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

	public void insertUser(User user) {
		this.userMapper.insertUser(user);
	}

}
