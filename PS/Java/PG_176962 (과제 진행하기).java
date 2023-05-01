import java.util.*;
/*
PG_176962 과제 진행하기

N개의 과제를 진행하려 한다. 각 과제는 이름, 시작 시간과 걸리는 시간으로 구성되어있다.
과제를 진행하는 도중 새로운 과제를 할 시간이 왔다면 현재 과제를 미뤄두고 그 과제를 수행한다.
이 때, 미루었다고 해서 진행도가 초기화되지 않는다.
새로 온 과제를 다 처리했다면 이전에 미룬 과제를 가장 최근에 미룬 것 부터 처리한다.
과제가 끝난 시간과 과제를 시작하는 시간이 같다면, 새로 들어온 과제를 먼저 처리한다.
이 때, 처리하는 과제 순서대로 이름을 출력해보자.

스-택 문제.
체감난이도 실버 1 ~ 골드 5.
딴건모르겠고 파이썬이면 그냥 슥슥 풀텐데 자바라서 너무 귀찮았습니다. 이거때문에 가산난이도 붙었고.
어쨌든.. 난이도는 쉬웠지만.. 귀찮았습니다.. 너무.. 진짜 너무..
*/
class Solution {
    public int toInt(String time) {
        String[] tmp = time.split(":");
        return Integer.parseInt(tmp[0])*60 + Integer.parseInt(tmp[1]);
    }
    public Vector<String> solution(String[][] plans) {
        Vector<String> answer = new Vector<String>();
        int len = plans.length;
        
        Arrays.sort(plans, (i, j) -> {
            return toInt(i[1]) - toInt(j[1]);
        });
        
        String curT = "00:00";
        Vector<String[]> stk = new Vector<String[]>();
        for (int i = 0; i < plans.length; i++) {
            String[] cur = plans[i];
            int workT = toInt(cur[1])-toInt(curT);
            
            while (!stk.isEmpty() && workT > 0) {
                String[] item = stk.lastElement();
                stk.remove(stk.size()-1);
                int remainT = Integer.parseInt(item[2]);
                if (remainT > workT) {
                    item[2] = String.format("%d", remainT-workT);
                    stk.add(item);
                } else answer.add(item[0]);
                workT -= remainT;
            }
            
            stk.add(cur);
            curT = plans[i][1];
        }
        while (!stk.isEmpty()) {
            String[] item = stk.lastElement();
            stk.remove(stk.size()-1);
            answer.add(item[0]);
        }
     
        return answer;
    }
}