import java.util.*;
import java.io.*;
/*
24462번 일어나... 코딩해야지...

package 안지워서 틀린거 제외하면 2번 틀렸네요.
시간은 한시간 이상.. 거의 두시간..

스토리텔링 없이 문제의 요지만 말하자면,
----------
첫 줄에 등차수열의 개수 N, 최댓값 D가 주어집니다. (2 <= N <= 1000, 1 <= D <= 10^9)

2번째 줄부터 N+1번째까지 각각의 수열을 T(i)라 할 때,
T(i)마다 시작 수 S(i)와 공차 K(i)가 공백으로 나뉘어져 주어집니다.
(단, 0 <= S(i) <= D, 1 <= K(i) <= 10^9)

시작 수가 S(i)이고 공차가 K(i)라고 하면,
T(i)는 [S(i), S(i)+K(i), S(i)+2K(i),  ..., S(i)+PK(i)]가 됩니다. (단, P <= (D-S(i)) / K(i))

이제, 1 <= x < y <= N을 만족하는 T(x), T(y)를 골라, 합집합중 가장 클 때 크기를 구하려 합니다.
이를 만족하는 x, y와 그 합집합의 크기를 구해봅시다. 만약 그 합집합의 크기가 두 개 이상 나온다면, 사전순으로 앞서는걸 출력합시다.
----------
제목 재밌어서 골랐다가 재미가 나를 잡아먹겠구나 느낀 문제입니다.
수학문제는 이 악물고 도망쳤는데 랜덤문제에서 덜미 잡혔네요.
문제 보고 좀 생각해보다가 부르트포스에 최대공약수, 최소공배수 문제구나 했습니다.
*/
public class Main {
	public static int sti(StringTokenizer st) {
		return Integer.parseInt(st.nextToken());
	}
	public static int gcd(int a, int b) {
		while (b != 0) {
			int r = a % b;
			a = b; b = r;
		}
		return a;
	}
	public static long lcm(int a, int b) {
		// 최악의 경우 10^18이 계산과정에서 나오기에 long형으로 해줍시다. 
		return (long)a*b/gcd(Math.max(a, b), Math.min(a, b));
	}
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = sti(st), obj = sti(st);
		int [][]alarm = new int[n][2];
		
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			alarm[i][0] = sti(st);
			alarm[i][1] = sti(st);
		}
		
		long []res = new long[3];
		for (int i = 0; i < n; i++) {
			// 문제상 각 T(i)는 알람입니다. 첫 알람은 이 횟수만큼 울리게됩니다.
			// +1의 이유는, D = 6에 0 3이라고 할 때 0 3 6 세 번 울려야하지만, 6/3만 하면 2만 나옵니다.
			// 3 6만 센단 소리죠. 시작값은 무시당하기에 +1로 추가해주는겁니다.
			long first_alarm = (obj/alarm[i][1])-(alarm[i][0]/alarm[i][1])+1;
			for (int j = i+1; j < n; j++) {
				// 두 번째 알람은 똑같이 이 횟수만큼 울리겠죠.
				long second_alarm = (obj/alarm[j][1])-(alarm[j][0]/alarm[j][1])+1;
				long lc = lcm(alarm[i][1], alarm[j][1]); // lcm은 자주 쓰이니까 아예 한번 구해두고,
				long dup = obj / lc - Math.max(alarm[i][0], alarm[j][0]) / lc; // 중복하여 울리는 횟수를 세줍니다.
				// 시작하는 시간의 최댓값이 lc로 나누어 떨어지거나 시작시간이 중복된다면 dup을 1 올려줍니다.
				// 참고로 dup은 duplication 줄임말로 썼습니다.
				if (Math.max(alarm[i][0], alarm[j][0]) % lc == 0 || alarm[i][0] == alarm[j][0]) dup++;
				if (res[2] < first_alarm+second_alarm-dup) { // 만약에 울린 횟수가 현재 값보다 크다면
					res[0] = i+1; res[1] = j+1; res[2] = first_alarm+second_alarm-dup; // 다 덮어씌우고요.
					// i = 0, j = i+1로 시작하기에 자연스럽게 사전순으로 들어오게 됩니다.
				}
			}
		}
		System.out.println(res[0] + " " + res[1]);
		System.out.println(res[2]);
	}
}
