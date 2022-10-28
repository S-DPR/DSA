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
    
class deque_:
    def __init__(self):
        self.head = LinkedList(None, "HEAD", None)
        self.top = self.head
        self.first = self.head
        self.size = 0
    def append(self, val):
        n = LinkedList(self.top, val, None)
        self.top.changeR(n)
        self.size += 1
        self.top = n
    def appendleft(self, val):
        n = LinkedList(None, val, self.first)
        self.first.changeL(n)
        self.size += 1
        self.first = n
    def pop(self):
        if not self.size: return -1
        if (res := self.top.getV()) == "HEAD":
            res = self.top.l.getV()
            if self.top.l == self.first: self.first = self.top
            self.top.l.brk()
        else:
            self.top = self.top.l
        self.size -= 1
        return res
    def popleft(self):
        if not self.size: return -1
        if (res := self.first.getV()) == "HEAD":
            res = self.first.r.getV()
            if self.first.r == self.top: self.top = self.first
            self.first.r.brk()
        else:
            self.first = self.first.r
        self.size -= 1
        return res
    def peek(self):
        if not self.size: return -1
        if (res := self.top.getV()) == "HEAD":
            res = self.top.l.getV()
        return res
    def peekleft(self):
        if not self.size: return -1
        if (res := self.first.getV()) == "HEAD":
            res = self.first.r.getV()
        return res
    def __repr__(self):
        res = "[ "
        k = self.first
        while k.r:
            res += "<" if k.l else ""
            res += f"{k.v}"
            res += ">" if k.r else ""
            res += " "
            k = k.r
        res += "<" if k.l else ""
        res += f"{k.v}"
        res += ">" if k.r else ""
        res += " ]"
        return res


k = deque_()
for _ in ' '*int(input()):
    c = list(input().split())
    match c[0]:
        case 'push_back': k.appendleft(c[1])
        case 'push_front': k.append(c[1])
        case 'front': print(k.peek())
        case 'back': print(k.peekleft())
        case 'pop_front': print(k.pop())
        case 'pop_back': print(k.popleft())
        case 'size': print(k.size)
        case 'empty': print(1 if not k.size else 0)
    print(k)
