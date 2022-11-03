import sys
input = sys.stdin.readline
"""
19582번 200년간 폐관수련했더니 PS 최강자가 된 건에 대하여

수열의 길이 A가 주어집니다.
A의 각 원소는 a, b로 이루어져있습니다.
이 때, 첫 원소부터 b를 계속 더해갑시다.
그러다가 다음 원소의 a보다 누적합이 더 커진다면, 더했던 b중 하나를 뺄 수 있습니다.
이렇게하여 해당수열이 b를 최대 한번만 빼내어 모든 원소를 더할 수 있으면 Kkeo-eok을, 아니면 Zzz를 출력해주세요.

꽤 재밌는 문제입니다.
단도직입적으로 그냥 푸는 알고리즘을 쓴다면,
그냥 다 더하다가 다음 a에 막힐 때 이전에서 얻은 값중 최댓값을 빼봅니다.
만약에 그렇게해서 다음 b를 더할 수 있다면 더하고,
그래도 못하면 그 b를 포기합니다.
만약에 한번 더 a보다 누적값이 더 커진다면 그대로 Zzz를 출력하고 종료합니다.
"""
val = giveup = 0
get_val_list = []
for _ in ' '*int(input()):
    ban_val, get_val = map(int, input().split())
    get_val_list.append(get_val)
    if val <= ban_val: val += get_val
    elif not giveup:
        t = max(get_val_list)
        if val-t <= ban_val:
            val += get_val-t
        giveup = 1
    else:
        print('Zzz')
        break
else:
    print('Kkeo-eok')
