import java.io.*;
import java.util.*;
/*
1539번 이진 검색 트리

N개의 원소를 이진 탐색 트리에 넣으려 한다.
다 넣고 난 뒤, 모든 노드의 높이 합을 구해보자.
루트는 높이가 1이다.

자기 전에 하나 생각하면서 자볼까.. 하면서 고른 문제였는데,
그냥 20분정도 생각하다보니 '이거보다 크고 가장 작은 값이랑 작고 가장 큰 값 중 더 높은곳 + 1이네?'
그래서 C++로 한번 해보니 AC맞고.. 본계정으로 Java를 써서 풀었습니다.

내가 지금까지 본 플레5는 이렇지 않은데..
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
		int N = sti();
		long[] H = new long[N];
		long ret = 0;
		TreeSet<Integer> s = new TreeSet<>();
		for (int i = 0; i < N; i++) {
			int x = sti();
			Integer lo = s.lower(x);
			Integer hi = s.higher(x);
			long lov = 0, hiv = 0;
			if (lo != null) lov = H[lo];
			if (hi != null) hiv = H[hi];
			long v = Math.max(lov, hiv) + 1;
			H[x] = v;
			s.add(x);
		}
		System.out.println(Arrays.stream(H).sum());

        bw.flush();
        bw.close();
        br.close();
    }
}
