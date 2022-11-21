import java.util.*;
import java.io.*;
/*
2470번 두 용액

수열 A가 주어진다. 두 수를 더해 0에 가장 가깝게 되는 두 수를 출력하라.

풀이방법이 두 개인 문제입니다. 저는 이분탐색으로 했습니다.
모든 원소에 대해 그 반전값(*-1값)으로 lowerbound를 써 나온 인덱스와 그 바로 전 인덱스를 읽어 더한 값이 지금까지 나온 최솟값보다 작으면 갱신하는 방식입니다.
꽤 간단한 문제였습니다.
*/
public class Main {
    static int lowerbound(int[] arr, int x) {
		int l = 0, r = arr.length-1;
		while (l < r) {
			int m = (l + r) / 2;
			if (arr[m] >= x) r = m;
			else l = m+1;
		}
		return r;
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		int[] arr = new int[n];
		for (int i = 0; i < n; i++)
			arr[i] = Integer.parseInt(st.nextToken());
		Arrays.sort(arr);
		int res1 = 0, res2 = 0, min = 2000000001;
		for (int i = 0; i < n; i++) {
			int idx = lowerbound(arr, -arr[i]);
			if (i != idx && min > Math.abs(arr[i] + arr[idx])) {
				min = Math.abs(arr[i] + arr[idx]);
				res1 = arr[i]; res2 = arr[idx];
			}
			if (idx-1 >= 0 && idx-1 != i && min > Math.abs(arr[i] + arr[idx-1])) {
				min = Math.abs(arr[i] + arr[idx-1]);
				res1 = arr[i]; res2 = arr[idx-1];
			}
		}
		if (res1 < res2) System.out.println(res1 + " " + res2);
		else System.out.println(res2 + " " + res1);
	}
}