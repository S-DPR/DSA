#![allow(warnings)]
use std::fmt::Write;
use std::io;
/*
1154번 팀 편성

그래프를 둘로 나눠 각 그래프가 완전그래프가 되게 할려고 한다.
정점을 어떻게 나눠야할까?

하아..
너무.. 너무 당연한 아이디어..
하지만 너무 어렵고..

"그래프를 둘로 나눠"가 핵심.
결국 첫 팀에 1 넣어두고, 2는 1을 담을 수 있나 테스트.
담을 수 있으면 1에 담고, 아니면 2에담고..
이거를 모든 경우에 대해 반복.
dfs에 완탐, 완벽하죠. 질문게시판 코드 분석하면서 깨달음을 얻었습니다..
*/
fn dfs(G: &[[bool; 1001]; 1001], R1: &mut Vec<usize>, R2: &mut Vec<usize>, N: usize, x: usize) -> bool {
    if x == N+1 { return true; }
    let mut r1f = true;
    for i in R1.into_iter() {
        r1f = r1f && G[x][*i];
    }
    if r1f {
        R1.push(x);
        if dfs(G, R1, R2, N, x+1) {
            return true;
        };
        R1.pop();
    }

    let mut r2f = true;
    for i in R2.into_iter() {
        r2f = r2f && G[x][*i];
    }
    if r2f {
        R2.push(x);
        if dfs(G, R1, R2, N, x+1) {
            return true;
        };
        R2.pop();
    }
    false
}

fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();

    let N = input() as usize;
    let mut G = [[false; 1001]; 1001];
    let mut R1 = Vec::new();
    let mut R2 = Vec::new();

    loop {
        let u = input();
        let v = input();
        if u < 0 { break; }
        G[u as usize][v as usize] = true;
        G[v as usize][u as usize] = true;
    }
    for i in 1..1000 {
        G[i][i] = true;
    }

    let ret = dfs(&G, &mut R1, &mut R2, N, 1);

    if ret {
        println!("1");
        println!("{} -1", R1.iter().map(|x| x.to_string()).collect::<Vec<_>>().join(" "));
        println!("{} -1", R2.iter().map(|x| x.to_string()).collect::<Vec<_>>().join(" "));
    } else {
        println!("-1");
    }
}
