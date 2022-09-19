import java.io.*

/*
8911번 거북이
코틀린 재활치료 2
뭔가 좀 더러운 방법으로 푼거같긴 한데..
밤도 늦고 해서 그냥 이대로 올립니다.
*/
fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))
	
	// 방향을 정해주는 vec입니다. 0이면 U, 1이면 L.. 네.
    val vec = listOf('U', 'L', 'D', 'R')
	
	// repeat함수. 정말 좋다고 생각합니다.
    repeat(br.readLine().toInt()){
        var y = 0; var x = 0; // 현재 X, Y입니다.
        // 0번 인덱스 : 최대 X, 1번 : 최소 X, 2번 : 최대 Y, 3번 : 최소 Y
		// 최대, 최소 좌표 저장 인덱스입니다.
        val coorInfo = IntArray(4) {0}
        var nowFront = 0 // 현재 보고있는 방향입니다.
        br.readLine().forEach{ // 읽어들인거를 for문 돌립니다.
            when(it){
                'F' -> { // F면 보는방향으로 앞으로 가고..
                    when(vec[nowFront]){
                        'U' -> y++
                        'D' -> y--
                        'L' -> x--
                        'R' -> x++
                    }
                }
                'B' -> { // B면 뒤로가고.. 개인적으로 F랑 B는 확 줄일 수 있을거라 보는데..
                    when(vec[nowFront]){
                        'U' -> y--
                        'D' -> y++
                        'L' -> x++
                        'R' -> x--
                    }
                }
                'L' -> nowFront = (++nowFront)%4
                'R' -> nowFront = (--nowFront+4)%4 // 이 둘은 고심하다가 줄이는데 성공했습니다. 와~
            }
			// 한번 실행 후 최대, 최소값을 갱신해줍니다.
            coorInfo[0] = maxOf(coorInfo[0], x)
            coorInfo[1] = minOf(coorInfo[1], x)
            coorInfo[2] = maxOf(coorInfo[2], y)
            coorInfo[3] = minOf(coorInfo[3], y)
        }
		// 그리고 답을 출력합니다.
        bw.write("${(coorInfo[0] - coorInfo[1]) * (coorInfo[2] - coorInfo[3])}\n")
    }

    br.close()
    bw.flush()
    bw.close()
}