#![allow(warnings)]
use std::fmt::Write;
use std::io;
use std::mem;
use std::collections::HashSet;
/*
10838번 트리

0이 루트노드이고, 1부터 N-1까지 모두 깊이가 1인 자식으로 두고있는 트리가 있다.
아래 세 쿼리를 처리해보자.
1 a b c : a와 b를 잇는 최단경로의 간선들을 c로 칠한다.
2 a b : a를 b의 자식노드로 옮긴다. 아래 자식도 모두 옮겨지게 된다.
3 a b : a와 b를 잇는 최단경로의 간선들의 색의 개수를 구한다. 중복은 세지 않는다.
초기에는 모든 간선이 0으로 칠해져있으며,
1, 3번 쿼리에서 a와 b는 거리가 1000 이하임이 보장된다.

구아악 끔찍해
그니까.. 이게 그 lca긴한데..
이걸 lca라고 할 수 있냐..?

일반적인 LCA는 쓸 수가 없습니다. 2번이 업데이트연산이라서요.
그래서 부모만을 이용해야하는데..
a와 b의 거리가 1000 이하이고, 쿼리가 최대 30만개. 그리고 시간은 3초.
대충 3억정도 나오니 여기에 믿음을 가져야합니다.
이거 가지면 사실 거의 끝나요.

lca구하는 연산이 정말 단순한데 구현에 코드가 좀 더러워서..
별로 맘에 드는 문제는 아니네요. 풀기는 쉬웠다.
*/
const MAX: usize = 100000;

fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();

    let N = input() as usize;
    let Q = input() as usize;
    let mut P = [0;MAX];
    let mut C = [0;MAX];
    let mut uP = [false;MAX];
    let mut S = HashSet::new();
    uP[0] = true;
    P[0] = 0;
    for _ in 0..Q {
        let q = input() as usize;
        let mut u = input() as usize;
        let mut v = input() as usize;
        if q == 1 {
            let c = input() as usize;
            let mut a = 0;
            let mut uu = u;
            let mut uuu = u;
            let mut vv = v;
            for __ in 0..1000 {
                if uu == 0 { break; }
                uP[uu] = true;
                uu = P[uu];
            }
            for __ in 0..1000 {
                if vv == 0 { break; }
                if uP[vv] {
                    a = vv;
                    break;
                }
                vv = P[vv];
            }
            while u != a {
                C[u] = c;
                u = P[u];
            }
            while v != a {
                C[v] = c;
                v = P[v];
            }
            for __ in 0..1000 {
                if uuu == 0 { break; }
                uP[uuu] = false;
                uuu = P[uuu];
            }
        }
        if q == 2 {
            P[u] = v;
        }
        if q == 3 {
            S.clear();
            let mut a = 0;
            let mut uu = u;
            let mut uuu = u;
            let mut vv = v;
            for __ in 0..1000 {
                if uu == 0 { break; }
                uP[uu] = true;
                uu = P[uu];
            }
            for __ in 0..1000 {
                if vv == 0 { break; }
                if uP[vv] {
                    a = vv;
                    break;
                }
                vv = P[vv];
            }
            while u != a {
                S.insert(C[u]);
                u = P[u];
            }
            while v != a {
                S.insert(C[v]);
                v = P[v];
            }
            for __ in 0..1000 {
                if uuu == 0 { break; }
                uP[uuu] = false;
                uuu = P[uuu];
            }
            writeln!(output, "{}", S.len());
        }
    }
    println!("{}", output);
}
