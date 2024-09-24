import java.io.*;
import java.util.*;
/*
14464번 소가 길을 건너간 이유 4

수직선상에 선이 M개, 점이 N개 있다.
선과 점이 만나면 사라지게 할 수 있다.
각 선의 시작/끝위치, 각 점의 위치가 주어질 때,
최대 몇 개의 점과 선을 사라지게 할 수 있을까?

어떻게하지.. 그리디인가.. 했는데.
그리디에서 뭐쓰지.. 하다가 TreeMap 생각나서 TreeMap 썼습니다.
근데 다시 생각해보니 이거 구현한거 그대로 그냥 힙으로 바꿔도 되네
알고리즘태그 힙으로 바꿔야게따
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
        int N = ini(), M = ini();
        List<Integer> A = new ArrayList<>();
        Map<Integer, List<Integer>> B = new TreeMap<>();
        TreeMap<Integer, Integer> cur = new TreeMap<>();
        for (int i = 0; i < N; i++) A.add(ini());
        for (int i = 0; i < M; i++) {
            int u = ini(), v = ini();
            if (!B.containsKey(u)) B.put(u, new ArrayList<>());
            B.get(u).add(v+1);
        }
        Collections.sort(A);
        List<Integer> go = new ArrayList<>(B.keySet());
        int idx = 0, ret = 0;
        for (int i: A) {
            while (idx < go.size() && go.get(idx) <= i) {
                for (int j: B.get(go.get(idx))) {
                    cur.put(j, cur.getOrDefault(j, 0)+1);
                }
                idx++;
            }
            while (!cur.isEmpty() && cur.firstKey() <= i) {
                cur.remove(cur.firstKey());
            }
            if (!cur.isEmpty() && cur.firstKey() >= i) {
                int f = cur.firstKey();
                cur.put(f, cur.get(f)-1);
                if (cur.get(f) == 0) cur.remove(f);
                ret++;
            }
        }
        System.out.println(ret);

	    bw.flush();
        bw.close();
        br.close();
    }
}
