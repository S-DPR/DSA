import java.io.*;
import java.util.*;
/*
1931번 회의실 배정

회의실 하나가 주어진다.
이후, N개의 스케줄이 주어진다.
각 스케줄은 정수 두 개로 이루어져 있으며,
첫 정수부터 두번째 정수까지의 시간을 필요로 한다는 뜻이다.
최적으로 선택을 했을 때, 최대 몇 개의 팀이 회의실을 사용할 수 있을까?

에타에서 한 분이 스터디를 구하길래 들어갔고,
브실문제 푼다 하시고 그 수준 문제 들오고실거라길래 엄청 만만하게 보고있었는데,
처음에 내는 문제가 이거인걸 보고 머리가 어질어질해졌습니다.
이거 아무리봐도 골드는 갈만한 그리디같은데..

요즘은, '빨리 끝내야 뒷사람이 빨리 들어간다'.
그리디입니다. 처음 봤을때는 진지하게 스위핑 생각하고있었는데..
이게 그리디로 풀릴거라는 생각은 못했네요.
태그 몰랐으면 진짜 못풀뻔했습니다.

증명이라 해봐야 진짜 별거 없이, '선택지가 많아야 유리하다'는 점을 사용해보면 됩니다.
예를들어 S1 E1, S2 E2 (E1 < E2)라고 해봅시다.
E1을 고르면 N-E1만큼 회의실을 사용할 수 있고, E2를 고르면 N-E2만큼 회의실을 사용할 수 있습니다.
N-E1 > N-E2니 당연히 N-E1을 선택하는 편이 훨씬 더 선택폭이 넓어지겠죠.
*/
public class Main {
	public static int sti(StringTokenizer st) {
		return Integer.parseInt(st.nextToken());
	}
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st;
        int[][] arr = new int[N][2];
        for (int i = 0; i < N; i++) {
        	st = new StringTokenizer(br.readLine());
        	arr[i][0] = sti(st);
        	arr[i][1] = sti(st);
        }
        Arrays.sort(arr, (i, j) -> {
        	if (i[1] == j[1]) return i[0] - j[0];
        	return i[1] - j[1];
        });
        
        int result = 0, cur = 0;
        for (int[] x: arr) {
        	if (x[0] >= cur) {
        		cur = x[1];
        		result++;
        	}
        }
        System.out.println(result);
    }
}
