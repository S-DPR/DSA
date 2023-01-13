package Main;
import java.io.*;
import java.util.*;
/*

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
