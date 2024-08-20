import java.io.*;
import java.util.*;
/*
1945번 직사각형

2차원 평면상에 N개의 직사각형이 있다.
각 변은 축과 평행하다.
이 때, (0, 0)을 지나는 선을 하나 그으려 한다.
이 선은 최대 몇 개의 직사각형을 지날 수 있을까?

이거 어캐푸냐 하고 보면서 멍때리다가 각도 재면 될 것 같다는 생각.
한 직사각형을 지나는 직선의 기울기는 어느 각도에서 시작해, 어느 각도에서 끝날겁니다.
이를 캐치헤서 스위핑하면 AC.
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

    public static char cni() throws IOException {
        while (!st.hasMoreTokens())
            st = new StringTokenizer(br.readLine());
        return st.nextToken().charAt(0);
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

	public static void main(String[] args) throws IOException {
        int N = ini();
        var P = new TreeMap<Double, Long>();
        var M = new TreeMap<Double, Long>();
        for (int i = 0; i < N; i++) {
            int x1 = ini(), y2 = ini();
            int x2 = ini(), y1 = ini();
            double mx = (double) y1 / x1;
            double mn = (double) y2 / x2;
            M.put(mx, M.getOrDefault(mx, 0L)+1);
            P.put(mn, P.getOrDefault(mn, 0L)+1);
        }
        long ret = 0, cur = 0;
        var T = new TreeSet<Double>();
        T.addAll(P.keySet());
        T.addAll(M.keySet());
        for (double x: T) {
            cur += P.getOrDefault(x, 0L);
            ret = Math.max(ret, cur);
            cur -= M.getOrDefault(x, 0L);
        }
        System.out.println(ret);

	    bw.flush();
        bw.close();
        br.close();
    }
}
