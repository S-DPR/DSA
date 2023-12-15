#![allow(warnings)]
use std::fmt::Write;
use std::io;
/*
13977번 이항 계수와 쿼리

nCk를 1_000_000_007로 나눈 나머지를 구해보자.
한번 하면 재미 없으니 Q번 해보자.

음..
이거 겨우 팩토리얼 전처리 붙었다고 난이도가 +1된거야..?
난이도의 세계 알수가 없다..
*/
const MOD: i64 = 1_000_000_007;
const MAX: usize = 4_000_000;

fn pow(x: i64, p: i64) -> i64 {
    let mut k = x;
    let mut pp = p;
    let mut ret: i64 = 1;
    while pp > 0 {
        if pp&1 == 1 {
            ret = (ret * k) % MOD;
        }
        k = (k * k) % MOD;
        pp >>= 1;
    }
    return ret;
}

fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();

    let Q = input() as usize;
    let mut A = [1 as i64;MAX+1];
    for i in 1..=MAX {
        A[i] = (A[i-1] * i as i64) % MOD;
    }
    for _ in 0..Q {
        let N = input() as usize;
        let K = input() as usize;
        let up = A[N];
        let down = pow((A[K]*A[N-K])%MOD, MOD-2);
        writeln!(output, "{}", (up*down)%MOD);
    }
    println!("{}", output);
}
