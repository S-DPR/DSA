import java.io.*
/* 
20206번 푸앙이가 길을 건너간 이유

수 A, B, C가 주어진다.
직선의 방정식 Ax+By+C=0이,
주어지는 직선 X1, X2, Y1, Y2를 각 변으로 하는 직사각형 내부를 지나갈경우 Poor을,
아닐경우 Lucky를 출력하라.
테두리만 닿는 경우는 내부를 지나는 것이 아니고, A+B != 0이다.

이유는 몰라도 맞았습니다.
뭔가 고치니까 맞았네요.
실버 랜덤 수학문제 고르고 히히 이번엔 정수론이겠지 했더니,
어림도없지 바로 기하학.
제목이 재밌어보이는 정수론은 세상에 존재하지 않나봅니다.

방법은 간단합니다.
x1, x2, y1, y2를 대입해보고 각 선을 관통하는지 체크.
그리고, 사각형의 중앙을 관통해 위 조건을 패스하는지 체크.
그러면 AC를 맞을 수 있습니다.
*/
fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val (a, b, c) = br.readLine().split(" ").map{it.toLong()}
    val (x1, x2, y1, y2) = br.readLine().split(" ").map{it.toLong()}
    val y1_use = -(y1*b+c).toDouble()/a
    val y2_use = -(y2*b+c).toDouble()/a
    val x1_use = -(x1*a+c).toDouble()/b
    val x2_use = -(x2*a+c).toDouble()/b
    val xmid = (x1.toDouble()+x2.toDouble())/2
    val ymid = (y1.toDouble()+y2.toDouble())/2
    if ((x1 < y1_use && y1_use < x2) || (x1 < y2_use && y2_use < x2)) bw.write("Poor")
    else if ((y1 < x1_use && x1_use < y2) || (y1 < x2_use && x2_use < y2)) bw.write("Poor")
    else if ((xmid*a)+(ymid*b)+c==0.0) bw.write("Poor")
    else bw.write("Lucky")
    br.close()
    bw.flush()
    bw.close()
}
