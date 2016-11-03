package jmx;

import java.util.ArrayList;
import java.util.List;

public class Hello implements HelloMBean {
    private String name;
    private List<Long> list = new ArrayList<Long>();
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Long> getList() {
		return list;
	}
	public void setList(List<Long> list) {
		this.list = list;
	}
	public void addToList(long l){
    	System.out.println(l);
    	list.add(l);
    }
    
    
    public void printHello() {
        System.out.println("Hello World, " + name +"	"+list.size());
    }
    public void printHello(String whoName) {
        System.out.println("Hello , " + whoName);
    }
    public int sum(int a,int b){
    	return a+b;
    }
}