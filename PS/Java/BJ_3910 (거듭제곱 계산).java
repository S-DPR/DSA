import java.io.*;
import java.util.*;
/*
3910번 거듭제곱 계산

어떤 수 x에 대해 다음과 같은 행위를 할 수 있다.
- x에다가 1을 더하거나 빼기
- x로 오는 경로에 있던 수를 더하거나 빼기
- x에다가 x를 더하거나 빼기
처음 수는 1이다. 이 때, 어떤 수 x가 되는데 필요한 행위의 최소횟수를 구해보자.

음..
이게 백트래킹이네..
BFS나 다익스트라도 된다는 소리 보고 어이없었는데..

일단 x가 커봐야 1000이라는 점.
이거 그러면 정말 잘쳐줘도 루프를 20번 이상 돌 필요가 없다는 점이 키포인트.
맞추고 답 보니까 14 이상의 답은 나오지 않는다고 하네요.

초기에 전처리 다 해두고 나오는 테스트케이스대로 출력해주면 됩니다.
이 때, 백트래킹으로 하는것이 키포인트.
루프함수가 너무 간단해서 보자마자 아실 것 같습니다.
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

	static int[][] V = new int[1001][1001];
	public static void loop(int x, int nxt, int t) {
		if (x < 0 || x > 1000) return;
		if (V[x][nxt] <= t) return;
		if (14 <= t) return;
		V[x][nxt] = t;
		loop(x+nxt, nxt, t+1);
		loop(x-nxt, nxt, t+1);
		loop(x+nxt, x, t+1);
		loop(x-nxt, x, t+1);

		loop(x+1, nxt, t+1);
		loop(x-1, nxt, t+1);
		loop(x+1, x, t+1);
		loop(x-1, x, t+1);
		
		loop(x+x, x, t+1);
		loop(x+x, nxt, t+1);
	}

	public static void main(String[] args) throws IOException {
		for (int i = 0; i <= 1000; i++)
			Arrays.fill(V[i], ~(1<<31));
		loop(1, 1, 0);
		int[] ret = new int[1001];
		Arrays.fill(ret, ~(1<<31));
		for (int i = 1; i <= 1000; i++)
			for (int j = 1; j <= 1000; j++)
				ret[i] = Math.min(ret[i], V[i][j]);
		int T = sti();
		for (int i = 0; i < T; i++) {
			int x = sti();
			bw.write(ret[x] + "\n");
		}

        bw.flush();
        bw.close();
        br.close();
    }
}

class Item {
	int N, cur;
	Item(int N, int cur) {
		this.N = N;
		this.cur = cur;
	}
}
