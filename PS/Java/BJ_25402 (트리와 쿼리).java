package Main;
import java.io.*;
import java.util.*;
/*
25402번 트리와 쿼리

그래프가 주어진다. 이후 Q개의 줄에 T+1개의 수가 공백으로 구분되어 주어진다.
T는 Q개의 줄의 첫 번째 숫자이다. 이후 몇 개의 서로 다른 수가 나올지에 대한 정보이다.
T개의 수로 구성된 집합을 S라고 하자. S의 수중 서로 다른 i, j를 잡았을 때 S 위에서 i, j가 연결되어있는지 체크해보자.
단, S에 있는 수 이외의 노드에는 방문할 수 없다. 이 때, 연결되어있는 S의 두 수, i < j인 i, j의 개수를 각 쿼리마다 세어보자.
단, T의 합은 100만을 넘지 않는다.

트리 = 할만함
쿼리 = 할..만한가?
트리와 쿼리 = 꺄아악!!

1. dfs로 모든 정점에 대하여 부모의 정점을 찾고 (루트노드는 1로 임의로 지정)
2. 분리집합으로 S상에서 이어져있는지 부모와의 연결 여부로 확인하고,
3. 각 집합의 크기를 K라고 하면 K*(K-1)/2의 값을 다 더하면 AC.

아이디어만 떠올리면 할만한 문제였..는데 아이디어가 어렵네..
트리의 기초에 대해 묻는 문제였습니다. P5정도면 유니온파인드랑 dfs는 기본소양이니까.
*/
public class Main {
	static Vector<Vector<Integer>> G;
	static int[] parent;
	static int[] U;
	static long[] S;
	static boolean[] visit;
	static boolean[] exist;
	public static int sti(StringTokenizer st) {
		return Integer.parseInt(st.nextToken());
	}
	public static long stl(StringTokenizer st) {
		return Long.parseLong(st.nextToken());
	}
	public static void dfs(int cur) {
		if (visit[cur]) return;
		visit[cur] = true;
		G.get(cur).forEach(i -> {
			if (!visit[i]) parent[i] = cur;
			dfs(i);
		});
	}
	public static void union(int u, int v) {
		u = find(u);
		v = find(v);
		if (u > v) {
			int tmp = u;
			u = v;
			v = tmp;
		}
		S[u] += S[v];
		U[v] = U[u];
	}
	public static int find(int x) {
		if (U[x] != x) U[x] = find(U[x]);
		return U[x];
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		int N = Integer.parseInt(br.readLine());
		G = new Vector<Vector<Integer>>();
		U = new int[N+1];
		S = new long[N+1];
		parent = new int[N+1];
		visit = new boolean[N+1];
		exist = new boolean[N+1];
		for (int i = 0; i <= N; i++) {
			G.add(new Vector<Integer>());
			U[i] = i;
			S[i] = 1;
		}
		for (int i = 1; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int u = sti(st), v = sti(st);
			G.get(u).add(v);
			G.get(v).add(u);
		}
		dfs(1);
		
		visit = new boolean[N+1];
		int Q = Integer.parseInt(br.readLine());
		for (int q = 0; q < Q; q++) {
			st = new StringTokenizer(br.readLine());
			long ret = 0;
			int cnt = sti(st);
			int[] arr = new int[cnt];
			for (int i = 0; i < cnt; i++) {
				arr[i] = sti(st);
				exist[arr[i]] = true;
			}
			for (int x: arr) {
				int p = parent[x];
				if (exist[p]) union(x, p);
			}
			for (int x: arr) {
				int s = find(x);
				if (!visit[s]) {
					ret += S[s]*(S[s]-1)/2;
					visit[s] = true;
				}
			}
			bw.write(ret + "\n");
			for (int i = 0; i < cnt; i++) {
				int item = arr[i];
				exist[item] = false;
				S[item] = 1;
				U[item] = item;
				visit[find(item)] = false;
			}
		}
		
		br.close();
		bw.flush();
		bw.close();
	}
}