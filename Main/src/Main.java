import java.io.*;
import java.math.*;
import java.util.*;

public class Main {
	public static void main(String args[]){
		Scanner cin = new Scanner(System.in);
		while (cin.hasNext()){
			int n = cin.nextInt(), m = cin.nextInt();
			if (n+m==0) break;
			if ((n-1)%(m+1)==0) System.out.println("Jiang");
			else System.out.println("Tang");
		}
	}
}
