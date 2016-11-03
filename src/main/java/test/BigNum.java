package test;

import java.util.Scanner;

/*
 * �������˷�
 */
public class BigNum {

	static int N=100;
	static int a[]=new int[N];
	static int b[]=new int[N];
	static int c[]=new int[2*N];
	static String s1=new String(); 
	static String s2=new String(); 
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BigNum demo=new BigNum();
		demo.Input();
		demo.Multiply(a, b, c);
		demo.Output();
	}
	
	private void Output() {
		System.out.println("result=");
		int flag=2*N-1;
		while(c[flag]==0) {
			if(flag==0) {
				System.out.println("0");
				return ;
			}
			flag--;
		}
		for(int i=flag;i>=0;i--) {
			System.out.print(c[i]);
		}
		System.out.println("");
	}
	
	private void Multiply(int a[],int b[],int c[]) {
		//������
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				c[i+j]+=a[i]*b[j];
			}
		}
		//��λ����λ
		for(int i=0;i<2*N-1;i++) {
			c[i+1]+=c[i]/10;
			c[i]=c[i]%10;
		}
	}
	
	private void Input() {
		Scanner scanner=new Scanner(System.in);
		System.out.println("input two big data:");
		s1=scanner.nextLine();
		s2=scanner.nextLine();
		GetDigit(s1, a);
		GetDigit(s2, b);
	}
	
	private static void GetDigit(String s,int a[]) {
		int len=s.length();
		for(int i=0;i<len;i++) {
			a[len-1-i]=s.charAt(i)-'0';
		}
	}
}