#![allow(warnings)]
use std::fmt::Write;
use std::io;
/*
1502번 숫자 퍼즐 ≤ 8

두 개의 숫자가 있다. 이 숫자를 이어보자.
단, 모든 칸을 이동해야한다. 이동할 수 있다면 그 경로를 모두 출력하자.

음..
이게왜플레4

그냥 대충 백트래킹 해주면 끝납니다.
진짜 그거밖에 없어요.
일단 제출해보고 시간초과 뜨는거 본다음에 가지치기해야지..했는데,
머야 퍼센트가 오르네?
바로 속도 빠른 Rust로 가져와서 AC.
아마 곧 골드3정도로 쳐박히지않을까요..
*/
const dr: [i32;4] = [0, 0, 1, -1];
const dc: [i32;4] = [1, -1, 0, 0];

fn looping(r: usize, c: usize, re: usize, ce: usize, cnt: usize, R: usize, C: usize, stk: &mut Vec<[usize; 2]>, V: &mut [[bool;9];9]) -> bool {
    if (r == re && c == ce) {
        return cnt == R*C;
    }
    let mut ret = false;
    for i in 0..4 {
        if ret { break; }
        let nr = (r as i32+dr[i]) as usize;
        let nc = (c as i32+dc[i]) as usize;
        if !(0 < nr && nr <= R) { continue; }
        if !(0 < nc && nc <= C) { continue; }
        if V[nr][nc] { continue; }
        V[nr][nc] = true;
        stk.push([nr, nc]);
        ret = looping(nr, nc, re, ce, cnt+1, R, C, stk, V);
        if ret { break; }
        stk.pop();
        V[nr][nc] = false;
    }
    return ret;
}

fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i32>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();

    let T = input();
    for _ in 0..T {
        let R = input() as usize;
        let C = input() as usize;
        let rs = input() as usize;
        let cs = input() as usize;
        let re = input() as usize;
        let ce = input() as usize;
        let mut vis = [[false; 9]; 9];
        let mut stk = Vec::new();
        stk.push([rs, cs]);
        vis[rs][cs] = true;
        let ret = looping(rs, cs, re, ce, 1, R, C, &mut stk, &mut vis);
        if ret {
            writeln!(output, "{}", 1);
            for i in stk {
                writeln!(output, "{} {}", i[0], i[1]);
            }
        } else {
            writeln!(output, "{}", -1);
        }
    }
    println!("{}", output);
}
