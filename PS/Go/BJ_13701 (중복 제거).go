package main

import (
	"bufio"
	"fmt"
	"io"
	"os"
)

/*
13701번 중복 제거

1부터 2**25까지의 수가 N개 들어온다.
들어오는 순서대로 출력하자.
단, 앞에서 출력한 수는 다시 출력하지 말자.

set 쓰면 메모리초과의 길에 빠져드는 문제.
메모리제한이 단 8MB, 그러다보니 입력형식은 C의 형식을 쓰는게 이롭습니다.
그래서 go로 풀었고요.

저는 bool 배열을 활용했지만, 정해는 bitset인듯합니다.
대충 int배열인데, 비트연산을 이용하여 이 원소가 존재하는지 존재하지 않는지 체크하는 방식인듯 합니다.
int는 하나당 32비트를 가지고있으므로 (크기)/32번째 원소의 (크기)%32번째 비트를 바꾸면 되겠죠.
네, 이런형식의 문제같습니다. 새로운 자료구조네요. 쓸일은 거의 없어보이지만..
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)

	arr := make([]bool, (1<<25)+1)
	for {
		var n int
		_, e := fmt.Fscan(br, &n)
		if e == io.EOF {
			break
		}
		if !arr[n] {
			arr[n] = true
			fmt.Fprintf(bw, "%d ", n)
		}
	}

	bw.Flush()
}
