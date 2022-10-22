import java.util.Scanner;
/*
14225번 부분수열의 합

수열 A가 주어집니다.
수열 A에서 몇개든지 수를 집어들고, 그 합을 구한 배열을 K라고 합시다.
이 때, K에 존재할 수 없는 수중 가장 작은 수를 출력하면 됩니다.

이 문제에선 수열 A의 길이가 최대 20밖에 안되기때문에 O(N^2)정도의 시간복잡도를 가진 백트래킹을 사용했습니다.
문제를 풀 때는 그냥 평범하게 백트래킹 짜고 나오는 수를 다 기록하면 됩니다.
그냥 그리디처럼, 정렬 후 누적합을 구하는 방식으로도 가능합니다.. 이게 정해입니다.
그나저나 숏코딩으로 보니까 어지럽네요..
*/
public class Main {
	public static void backTracking(int[] arr, int[] N, int now, int k, int sum) {
		if (now >= arr.length) return;
		for (int i = now; i < arr.length; i++) {
			if (k == 1) {
				N[sum+arr[i]] = 1;
				backTracking(arr, N, i+1, k, sum);
			}
			else backTracking(arr, N, i+1, k-1, sum+arr[i]);
		}
	}
	public static void main(String[] args){
		Scanner t = new Scanner(System.in);
		int n = t.nextInt();
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) arr[i] = t.nextInt();
		int[] N = new int[2000001];
		for (int i = 1; i <= n; i++) {
			backTracking(arr, N, 0, i, 0);
		}
		for (int i = 1; i < 2000001; i++) {
			if (N[i] == 0) {
				System.out.println(i);
				break;
			}
		}
		t.close();
	}
}