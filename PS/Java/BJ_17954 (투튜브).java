import java.io.*;
import java.util.*;
/*
17954번 투튜브

1부터 시간하는 T와 1부터 2N까지의 수가 있는 두 배열이 있다.
각 배열의 양 끝의 수를 보고, 가장 작은 수를 제거한다.
이후 배열에 남은 수를 다 더한 뒤 T를 곱한 값을 누적한다.
이 값을 최소화하려한다. N이 주어질 때, 최솟값은 몇일까? 그리고 배열이 어떻게있어야할까?

음..
답은 다 찾았는데 아쉽다.
맨 끝을 잘못넣었네..

생각해보면 어차피 4개중 하나만 빠지니까.. 2N, 2N-1, 2N-2는 나머지 안나가면 평생 갇혀있습니다.
2N-3을 뽑고 이후에 2N-4, 2N-5, ..., N+1을 뽑을 수 있게 작업칩니다.
다음으로 뽑히는건 2N-2.
이거 뽑은 이후에 N, N-1, N-2, ..., 1을 뽑게 작업칩니다.
그리고 문자열로 어따 저장해둔다음, 직접 만든 덱을 갈아서 최솟값을 구해줍니다.
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
        long N = lni()*2;
        if (N == 2) {
            System.out.println(2);
            System.out.println(1);
            System.out.println(2);
            return;
        }
        ArrayDeque<Long>[] deq = new ArrayDeque[2];
        for (int i = 0; i < 2; i++) deq[i] = new ArrayDeque<Long>();
        for (long i = 1; i <= N-4; i++) {
            ArrayDeque<Long> target = i <= N/2-2 ? deq[0] : deq[1];
            target.add(i);
        }
        deq[0].addFirst(N);
        deq[1].addFirst(N-2);
        deq[0].add(N-1);
        deq[1].add(N-3);
        StringBuilder sb = new StringBuilder();
        for (ArrayDeque<Long> d: deq) {
            for (long x: d) sb.append(x + " ");
            sb.append("\n");
        }

        long ret = 0;
        long sm = N*(N+1)/2;
        long time = 1;
        while (!deq[0].isEmpty() || !deq[1].isEmpty()) {
            long mn = 1<<30;
            for (ArrayDeque<Long> d: deq) {
                if (!d.isEmpty()) {
                    long mn_ = Math.min(d.getFirst(), d.getLast());
                    mn = Math.min(mn, mn_);
                }
            }
            for (ArrayDeque<Long> d: deq) {
                if (!d.isEmpty()) {
                    if (mn == d.getFirst()) {
                        sm -= d.pollFirst();
                    }
                    else if (mn == d.getLast()) {
                        sm -= d.pollLast();
                    }
                }
            }
            ret += sm*time++;
        }

        bw.write(ret + "\n");
        bw.write(sb.toString());

	    bw.flush();
        bw.close();
        br.close();
    }
}
