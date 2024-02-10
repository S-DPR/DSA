import java.io.*;
import java.util.*;
/*
28129번 2022 APC가 어려웠다고?

i번째 문제는 u~v중 하나의 난이도를 가질 수 있다.
이전 문제와 난이도의 차이가 K 이하여야 할 때,
문제를 낼 수 있는 경우의 수를 구해보자.

간단한 dp인거 바로 알았고 실제로 구현도 금방 했는데 맞왜틀만 엄청 당한문제..
그냥.. 풀긴했는데 기분나쁘네..
*/
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st = new StringTokenizer("");
    
	public static int sti() throws IOException {
		if (!st.hasMoreTokens())
			st = new StringTokenizer(br.readLine());
		return Integer.parseInt(st.nextToken());
	}
	
	public static long stl() throws IOException {
		if (!st.hasMoreTokens())
			st = new StringTokenizer(br.readLine());
		return Long.parseLong(st.nextToken());
	}

	public static void main(String[] args) throws IOException {
		long MOD = 1_000_000_007;
		int MAX = 3000;
		int N = sti(), K = sti();
		long[] dp = new long[MAX+1];
		int u = sti(), v = sti();
		for (int i = u; i <= v; i++) {
			dp[i] = 1;
		}
		for (int i = 1; i < N; i++) {
			long[] nxt = new long[MAX+1];
			u = sti();
			v = sti();
			for (int j = 1; j <= MAX; j++) {
				dp[j] += dp[j-1];
			}
			for (int j = u; j <= v; j++) {
				nxt[j] = dp[Math.min(MAX, j+K)] - dp[Math.max(0, j-K-1)];
				nxt[j] %= MOD;
			}
			dp = nxt;
		}
		long ret = 0;
		for (int i = u; i <= v; i++) {
			ret = (dp[i] + ret) % MOD;
		}
		System.out.println(ret);

        bw.flush();
        bw.close();
        br.close();
    }
}