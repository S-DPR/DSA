#![allow(warnings)]
use std::fmt::Write;
use std::io;
/*
1029번 그림 교환

N개의 노드가 있고, 가중치가 있는 완전 그래프가 있다.
1번 노드에서 시작해 최대한 많은 노드를 방문하려 한다.
최대 몇 개 방문 가능할까?
단, 이전에 사용한 간선보다 가중치가 크거나 같은 간선을 사용해야한다.

그냥 대충 쭉 보다가, N은 15.
음.. 아무리봐도 MITM은 아닌거같고..
백트래킹은 더더욱 아닌거같고..
그렇다고 그리디도 아니겠지 설마 N이 15인데.
그럼 남은건? bit dp! 이러면서 풀었습니다.

네.. 그냥 dp에 3차원 공간만 설정하면 AC.
간단하죠.
*/
const MAX_SZ: usize = 15;

fn looping(N: usize, cur: usize, vis: usize, price: usize, dp: &mut [[[i32;1<<MAX_SZ];15];15], G: &mut [[usize;15];15]) -> i32 {
    if vis+1 == 1<<(N+1) { return 0; }
    if dp[cur][price][vis] != -1 { return dp[cur][price][vis]; }
    dp[cur][price][vis] = 1;
    for i in 0..N {
        if i == cur { continue; }
        if vis&(1<<i) != 0 { continue; }
        if G[cur][i] < price { continue; }
        dp[cur][price][vis] = std::cmp::max(dp[cur][price][vis], looping(N, i, vis|(1<<i), G[cur][i], dp, G)+1);
    }
    return dp[cur][price][vis];
}

fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace();//.flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();

    let N: usize = input().parse().unwrap();
    let mut G = [[0;15];15];
    let mut dp = [[[-1;1<<MAX_SZ];15];15];
    for i in 0..N {
        let mut S = input().chars();
        for j in 0..N {
            G[i][j] = S.nth(0).unwrap().to_digit(10).unwrap() as usize;
        }
    }
    println!("{}", looping(N, 0, 1, 0, &mut dp, &mut G));
}
