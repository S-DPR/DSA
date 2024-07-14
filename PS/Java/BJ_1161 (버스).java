import java.io.*;
import java.util.*;
/*
1161번 버스

A역에서 타고 B역에서 내리는 사람이 C만큼 있는 그룹이 K개 있다.
1번역에서 N번역까지 이동 할 때, 최대 수송량이 X라면 최대 몇 명을 수송할 수 있을까?

찲내
레이지세그 박혀있는데, 아무리봐도 저번에 푼 골1이랑 같은문젠데?? 하면서 그대로 구현하니까 AC.
바아로 골드1 기여하고 왔습니다.

저번에 푼 골드1, 버스랑 그냥 똑같은 문제입니다.
진짜 왜 이건 플3이고 레이지세그가 박혀있는데 왜 그건 골드1일까 대체 무슨차이일까 고민 많이했는데,
소신대로 박으니까 AC..
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
        int K = ini(), N = ini(), C = ini();
        int[][] A = new int[K][3];
        for (int i = 0; i < K; i++) {
            A[i] = new int[] { ini(), ini(), ini() };
        }
        Arrays.sort(A, (i, j) -> {
            return i[0]-j[0];
        });
        int ret = 0, cur = 0, idx = 0;
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int i = 0; i <= N; i++) {
            int off = map.getOrDefault(i, 0);
            ret += off;
            cur -= off;
            while (idx < K && A[idx][0] == i) {
                int e = A[idx][1];
                int c = A[idx][2];
                map.put(e, map.getOrDefault(e, 0)+c);
                cur += c;
                idx++;
            }
            while (C < cur) {
                int mx = map.lastKey();
                int val = map.get(mx);
                int newV = val - Math.min(cur-C, val);
                if (newV > 0) map.put(mx, newV);
                else map.remove(mx);
                cur -= Math.min(cur-C, val);
            }
        }
        System.out.println(ret);

	    bw.flush();
        bw.close();
        br.close();
    }
}
