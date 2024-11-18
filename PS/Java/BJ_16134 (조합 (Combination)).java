import java.io.*;
import java.util.*;
/*
16134번 조합 (Combination)

N과 K가 주어진다. N개 중 K개를 선택하는 경우의 수를 구해보자.
수가 너무 클 수 있으니, 1_000_000_007로 나눈 나머지를 구해보자.

개날먹문제
이런문제 한 3번은 더 풀어본 것 같습니다.
그냥 풀기싫은날에 푸는, 그런느낌..
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

    public static String sni() throws IOException {
        while (!st.hasMoreTokens())
            st = new StringTokenizer(br.readLine());
        return st.nextToken();
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

    static long MOD = 1_000_000_007;

    public static long pow(long x, long p) {
        long ret = 1;
        while (p > 0) {
            if ((p&1) == 1) {
                ret = (ret * x) % MOD;
            }
            x = (x * x) % MOD;
            p >>= 1;
        }
        return ret;
    }

	public static void main(String[] args) throws IOException {
        int N = ini(), K = ini();
        long[] F = new long[N+1];
        F[0] = 1;
        for (long i = 1; i <= N; i++) {
            F[(int)i] = (F[(int)(i-1)]*i)%MOD;
        }
        System.out.println((F[N]*((pow(F[N-K], MOD-2)*pow(F[K], MOD-2))%MOD))%MOD);

	    bw.flush();
        bw.close();
        br.close();
    }
}
