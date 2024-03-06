#![allow(warnings)]
use std::fmt::Write;
use std::io;
/*
1047번 울타리

길이가 w인 나무가 x, y에 있다.
나무를 최소한으로 잘라 남은 모든 나무를 하나의 큰 직사각형으로 감싸려 한다.
이 때, 적어도 몇 개의 나무를 잘라야할까?

N이 40.
보통 이 수는 MITM에서 나오는 수인데, 이건 아무리생각해도 MITM은 아닌거같고..
음.. dp 굴리기엔 최적부분구조 아예 안보이고..
그리디? N = 40으로 그리디만 박기엔 좀 아니고, 최적의 해가 예측이 안되는데?
완탐? N^5? 1억? 돌긴도네?

모든 나무의 교차점을 구하고, 교차점 두 개를 잡아 직사각형을 만든다는 아이디어.
내부 나무는 냅두고, 외부 나무만 잘라보자.
외부나무로 부족하면 내부에선 가장 긴 나무 먼저 희생.
이렇게해서 몇 개 잘랐는지 구해보자.. 가 최종 결론.
답지 봤을땐 이게 먼소리여 하고 껐었는데,
풀고보니 알겠다..
*/
fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();

    let N = input() as usize;
    let mut inter = Vec::new();
    let mut A = [[0; 3]; 40];
    for i in 0..N {
        A[i] = [input(), input(), input()];
    }
    for i in 0..N {
        for j in 0..N {
            inter.push([A[i][0], A[j][1]]);
            inter.push([A[j][0], A[i][1]]);
        }
    }
    inter.sort();
    let mut mn: i64 = N as i64;
    for i in 0..inter.len() {
        let [x1, y1] = inter[i];
        for j in i..inter.len() {
            let [x2, y2] = inter[j];
            let [mut len, mut cut] = [0, 0];
            let mut contains = Vec::new();
            for k in 0..N {
                let [x, y, w] = A[k];
                if x1 <= x && x <= x2 && y1 <= y && y <= y2 {
                    contains.push(A[k]);
                    continue;
                }
                len += w;
            }
            contains.sort_by(|i, j| i[2].cmp(&j[2]));
            let width = (x1-x2).abs();
            let height = (y1-y2).abs();
            while !contains.is_empty() && len < 2*(width+height) {
                let [_, _, w] = contains.pop().unwrap();
                len += w;
            }
            mn = std::cmp::min(mn, (N - contains.len()) as i64);
        }
    }
    println!("{}", mn);
}
