import java.io.*
/*
16165번 걸그룹 마스터 준석이

팀 개수 N과 쿼리의 개수 Q가 첫 줄에 공백으로 구분되어 주어진다.
다음 줄에는 팀 이름이 주어지고, 그 다음 줄에는 팀원의 수 K가 주어진다.
이후 K개 줄에는 팀원의 이름이 주어진다.
이 모양으로, N개의 팀이 주어지고,
2*Q개의 줄에 이름 혹은 팀명이 주어지고 다음줄에 0 혹은 1이 주어진다.
(0이 주어졌다면 이전 줄에는 팀의 이름, 1이 주어졌다면 이전 줄에는 멤버의 이름이 주어져있다.)
0이 주어졌다면 그 팀의 팀원들을 사전순으로 모두 출력하고,
1이 주어졌다면 그 멤버가 어느 팀에 있는지 출력하라.

그냥 보면 딕셔너리쓰는 문제인데,
생각해보니 코틀린으로 딕셔너리 써본적이 없어서 그냥 풀어봤습니다.
*/
fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val (n, m) = br.readLine().split(" ").map {it.toInt()}
    val team = HashMap<String, MutableList<String>>()
    val member = HashMap<String, String>()

    repeat(n) {
        val teamName = br.readLine()
        val k = br.readLine().toInt()
        if (!team.containsKey(teamName)) {
            team[teamName] = mutableListOf()
        }
        repeat(k) {
            val memberName = br.readLine()
            team[teamName]?.add(memberName)
            member[memberName] = teamName
        }
    }

    team.forEach{ (key, _) ->
        team[key]?.sort()
    }

    repeat(m) {
        val query = br.readLine()
        val kind = br.readLine().toInt()
        if (kind == 1) {
            bw.write("${member[query]}\n")
        } else {
            team[query]?.forEach {
                bw.write("${it}\n")
            }
        }
    }

    br.close()
    bw.flush()
    bw.close()
}
