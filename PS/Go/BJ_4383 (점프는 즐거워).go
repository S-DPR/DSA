package main

import (
	"bufio"
	"fmt"
	"io"
	"math"
	"os"
)

/*
4383번 점프는 즐거워

수열 arr이 주어진다. 이 arr의 서로 인접한 인덱스의 차를 구해보자.
이 차를 정렬하였을 때 1부터 (수열의길이)-1까지 있다면 Jolly를, 아니면 Not jolly를 출력하자.

희대의 쓰레기문제
입력데이터 자체에 큰 문제가 있습니다.
Python으로 옛날에 들이박았다가 안돼고,
Node.js로도 안되길래 한번 홈페이지 들어가서 데이터 뜯어보니 데이터 상태가 영..
그냥 틀린문제 지우고 간다.. 이 마인드로 가렵니다..
*/

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	for {
		var len int64
		_, error := fmt.Fscan(br, &len)
		if error == io.EOF {
			break
		}
		var flg bool
		flg = true
		check := make([]bool, len)
		var prev, cur int64
		fmt.Fscan(br, &prev)
		for i := int64(1); i < len; i++ {
			fmt.Fscan(br, &cur)
			diff := int64(math.Abs(float64(prev - cur)))
			if diff < 1 || diff >= len {
				flg = false
				continue
			}
			if check[diff] {
				flg = false
				continue
			}
			check[diff] = true
			prev = cur
		}
		if flg {
			fmt.Fprintln(bw, "Jolly")
		} else {
			fmt.Fprintln(bw, "Not jolly")
		}
	}

	bw.Flush()
}
