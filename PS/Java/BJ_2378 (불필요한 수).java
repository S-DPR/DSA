package Main;
import java.io.*;
import java.util.*;
/*
2378번 불필요한 수

각 원소의 범위가 0~M이고 길이가 N인 수열 A가 있다.
수열의 인접한 값을 더하여 새로운 수열을 만드는 연산을 ADD라고 하자.
ADD를 N-1번 실행하면 수열 A는 길이가 1이 되는데,
그 값을 M으로 나눈 나머지를 얻으려 한다.
이 때, 원본 A에 대하여 어떤 K번째 수는 어떤 값이든 결과값에 영향을 주지 않는데,
K번째 수의 개수와 그 K를 모두 출력해보자.
(1 <= N <= 100000, 2 <= M <= 10^9)

오랜만에 보는 정수론문제
순수수학보단 100만배 낫습니다.

먼저 문제 특성을 알아야합니다.
A의 원소를 a, b, c, d, ... 이렇게 정해봅시다.
ADD연산을 한번 하면 a+b, b+c, c+d, ... 이렇게 되고,
이상태에서 한번 더 하면 a+2b+c, b+2c+d, ... 이렇게 되고,
한번 더 하면 a+3b+3c+d, b+3c+3d+e, ...
또 해보면 a+4b+6c+4d+e, ...

모듈러연산은 (A+B)%K = A%K + B%K이기 때문에,
(a+4b+6c+4d+e)%M을 최종적으로 원한다 하면,
a%M + 4b%M + 6c%M + 4d%M + e%M 로 고쳐쓸 수 있고,
a b c d e 앞에 있는 계수가 M의 배수라면 그 수는 불필요한 수가 됩니다.

linear-sieve를 구현한 뒤, M를 소인수분해 해줍니다.
이걸 이용해 각 계수를 계산할 때 M로 나누어지는가를 확인할겁니다.

ADD연산이 N-1번 일어나고, 원하는 인덱스를 i라고 한다면,
이항계수를 이용하여 (N-1)C(i-1)를 계산할 수 있습니다.
이 값을 그대로 계산하지 말고, 분자와 분모를 따로 분리한 뒤,
각각을 소인수분해하여 배열에 집어넣습니다. 분자의경우 +로, 분모의경우 -로 집어넣을 수 있습니다.

이후 소인수분해 한 M를 이용하여 M을 소인수분해 한 값보다 더 큰지 체크합니다.
예를들어 M가 2*5였다면 소인수분해하여 배열에 넣은 값이 2는 1 이상, 5는 1 이상이 되어야겠죠.
만약 2*5일 때 2가 1 이상, 5가 1 이상 둘 모두를 충족하면 이 수는 계수가 M으로 나누어 떨어지므로 불필요한 수가 됩니다.
이러면 Big-Integer 없이 풀 수 있게 됩니다.
*/
public class Main {
	final static int MAX = 100_000;
	public static int sti(StringTokenizer st) {
		return Integer.parseInt(st.nextToken());
	}
	public static long stl(StringTokenizer st) {
		return Long.parseLong(st.nextToken());
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine());
		int N = sti(st), K = sti(st);
		
		int[] linear = new int[MAX+1];
		Vector<Integer> prime = new Vector<Integer>();
		for (int i = 0; i <= MAX; i++)
			linear[i] = i;
		for (int i = 2; i <= MAX; i++) {
			if (linear[i] == i)
				prime.add(i);
			for (int j: prime) {
				if ((long)i*j > MAX) break;
				linear[i*j] = j;
				if (i%j == 0) break;
			}
		}
		
		int tmp = K;
		HashMap<Integer, Integer> KPrime = new HashMap<Integer, Integer>();
		for (int i: prime) {
			if (tmp == 1) break;
			if (tmp%i != 0) continue;
			KPrime.put(i, 0);
			while (tmp%i == 0) {
				KPrime.put(i, KPrime.get(i)+1);
				tmp /= i;
			}
		}
		
		int cnt = 0;
		int[] cur = new int[MAX+1];
		Vector<Integer> result = new Vector<Integer>();
		for (int i = 1, j = N-1; i < N; i++, j--) {
			int x = i, y = j;
			while (x != 1) {
				cur[linear[x]]--;
				x /= linear[x];
			}
			while (y != 1) {
				cur[linear[y]]++;
				y /= linear[y];
			}
			boolean flg = true;
			for (int key : KPrime.keySet()) {
				flg &= cur[key] >= KPrime.get(key);
			}
			if (flg) {
				cnt++;
				result.add(i+1);
			}
		}
		bw.write(cnt + "\n");
		for (int i: result)
			bw.write(i + "\n");
		
		br.close();
		bw.flush();
		bw.close();
	}
}