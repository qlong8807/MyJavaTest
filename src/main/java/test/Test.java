package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import utils.JsonUtil;



public class Test {
	public static void main(String[] args) {
		List<User1> list = new ArrayList<User1>();
		Random r = new Random();
		for(int i=0;i<10;i++){
			User1 u = new User1();
			u.setName("name"+i);
			u.setAge(r.nextInt(10));
//			for(int j=0;j<3;j++){
//				User1 u1 = new User1();
//				u1.setName("name"+i);
//				u1.setAge(r.nextInt(10));
//				u.addChild(u1);
//			}
			list.add(u);
		}
		
		int size = list.size();
		for(int i=0;i<size;i++){
			for(int j=i+1;j<size;j++){
				User1 vo1 = list.get(i);
				User1 vo2 = list.get(j);
				if(vo1.getAge() > vo2.getAge()){
					list.set(i, vo2);
					list.set(j, vo1);
				}
			}
		}
		
		System.out.println(JsonUtil.objectToJson(list));
	}

}
class User1{
	private int age;
	private String name;
	private List<User1> child;
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<User1> getChild() {
		return child;
	}
	public void setChild(List<User1> child) {
		this.child = child;
	}
	public void addChild(User1 c) {
		if(null == this.child) this.child = new ArrayList<User1>();
		child.add(c);
	}
	@Override
	public String toString() {
		return "User [age=" + age + ", name=" + name + ", child=" + child + "]";
	}
	
}