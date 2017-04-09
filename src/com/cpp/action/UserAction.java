package com.cpp.action;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.cpp.dao.ElectorDao;
import com.cpp.daoImol.ElectorDaoImpl;
import com.cpp.modle.Elector;


public class UserAction {
	ElectorDao ud=null;
	private String name;
	private String result;
	
	//如果是struts内置的json拦截器
	//struts默认将值栈（root）的顶端对象返回---所有的的get方法。
	//若有各别方法不想返回，只需要在该方法的上面添加一个@JSON(serialize=false)注解即可）
	//传到前台的不是string，就是个json对象（Object）
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}


	public String checkAllUser(){
		System.out.println("执行-----action");
		ud=new ElectorDaoImpl();
		List<Elector> lists=ud.checkAllElector();
		if(lists==null){
			System.out.println("lists为空");
			return "error";
		}
		
		//把lists转成json字符串
		result=JSON.toJSONString(lists);
		
		/*JSONArray jsonArray=new JSONArray();
		for(Elector user:lists){
			JSONObject jo=new JSONObject();
			jo.put("name",user.getName());
			jo.put("pass",user.getPassword());
			System.out.println(jo.get("name"));
			jsonArray.add(jo);
		}
		jsonArray.toString();*/
		return "success";
	}
}
