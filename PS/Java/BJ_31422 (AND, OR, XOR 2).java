import java.io.*;
import java.util.*;
/*
31422번 AND, OR, XOR 2

모든 연속 부분수열의 AND 합, OR 합, XOR 합을 각각 구해보자.
수가 클 수 있으니 998244353으로 나눈 나머지를 구하자.

어휴
AND랑 OR은 금방했고(항상 연속하거나 하나만 잡고 조합론치거나),
XOR은 하..
쉽지않네..

누적합으로 처리했습니다. 비트별로 나눈다는 생각은 코포에서 좀 충격을 가져다주었는데, 여따 쓰네요.
네 머..
xor은 누적합 쓰면 됩니다.
어렵게 생각 안하고, 그냥 누적합.
cnt[0] = xor결과가 0인 개수, cnt[1] = xor결과가 0이 아닌 개수.
이렇게하고 매번 저기서 1이 되도록 가져오면 되겠죠.
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
        int[] A = ins(N);
        long and = 0, or = 0, xor = 0, MOD = 998_244_353;
        for (int i = 0; i < 30; i++) {
            long bit = (1<<i)%MOD;
            long andx = 0, orx = 0, xorx = 0;
            for (int x: A) {
                if ((x&(1<<i)) != 0) {
                    andx++;
                } else {
                    and = (and+(((andx-1)*andx)/2)%MOD*bit)%MOD;
                    and = (and+andx*bit)%MOD;
                    andx = 0;
                }
            }
            for (int j = 0; j < N; j++) {
                if ((A[j]&(1<<i)) != 0) {
                    or += (((orx+1)*(N-j))%MOD)*bit;
                    or %= MOD;
                    orx = 0;
                } else {
                    orx++;
                }
            }
            int cur = 0;
            long[] cnt = new long[2];
            cnt[0] = 1;
            for (int x: A) {
                cur ^= x&(1<<i);
                int idx = ((cur&(1<<i)) != 0) ? 1 : 0;
                xorx += cnt[idx^1];
                xorx %= MOD;
                cnt[idx]++;
            }
            and = (and+(((andx-1)*andx)/2)%MOD*bit)%MOD;
            and = (and+andx*bit)%MOD;
            xor += xorx*bit;
            and %= MOD;
            or %= MOD;
            xor %= MOD;
        }
        System.out.println(and + " " + or + " " + xor);

	    bw.flush();
        bw.close();
        br.close();
    }
}
