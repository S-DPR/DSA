import java.io.*;
import java.util.*;
/*
1826번 연료 채우기

현재 위치는 0이고 연료는 L만큼 있다. K에 가려고 한다.
중간에 주유소가 N개 있는데, 위치 l에서 k만큼 충전할 수 있다.
주유소를 최소한으로 들리려한다. 몇 번 들릴 수 있을까?

보고 대충 10분 타자 뚜들겨서 푼 문제.
오늘 어려운거 걸리면 못풀뻔했는데 다행히 빠르게 풀었네요.
빠른문제 찾기 쉽지않네..

그냥 PQ 쓱 굴리면됩니다.
이런문제 많기도하고.. 그 전에 골드2치곤 너무 쉽죠..
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

    public static void printArray(long[] arr) {
        for (long i: arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

	public static void main(String[] args) throws IOException {
        int N = ini();
        int[][] info = new int[N][2];
        for (int i = 0; i < N; i++) {
            info[i][0] = ini();
            info[i][1] = ini();
        }
        int K = ini(), L = ini();
        Arrays.sort(info, (i, j) -> {
            return i[0]-j[0];
        });
        int idx = 0, cur = L, cnt = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((i, j) -> {
            return j[1]-i[1];
        });
        while (cur < K) {
            while (idx < N && info[idx][0] <= cur) {
                pq.add(info[idx++]);
            }
            if (pq.isEmpty()) {
                System.out.println(-1);
                return;
            }
            int go = pq.poll()[1];
            cur += go;
            cnt++;
        }
        System.out.println(cnt);

	    bw.flush();
        bw.close();
        br.close();
    }
}
