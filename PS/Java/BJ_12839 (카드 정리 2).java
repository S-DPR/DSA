package Main;
import java.io.*;
import java.util.*;
/*
12839번 카드 정리 2

상자가 N개, 카드 종류가 K개 있다.
모든 카드 종류를 서로 다른 상자 K개의 상자에 각각 몰아넣으려 할 때,
옮겨야 하는 카드의 최소 개수를 구해보자.

진짜
진짜고통스러웠다..
최적화 한번 빡세게 안했다고 시간초과나네..

어.. 그니까
아이디어 자체는 생각보다 금방나왔어요.
그런데 (K-cnt)+x > N 조건일 때 바로 return INF 하는게 아니라
x == N일때만 return INF하니까 시간초과가 나더라고요..
저 조건 하나 추가하고 인생이 달라지는 기적을 체험하고 충격먹었습니다.
그나저나, 루비는 또 죽었어..
*/
public class Main {
	static int INF = 1<<30;
	static int N, K;
	static int[] sum;
	static int[][] arr;
	static int[][] dp;
	static HashMap<Character, Integer> direc = new HashMap<>();
	public static int sti(StringTokenizer st) {
		return Integer.parseInt(st.nextToken());
	}
	public static long stl(StringTokenizer st) {
		return Long.parseLong(st.nextToken());
	}
	public static int loop(int x, int k, int cnt) {
		if (k+1 == 1<<K) return 0;
		if ((K-cnt)+x > N) return INF;
		if (dp[x][k] != INF) return dp[x][k];
		dp[x][k] = loop(x+1, k, cnt);
		for (int i = 0; i < K; i++) {
			if ((k&(1<<i)) != 0) continue;
			dp[x][k] = Math.min(dp[x][k], loop(x+1, k|(1<<i), cnt+1)+arr[x][i]);
		}
		return dp[x][k];
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = sti(st); K= sti(st);
		arr = new int[N][K];
		sum = new int[K];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < K; j++) {
				arr[i][j] = sti(st);
				sum[j] += arr[i][j];
			}
		}
		for (int i = 0; i < N; i++)
			for (int j = 0; j < K; j++)
				arr[i][j] = sum[j] - arr[i][j];
		dp = new int[N][1<<K];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < 1<<K; j++)
				dp[i][j] = INF;
		System.out.println(loop(0, 0, 0));
		
		br.close();
		bw.flush();
		bw.close();
	}
}