import java.util.*;
/*
PG_154539 뒤에 있는 큰 수 찾기

수열 A가 있다. 각 원소에서 우측에 있는 가장 가까운 자신보다 큰 수를 담은 배열을 출력해보자.

오큰수 아십니까? 정말 근본 문제입니다!
https://www.acmicpc.net/problem/17298
*/
class Solution {
    public int[] solution(int[] numbers) {
        int len = numbers.length;
        int[] answer = new int[len];
        Stack<int[]> st = new Stack<int[]>();
        for (int i = len-1; i >= 0; i--) {
            int cur = numbers[i];
            while (!st.isEmpty() && st.peek()[0] <= cur) st.pop();
            answer[i] = st.isEmpty() ? -1 : st.peek()[0];
            st.add(new int[] {cur, i});
        }
        return answer;
    }
}