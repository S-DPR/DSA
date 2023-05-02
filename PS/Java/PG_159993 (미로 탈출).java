import java.util.*;
/*
PG_159993 미로 탈출

S에서 L을 거쳐 E로 가는 최단거리를 출력하자.

BFS아시는구나~ 정말정말 쉽습니다~
그냥 자바라서 패널티가 붙었을 뿐 진짜 쉬운문제입니다.
제목만 봐도 BFS겠거니 했는데 그냥 자바 연습용으로 풀어봤습니다.
*/
class Solution {
    public int solution(String[] maps) {
        int N = maps.length, M = maps[0].length();
        int INF = 1<<30;
        int[] S = new int[3];
        int[] E = new int[2];
        int[][][] V = new int[N][M][2];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                V[i][j][0] = INF;
                V[i][j][1] = INF;
                switch (maps[i].charAt(j)) {
                    case 'S':
                        S = new int[] {j, i, 0};
                        V[i][j][0] = 0;
                        break;
                    case 'E':
                        E = new int[] {j, i};
                        break;
                }
            }
        }
        int[] dx = new int[] {1, -1, 0, 0};
        int[] dy = new int[] {0, 0, 1, -1};
        ArrayDeque<int[]> Q = new ArrayDeque<int[]>();
        Q.add(S);
        while (!Q.isEmpty()) {
            int[] item = Q.pollFirst();
            int x = item[0], y = item[1], s = item[2];
            if (x == E[0] && y == E[1] && s == 1) break;
            for (int i = 0; i < 4; i++) {
                int nx = x+dx[i], ny = y+dy[i];
                if (!(0 <= nx && nx < M)) continue;
                if (!(0 <= ny && ny < N)) continue;
                if (maps[ny].charAt(nx) == 'X') continue;
                if (V[ny][nx][s] <= V[y][x][s]+1) continue;
                boolean isLever = s == 1 || maps[ny].charAt(nx) == 'L';
                Q.addLast(new int[] {nx, ny, isLever ? 1 : 0});
                V[ny][nx][isLever ? 1 : 0] = V[y][x][s]+1;
            }
        }
        return V[E[1]][E[0]][1] == INF ? -1 : V[E[1]][E[0]][1];
    }
}