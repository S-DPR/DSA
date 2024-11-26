import java.io.*;
import java.util.*;
/*
1655번 가운데를 말해요

수열에 추가될 수가 N개 주어진다.
수열의 중간값을 말해보자. 길이가 짝수라면, 그 중 작은쪽을 말해보자.

5분컷 ㅋㅋ
힙인건 알고있었는데 은근 까다롭다면 까다롭습니다.
힙 두개를 굴리고, 그중 작은값을 관리하는게 항상 사이즈가 +1이 되도록 하는게 포인트.
그리고 작은쪽을 출력하면 됩니다.
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

	public static void main(String[] args) throws IOException {
        int N = ini();
        PriorityQueue<Integer> mn = new PriorityQueue<>();
        PriorityQueue<Integer> mx = new PriorityQueue<>((i, j) -> {
            return j-i;
        });
        mn.add(1<<30);
        mx.add(-1<<30);
        for (int i = 0; i < N; i++) {
            int x = ini();
            if (mx.peek() >= x) {
                mx.add(x);
            } else {
                mn.add(x);
            }
            while (mx.size() <= mn.size()) {
                mx.add(mn.poll());
            }
            while (mn.size()+1 < mx.size()) {
                mn.add(mx.poll());
            }
            bw.write(mx.peek()+"\n");
        }

	    bw.flush();
        bw.close();
        br.close();
    }
}
