import java.io.*;
import java.util.*;
/*
30982번 폭탄주를 피해라! 파란댕댕이!

길이가 N인 수열 A가 주어진다.
현재 위치가 P번째 수일 때, P-1, P+1로 1초의 시간을 소요해 이동할 수 있다.
P번째 칸은 반드시 선택되어있고, 이후에 가는 칸은 선택을 해도 되고 안해도 될 때,
선택한 칸의 합이 정확히 M이 되게 하려 한다.
이 행위가 T초 이하의 시간을 써서 가능할까?

어..
배낭인가? 하다가 에이 설마, 하고 안했는데 진짜 배낭이었던문제..
배낭을 두 번 쓰게 할줄은 몰랐네요 솔직히.

배낭 두번 슥슥 긁어주면 AC.
배낭의 각 칸은 현재 수를 만들기 위해 필요한 최소 이동 수입니다.
그리고 합이 M이 되게 하는 두 칸에 대해 l*2+r, r*2+l이 T 이하면 AC.
답지 봤는데.. 음.. 조금 아쉽네..
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

    public static void printArray(int[] arr) {
        for (int i: arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

	public static void main(String[] args) throws IOException {
        int inf = 1<<28;
        int N = ini(), M = ini(), T = ini();
        int[] A = ins(N);
        int P = ini();
        int[] left = new int[M+1];
        int[] right = new int[M+1];
        Arrays.fill(left, inf);
        Arrays.fill(right, inf);
        left[0] = 0;
        right[0] = 0;
        for (int i = P-2; i >= 0; i--) {
            for (int j = M; j-A[i] >= 0; j--) {
                if (left[j-A[i]] == inf) continue;
                left[j] = Math.min(left[j], P-i-1);
            }
        }
        for (int i = P; i < N; i++) {
            for (int j = M; j-A[i] >= 0; j--) {
                if (right[j-A[i]] == inf) continue;
                right[j] = Math.min(right[j], i-P+1);
            }
        }
        boolean ret = false;
        for (int i = 0; i <= M; i++) {
            if (M-A[P-1]-i < 0) continue;
            int l = left[i];
            int r = right[M-A[P-1]-i];
            ret = ret || l*2+r <= T || r*2+l <= T;
        }
        System.out.println(ret ? "YES" : "NO");

	    bw.flush();
        bw.close();
        br.close();
    }
}
