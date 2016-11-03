package jmx;

import java.util.List;

public interface HelloMBean {
	public String getName();

	public void setName(String name);

	public List<Long> getList();

	public void setList(List<Long> list);

	public void addToList(long l);

	public void printHello();

	public void printHello(String whoName);

	public int sum(int a, int b);
}