#![allow(warnings)]
use std::fmt::Write;
use std::io;
use std::collections::BinaryHeap;
use std::cmp::Reverse;
/*
24888번 노트 조각

N개의 노드가 있는 그래프가 있다.
1에서 N으로 최단경로로 갈 때, 특정 정점을 모두 방문할 수 있을까?
가능하면 지나치는 노드의 개수와 그 순서를, 불가능하면 -1을 출력하자.
노트를 신경쓰지 않는다면 1부터 N까지 갈 수 있음이 보장된다.

캬
자바랑 Kotlin 열심히 굴렸는데 TLE랑 NumberFormatError나서
하 때려쳐 하고 오랜만에 Rust로 했습니다.
솔직히 dp같은 자료구조 필요 없는거 빼면 진짜 오랜만인듯 ㅋㅋ

그냥 최단경로로 가면서 노트조각 제대로 갱신해주면 됩니다.
원래는 curW+nxtW가 dist[nxtN]보다 작을때만 갱신했지만,
여기선 같아도 됩니다, 같은대신 주운 note개수만 더 많으면 돼요.
그러면 무조건 이득이니까요.

rust 힙쓰기 의외로 간단하더라고요. struct도 필요 없더라.
*/
fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();

    let N = input() as usize;
    let K = input() as usize;
    let mut G = Vec::new();
    let mut A = Vec::new();
    for _ in 0..=N {
        G.push(Vec::new());
        A.push(0);
    }
    for _ in 0..K {
        let u = input() as usize;
        let v = input() as usize;
        let w = input() as usize;
        G[u].push((v, w));
        G[v].push((u, w));
    }
    for i in 1..=N {
        A[i] = input() as usize;
    }

    let mut note: Vec<_> = (0..=N).map(|_| 0).collect();
    let mut dist: Vec<_> = (0..=N).map(|_| (1 as usize) << 60).collect();
    let mut trace: Vec<_> = (0..=N).map(|_| 0).collect();
    let mut pq = BinaryHeap::new();
    dist[1] = 0;
    dist[1] = A[1];
    pq.push(Reverse((0, A[1], 1)));
    while let Some(Reverse((curW, curNT, curN))) = pq.pop() {
        if curNT < note[curN] || dist[curN] < curW { continue; }
        for &(nxtN, nxtW) in &G[curN] {
            let nxtNT = curNT+A[nxtN];
            if curW+nxtW < dist[nxtN] || (curW+nxtW == dist[nxtN] && note[nxtN] < nxtNT) {
                note[nxtN] = nxtNT;
                trace[nxtN] = curN;
                dist[nxtN] = curW+nxtW;
                pq.push(Reverse((dist[nxtN], note[nxtN], nxtN)));
            }
        }
    }
    if note[N] != A.iter().sum() {
        println!("-1");
        return;
    }
    let mut ret = Vec::new();
    let mut cur = N;
    while (cur != 0) {
        ret.push(cur);
        cur = trace[cur];
    }
    ret.reverse();
    let traced = ret.iter()
                    .map(|n| n.to_string())
                    .collect::<Vec<String>>()
                    .join(" ");
    println!("{}", ret.len());
    println!("{}", traced);
}
