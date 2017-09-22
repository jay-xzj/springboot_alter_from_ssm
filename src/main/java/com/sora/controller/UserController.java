package com.sora.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sora.pojo.User;
import com.sora.service.UserService;
import com.sora.vo.DataGridResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.Request;


@Controller
@RequestMapping("user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

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
		return "user";
	}//这边return的字符串必须和templates里面的ftl文件名一样才能映射到


	/**
	 * 分页查询 ： 访问user/users之后，在userList表单中再次发出user/list的请求
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="list", method=RequestMethod.POST)
	@ResponseBody
	public DataGridResult queryUserList(
			@RequestParam(value="page", defaultValue = "1")Integer page,
			@RequestParam(value="rows", defaultValue = "5")Integer rows
	){
		// 查询service，获取结果
		return this.userService.queryUserList(page,rows);
	}

	// 跳转到新增页面
	@RequestMapping("user-add")
	public String toUserAdd(){
		return "user-add";
	}

	@RequestMapping("save")
	@ResponseBody
	public Map<String,Integer> saveUser(User user){
		Map<String,Integer> map = new HashMap<>();
		try {
			Integer flag = this.userService.insertUser(user);
			//判断，插入成功返回改变记录数，不成功为0
			if (flag>0) {
				// 新增成功就返回200
				map.put("status", 200);
				return map;
			}
			map.put("status", 500);
		} catch (Exception e) {
			e.printStackTrace();
			// 新增失败就返回500
			map.put("status", 500);
		}
		return map;
	}

	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Integer> deleteUser(@RequestParam List<Long> ids){
		Map<String,Integer> map = new HashMap<>();
		try {
			int flag = this.userService.deleteUserByIds(ids);
			if (flag == 1){
				map.put("status", 200);
            }else if (flag == 2){
				map.put("status", 505);
			}else{
				map.put("status", 500);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 500);
		}
		return map;
	}

	// 跳转到修改页面
	@RequestMapping("user-modify")
	public String toUserModify(){
		return "user-modify";
	}

    //TODO: 回显修改的user的信息

    @RequestMapping("update")
	@ResponseBody
	public Map<String,Integer> updateUser(@RequestParam Long id){
        Map<String,Integer> map = new HashMap<>();
        try {
            int flag = this.userService.updateUser(id);
            if (flag == 1){
                map.put("status", 200);
            }else{
                map.put("status", 500);
                logger.info("修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
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
