import java.io.*;
import java.util.*;
/*
3988번 수 고르기

수열에서 가장 큰 값의 차이를 M, 가장 작은 값의 차이를 m이라고 하자.
길이가 N인 수열에서 K개를 뽑으려 할 때, M+m의 최솟값을 구해보자.

와
진짜 출근길에 잘 생각해보면서, '이거 일단 정렬은 맞는거같고..' 까지는 갔다가,
아니 이거 어떻게하지? 계속 머리 굴렸습니다.

그런데 생각해보니 m을 구하는데 최솟값 찾기 문제를 활용할 수 있겠더라고요.
즉시 활용했고, 그렇게 성공.
어쩌면 그리디느낌도 가미했는데, 이게 되네..
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
		int len = N-K;
		int[] A = new int[N+1];
		for (int i = 0; i < N; i++) {
			A[i] = sti();
		}
		A[N] = 1<<30;
		Arrays.sort(A);
		ArrayDeque<Pair> deq = new ArrayDeque<>();
		int ret = 1<<30;
		for (int i = 0; i < N; i++) {
			int m = Math.abs(A[i] - A[i+1]);
			while (!deq.isEmpty() && deq.peekLast().f >= m) {
				deq.pollLast();
			}
			while (!deq.isEmpty() && deq.peekFirst().s < i-len) {
				deq.pollFirst();
			}
			deq.add(new Pair(m, i));
			if (i >= len-1) {
				ret = Math.min(ret, Math.abs(A[i]-A[i-(len-1)])+deq.peekFirst().f);
			}
		}
		System.out.println(ret);

        bw.flush();
        bw.close();
        br.close();
    }
}

class Pair {
	int f, s;
	Pair(int f, int s) {
		this.f = f;
		this.s = s;
	}
}
