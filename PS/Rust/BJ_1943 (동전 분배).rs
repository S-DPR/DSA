#![allow(warnings)]
use std::fmt::Write;
use std::io;
/*
1943번 동전 분배

V_i의 가치를 가지는 동전이 C_i개 있다.
동전 가치의 합이 10만을 넘지 않을 때, 정확하게 절반으로 나눌 수 있을까?

아하..
이거.. 평범한배낭 2구나.
음.. 근데..

이게 평범한배낭2보다 난이도가 4나 낮은 이유는 간단합니다.
최적화 나이브가 풀려버려서.
합이 10만을 넘지 않는다, 이 조건때문에 나이브가 성립되네요.
그냥 개수가 가장 많은거 먼저 true로 처리해두고 나머지 처리하면 됩니다.

위처럼 풀면 1750ms, 평범한배낭대로 풀면 150ms가 됩니다.
개쩐다..
*/
fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();

    for _ in 0..3 {
        let N = input() as usize;
        let mut dp = [false; 50001];
        let mut sm = 0;
        let mut kind_of = Vec::new();
        dp[0] = true;
        for i in 0..N {
            let V = input() as usize;
            let C = input() as usize;
            sm += V*C;
            kind_of.push([C, V]);
        }
        kind_of.sort();
        for i in 1..=N {
            let idx = N-i;
            let V = kind_of[idx][1];
            let C = kind_of[idx][0];
            if i == 1 {
                let mut cur = 0;
                for _ in 0..C {
                    if cur > 50000 { break }
                    dp[cur] = true;
                    cur += V;
                }
            } else {
                for _ in 0..C {
                    for j in (V..=50000).rev() {
                        dp[j] = dp[j] || dp[j-V];
                    }
                }
            }
        }
        println!("{}", if sm&1 == 0 && dp[sm/2] { 1 } else { 0 });
    }
}

/*
평범한 배낭 2 풀이 (150ms)
#![allow(warnings)]
use std::fmt::Write;
use std::io;

fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();

    for _ in 0..3 {
        let N = input() as usize;
        let mut dp = [false; 100001];
        let mut sm = 0;
        let mut kind_of = Vec::new();
        dp[0] = true;
        for i in 0..N {
            let V = input() as usize;
            let mut C = input() as usize;
            sm += V*C;
            for j in 0..C {
                if C == 0 { break; }
                let cnt = std::cmp::min(C, 1<<j);
                kind_of.push(V*cnt);
                C -= cnt;
            }
        }
        for i in kind_of {
            for j in (i..=100000).rev() {
                dp[j] = dp[j] || dp[j-i];
            }
        }
        println!("{}", if sm&1 == 0 && dp[sm/2] { 1 } else { 0 });
    }
}
*/
