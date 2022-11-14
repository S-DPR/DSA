import java.util.Scanner;
/*
6219번 소수의 자격

A이상 B이하 수중 숫자 D를 포함하는 소수의 개수를 구하시오.
(1 <= A <= B <= 4000000, B <= A+2000000, 0 <= D <= 9)

수업시간에 몰래 10분만에 풀었습니다.
그냥 에라토스테네스의 체를 쓰고, 숫자에 D가 있나 확인하면 됩니다.
사실 수의 범위가 저래서그렇지 10^18로 난리쳐놨으면..
*/
public class Main {
	public static void main(String[] args) {
		Scanner t = new Scanner(System.in);
		int A = t.nextInt(), B = t.nextInt(), K = t.nextInt();
		boolean[] prime = new boolean[B+1];
		for (int i = 0; i <= B; i++) prime[i] = true;
		prime[0] = false; prime[1] = false;
		for (int i = 2; i <= B; i++) {
			if (!prime[i]) continue;
			for (int j = i+i; j <= B; j += i)
				prime[j] = false;
		}
		int cnt = 0;
		for (int i = A; i <= B; i++) {
			if (!prime[i]) continue;
			int tmp = i;
			while (tmp > 0) {
				if (tmp%10 == K) {cnt++; break;}
				else tmp/=10;
			}
		}
		System.out.println(cnt);
	}
}