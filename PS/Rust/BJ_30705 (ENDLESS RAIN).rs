#![allow(warnings)]
use std::fmt::Write;
use std::io;
/*
30705번 ENDLESS RAIN

1부터 N까지 건물이 있다. 각 건물 사이에 파라솔을 설치 할 것이다.
매일 u부터 v까지 왔다갔다 할 일이 있는데, 매일 하나씩만 설치하려 한다.
그런데 비를 아예 맞지 않으려, 미리 K개의 파라솔을 미리 설치해두려한다.
K의 최솟값을 구해보자.

입력이 커서 그런가 루비는 기대도 안했고 파이썬도 안된 문제.
C++은 쓰기 너무 아까워서 속도 비슷한 Rust 끌고와서 풀었습니다.
이게 세그트리로 풀면 G1보다 조금 높거나 P5수준일거같은데,
분리집합은 거의 P5수준인거같아요.

최적화를 가능하면 최대한 때려주어야합니다.
v를 최솟값으로 잡는거는 배열 하나 더 만들고 find도 새로 갱신해야하길래 안했는데,
u를 현재까지 본 최대값으로 잡아버려 최대한 최적화를 했습니다.
그거 하나 신경써야하는데, 이런거 생각 못하면 분리집합이 떠오를거같지도 않네요..
*/
const MAX_SIZE: usize = 1_000_000;

fn find(U: &mut [usize; MAX_SIZE+1], x: usize) -> usize {
    if U[x] != x {
        U[x] = find(U, U[x]);
    }
    return U[x];
}

fn merge(U: &mut [usize; MAX_SIZE+1], x: usize, y: usize) {
    let xp = find(U, x);
    let yp = find(U, y);
    U[std::cmp::min(xp, yp)] = U[std::cmp::max(xp, yp)];
}

fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();

    let N = input() as usize;
    let M = input() as usize;
    let mut U = [0 as usize; MAX_SIZE+1];
    let mut ret = 0;
    let mut rest = 0;
    for i in 0..=N {
        U[i] = i;
    }
    for _ in 0..M {
        rest += 1;
        let mut u = input() as usize;
        let v = input() as usize;
        if find(&mut U, u) == find(&mut U, v) { continue; }
        while u < v {
            let i = find(&mut U, u);
            if find(&mut U, i) != find(&mut U, i+1) {
                if rest > 0 {
                    rest -= 1;
                } else {
                    ret += 1
                }
            }
            merge(&mut U, i, i+1);
            u = find(&mut U, u);
        }
    }
    println!("{}", ret);
}
