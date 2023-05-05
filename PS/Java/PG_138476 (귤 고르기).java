import java.util.*;
/*
PG_138476 귤 고르기

수열 A가 있다. 이 수열 A에서 몇 개의 수를 골라내 수열 B를 만들려고 한다.
수열 B에 최대한 적은 서로 다른 숫자가 있게 하려고 할 때, 가능한 최솟값을 구해보자.

그냥.. 파이썬이면 Counter 쓰고, 아니면 map쓰고..
제일 큰거 정렬하고.. k에서 그 수만큼 빼고 하다보면 끝나는 문제.
실버 4~3? 그정도?
*/
class Solution {
    public int solution(int k, int[] tangerine) {
        HashMap<Integer, Integer> cnt = new HashMap<Integer, Integer>();
        for (int i: tangerine) {
            if (!cnt.containsKey(i))
                cnt.put(i, 0);
            cnt.put(i, cnt.get(i)+1);
        }
        Vector<Integer> item = new Vector<Integer>();
        item.addAll(cnt.keySet());
        item.sort((i, j) -> {
            return cnt.get(j)-cnt.get(i);
        });
        int answer = 0;
        for (int i: item) {
            if (k == 0) return answer;
            if (k <= cnt.get(i)) return answer+1;
            k -= cnt.get(i);
            answer++;
        }
        return 0;
    }
}