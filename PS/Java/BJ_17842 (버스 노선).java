import java.io.*;
import java.util.*;
/*
17842번 버스 노선

트리의 간선을 따라 선을 이어 모든 노드에 선을 그어보려 한다.
선을 적어도 몇 번 그어야 할까?
단, 선을 한 번 그을 때 그 선에서 이미 사용한 간선을 다시 사용할 수 없다.

이야
그냥 '에이 모르겠다 맞겠지' 하고 제출했는데 진짜 맞아버렸네

그냥 리프노드 수 세서 +1하고 2로 나누기.
증명은 모르겠고 '그냥 그럴거같은데' 생각에 제출했더니 진짜 맞은..
맞고 생각해보니 너무 당연한이야기같기도하고..
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

	static int N, ret = 0;
	static boolean[] V;
	static List<Integer> G[];

	public static void main(String[] args) throws IOException {
		N = sti();
		G = new List[N];
		for (int i = 0 ; i < N; i++)
			G[i] = new ArrayList();
		for (int i = 1; i < N; i++) {
			int u = sti(), v = sti();
			G[u].add(v);
			G[v].add(u);
		}
		for (int i = 0; i < N; i++) {
			if (G[i].size() != 1) continue;
			ret++;
		}
		System.out.println((ret+1)/2);

        bw.flush();
        bw.close();
        br.close();
    }
}
