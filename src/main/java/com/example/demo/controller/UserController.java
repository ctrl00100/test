package com.example.demo.controller;



import com.example.demo.entity.PageResult;
import com.example.demo.entity.Result;
import com.example.demo.entity.StatusCode;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;


@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private JwtUtil jwtUtil;

	//

//	@RequestMapping(path = "/search/page", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseEntity findTableData(@RequestBody Map<String, Object> params) {



/*	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}*/

/*
* @RequestMapping(value="/findAllByPage/{page}/{size}",method=RequestMethod.GET)
	public Result findAllByPage( @PathVariable int page, @PathVariable int size){
		Page<User> pageList = userService.findAllByPage(page, size);
		return  new Result(true,StatusCode.OK,"分页findAll查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}*/
	@RequestMapping(path = "/findAll2", method = RequestMethod.POST)
	public Result findAll2(@RequestBody Map<String, Object> params) {
		System.out.println(params);
		int page= (int) params.get("pagenum");
		int size= (int) params.get("pagesize");
		Map searchMap = new HashMap();
		searchMap.put("nickname", params.get("query"));
		searchMap.put("email", params.get("email"));
		searchMap.put("username", params.get("username"));

		Page<User> pageList = userService.findSearch(searchMap, page, size);

		return new Result(true, StatusCode.OK, "搜索ok" ,new PageResult<User>(pageList.getTotalElements(), pageList.getContent()));

	}
	//更改用户状态

	@RequestMapping(value="/user/{id}/{bo}",method=RequestMethod.PUT)
	public Result changeBo( @PathVariable String id, @PathVariable boolean bo){


		userService.updateBo(id,bo);


		return new Result(true, StatusCode.OK, "更改状态ok" );


	}

//	//登陆
//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public Result login(@RequestBody User user) {
//		User login = userService.login(user);
//		if (login != null) {
//
//
//			Map<String, Object> map = new HashMap<>();
//			map.put("nikename", login.getNickname());
//			map.put("email", login.getEmail());
//
//			return new Result(true, StatusCode.OK, login.getNickname() + "user登陆ok", map);
//		} else {
//			return new Result(false, StatusCode.ERROR, "密码错误或用户未注册");
//
//		}
//
//	}

	//登陆
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Result login(@RequestBody User user) {
		User login = userService.login(user);
		if (login != null) {

			String token = jwtUtil.createJWT(login.getId(), login.getNickname(), "user");
			//String token = jwtUtil.createJWT(login.getId(), login.getNickname(), "admin");
			Map<String, Object> map = new HashMap<>();
			map.put("token", token);
			map.put("role", "user");

			return new Result(true, StatusCode.OK, login.getNickname() + "user登陆ok", map);
		} else {
			return new Result(false, StatusCode.ERROR, "密码错误或用户未注册");

		}

	}

	//info
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public Result info(@RequestBody User user) {
		User login = userService.login(user);
		if (login != null) {


			Map<String, Object> map = new HashMap<>();
			map.put("nikename", login.getNickname());
			map.put("email", login.getEmail());

			return new Result(true, StatusCode.OK, login.getNickname() + "user登陆ok", map);
		} else {
			return new Result(false, StatusCode.ERROR, "密码错误或用户未注册");

		}

	}
	// 分页findAllByPage

	@RequestMapping(value="/findAllByPage/{page}/{size}",method=RequestMethod.GET)
	public Result findAllByPage( @PathVariable int page, @PathVariable int size){
		Page<User> pageList = userService.findAllByPage(page, size);
		return  new Result(true,StatusCode.OK,"分页findAll查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}
	//findAll
	@RequestMapping(value = "/findAll",method= RequestMethod.GET)
	public Result findAll(){

		Map<String, Object> map23 = new HashMap<>();

//		Map map = new HashMap();
//		Map map2 = new HashMap();
//		Map map3 = new HashMap();
//		map.put("id", 0);
//		map.put("nickname", "用户管理");
//		map2.put("id", 1);
//		map2.put("nickname", "用户信息");
//		map2.put("path", map);
//		JSONObject json = JSONObject.fromObject(map);
//		List list = new ArrayList();
//		list.add( map );
//		list.add( map2 );
//		list.add( map3 );

//		JSONObject jsonStatus = new JSONObject();
//		jsonStatus.put("code", 0);
//		jsonStatus.put("path", "succ");
//		// ===========================
//		JSONObject jsonData = new JSONObject();
//		jsonData.put("taskid", 5656);
//		jsonData.put("tableName", 5656);
//		// ===========================
//		JSONObject objData = new JSONObject();
//		objData.put("status", jsonStatus);
//		objData.put("data", jsonData);
//		JSONObject objData2 = new JSONObject();
//		objData2.put("aaa",objData);


		//JSONArray jsonArray2 = JSONArray.fromObject( list );
		return new Result(true, StatusCode.OK,"查询成功",userService.findAll());
	}


	//findList
	@RequestMapping(value = "/findList",method= RequestMethod.GET)
	public Result findList(){


//	    //用户管理下
//		JSONObject jsonStatus3 = new JSONObject();
//		jsonStatus3.put("id", 30);
//		jsonStatus3.put("name", "用户信息");
//		jsonStatus3.put("path", "user");
//	JSONObject jsonStatus4 = new JSONObject();
//		jsonStatus3.put("id", 31);
//		jsonStatus3.put("name", "用户信息2");
//		jsonStatus3.put("path", "user1");
//	JSONObject jsonStatus5 = new JSONObject();
//		jsonStatus3.put("id", 32);
//		jsonStatus3.put("name", "用户信息3");
//		jsonStatus3.put("path", "user3");
//
//
////		333
//		JSONObject jsonStatus2 = new JSONObject();
////		jsonStatus2.put("id2", 00);
////		jsonStatus2.put("name2", "用户信息");
////		jsonStatus2.put("path", "/user");
//		jsonStatus2.put("id1", jsonStatus3);
//		jsonStatus2.put("id2", jsonStatus4);
//		jsonStatus2.put("id3", jsonStatus5);
//
//
//		//用户权利下
//
//        JSONObject jsonStatus31 = new JSONObject();
//        jsonStatus3.put("id", 30);
//        jsonStatus3.put("name", "用户权利1");
//        jsonStatus3.put("path", "power1");
//        JSONObject jsonStatus41 = new JSONObject();
//        jsonStatus3.put("id", 31);
//        jsonStatus3.put("name", "用户权利2");
//        jsonStatus3.put("path", "power2");
//        JSONObject jsonStatus51 = new JSONObject();
//        jsonStatus3.put("id", 32);
//        jsonStatus3.put("name", "用户权利3");
//        jsonStatus3.put("path", "power2");
//
//
////		333
//        JSONObject jsonStatus221 = new JSONObject();
////		jsonStatus2.put("id2", 00);
////		jsonStatus2.put("name2", "用户信息");
////		jsonStatus2.put("path", "/user");
//        jsonStatus221.put("id1", jsonStatus31);
//        jsonStatus221.put("id2", jsonStatus41);
//        jsonStatus221.put("id3", jsonStatus51);
//
//
//
//		// ===========================2级 0.1.2.3
//
//		JSONObject jsonStatus = new JSONObject();
//		jsonStatus.put("id", 20);
//		jsonStatus.put("name", "用户管理");
//		jsonStatus.put("children", jsonStatus2);
//
//
//
//		JSONObject jsonData = new JSONObject();
//		jsonData.put("id", 10);
//		jsonData.put("name", "用户权利");
//		// ===========================1/////一级0.1.2.3
//		JSONObject objData = new JSONObject();
//		objData.put("0", jsonStatus);
//		objData.put("1", jsonData);
//        jsonStatus.put("children", jsonStatus221);
//
//
////4

//        用户
		Map map = new HashMap();
		map.put("id",20 );
		map.put("name","用户信息" );
		map.put("path","user" );


		Map map2 = new HashMap();
		map2.put("id",21 );
		map2.put("name","用户信息2" );
		map2.put("path","user2" );
//  权利
        Map mapql1 = new HashMap();
        mapql1.put("id",20 );
        mapql1.put("name","权利1" );
        mapql1.put("path","power1" );


        Map mapql2 = new HashMap();
        mapql2.put("id",21 );
        mapql2.put("name","权利2" );
        mapql2.put("path","power2" );

//3
		Map map22 = new HashMap();
		map22.put("id1",map2 );
		map22.put("id2",map );


        Map mapql = new HashMap();
        mapql.put("id1",mapql2 );
        mapql.put("id2",mapql1 );


//22
		Map map11 = new HashMap();
		map11.put("id",10 );
		map11.put("name","用户管理" );
		map11.put("children",map22 );


		Map map112 = new HashMap();
		map112.put("id",11 );
		map112.put("name","用户权利" );
        map112.put("children",mapql );


//1

		Map map223 = new HashMap();
		map223.put("id",map112);
		map223.put("id1",map11 );



		return new Result(true, StatusCode.OK,"查询列表成功",map223);

	}

	//findById
	@RequestMapping(value="/findById/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",userService.findById(id));
	}



	 // 分页+多条件查询

	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	//条件查询
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",userService.findSearch(searchMap));
    }

	//add
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public Result add(@RequestBody User user  ){
		userService.add(user);
		return new Result(true,StatusCode.OK,"增加成功");
	}

	//update没用
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id ){
		user.setId(id);

		userService.update(user);
		return new Result(true,StatusCode.OK,"修改成功");
	}

	//update用了
	@RequestMapping(value="/update",method= RequestMethod.PUT)
	public Result update(@RequestBody User user ){


		userService.updateUser(user);
		return new Result(true,StatusCode.OK,"修改成功");
	}

	//delete
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		userService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}



}
