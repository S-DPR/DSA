import java.util.*;
/*
PG_178870 연속된 부분 수열의 합

비내림차순 정렬이 되어있는 수열 A의 인덱스 i, j에 대하여,
A[i] + ... + A[j]의 값이 k가 되는 순간의 인덱스를 구해보자.
그중 가장 짧은 것, 그것도 여러개라면 그 중 가장 먼저 나오는 것을 출력하자.

https://www.acmicpc.net/problem/2003
이 문제가 차라리 더 어렵습니다..
이건 그냥 투포인터 박으면 풀리는 문제입니다.
자바로 프로그래머스 풀어보려고 해봤습니다.
*/
class Solution {
    public Vector<Integer> solution(int[] sequence, int k) {
        Vector<Integer> answer = new Vector<Integer>();
        int left = 0, right = -1, sum = 0;
        int len = 1_000_000_000;
        while (left < sequence.length) {
            if (right+1 < sequence.length && sum < k)
                sum += sequence[++right];
            else sum -= sequence[left++];
            if (sum == k && right - left + 1 < len) {
                len = right - left + 1;
                answer = new Vector<Integer>();
                answer.add(left);
                answer.add(right);
            }
        }
        return answer;
    }
}