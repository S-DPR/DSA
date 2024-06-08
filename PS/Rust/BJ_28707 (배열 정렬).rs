#![allow(warnings)]
use std::fmt::Write;
use std::io;
use std::collections::BinaryHeap;
use std::collections::BTreeMap;
/*
28707번 배열 정렬

길이가 N인 배열 A와 그 i번째와 j번째 요소를 옮기기 위해 필요한 비용 w가 K개 주어진다.
A를 정렬하기 위해 최소 얼마의 비용이 필요할까?

오..
이거 한 1년 5개월..전에 본 문젠데,
이게 그 백준 아레나 대회때는 못풀었고, 잊고 살았던 문제입니다.
그래서 에이 어차피 못풀거면 에디토리얼이나 보자라는 마인드가 되어버린김에 그냥 답지 보고 풀었습니다.

사실 답지에서 핵심 아이디어는.. 배열을 노드로보자! 라는거.
N의 크기가 8이하라서 많이많이작은데 솔직히 당황했어요.
진짜 생각 해본적이 없어서 ㅋㅋ

어쨌든.. 그거만 하고 그래프만 평소랑 조금 다르게 그려주면 끝납니다.
신기하네요.
*/
fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();
    
    let N = input() as usize;
    let mut A = Vec::new();
    let mut G = Vec::new();
    for i in 1..=N {
        A.push(input());
    }
    let K = input() as usize;
    for i in 0..K {
        let u = input() as usize - 1;
        let v = input() as usize - 1;
        let w = input();
        G.push((u, v, w));
    }

    let mut V = BTreeMap::new();
    let mut H = BinaryHeap::new();
    V.insert(A.clone(), 0);
    H.push((0, A.clone()));
    while !H.is_empty() {
        let (w, a) = H.pop().unwrap();
        let mut is_sorted = true;
        for i in 1..N {
            is_sorted = is_sorted && a[i-1] <= a[i];
        }
        if is_sorted {
            println!("{}", -w);
            return;
        }
        for (u, v, nw) in &G {
            let mut x = a.clone();
            x.swap(*u, *v);
            if V.contains_key(&x) && V[&x] <= -w+nw {
                continue;
            }
            V.insert(x.clone(), -w+nw);
            H.push((-(-w+nw), x.clone()));
        }
    }
    println!("{}", -1);
}
