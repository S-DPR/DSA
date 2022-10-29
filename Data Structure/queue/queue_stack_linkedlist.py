import sys, time
from collections import deque
input = sys.stdin.readline

class LinkedList:
    def __init__(self, l=None, val=None, r=None):
        self.l = l
        self.v = val
        self.r = r
    def brk(self):
        left = self.l
        right = self.r
        if left: left.changeR(right)
        if right: right.changeL(left)
        del self
    def changeR(self, r):
        self.r = r
    def changeL(self, l):
        self.l = l
    def changeV(self, val):
        self.v = val
    def getR(self):
        return self.r
    def getL(self):
        return self.l
    def getV(self):
        return self.v

class queue_with_stack:
    def __init__(self):
        self.input = list()
        self.output = list()
        self.size = 0
    def push(self, val):
        self.input.append(val)
        self.size += 1
    def pop(self):
        if not self.size: return -1
        self.size -= 1
        if self.output: return self.output.pop()
        while self.input: self.output.append(self.input.pop())
        return self.output.pop()

class queue_with_linkedlist:
    def __init__(self):
        self.head = LinkedList(None, "HEAD", None)
        self.first = self.head
        self.size = 0
    def push(self, val):
        n = LinkedList(self.first, val, None)
        self.first.changeR(n)
        self.size += 1
        self.first = n
    def pop(self):
        if not self.size: return -1
        if self.first == self.head.r:
            self.first = self.head
        res = self.head.r.v
        self.head.r.brk()
        self.size -= 1
        return res
    def __repr__(self):
        res = ""
        k = self.head
        while k:
            res += "<" if k.l else ""
            res += f"{k.v}"
            res += "> " if k.r else " "
            k = k.r
        return res

k = queue_with_linkedlist()
for _ in ' '*int(input()):
    c = list(input().split())
    match c[0]:
        case 'push': k.push(c[1])
        case 'pop': print(k.pop())
        case 'size': print(k.size)
        case 'empty': print(0 if k.size else 1)
        case 'front': print(k.head.r.v if k.size else -1)
        case 'back': print(k.first.v if k.size else -1)
