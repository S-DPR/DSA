import java.io.*;
import java.util.*;
/* 
23578번 비 오는 날

가중치가 있는 노드가 있다.
각 노드의 최종 가중치는 (원래가중치)*(연결된간선의 수)^2일 때,
모든 노드를 잇는 최소 가중치를 구해보자.

어..어어...
새로운걸 배운 문제.
그래프의 차수를 써본적이 한번도 없던거같은데, 처음 써봤습니다.
degree sequence라고 하네요.
관련문제도 골드2로 하나 얻어놨으니 그거 보고 풀어야할것같아요.
어렵다..
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
		long[] A = new long[N];
		long[] degree = new long[N];
		PriorityQueue<Item> pq = new PriorityQueue<>((i, j) -> {
			long left = i.u*i.w;
			long right = j.u*j.w;
			return left < right ? -1 : 1;
		});
		for (int i = 0; i < N; i++) {
			long x = stl();
			A[i] = x;
			degree[i] = 1;
			pq.add(new Item(i, x, 3));
		}
		long ret = 0;
		for (int i = 1; i < N-1; i++) {
			Item item = pq.poll();
			degree[item.n]++;
			pq.add(item.update());
		}
		for (int i = 0; i < N; i++)
			ret += A[i]*degree[i]*degree[i];
		System.out.println(N == 1 ? 0 : ret);

        bw.flush();
        bw.close();
        br.close();
    }
}

class Item {
	int n;
	long u, w;
	Item(int n, long u, long w) {
		this.n = n;
		this.u = u;
		this.w = w;
	}
	Item update() {
		this.w += 2;
		return this;
	}
}
