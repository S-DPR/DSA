import java.util.*;
/*
PG_142085 디펜스 게임

배열 A에서 K개의 원소를 뺀 값을 모두 더한 값이 N 이하인 가장 큰 인덱스를 구해보자.

백준에 있는 누적합 문제인줄 알았는데 아니네..
그냥 우선순위 큐 문제입니다. 간단하죠?
구현 귀찮아서 이거말고 딴 방법 있나 생각해봤는데, 이게 제일 낫더라고요.
*/
class Solution {
    public int solution(int n, int k, int[] enemy) {
        int len = enemy.length;
        int answer = 0;
        long sum = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        for (int i = 0; i < len; i++) {
            sum += enemy[i];
            if (pq.size() < k || pq.peek() < enemy[i]) {
                if (pq.size() >= k) sum += pq.poll();
                pq.add(enemy[i]);
                sum -= enemy[i];
            }
            if (n < sum) return i;
        }
        return len;
    }
}