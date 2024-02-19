#![allow(warnings)]
use std::fmt::Write;
use std::io;
use std::collections::VecDeque;
/*
15730번 수영장 사장님

맵이 주어진다. 각 수는 그 수에 있는 암석의 높이를 나타낸다.
물을 최대한 담으려 한다. 이 때, 물을 얼마나 넣을 수 있나 구해보자.

전에 풀었던 수영장 만들기랑 R, C, H 차이만 나는 문제.
대충 O(RCH)정도입니다. 10000^2이라서 C++은 여기 쓰긴 아쉽고, 그래서 Rust 가져왔고..
RCRCH인줄 알았는데 생각해보니 모든 칸이 다 다르면 BFS를 RC번 하진 않더라고요.
확실히 한 번 했던 문제라 그냥 뚝딱뚝딱 만들었습니다.
*/
fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();

    let R = input();
    let C = input();
    let mut N = Vec::new();
    let mut M = [[0;100];100];
    let mut GV = [[false; 100]; 100];
    for i in 0..R as usize {
        for j in 0..C as usize {
            M[i][j] = input();
            N.push(M[i][j]);
        }
    }
    N.sort();
    N.dedup();
    N.reverse();
    let dr: [i64; 4] = [1, -1, 0, 0];
    let dc: [i64; 4] = [0, 0, 1, -1];
    let mut area = 0 as i64;
    for water in N {
        let mut Q = VecDeque::new();
        let mut V = [[false; 100]; 100];
        for r in 1..(R-1) as usize {
            for c in 1..(C-1) as usize {
                if M[r][c] >= water { continue; }
                if GV[r][c] || V[r][c] { continue; }
                Q.push_back([r, c]);
                let mut connEdge = false;
                let mut S = M[r][c];
                let mut cArea = 1;
                let mut visVec = Vec::new();
                visVec.push([r, c]);
                V[r][c] = true;
                while !Q.is_empty() {
                    let cur = Q.pop_front().unwrap();
                    let curR = cur[0];
                    let curC = cur[1];
                    for i in 0..4 {
                        let nr = curR as i64+dr[i];
                        let nc = curC as i64+dc[i];
                        if !(0 <= nr && nr < R) { continue; }
                        if !(0 <= nc && nc < C) { continue; }
                        let unr = nr as usize;
                        let unc = nc as usize;
                        if GV[unr][unc] || V[unr][unc] { continue; }
                        if M[unr][unc] >= water { continue; }
                        connEdge = connEdge || nr == 0 || nr == R-1;
                        connEdge = connEdge || nc == 0 || nc == C-1;
                        Q.push_back([unr, unc]);
                        V[unr][unc] = true;
                        S += M[unr][unc];
                        visVec.push([unr, unc]);
                        cArea += 1;
                    }
                }
                if !connEdge {
                    for i in visVec {
                        let rr = i[0];
                        let cc = i[1];
                        GV[rr][cc] = GV[rr][cc] || V[rr][cc];
                    }
                    area += cArea*water - S;
                }
            }
        }
    }
    println!("{}", area);
}
