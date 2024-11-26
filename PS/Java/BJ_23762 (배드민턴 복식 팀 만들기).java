import java.io.*;
import java.util.*;
/*
23762번 배드민턴 복식 팀 만들기

N명의 사람이 있고, 이들을 4명씩 팀으로 묶으려 한다.
(실력 최댓값)-(실력 최솟값)을 그 팀의 실력차라고 하자.
이 때, 팀의 실력차가 최소로 되도록 할 때, 그 값을 구해보자.
N이 4의 배수가 아닌경우, 빠져야하는 사람도 구해보자.

그리디? dp? 계속 생각하다가 그냥 dp로 쫙
탑다운으로 해보려다가 한계가 느껴져서 그냥 바텀업 짰습니다.
dp[i][j] = i번째 사람까지 봤을 때 j명 스킵함
한시간정도 걸렸나? 아쉽네요..
*/
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st = new StringTokenizer("");
    
	public static int ini() throws IOException {
		while (!st.hasMoreTokens())
			st = new StringTokenizer(br.readLine());
		return Integer.parseInt(st.nextToken());
	}
	
	public static long lni() throws IOException {
		while (!st.hasMoreTokens())
			st = new StringTokenizer(br.readLine());
		return Long.parseLong(st.nextToken());
	}

    public static String sni() throws IOException {
        while (!st.hasMoreTokens())
            st = new StringTokenizer(br.readLine());
        return st.nextToken();
    }

    public static int[] ins(int sz) throws IOException {
        int[] ret = new int[sz];
        for (int i = 0; i < sz; i++) {
            ret[i] = ini();
        }
        return ret;
    }

    public static long[] lns(int sz) throws IOException {
        long[] ret = new long[sz];
        for (int i = 0; i < sz; i++) {
            ret[i] = lni();
        }
        return ret;
    }

    static int N;
    static long[][] A;
    static long[][] dp;
    static List<Long>[][] skips;

	public static void main(String[] args) throws IOException {
        N = ini();
        A = new long[N][2];
        dp = new long[N+1][5];
        skips = new List[N+1][5];
        for (int i = 0; i < N; i++) {
            A[i] = new long[] { lni(), i };
        }
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j < 5; j++) {
                skips[i][j] = new ArrayList<>();
                dp[i][j] = 1L<<60;
            }
        }
        dp[0][0] = 0;
        Arrays.sort(A, (i, j) -> {
            return Long.compare(i[0], j[0]);
        });
        for (int idx = 0; idx < N; idx++) {
            for (int skip = 0; skip <= N%4; skip++) {
                if (dp[idx][skip] == 1L<<60) continue;
                if (dp[idx][skip] < dp[idx+1][skip+1]) {
                    dp[idx+1][skip+1] = dp[idx][skip];
                    skips[idx+1][skip+1] = new ArrayList<>(skips[idx][skip]);
                    skips[idx+1][skip+1].add(A[idx][1]);
                }
                for (int curSkip = 0; curSkip <= (N%4)-skip; curSkip++) {
                    if (idx+4+curSkip > N) break;
                    long val = dp[idx][skip]+A[idx+3+curSkip][0]-A[idx][0];
                    if (val < dp[idx+4+curSkip][skip+curSkip]) {
                        dp[idx+4+curSkip][skip+curSkip] = val;
                        skips[idx+4+curSkip][skip+curSkip] = new ArrayList<>(skips[idx][skip]);
                        for (int i = 0; i < curSkip; i++) {
                            skips[idx+4+curSkip][skip+curSkip].add(A[idx+i+1][1]);
                        }
                    }
                }
            }
        }
        System.out.println(dp[N][N%4]);
        skips[N][N%4].forEach(i -> System.out.println(i));

	    bw.flush();
        bw.close();
        br.close();
    }
}
