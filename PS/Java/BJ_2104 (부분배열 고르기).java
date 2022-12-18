import java.io.*;
import java.util.*;
/*
2104번 부분배열 고르기

배열 arr이 주어진다. (arr.length <= 100,000)
sum(arr[l:r])*min(arr[l:r])의 최댓값을 구하시오.
(l <= r)

문제풀이를 처음 시작했을 때 잡았다가 O(N^2)만 주구장창 제출하고 시간초과만 맞은 문제입니다.
어제 GO로 히스토그램을 푼 김에 이거도 똑같은 방법으로 풀었습니다.
히스토그램이랑 본질적으로 같은 문제란건 이 문제 처음 풀 때도 알고는있었는데,
그거를 못풀어서 이거도 못풀었거든요.

Stack과 Segment Tree를 이용한 풀이법도 있다는데,
그건 Kotlin, Python으로 풀어보도록 합시다.
어차피 유사 히스토그램 문제는 엄청 많으니까요.
*/
public class Main {
	static long loop(long[] arr, int left, int right) {
		if (left == right) {
			return arr[left];
		}
		int mid = (left + right) >> 1;
		long sumHeight = arr[mid];
		int curLeft = mid, curRight = mid;
		long curMin = arr[mid], res = sumHeight*curMin;
		while (left < curLeft && curRight < right) {
			if (arr[curLeft-1] < arr[curRight+1]) {
				curRight++;
				curMin = Math.min(arr[curRight], curMin);
				sumHeight += arr[curRight];
			} else {
				curLeft--;
				curMin = Math.min(arr[curLeft], curMin);
				sumHeight += arr[curLeft];
			}
			res = Math.max(res, sumHeight*curMin);
		}
		while (left < curLeft) {
			curLeft--;
			curMin = Math.min(arr[curLeft], curMin);
			sumHeight += arr[curLeft];
			res = Math.max(res, sumHeight*curMin);
		}
		while (curRight < right) {
			curRight++;
			curMin = Math.min(arr[curRight], curMin);
			sumHeight += arr[curRight];
			res = Math.max(res, sumHeight*curMin);
		}
		
		long leftValue = loop(arr, left, mid);
		long rightValue = loop(arr, mid+1, right);
		return Math.max(Math.max(leftValue, rightValue), res);
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		long[] arr = new long[n];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++) {
			arr[i] = Long.parseLong(st.nextToken());
		}
		System.out.println(loop(arr, 0, n-1));
	}
}