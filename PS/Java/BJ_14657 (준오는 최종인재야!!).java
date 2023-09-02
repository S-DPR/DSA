import java.io.*;
import java.util.*;
/*
14657번 준오는 최종인재야!!

트리가 있다.
한 노드에서 시작해 이전 노드로 돌아가지 않고 최대한 많은 노드를 지나치려 한다.
이 때, 지나온 가중치가 최소로 되게 하려 한다.
T가 주어질 때, (W+T-1)//T를 구해보자.

아..
계속 트리지름문제를 트리dp로 생각해서 망하네..

북마크 골드2 처리하려고 골랐는데, 망했습니다.
처음엔 그냥 트리지름인줄알았는데, 하다보니 또 트리지름을 트리dp로 풀 때 노가다를 하려고 하더라고요..
그래서 이거 대체 어떻게 깔끔하게 풀어야하나 해서 답지보고 풀었습니다.
트리지름 활용문제였을줄이야..
*/
class Edge {
	int d, w;
	Edge(int d, int w) {
		this.d = d;
		this.w = w;
	}
}

class Triple {
	int f, s, t;
	Triple(int f, int s, int t) {
		this.f = f;
		this.s = s;
		this.t = t;
	}
}

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
	
	static ArrayList<Edge>[] G;
	static boolean[] V;
	static Edge ret;
	
	// 첫번째 : 노드, 두번쨰: 몇개 거쳤는가, 세번쨰: 거리
	public static Triple dfs(Triple item) {
		Triple ret = new Triple(item.f, item.s, item.t);
		V[item.f] = true;
		for (Edge i: G[item.f]) {
			if (V[i.d]) continue;
			Triple nxt = dfs(new Triple(i.d, item.s+1, item.t+i.w));
			if (ret.s <= nxt.s) {
				if (ret.s < nxt.s)
					ret = nxt;
				else if (ret.t > nxt.t)
					ret = nxt;
			}
		}
		return ret;
	}
	public static void main(String[] args) throws IOException {
		int N = sti(), T = sti();
		G = new ArrayList[N+1];
		V = new boolean[N+1];
		for (int i = 0; i <= N; i++)
			G[i] = new ArrayList<Edge>();
		for (int i = 1; i < N; i++) {
			int u = sti(), v = sti(), w = sti();
			G[u].add(new Edge(v, w));
			G[v].add(new Edge(u, w));
		}
		Triple item = dfs(new Triple(1, 1, 0));
		V = new boolean[N+1];
		Triple ret = dfs(new Triple(item.f, 1, 0));
		System.out.println((ret.t+T-1)/T);
		
        bw.flush();
        bw.close();
        br.close();
    }
}
