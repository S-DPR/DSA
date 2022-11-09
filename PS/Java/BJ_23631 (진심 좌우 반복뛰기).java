import java.io.*;
import java.util.StringTokenizer;
/*
23631번 진심 좌우 반복뛰기

N, K가 주어진다.
그리고 수직선상에서 K, -K, 2K, -2K, 3K, -3K.. 이렇게 움직이는 사람이 있다.
이 사람의 초기좌표는 0이고 1걸음당 1만큼 움직인다 할 때, N-1걸음째의 좌표와 보고있는 방향을 구하시오.
양수방향이 오른쪽(R)이고, 출력형식은 각 줄마다 "(좌표) (L/R)" 이다.

정수론 공부하고 야무지게 실버 수학 랜덤문제 골랐습니다.
그리고 정수론과 하등 관계없는놈을 골라버렸습니다..
실제 문제에는 10의 100승이라는 무서운 수가 있긴한데 문제 푸는덴 아무 영향이 없습니다.

움직이는 걸음은 K(0->K), 2K(K->-K), 3K(-K->2K) .. 이렇게 증가하는걸 알 수 있고,
자연스럽게 등차수열의 합 공식이 생각나게 됩니다.
2S = n(2k+(n-1)k)
여기서 S는 N이고 k는 K입니다. 그리고 문제 조건상 등호가 아니라 부등호니까
2N > x(2K+(x-1)K)
로 고칠 수 있겠죠.
그리고 우리는 x를 구해야하니 x로 정리하면
2N > Kx^2+2Kx-Kx
2N > Kx^2+Kx
그러므로, 2N/K > x^2+x를 만족하는 가장 큰 x를 구하면 됩니다.
이 가장 큰 x를 구할때는 매개변수탐색을 이용하면 됩니다.

x를 구한 후에는
N에서 등차가 K이고 시작수가 K인 수열 A의 첫째 항부터 x번째 항까지 뺀 값을 구합니다.
이후 왼쪽, 혹은 오른쪽에서 간 거리에서 구한 값을 빼거나 더하면 현재 좌표가 나옵니다.
x가 홀수면 오른쪽 마치고 완쪽으로 가고있는거니 L을,
X가 짝수면 왼쪽 마치고 오른쪽으로 가고있는거니 R을 출력합시다.
*/
public class Main {
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		int T = Integer.parseInt(br.readLine());
		for (int i = 0; i < T; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int K = Integer.parseInt(st.nextToken());
			long l = 0, r = N;
			while (l < r) {
				long m = (l + r) >> 1;
				if (m*m+m >= (double)N*2/K) r = m;
				else l = m+1;
			}
			r--;
			long leave_dist = N-1-(r+1)*r/2*K;
			if (r%2 == 0)
				bw.write(-r/2*K+leave_dist + " R\n");
			else
				bw.write((r+1)/2*K-leave_dist + " L\n");
		}
		bw.flush();
		bw.close();
		br.close();
	}
}