import time
class Queue: # 선형큐, 메모리 누수 심함
    def __init__(self):
        self.Q = [0]
        self.top = 0
        self.rear = 0
        self.next = 1
    def push(self, item):
        if self.top >= len(self.Q): self.Q.extend([0]*(len(self.Q)*2))
        self.Q[self.top] = [item, self.next]
        self.top = self.next
        self.next += 1
    def pop(self):
        k = self.rear
        res, self.rear = self.Q[self.rear]
        self.Q[k] = 0
        return res
    def is_empty(self):
        return self.rear == self.top

k = Queue()
s = time.time()
for i in range(1000000):
    k.push(i); k.push(i+1)
    k.pop(); k.pop()
print(time.time()-s)
input()

'''
import time
def a():
    l = list(range(1000000))
    s = time.time()
    while l: l.pop()
    print(time.time()-s)

    
def b():
    l = list(range(1000000))
    s = time.time()
    while l: l.pop(0)
    print(time.time()-s)

def c():
    l = list(range(1000000))
    s = time.time()
    l.sort()
    while l: l.pop()
    print(time.time()-s)

def d():
    l = list(zip(range(1000000), range(1000000, -1, -1)))
    s = time.time()
    l.sort(key = lambda x: x[0])
    while l: l.pop()
    print(time.time()-s)

from collections import deque
def e():
    l = deque(zip(range(1000000), range(1000000, -1, -1)))
    s = time.time()
    while l: l.popleft()
    print(time.time()-s)

단위: 초(s)
a()
0.027005910873413086
b() # 절! 대! 큐를 구현할 때 list.pop(0)를 사용하지 말 것.
334.3537666797638
c()
0.03200674057006836
d()
0.0960230827331543
e()
0.04100918769836426
'''
