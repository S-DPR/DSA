package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

/*
2978번 A=S

A=S꼴로 이루어진 등식이 주어진다. 초기상태는 A=S가 아닐 수 있다.
A에 +를 적절히 추가하여 진짜로 A=S로 만들어보자.
해는 하나 이상 존재한다.

보고 좀 생각한다음 이건 배낭이네요 하면서 푼 문제.
원래는 N^2S로 약 100만*5000수준의 시간복잡도였지만..
4NS정도로 대폭 닫축시켰습니다. 백트래킹단계에서 조금 희생을 했지만.
*/
type info struct {
	cnt, prvR, prvC int
}

func backT(s string, r, c int, dp [][]info) ([]string, bool) {
	if r == -1 || c == -1 {
		return []string{}, false
	}
	inf := dp[r][c]
	prvR, prvC := inf.prvR, inf.prvC
	str, isPrvOnlyZero := backT(s, prvR, prvC, dp)
	isCurOnlyZero := true
	var sb strings.Builder
	if isPrvOnlyZero {
		lnStr := len(str)
		last := str[lnStr-1]
		str = str[:lnStr-1]
		sb.WriteString(last)
	}
	for i := prvR + 1; i <= r; i++ {
		sb.WriteString(string(s[i]))
		isCurOnlyZero = isCurOnlyZero && s[i] == '0'
	}
	return append(str, sb.String()), isCurOnlyZero
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	defer bw.Flush()

	var S string
	fmt.Fscan(br, &S)
	split := strings.Split(S, "=")
	L, ln := split[0], len(split[0])
	R, _ := strconv.Atoi(split[1])
	dp := make([][]info, ln)
	for i := 0; i < ln; i++ {
		dp[i] = make([]info, R+1)
		for j := 0; j <= R; j++ {
			dp[i][j] = info{-1, -1, -1}
		}
	}
	for i := 0; i < ln; i++ {
		cur, nxt := 0, 1
		for j := 0; i-j >= 0 && j <= 4; j++ {
			cur += int(L[i-j]-'0') * nxt
			if R < cur {
				break
			}
			nxt *= 10
			for k := 0; i-j-1 >= 0 && k+cur <= R; k++ {
				if dp[i-j-1][k].cnt == -1 {
					continue
				}
				if dp[i][k+cur].cnt == -1 || dp[i-j-1][k].cnt+1 < dp[i][k+cur].cnt {
					dp[i][k+cur] = info{dp[i-j-1][k].cnt + 1, i - j - 1, k}
				}
			}
			if i-j == 0 { // 처음부분이면 그냥 기호 없이 추가가 된다.
				dp[i][cur] = info{0, -1, -1}
			}
		}
	}
	ret, _ := backT(L, ln-1, R, dp)
	fmt.Print(strings.Join(ret, "+"), "=", R)
}
