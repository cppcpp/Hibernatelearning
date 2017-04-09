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
	
	//�����struts���õ�json������
	//strutsĬ�Ͻ�ֵջ��root���Ķ��˶��󷵻�---���еĵ�get������
	//���и��𷽷����뷵�أ�ֻ��Ҫ�ڸ÷������������һ��@JSON(serialize=false)ע�⼴�ɣ�
	//����ǰ̨�Ĳ���string�����Ǹ�json����Object��
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
		System.out.println("ִ��-----action");
		ud=new ElectorDaoImpl();
		List<Elector> lists=ud.checkAllElector();
		if(lists==null){
			System.out.println("listsΪ��");
			return "error";
		}
		
		//��listsת��json�ַ���
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
