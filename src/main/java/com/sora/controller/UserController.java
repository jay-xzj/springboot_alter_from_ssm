package com.sora.controller;

import java.util.HashMap;
import java.util.Map;

import com.sora.pojo.User;
import com.sora.service.UserService;
import com.sora.vo.DataGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.Request;


@Controller
@RequestMapping("user")
public class UserController {

	@Value("${application.hello:Sora}")
	private String name;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/user/{name}",method = RequestMethod.GET)
	public ModelAndView HelloFtl(@PathVariable("name")String name,ModelAndView mv){
		User user = this.userService.findUserByUsername(name);
		mv.addObject("user",user);
		mv.setViewName("hello");
		return mv;
	}

	/*
	 * 跳转到用户列表页面
	 */
	@RequestMapping("users")
	public String toUserList(){
		return "users";
	}
	// 跳转到新增页面
	@RequestMapping("user-add")
	public String toUserAdd(){
		return "user-add";
	}
	
	@RequestMapping(value="list", method=RequestMethod.POST)
	@ResponseBody
	public DataGridResult queryUserList(
			@RequestParam(value="page", defaultValue = "1")Integer page,
			@RequestParam(value="rows", defaultValue = "5")Integer rows
			){
		// 查询service，获取结果
		return this.userService.queryUserList(page,rows);
	}
	
	
	
	@RequestMapping("save")
	@ResponseBody
	public Map<String,Integer> saveUser(User user){
		Map<String,Integer> map = new HashMap<>();
		try {
			this.userService.insertUser(user);
			// TODO 完善逻辑判断是否添加成功
			// 新增成功就返回200
			map.put("status", 200);
		} catch (Exception e) {
			e.printStackTrace();
			// 新增失败就返回500
			map.put("status", 500);
		}
		return map;
	}


	
	/**
	 * 报表导出
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("export/excel")
	public ModelAndView exportExcel(
			@RequestParam(value="page", defaultValue = "1")Integer page,
			@RequestParam(value="rows", defaultValue = "5")Integer rows){
		// 查询当前分页的数据
		DataGridResult result = this.userService.queryUserList(page, rows);
		// 创建模型和视图对象
		ModelAndView mv = new ModelAndView("excelView");
		// 添加模型数据
		mv.addObject("users", result.getRows());
		return mv;
	}
}
