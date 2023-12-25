import java.io.*;
import java.util.*;
/*
1184번 귀농

이차원 수열 A가 주어진다.
(x1, y1)부터 (x2, y2)까지 더한 값과 (x3, y3)부터 (x4, y4)까지 더한 값이 같아지게 하는 (x1, x2, x3, x4, y1, y2, y3, y4)의 개수를 구해보자.
단, 각각 사각형을 그렸을 때 정확히 한 점에서 만나야 한다.

N이 50.. 이건 브루트포스 각이네요! 하고 뇌 빼고 O(N^6)짠 다음 '이왜플' 이러면서 제출했는데,
50^6이면 당연히 시간초과..
이후 몇시간 더 생각해내서 해시에 잘 때려박고 해보자.. 해서 풀었습니다.

어떻게든 O(N^4)으로 압축시키자!! 하는 이야기.
좌표 두개를 찍어서 모든 값을 pf식으로 전처리해두고,
다시 두개 찍고 잘 처리해주면 됩니다.
후.. 플레엔 플레의 이유가 있다..
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
		int[][] A = new int[N+2][N+2];
		for (int i = 1; i <= N; i++)
			for (int j = 1; j <= N; j++)
				A[i][j] = sti();
		for (int i = 1; i <= N; i++)
			for (int j = 1; j <= N; j++)
				A[i][j] = A[i][j]+A[i-1][j]+A[i][j-1]-A[i-1][j-1];
		HashMap<Integer, Integer>[][] pfa = new HashMap[N+2][N+2];
		HashMap<Integer, Integer>[][] pfc = new HashMap[N+2][N+2];
		for (int i = 0; i <= N+1; i++) {
			for (int j = 0; j <= N+1; j++) {
				pfa[i][j] = new HashMap<Integer, Integer>();
				pfc[i][j] = new HashMap<Integer, Integer>();
			}
		}
		for (int left = 1; left <= N; left++) {
			for (int up = 1; up <= N; up++) {
				for (int right = left; right <= N; right++) {
					for (int down = up; down <= N; down++) {
						int f = A[down][right]-A[up-1][right]-A[down][left-1]+A[up-1][left-1];
						pfa[right][down].put(f, pfa[right][down].getOrDefault(f, 0)+1);
						pfc[left][down].put(f, pfc[left][down].getOrDefault(f, 0)+1);
					}
				}
			}
		}
		int cnt = 0;
		for (int left = 1; left <= N; left++) {
			for (int up = 1; up <= N; up++) {
				for (int right = left; right <= N; right++) {
					for (int down = up; down <= N; down++) {
						int f = A[down][right]-A[up-1][right]-A[down][left-1]+A[up-1][left-1];
						cnt += pfa[left-1][up-1].getOrDefault(f, 0);
						cnt += pfc[right+1][up-1].getOrDefault(f, 0);
					}
				}
			}
		}
		System.out.println(cnt);

        bw.flush();
        bw.close();
        br.close();
    }
}
