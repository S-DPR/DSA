import java.io.*;
import java.util.*;
/*
2088번 트리의 개수

문자열 S가 주어진다.
트리를 dfs한 결과가 S가 되도록 하는 트리의 개수를 구해보자.
단, dfs를 종료하고 부모노드로 돌아올 때마다 부모노드의 문자가 다시 붙는다.

하아
내가 오늘도 구간dp에 당했구나....
진짜 처음에 보고 '이거 구간dp네 구간dp' 이래놓고 구간dp의 기초를 망각해버리고..
아 구간dp 아닌가? 하고 혼자 삽질 엄청했네..

저번 세포분열 다음으로 보는 구간dp 변형입니다.
초기 dp값이랑 반복문 dp에 조건달기..
한번 당해놓고도 왜 못하는걸까..
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
	static long mod = 1_000_000_000;
	static long[][] dp;
	static char[] s;
	static int len;

	public static long loop(int l, int r) {
		if (l > r) return 0;
		if (dp[l][r] != -1) return dp[l][r];
		dp[l][r] = 0;
		for (int i = l; i <= r; i++) {
			if (s[l] != s[i]) continue;
			dp[l][r] += (loop(l+1, i-1)*loop(i, r)) % mod;
			dp[l][r] %= mod;
		}
		return dp[l][r];
	}

	public static void main(String[] args) throws IOException {
		s = br.readLine().toCharArray();
		len = s.length;
		dp = new long[len][len];
		for (int i = 0; i < len; i++) {
			Arrays.fill(dp[i], -1);
			dp[i][i] = 1;
		}
		System.out.println(loop(0, len-1));

        bw.flush();
        bw.close();
        br.close();
    }
}
