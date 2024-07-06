#![allow(warnings)]
use std::fmt::Write;
use std::io;
use std::collections::HashSet;
/*
1214번 쿨한 물건 구매

A, B가 주어질 때 Ax+By가 D 이상이 되도록 하는 쌍 (x, y)에 대해,
식의 값이 최소가 되도록 하려한다.
나오는 최솟값을 구해보자.

아니 뭐 한 1년째 북마크에 있는거같아서 그냥 답지 보고 했는데,
옛날에 시도한거중 set에 뭐 넣는건 맞는데 넣는게 틀렸었네요..
아쉽네..

프로베니우스의 동전문제? 이걸 활용하면 좋다고합니다.
한번쯤 봐야겠네..
서로소 p랑 q가 있을 때 pq-p-q 초과의 모든 값은 만들 수 있다였나?
*/
fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();

    let D = input();
    let mut A = input();
    let mut B = input();
    if A > B { std::mem::swap(&mut A, &mut B); } // B가 큼
    if D%A == 0 || D%B == 0 {
        println!("{}", D);
        return;
    }
    let mut vis = HashSet::new();
    let mut prod = 0;
    let mut ret = (D+B-1)/B*B;
    loop {
        let val = B*prod;
        let rem = D-val;
        if val > D || vis.contains(&(A-rem%A)) {
            break;
        }
        vis.insert(A-rem%A);
        ret = std::cmp::min(ret, val+(D-val+A-1)/A*A);
        prod += 1;
    }
    println!("{}", ret);
}
