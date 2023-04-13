package Main;
import java.io.*;
import java.util.*;
/*
17831번 대기업 승범이네

각 노드에 가중치가 있는 트리가 있다.
하나의 간선으로 이어져있는 두 노드에 대해 두 값을 곱한 값을 '노드의 시너지'라고 한다.
하나의 노드는 최대 하나의 노드와 시너지를 일으킬 수 있다. 이 '노드의 시너지'의 합의 최대를 구해보자.

또 나만 바보야.. 난이도기여 보니까 사람들은 "딱봐도 트리dp쓰라고 되어있는 문제네요 깔깔~" 이러고있어..
혼자 그리디로 빠져서 1% 틀렸습니다 세례 맞고 태그 보고 풀었습니다.
제생각엔 그냥 난이도 좀 있어보이는 트리는 다 dp 고려해보고 다음으로 넘어가야할거같아요.

dp를 [N+1][2]로 만들어줍니다. dp[x][0]은 '해당 노드가 페어가 되어있지 않을 때 최댓값', dp[x][1]은 페어가 되었을 때 최댓값입니다.
어떤 노드 x에 대해, dp[x][0]는 그냥 자식의 모든 노드를 k라고 할 때 dp[k][0], dp[k][1]중 최댓값을 다 더해주어 처리합니다.
dp[x][1]은 조금 생각을 해야하는데, dp[k][1]이 dp[k][0]보다 더 크다면 dp[x][0]에서 dp[k][1]을 빼고 dp[k][0]을 더하고 추가되는 시너지를 더해주어야합니다.
이유는 간단합니다. dp[k][1]은 현재 노드와 페어가 못되는 상태거든요. 그래서 그거 빼고 그게 페어가 아닌 상황을 더하고 시너지를 더하는겁니다.
dp[x][0]가 더 크면 그냥 시너지만 더하면됩니다.

참.. dp의 세계는 넓고도 깊다..
*/
public class Main {
	static Vector<Vector<Integer>> G = new Vector<Vector<Integer>>();
	static Vector<Integer> useful = new Vector<Integer>();
	static long[][] dp;
	static long result = 0;
	public static void dfs(int x) {
		int all = 0;
		for (int i : G.get(x)) {
			dfs(i);
			dp[x][0] += Math.max(dp[i][1], dp[i][0]);
			all += Math.max(dp[i][0], dp[i][1]);
		}
		for (int i : G.get(x)) {
			int extra = useful.get(i)*useful.get(x);
			if (dp[i][0] < dp[i][1])
				dp[x][1] = Math.max(dp[x][1], all - dp[i][1] + dp[i][0] + extra);
			else
				dp[x][1] = Math.max(dp[x][1], all + extra);
		}
	}
	public static int sti(StringTokenizer st) {
		return Integer.parseInt(st.nextToken());
	}
	public static long stl(StringTokenizer st) {
		return Long.parseLong(st.nextToken());
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());
		for (int i = 0; i <= N; i++)
			G.add(new Vector<Integer>());
		G.get(1).add(0);
		useful.add(0);
		dp = new long[N+1][2];
		
		st = new StringTokenizer(br.readLine());
		for (int i = 2; i <= N; i++)
			G.get(sti(st)).add(i);
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= N; i++)
			useful.add(sti(st));
		for (int i = 1; i <= N; i++)
			G.get(i).sort((n, m) -> { return useful.get(m) - useful.get(n); });
		
		dfs(1);
		System.out.println(Math.max(dp[1][0], dp[1][1]));
		
		br.close();
		bw.flush();
		bw.close();
	}
}