package Main;
import java.io.*;
import java.util.*;
/*
14621번 나만 안되는 연애

학교의 개수 N과 간선의 개수 M이 주어진다.
이 다음줄에는 각 학교가 남초인지 여초인지 주어진다.
여초 대학과 남초 대학만을 잇는 간선을 선택하여 최소스패닝트리의 가중치를 구해보자.
단, 모든 학교를 이을 수 없다면 -1을 출력하자.

놀라울정도로 최소스패닝트리 쓰라고 협박중인 문제
그냥 간선을 걸러받고 최소스패닝트리 만들면 됩니다.
*/
class Node {
	public int src, dst, cost;
	Node(int src, int dst, int cost) {
		this.src = src;
		this.dst = dst;
		this.cost = cost;
	}
}

public class Main {
	static int sti(StringTokenizer st) {
		return Integer.parseInt(st.nextToken());
	}
	
	static void union(int[] U, int x, int y) {
		U[find(U, x)] = U[find(U, y)];
	}
	
	static int find(int[] U, int x) {
		if (U[x] != x) U[x] = find(U, U[x]);
		return U[x];
	}
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        st = new StringTokenizer(br.readLine());
        int n = sti(st), m = sti(st);
        st = new StringTokenizer(br.readLine());
        int[] S = new int[n+1];
        for (int i = 1; i <= n; i++)
        	S[i] = st.nextToken().equals("M") ? 1 : 0;
        
        Vector<Node> G = new Vector<Node>();
        for (int i = 0; i < m; i++) {
        	st = new StringTokenizer(br.readLine());
        	int u = sti(st), v = sti(st), c = sti(st);
        	if (S[u] == S[v]) continue;
        	G.add(new Node(u, v, c));
        }
        Collections.sort(G, (i, j) -> i.cost-j.cost);
        int[] U = new int[n+1];
        for (int i = 0; i <= n; i++)
        	U[i] = i;
        int result = 0;
        for (Node x: G) {
        	if (find(U, x.src) == find(U, x.dst)) continue;
        	union(U, x.src, x.dst);
        	result += x.cost;
        }
        int is_union = find(U, 1);
        for (int i = 1; i <= n; i++) {
        	if (is_union != find(U, i)) {
        		System.out.println(-1);
        		return;
        	}
        }
        System.out.println(result);
    }
}
