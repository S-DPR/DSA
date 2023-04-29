/*
PG_92344 파괴되지 않은 건물

2차원 행렬에 직사각형 모양의 업데이트 쿼리가 Q개 주어진다.
1 이상인 원소의 개수를 구하여라.

아! 스위핑 아시는구나!!
아!!!! IMOS법도 아시는구나!!!!
*/
class Solution {
    public int solution(int[][] board, int[][] skill) {
        int answer = 0;
        int N = board[0].length;
        int M = board.length;
        int[][] status = new int[M+1][N+1];
        for (int[] item: skill) {
            int type = item[0];
            int y1 = item[1], x1 = item[2];
            int y2 = item[3], x2 = item[4];
            int degree = type == 1 ? -item[5] : item[5];
            status[y1][x1] += degree;
            status[y2+1][x1] -= degree;
            status[y1][x2+1] -= degree;
            status[y2+1][x2+1] += degree;
        }
        for (int i = 0; i <= M; i++)
            for (int j = 1; j <= N; j++)
                status[i][j] += status[i][j-1];
        for (int i = 1; i <= M; i++)
            for (int j = 0; j <= N; j++)
                status[i][j] += status[i-1][j];
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                if (-status[i][j] < board[i][j]) answer++;
        return answer;
    }
}