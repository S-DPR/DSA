#![allow(warnings)]
use std::fmt::Write;
use std::io;
/*
24537번 약수 계산

수열 A가 주어진다. 여기서 수를 최대한 고르며 GCD가 K가 되게 하려 한다.
K가 Q개 주어진다. 최대 집합의 크기를 구해보자.
단, gcd가 K가 될 수 없다면 -1을 출력하자.

풀다가 모르겠어서 태그 까니까 그리디+gcd
아니 진짜 태그 도움 하나도 안되네 하면서 끄적끄적 하는데,
와 진짜 그리디에 gcd네

우선.. 약수는 sqrt고 배수는 log. 무조건 로그로 가야한다는 생각을 해야하고.
i에 대해 i를 제외한 i의 배수를 세면서 수열 안에 그 수가 있나 봅니다.
있다면, gcd처리 해주고요.
그렇게 모든 배수를 gcd처리 해줍니다. 그렇게해서 gcd가 i가 아니면 이 수열에서 gcd로 i를 만들 수 없는겁니다.

와.. 개쩐다..
진짜 검증변수 3개 만들고 난리쳤었는데..
*/
fn gcd(x: usize, y: usize) -> usize {
    return if y == 0 { x } else { gcd(y, x%y) };
}

fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();

    const mx: usize = 1_000_000;
    let N = input() as usize;
    let mut cnt = [0;mx+1];
    let mut dp = [0;mx+1];
    for _ in 0..N {
        let x = input() as usize;
        cnt[x] += 1;
    }
    for x in 1..=mx {
        let mut i = x;
        while i <= mx {
            dp[x] += cnt[i];
            i += x;
        }
    }
    for i in 1..=mx {
        if dp[i] == 0 {
            dp[i] = -1;
            continue;
        }
        let mut j = i+i;
        let mut g = if cnt[i] == 0 { 0 } else { i };
        while j <= mx {
            if cnt[j] != 0 {
                g = gcd(g, j);
            }
            j += i;
        }
        if g != i {
            dp[i] = -1;
        }
    }
    let Q = input();
    for _ in 0..Q {
        let x = input() as usize;
        writeln!(output, "{}", dp[x]);
    }
    println!("{}", output);
}
