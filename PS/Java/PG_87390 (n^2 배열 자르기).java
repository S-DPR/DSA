/*
87390 n^2 배열 자르기

문제는 예시로 안보면 좀 많이 이해하기 까다롭습니다. 직접 봅시다.

각 칸에 들어가는 값의 규칙을 찾았냐 못찾았냐 여부에 난이도가 크게 갈리는 문제였습니다.
각 칸에는 max(row, col)값이 들어가게 됩니다. 이걸 찾으면 lv1 문제가 되고, 못찾으면 lv2중 어려운거..
전 못찾아서 어렵게 풀었습니다. 너무슬퍼요..
*/
class Solution {
    public int[] solution(int n, long left, long right) {
        int[] answer = new int[(int)(right-left+1)];
        int idx = 0;
        int lmn = (int)(left%n) > 0 ? (int)(left%n) : n;
        for (int i = lmn+1; i <= n && idx < answer.length; i++) {
            if (i <= left/n+1) answer[idx++] = (int)(left/n+1);
            else answer[idx++] = i;
        }
        int tmp = lmn < n ? 1 : 0;
        for (int i = (int)(left/n)+1+tmp; i <= (int)(right/n)+1; i++){
            for (int j = 0; j < i && idx < answer.length; j++) answer[idx++] = i;
            for (int j = i+1; j <= n && idx < answer.length; j++) answer[idx++] = j;
        }
        return answer;
    }
}