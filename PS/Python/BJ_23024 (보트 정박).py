import sys
input = sys.stdin.readline
splitIO = lambda: map(int, input().split())
"""
23024번 보트 정박

각각 길이가 n, m인 수열 A, B가 주어진다.
B의 i번째 값이 A의 j번째 값보다 작거나 같은 첫 번쨰 수에 대해, i*j의 값을 결과에 더하자.
B의 첫번째수부터 m번째 수까지 위 행위를 했을 때 나오는 최종 값을 구하시오.
단, A에서 한 번 사용한 값은 다시 사용할 수 없다.

와! 세그먼트트리 아시는구나!
정.말.어.렵.습.니.다

세그트리 문제만 열심히 풀 때는 그냥 세그트리겠거니 하면서 풀었기때문에 쉽다 느꼈는데,
이게 세그트리인걸 모르고 만나니 정신이 혼미하네요.
3시간정도 생각해서 세그트리인가? 하고 보니 세그트리 맞더라고요.

그냥 세그트리 구축하고 왼쪽 값 확인하고 오른쪽값 확인하고 아니면 0 출력하면서 쿼리 처리하면 끝나는 문제입니다.
"""
def init(seg, arr, n, s, e):
    if s == e:
        seg[n] = arr[s-1]
    else:
        m = (s + e) >> 1
        seg[n] = max(init(seg, arr, n*2, s, m), init(seg, arr, n*2+1, m+1, e))
    return seg[n]

def query(seg, n, s, e, val):
    if s == e: return s
    m = (s + e) >> 1
    if seg[n*2] >= val:
        return query(seg, n*2, s, m, val)
    elif seg[n*2+1] >= val:
        return query(seg, n*2+1, m+1, e, val)
    return 0

def update(seg, n, s, e, idx, val):
    if not s <= idx <= e: return
    if s != e:
        m = (s + e) >> 1
        update(seg, n*2, s, m, idx, val)
        update(seg, n*2+1, m+1, e, idx, val)
        seg[n] = max(seg[n*2], seg[n*2+1])
    else:
        seg[n] = val

tc = int(input())
for _ in ' '*tc:
    n, m = splitIO()
    seg = [0]*(n*4)
    init(seg, list(splitIO()), 1, 1, n)
    res = 0
    for idx, val in enumerate(splitIO(), 1):
        nth = query(seg, 1, 1, n, val)
        res += idx*nth
        update(seg, 1, 1, n, nth, -1)
    print(res)
