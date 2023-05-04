import java.util.*;
/*
PG_181188 요격 시스템

수직선상에 [s, e] (s <= e) 사이를 잇는 점이 N개 있습니다.
한 x좌표를 잡아 이 점을 포함하는 선을 모두 뽑아내려고 합니다.
이 행위를 최소 몇 번 해야 모든 선을 뽑아내는지 구해주세요.

우선순위큐로 착각해서 시간 조금 뺏긴 문제
그냥 그리디문제였습니다. e로 정렬하고 그 점이 끝나기 직전의 x좌표를 잡고,
그 좌표를 포함하면 continue, 아니면 answer++하고 점을 다시 잡으면 됩니다.

https://www.acmicpc.net/problem/26658
이 문제가 이 문제의 극단적인 상위호환같네요..
*/
class Solution {
    public int solution(int[][] targets) {
        int answer = 0, len = targets.length;
        Arrays.sort(targets, (i, j) -> { return i[1] - j[1]; });
        int item = 0;
        for (int i = 0; i < len; i++) {
            int[] cur = targets[i];
            if (cur[0] < item && item <= cur[1]) continue;
            item = cur[1];
            answer++;
        }
        return answer;
    }
}