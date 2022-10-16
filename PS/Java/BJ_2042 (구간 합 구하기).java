package test;

import java.io.*;
import java.util.*;

/*
2042번 구간 합 구하기

개인적으로 제가 정말 좋아하는 문제입니다. 그래서 여러 언어로, 많은 방법으로 푼 문제입니다.
제가 좋아하는 세그먼트 트리의 기초중의 기초 문제거든요.
그래서 한번 내일 자바 쪽지시험보는데 자바 공부 좀 해볼겸 자바로 풀어봤습니다.
재귀가 아닌, Bottom-Up Segment-Tree입니다.
*/
public class Main {
	public static class Segment{ // 세그트리 클래스를 만들어봤습니다!
		long[] seg;
		int arrSize;
		Segment(long[] arr){
			arrSize = arr.length;
			seg = new long[arrSize*2];
			for (int i = 0; i < arrSize; i++)
				seg[i+arrSize] = arr[i];
			for (int i = arrSize-1; i >= 1; i--)
				seg[i] = seg[i<<1] + seg[i<<1|1];
		}
		long query(int l, int r){
			long res = 0;
			for (l += arrSize-1, r += arrSize-1; l <= r; l >>= 1, r >>= 1) {
				if ((l & 1) == 1) res += seg[l++];
				if ((r & 1) == 0) res += seg[r--];
			}
			return res;
		}
		void update(int idx, long val) {
			seg[idx+=arrSize-1] = val;
			while (idx > 1) {
				seg[idx>>1] = seg[idx] + seg[idx^1];
				idx >>= 1;
			}
		}
	}
	
	public static long bp(StringTokenizer st) { // int(input())이 좀 많이 길어서..
		return Long.parseLong(st.nextToken());
	}
	
	public static void main(String[] args) throws IOException{
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer buf = new StringTokenizer(input.readLine());
		long n = bp(buf), k = bp(buf), m = bp(buf);
		long []arr = new long[(int) n];
		for (int i = 0; i < n; i++) arr[i] = Long.parseLong(input.readLine());
		long Q = k+m;
		Segment t = new Segment(arr);
		while (Q-- > 0) {
			buf = new StringTokenizer(input.readLine());
			long x = bp(buf), y = bp(buf), z = bp(buf);
			if (x == 2) System.out.println(t.query((int)y, (int)z));
			if (x == 1) t.update((int)y, z);
		}
		
		input.close();
	}
}
