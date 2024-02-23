import java.io.*;
import java.util.*;
/*
10888번 두 섬간의 이동

일렬로 늘어선 섬이 있다.
i번째 섬에 대해 i+1번째 섬과 다리를 연결할건데, 할 때마다 아래 수를 출력해보자.
1. i < j인 (i, j)에 대해 서로 이동할 수 있는 두 섬 쌍의 개수
2. i < j인 (i, j)에 대해 서로 이동할 때 건너는 다리의 총 합의 개수

ㅋㅋ 두개에 걸렸다
1. 연결해서 변화가 생긴 섬에 대해서만 하는줄알았는데 짜잔 항상 전체를 보는거였고
2. int 오버플로 안봐서 멸망 ㅋㅋ

진짜.. 한번에맞출수있는문제였는데.. 바보처럼 틀렸다..
그냥 보자마자 유니온파인드구나.. 했는데..
거기에 수학이 곁들어졌어..

그나저나 메모리제한 16MB는 뫠걸었는지 모르겠는데..
메모리초과나서 중간중간에 flush해줬습니다.
*/
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st = new StringTokenizer("");
    
	public static int ini() throws IOException {
		if (!st.hasMoreTokens())
			st = new StringTokenizer(br.readLine());
		return Integer.parseInt(st.nextToken());
	}
	
	public static long lni() throws IOException {
		if (!st.hasMoreTokens())
			st = new StringTokenizer(br.readLine());
		return Long.parseLong(st.nextToken());
	}

	static int[] U;
	static long[] S;
	static long union(int i, int j) {
		int ip = find(i), jp = find(j);
		S[ip] += S[jp];
		U[jp] = U[ip];
		return S[ip];
	}

	static int find(int i) {
		if (U[i] != i) U[i] = find(U[i]);
		return U[i];
	}

	public static void main(String[] args) throws IOException {
		int N = ini();
		U = new int[N];
		S = new long[N];
		long[] sm = new long[N+1];
		Arrays.fill(S, 1);
		for (int i = 0; i < N; i++) U[i] = i;
		for (long i = 1; i <= N; i++) sm[(int)i] = i*i;
		for (int i = 2; i <= N; i++) sm[i] += sm[i-2];
		long l = 0, r = 0;
		for (int i = 1; i < N; i++) {
			int x = ini()-1;
			long left = S[find(x)];
			long right = S[find(x+1)];
			long sz = union(x, x+1);
			l -= left*(left-1)/2 + right*(right-1)/2;
			r -= sm[(int)left-1] + sm[(int)right-1];
			l += sz*(sz-1)/2;
			r += sm[(int)sz-1];
			bw.write(l + " " + r + "\n");
			if (i%1000 == 0) bw.flush();
		}

        bw.flush();
        bw.close();
        br.close();
    }
}
