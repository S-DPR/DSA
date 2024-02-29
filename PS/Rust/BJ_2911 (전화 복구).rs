#![allow(warnings)]
use std::fmt::Write;
use std::io;
use std::collections::VecDeque;
/*
2911번 전화 복구

길이가 N인 수직선이 있다.
그리고 K개의 점에 선 몇 개가 지나가는지 주어진다.
선은 최소 몇 개 있는걸까?

보고 3분만에 폰으로 파이썬 짜서 AC까지 맞은 플레5문제
이야.. 진짜 사람들 기여 보니 세그트리에 분할정복까지 썼다고..
그리디는 역시 개인편차가 큽니다.

K개의 점을 좌표순으로 정렬한 뒤, 매번 그 좌표마다 그 정도의 수가 있다고 바꾸는게 핵심.
만약 더 많아질경우 선이 시작되는겁니다. 결과에 증가한 값만큼 더해줘야죠.
그러면 끝입니다. 이야..
*/
fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();

    let K = input() as usize;
    let N = input() as usize;
    let mut A = Vec::new();
    for i in 0..K {
        let u = input();
        let v = input();
        A.push([u, v]);
    }
    A.sort();
    let mut cur = 0;
    let mut ret = 0;
    for i in 0..K {
        if cur < A[i][1] {
            ret += A[i][1]-cur;
        }
        cur = A[i][1];
    }
    println!("{}", ret);
}
