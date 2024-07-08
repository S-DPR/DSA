import java.io.*;
import java.util.*;
/*
31790번 간단한 문제

b가 주어진다. 아래 조건을 만족하는 순열이 있을까?
1. S(i, j) = A[1], A[2], ..., A[i]번째 요소를 P로 나눴을 때 나머지가 j인 원소의 개수
2. b(i) = S(i, j), (0 <= j < P)의 최댓값

이게 뭔소리냐 하면서 읽어보니 상당히 간단했던 문제.
이게 수식으로 O(N)으로 처리 되던데 이건 나중에 고려해보도록하고..
저는 그냥 트리셋으로 직접 구현했습니다.
순열인걸 몰라서조금 헤멨지만.. 풀었으니 된거죠 머
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
        int N = ini(), P = ini();
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(0, P);
        boolean ret = true;
        for (int i = 0; i < N; i++) {
            int x = ini();
            int mx = map.lastKey();
            if (mx < x) {
                int item = map.get(mx);
                int val = map.getOrDefault(mx+1, 0);
                map.put(mx+1, val+1);
                map.remove(mx);
                if (item-1 != 0) map.put(mx, item-1);
            } else {
                int mn = map.firstKey();
                int item = map.get(mn);
                int val = map.getOrDefault(mn+1, 0);
                map.put(mn+1, val+1);
                map.remove(mn);
                if (item-1 != 0) map.put(mn, item-1);
            }
            mx = map.lastKey();
            ret = ret && mx == x && P*(mx-1) <= N;
        }
        System.out.println(ret ? "YES" : "NO");

	    bw.flush();
        bw.close();
        br.close();
    }
}
