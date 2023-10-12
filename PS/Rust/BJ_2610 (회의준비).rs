#![allow(warnings)]
use std::fmt::Write;
use std::io;
/*
2610번 회의준비

연결그래프가 아닐 수도 있는 그래프가 주어진다.
각각의 연결요소 중, 연결요소 내의 각 노드와의 최대거리가 최소가 되게 하는 노드들을 구해보자.

문제 귀찮아서 안풀었다가 골드2 채우기 겸 설렁설렁 푼 문제.
난이도가 높진 않습니다. 그냥 dfs나 bfs나 union-find로 각 연결요소 다 처리하고,
플로이드 워셜 대충 써주고, 각 연결요소마다 브루트포스 박아서 최대거리가 최소가 되게 하면 됩니다.

내가 알던 골드2 난이도가 아닌데..
*/
fn union_(u: &mut [usize; 101], x: usize, y: usize) {
    u[find_(u, x)] = u[find_(u, y)];
}

fn find_(u: &mut [usize; 101], x: usize) -> usize {
    if u[x] != x {
        u[x] = find_(u, u[x]);
    }
    return u[x];
}

fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();

    let N = input() as usize;
    let K = input() as usize;
    let mut G = [[1<<29; 101]; 101];
    let mut U = [0; 101];
    let mut V = Vec::new();
    for i in 0..=N {
        V.push(Vec::new());
        U[i] = i;
    }
    for _ in 0..K {
        let u = input() as usize;
        let v = input() as usize;
        G[u][v] = 1;
        G[v][u] = 1;
        union_(&mut U, u, v);
    }
    for i in 1..=N {
        let idx = find_(&mut U, i);
        V[idx].push(i);
        G[i][i] = 0;
    }
    for k in 1..=N {
        for i in 1..=N {
            for j in 1..=N {
                G[i][j] = std::cmp::min(G[i][j], G[i][k]+G[k][j]);
            }
        }
    }
    let mut retC = Vec::new();
    for i in 1..=N {
        if V[i].len() == 0 { continue; }
        let mut curM = 1<<30;
        let mut cur = i;
        for j in &V[i] {
            let mut localM = 0;
            for k in &V[i] {
                localM = std::cmp::max(localM, G[*j][*k]);
            }
            if localM < curM {
                curM = localM;
                cur = *j;
            }
        }
        retC.push(cur);
    }
    retC.sort();
    writeln!(output, "{}", retC.len());
    for i in retC {
        writeln!(output, "{}", i);
    }
    println!("{}", output)
}
