#include <iostream>
#include <vector>
#include <algorithm>
#include <cmath>
#include <deque>
#define ll long long
using namespace std;
/*
10712번 財宝

시장가치가 X, 실제가치가 Y인 보물이 N개 있다.
이 보물을 둘이서 나누려는데, 시장가치의 합이 D 이하이길 바란다.
이 때, 실제가치의 차가 최대가 될 때 그 값을 구하시오.
단, 둘 모두 가져가지 않는 보물이 있을 수 있음을 유의하라.

MITM 덱 투포인터 갖다 써야하는 문제.
MITM으로 나눠줍니다. 이 때, 세 경우(A가 가져감, B가 가져감, 둘다 안가져감)로 나눠야함을 감안합니다.
나온 L, R을 x기준으로, x가 같다면 y의 내림차순으로 정렬합니다.

이제 투포인터를 써줄겁니다. 하나는 L, 하나는 R포인터입니다. 둘다 0으로 시작합니다.
L이 정해지면 R의 최댓값이 정해집니다. 이런걸 효율적으로 처리하는건 단조큐입니다. 덱을 써서 구현합니다.

음.. MTIM인건 바로 알았는데 이후꺼를 몰라서 못풀었던 문제.
솔직히 다시봐도 쉽다! 란 생각은 안드네요..
*/
struct Info {
    ll x, y;
};

int N;
ll D;
vector<Info> A, L, R;

void loop(int s, int e, ll x, ll y, vector<Info> &arr) {
    if (s == e) {
        arr.push_back({x, y});
        return;
    }
    loop(s + 1, e, x + A[s].x, y + A[s].y, arr); // 내가 가져감
    loop(s + 1, e, x - A[s].x, y - A[s].y, arr); // 상대가 가져감
    loop(s + 1, e, x, y, arr);                   // 아무도 안가져감
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    cin >> N >> D;
    A.resize(N);
    for (int i = 0; i < N; i++) {
        cin >> A[i].x >> A[i].y;
    }

    loop(0, N / 2, 0, 0, L);
    loop(N / 2, N, 0, 0, R);

    sort(L.begin(), L.end(), [](const Info &a, const Info &b) {
        if (a.x != b.x) return a.x < b.x;
        return a.y > b.y;
    });
    sort(R.begin(), R.end(), [](const Info &a, const Info &b) {
        if (a.x != b.x) return a.x < b.x;
        return a.y > b.y;
    });

    ll mx = 0;
    deque<Info> deq;
    for (int i = 0, j = 0; i < L.size(); i++) {
        while (j < R.size() && R[j].x < L[i].x && abs(R[j].x - L[i].x) > D) {
            j++;
        }
        while (j < R.size() && abs(R[j].x - L[i].x) <= D) {
            while (deq.size() && deq.front().y < R[j].y) {
                deq.pop_front();
            }
            deq.push_back(R[j++]);
        }
        while (deq.size() && abs(L[i].x - deq.front().x) > D) {
            deq.pop_front();
        }
        if (deq.size()) mx = max(mx, deq.front().y - L[i].y);
    }
    cout << mx << "\n";

    return 0;
}
