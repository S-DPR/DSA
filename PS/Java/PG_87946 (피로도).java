/*
87946 피로도

수열이 들어있는 수열 A가 있습니다.
수열 A의 각 원소는 a, b로 이루어져 있습니다.
그리고, 정수 k가 주어집니다.
이 정수 k가 A의 어떤 원소중 a보다 크거나 같으면 k에서 b를 빼고 1점을 얻을 수 있습니다.
모든 a는 b 이상입니다. 얻을 수 있는 최대 점수는 몇점일까요?

파이썬으로 풀다가 이건 뭐 얻는게 너무 없는데요 하고 자바로 풀어봤습니다.
시작부터 지옥이네요 네.
디버깅을 머리로만 하니까 진짜 너무 머리아파요..
백트래킹으로 풀었습니다. dfs가 더 좋은가 싶기도하지만 전 백트래킹으로 했어요.
*/
class Solution {
    public int backT(int tired, int res, int[] visit, int[][] dungeons){
        int ans = res;
        for (int i = 0; i < visit.length; i++){
            if (visit[i] == 1) continue;
            if (dungeons[i][0] <= tired){
                visit[i] = 1;
                ans = Math.max(backT(tired-dungeons[i][1], res+1, visit, dungeons), ans);
                visit[i] = 0;
            }
        }
        return ans;
    }
    public int solution(int k, int[][] dungeons) {
        int answer = -1;
        for (int i = 0; i < dungeons.length; i++){
            int[] visit = new int[dungeons.length];
            visit[i] = 1;
            answer = Math.max(answer, backT(k-dungeons[i][1], 1, visit, dungeons));
        }
        return answer;
    }
}