import java.io.*;
import java.util.*;
/*
31838번 아이템 2

수열이 주어진다. 아래 조건을 만족하는 부분수열들을 원하는만큼 선택한 뒤 그 합들의 합의 최대를 구해보자.
1. 첫번째 또는 마지막 원소를 포함한다.
2. 길이가 K 이상이다.
3. 모든 부분 수열은 겹치지 않는다.

후..
에디토리얼 맛없게 뜯으면서 일단.. 구현은 한 문제.
아이디어는 에디토리얼 더 뜯어봐야겠는데요..
*/
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st = new StringTokenizer("");
    
	public static int ini() throws IOException {
		while (!st.hasMoreTokens())
			st = new StringTokenizer(br.readLine());
		return Integer.parseInt(st.nextToken());
	}
	
	public static long lni() throws IOException {
		while (!st.hasMoreTokens())
			st = new StringTokenizer(br.readLine());
		return Long.parseLong(st.nextToken());
	}

    public static int[] ins(int sz) throws IOException {
        int[] ret = new int[sz];
        for (int i = 0; i < sz; i++) {
            ret[i] = ini();
        }
        return ret;
    }

    public static long[] lns(int sz) throws IOException {
        long[] ret = new long[sz];
        for (int i = 0; i < sz; i++) {
            ret[i] = lni();
        }
        return ret;
    }

    public static void printArray(int[] arr) {
        for (int i: arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static void printArray(long[] arr) {
        for (long i: arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

	public static void main(String[] args) throws IOException {
        int N = ini(), K = ini();
        long[] A = new long[N+K+1];
        long[] pf = new long[N+K+1];
        long[] dp = new long[N+K+1];
        for (int i = 1; i <= N; i++) A[i] = lni();
        for (int i = 1; i <= N+K; i++) {
            pf[i] += pf[i-1] + A[i];
        }
        long mx = 0;
        for (int i = 1; i <= N+K; i++) {
            if (i-K >= 0) mx = Math.max(mx, dp[i-K]-pf[i-K]);
            dp[i] = Math.max(dp[i-1], mx+pf[i]);
        }
        System.out.println(Arrays.stream(dp).max().getAsLong());

	    bw.flush();
        bw.close();
        br.close();
    }
}
