import java.io.*;
import java.util.*;
/*
1202번 보석 도둑

무게가 M, 가치가 V인 보석이 N개 있다.
무게를 W까지 버티는 바구니가 K개 있다.
한 바구니에 최대 한 개의 보석만 담을 수 있을 때, 최대가치를 구해보자.

비상식량
인데 자바를 잘 몰라서 당해버린..

Long.compare가 있는지는 처음알았고,
일관성 없는 비교인자를 던져주면 Illegal Argument Exception 주면 문제가 되는것도 처음알았고..
아..

그래도 쉬운문제입니다. 정렬하고 우선순위큐에 넣기만 하면 되는지라..
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
		int N = sti(), K = sti();
		Pair[] items = new Pair[N];
		long[] pocket = new long[K];
		for (int i = 0; i < N; i++) {
			items[i] = new Pair(stl(), stl());
		}
		for (int i = 0; i < K; i++) {
			pocket[i] = stl();
		}
		Arrays.sort(pocket);
		Arrays.sort(items, (i, j) -> Long.compare(i.f, j.f));
		PriorityQueue<Long> pq = new PriorityQueue<>();
		int idx = 0;
		long ret = 0;
		for (long x: pocket) {
			while (idx < N && items[idx].f <= x)
				pq.add(-items[idx++].s);
			if (pq.isEmpty()) continue;
			ret += -pq.poll();
		}
		System.out.println(ret);

        bw.flush();
        bw.close();
        br.close();
    }
}

class Pair {
	long f, s;
	Pair(long f, long s) {
		this.f = f;
		this.s = s;
	}
}
