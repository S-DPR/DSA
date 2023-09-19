use std::fmt::Write;
use std::io;
/*
12920번 평범한 배낭 2

무게 K까지 들어가는 가방이 있다.
N개의 물건 종류가 있다. 각 물건은 w만큼의 무게를, v만큼의 가치를, t만큼의 개수를 가진다.
가방에 최대의 가치를 담아보자.

와 세상에 진짜
배낭에 저 개수조건 하나 때려박았다고 난이도가 7단계상승한다고?
아니 근데 진짜 신기한게 이걸 개수를 logT개로 압축은 그렿다 쳐
그런데 5를 1 2 2로, 9를 1 2 4 2로 나누는 발상은..
질문게시판 아니었으면 평생 못풀었다.. 진짜..
*/
fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();

    let N = input() as usize;
    let K = input();
    let mut dp = [0;10001];
    for _ in 0..N {
        let W = input();
        let V = input();
        let mut T = input();
        for bit in 0..=20 {
            if T <= 0 { break; }
            let t = std::cmp::min(T, 1<<bit);
            for i in (0..=K-t*W).rev() {
                if dp[i as usize] == 0 { continue; }
                dp[(i+t*W) as usize] = std::cmp::max(dp[(i+t*W) as usize], dp[i as usize]+V*t);
            }
            if (t*W <= K) {
                dp[(t*W) as usize] = std::cmp::max(dp[(t*W) as usize], t*V);
            }
            T -= t;
        }
    }
    println!("{}", dp.iter().max().unwrap());
}
