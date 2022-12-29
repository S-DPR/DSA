package main

import (
	"bufio"
	"fmt"
	"os"
)
/*
16401번 과자 나눠주기

정수 n과 k가 공백으로 구분되어 첫 줄에 주어지고,
두번째 줄에 k개의 정수가 공백으로 구분되어 주어진다.
당신은 두번째 나온 수중에 원하는만큼 두 양의 정수의 합으로 쪼갤 수 있다.
(단, 다시 합치진 못한다.)
이 행위를 적절히 하였을 때, 어떤 한 정수가 n번 이상 나오게 할 수 있다.
이 때, 가장 큰 '어떤 한 정수'를 구하시오.

문제이해가 안되서 10분 문제만 보고있다가,
아.. 이거 실버문제구나.. 매개변수탐색이구나..
하고 루비로 딱 코드를 짰습니다.

n, m = gets.split.map &:to_i
arr = gets.split.map &:to_i
l, r = 1, arr.max+1
while l < r do
  m = (l + r) >> 1
  cnt = arr.map do |i| i/m end.sum
  if cnt < n then r = m else l = m+1 end
end
puts r-1

그런데 짜잔! 시간초과가 떴네요!
뇌가 싸아악 비워지고, 내 로직에 오류가 있나 테스트를 해봅니다.
웬걸, 최악의경우에도 연산이 3000만번을 넘어가기 어려워보이네요!
이제 로직을 고칠까, 다른 언어를 쓸까 보민을 해보다가,
C++은 쿼리문제 대항마로 키운애라서 쓰기 아깝고..
파이썬은 pypy쓰면 될거같긴 한데 재미없고..
자바랑 코틀린은 걍 생각조차 안들었고..
node.js는 얘보다 느리면 느렸지 빠를거같진 않고..
하여 나온게 Go입니다.
*/
func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	max := func(i, j int64) int64 {
		if i > j {
			return i
		}
		return j
	}

	var n int64
	var m int
	fmt.Fscan(br, &n, &m)
	MAX := int64(0)
	arr := make([]int64, m)
	for i := 0; i < m; i++ {
		fmt.Fscan(br, &arr[i])
		MAX = max(MAX, arr[i])
	}
	l, r := int64(1), int64(MAX+1)
	for l < r {
		m := (l + r) >> 1
		cnt := int64(0)
		for _, i := range arr {
			cnt += i / m
		}
		if cnt < n {
			r = m
		} else {
			l = m + 1
		}
	}
	fmt.Fprintln(bw, r-1)
	bw.Flush()
}
