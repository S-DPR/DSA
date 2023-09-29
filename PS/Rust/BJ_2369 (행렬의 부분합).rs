use std::fmt::Write;
use std::io;
use std::collections::HashMap;
/*
2369번 행렬의 부분합

R*C 행렬이 주어진다.
K로 나누어떨어지는 부분행렬의 개수를 구해보자.

저한텐 너무 잔인하게 어려웠던 문제..
진짜 답을 알아도 구현을 전혀 못하는 완전탐색이라니 고통의 연속이었다..
Swift 쓰려고했었는데 시간초과나서 Rust썼습니다. 900ms인거보면 비효율적인듯..

각 행렬을 1차원으로 보고 누적합을 구합니다.
이제 누적합 열심히 굴리고.. '나머지 합'문제처럼 풀어주면 됩니다.
단, K가 100만이나 되기떄문에 Map써주는건 덤.
생각해보니 2차원 누적합쓰면 tmpA 없어져서 더 낫지 않았을까?
*/
fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();

    let R = input() as usize;
    let C = input() as usize;
    let K = input();
    let mut A = [[0;257];257];
    for i in 1..=R {
        for j in 1..=C {
            A[i][j] = input() + A[i][j-1];
        }
    }
    let mut ret = 0;
    for r1 in 0..R {
        let mut tmpA = [0;257];
        let mut P = HashMap::new();
        for r2 in r1+1..=R {
            for c in 1..=C {
                tmpA[c] += A[r2][c];
                let cur = tmpA[c]%K;
                *P.entry(cur).or_insert(0) += 1;
            }
            for c in 1..=C {
                let left = tmpA[c-1]%K;
                ret += *P.get(&left).unwrap_or(&0);
                *P.entry(tmpA[c] % K).or_insert(0) -= 1;
            }
        }
    }
    println!("{}", ret);
}
