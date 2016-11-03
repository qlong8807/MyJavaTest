package test;

import java.util.ArrayList;
import java.util.List;

public class TestList {
	public static void main(String[] args) {
		List<User> list = new ArrayList<User>();
		User u1 = new User();
		u1.id = 1;u1.times = 1;
		list.add(u1);
		User u2 = new User();
		u2.id = 1;u2.times = 1;
		list.add(u2);
		User u3 = new User();
		u3.id = 2;u3.times = 1;
		list.add(u3);
		User u4 = new User();
		u4.id = 2;u4.times = 1;
		list.add(u4);
		User u5 = new User();
		u5.id = 2;u5.times = 1;
		list.add(u5);
		System.out.println("aalist:"+list.toString());
		List<User> l2 = new ArrayList<User>();
		for(User u : list){
			boolean has = false;
			for(User us : l2){
				if(u.id == us.id){
					has = true;
					us.times = u1.times + u2.times;
				}
			}
			if(!has){
				l2.add(u);
			}
		}
		System.out.println("list:"+list.toString());
		System.out.println("l2:"+l2.toString());
	}
	
}
class User{
	public int id;
	public int times;
	
	@Override
	public String toString(){
		return "id:"+id+"-times:"+times;
	}
}