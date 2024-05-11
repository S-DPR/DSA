import java.io.*;
import java.util.*;
/*
12982번 공 포장하기 2

공 색의 종류가 N개 있다.
이후 N개의 숫자가 주어진다. 각각 1번째 색 공의 개수, 2번째 색 공의 개수, ... 이다.
상자에는 서로 다른 색의 공만을 넣거나, 같은 색의 공만을 최대 N개 넣을 수 있다.
상자가 최소 몇 개 필요할까?

흚..
그리디..

우선 각 공의 개수가 N개 이상이라면 N개 미만이 될 때까지 상자에 담고,
남은건 정렬해서 브루트포스.
정렬이 되었으므로 i >= 1일 때 A[i-1]+(N-i)만큼의 상자가 필요하겠죠.
와! 그럼 AC! 왜지?? 이걸 직관으로 봐야한다고??

한번 나름대로 납득을 해보면..
N보다 A[i]가 압도적으로 클 확률이 높습니다.
상자에는 한 상자에 최대한 많이 넣는게 무조건 좋긴 합니다.
예를 들어, 2 0개의 공이 있다면 1 1로 나누는거보단 무조건 2로 담는게 좋겠네요.

그러므로, 상자에 N개 넣을 수 있다면 무조건 넣어야합니다.
선택지가 두 개니까요. 남은 하나의 선택지는 A[i]개를 전부 다른 상자에 나눠서 넣는다는건데,
이러면 반드시 답이 N 이하로 나오는 문제에서 A[i] > N이 되는 순간 터져버리니 당연히 안됩니다.

이제 A의 모든 원소는 N 미만이 되었습니다.
하나의 원소를 0으로 만드는데 하나의 상자만이 필요함을 알 수 있습니다.
이거는 정렬만 하면 모든 요소에 대해 O(1)로 처리 가능하므로, O(N)으로 처리할 수 있겠네요.

아니 근데 이건 증명이아니라 설명인데 증명은 못하겠네..
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
        int N = ini();
        int[] A = new int[N];
        int ret = 0, pos = 0;
        for (int i = 0; i < N; i++) {
            int x = ini();
            ret += x/N;
            A[i] = x%N;
            if (A[i] > 0) pos++;
        }
        Arrays.sort(A);
        pos = Math.min(pos, A[N-1]);
        for (int i = 1; i < N; i++) {
            pos = Math.min(pos, A[i-1]+(N-i));
        }
        System.out.println(ret+pos);

	    bw.flush();
        bw.close();
        br.close();
    }
}
