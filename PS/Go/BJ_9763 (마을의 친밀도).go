package main
/*
9763번 마을의 친밀도

정점의 수 N이 주어지고, 삼차원상의 점이 x, y, z꼴로 N개 주어진다.
D_ij = abs(D_ix-D_jx) + abs(D_iy-D_jy) + abs(D_iz-D_jz)
D_xyz = D_xy + D_yz일 때, 이 값의 최솟값을 구하시오.

이게 진짜 실버의 발상??
진짜로??
골드 5로 제출해서 실버 2에서 1로 직접 올렸습니다.

발상은, D_xyz를 구할 때 D_y가 결국 중점임을 이용하여 D_y를 고정하고 D_y에서 최소거리 두개를 찾아 둘을 더하여 D_xyz를 각각 O(n)에 구하고,
y를 모든 정점으로 바꾸어보면서 결국 나이브한 O(n^3)에서 O(n^2)으로 바꾼다는 놀라운 발상입니다.

Go 오랜만에 써보려고 브루트포스 재활문제 찾다가 이게 무슨..
*/
import (
	"bufio"
	. "fmt"
	"os"
)

func calcDist(i, j []int) int {
	abs := func(i int) int {
		if i >= 0 {
			return i
		}
		return -i
	}
	return (abs(i[0]-j[0]) +
		abs(i[1]-j[1]) +
		abs(i[2]-j[2]))
}

func main() {
	br := bufio.NewReader(os.Stdin)
	bw := bufio.NewWriter(os.Stdout)
	min := func(i, j int) int {
		if i > j {
			return j
		}
		return i
	}
	// Go는 lambda를 이렇게 하더라고요! min지원도 안해서 이렇게 해야합니다..

	var n int
	Fscan(br, &n)
	arr := make([][]int, n)
	for i := 0; i < n; i++ {
		var x, y, z int
		Fscan(br, &x, &y, &z)
		arr[i] = []int{x, y, z}
	}

	res := 10000
	for i := 0; i < n; i++ {
		tmp1 := 10000
		tmp2 := 10000
		for j := 0; j < n; j++ {
			if i == j {
				continue
			}
			if tmp1 < tmp2 {
				tmp2 = min(tmp2, calcDist(arr[i], arr[j]))
			} else {
				tmp1 = min(tmp1, calcDist(arr[i], arr[j]))
			}
		}
		res = min(res, tmp2+tmp1)
	}
	Fprint(bw, res)

	bw.Flush()
}
