package Main;
import java.io.*;
import java.util.*;
/*
8012번 한동이는 영업사원!

트리가 주어지고, 방문해야하는 노드가 순서대로 주어진다.
모두 방문한다 할 때, 최소거리를 출력하자.

LCA 기본문제 풀고 LCA로 뭘 할수있나 보는 문제였습니다.
그냥 뭐 거의 기본이라고들 하네요.
먼저 첫번째로 만난 활용은 트리에서 두 정점간의 최단거리 찾기.
업데이트가 없고 이런식으로 쿼리가 주어지면 LCA문제라고 합니다.

LCA구축후 노드가 두 번의 반복문으로 옮겨지는데,
첫번째로 u와 v의 높이 맞출때, 두번째로 lca 찾을떄.
첫번쨰의 경우에는 v를 옮기면서 전 depth[v]와 후 depth[v]의 값을 더해주면 되고,
두번째의 경우에는 반복문 전에 현재 높이를 저장해두고 lca 구한다음 depth[lca]의 차를 구한 뒤 그 두배를 더하면 됩니다.

문제를 잘못봐서 시간날리고, LCA 잘못이해해서 시간날렸네요..
익숙해 이제..
*/
public class Main {
	static Vector<Vector<Integer>> G;
	static Vector<int[]> sparse;
	static int[] depth;
	static boolean[] visit;
	static int height;
	public static int sti(StringTokenizer st) {
		return Integer.parseInt(st.nextToken());
	}
	public static long stl(StringTokenizer st) {
		return Long.parseLong(st.nextToken());
	}
	public static void dfs(int cur, int dep) {
		if (visit[cur]) return;
		visit[cur] = true;
		depth[cur] = dep;
		G.get(cur).forEach(i -> {
			if (!visit[i]) sparse.get(i)[0] = cur;
			dfs(i, dep+1);
		});
	}
	public static long lca(int u, int v) {
		long ret = 0;
		if (depth[u] > depth[v]) {
			int tmp = u;
			u = v;
			v = tmp;
		}
		for (int i = height-1; i >= 0 && depth[u] != depth[v]; i--) {
			if (depth[u] <= depth[sparse.get(v)[i]]) {
				ret += depth[v];
				v = sparse.get(v)[i];
				ret -= depth[v];
			}
		}
		int curD = depth[u];
		int lcmN = u;
		for (int i = height-1; i >= 0 && u != v; i--) {
			if (sparse.get(u)[i] != sparse.get(v)[i]) {
				u = sparse.get(u)[i];
				v = sparse.get(v)[i];
			}
			lcmN = sparse.get(u)[i];
		}
		ret += (curD-depth[lcmN])*2;
		return ret;
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		int N = Integer.parseInt(br.readLine());
		G = new Vector<Vector<Integer>>();
		sparse = new Vector<int[]>();
		depth = new int[N+1];
		visit = new boolean[N+1];
		height = (int)Math.ceil(Math.log(N)/Math.log(2))+1;
		for (int i = 0; i <= N; i++) {
			G.add(new Vector<Integer>());
			sparse.add(new int[height]);
		}
		for (int i = 1; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int u = sti(st), v = sti(st);
			G.get(u).add(v);
			G.get(v).add(u);
		}
		dfs(1, 0);
		for (int i = 1; i < height; i++)
			for (int j = 1; j <= N; j++)
				sparse.get(j)[i] = sparse.get(sparse.get(j)[i-1])[i-1];
		int cur = 1;
		long dist = 0;
		int Q = Integer.parseInt(br.readLine());
		for (int i = 0; i < Q; i++) {
			int nxt = Integer.parseInt(br.readLine());
			dist += lca(cur, nxt);
			cur = nxt;
		}
		System.out.println(dist);
		
		br.close();
		bw.flush();
		bw.close();
	}
}