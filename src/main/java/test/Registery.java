package test;

import java.util.prefs.Preferences;

/**
 * ע���
 * @author zhangyilong
 * @Date 2015-1-14-����4:57:29
 */
public class Registery {

	String[] keys = {"version", "initial", "creator"};
	String[] values = {"1.5", "ini123.mp3", "caokai1818@sina.com"};
	//����Ӧ��ֵ���浽������ȥ
	public void writeValue() {
		// HKEY_LOCAL_MACHINE\Software\JavaSoft\prefs��д��ע���ֵ.
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
