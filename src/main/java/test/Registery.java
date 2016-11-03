package test;

import java.util.prefs.Preferences;

/**
 * 注册表
 * @author zhangyilong
 * @Date 2015-1-14-下午4:57:29
 */
public class Registery {

	String[] keys = {"version", "initial", "creator"};
	String[] values = {"1.5", "ini123.mp3", "caokai1818@sina.com"};
	//把相应的值储存到变量中去
	public void writeValue() {
		// HKEY_LOCAL_MACHINE\Software\JavaSoft\prefs下写入注册表值.
		Preferences pre = Preferences.systemRoot().node("/javaplayer");
		for (int i = 0; i < keys.length; i++) {
			pre.put(keys[i], values[i]);
		}
		System.out.println("ok");
		String v = pre.get("version2", "haha");
		System.out.println(v);
	}
	public static void main(String[] args) {
		Registery reg = new Registery();
		reg.writeValue();
	}

}
