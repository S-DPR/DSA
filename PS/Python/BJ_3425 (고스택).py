import sys
input = sys.stdin.readline
plus_inf = 10**9
minus_inf = -10**9
"""
3425번 고스택

스택 구현문제인데, 좀 구현할게 많은 스택입니다.
그래서 일반 스택 구현문제가 실버 5에 박혀있는데, 이건 골드 3에 있습니다.
실제로 실수할거리가 너무 많아요.
꼼꼼하게 구현 안하면 틀렸습니다 맞다가 때려칠 수도 있다고 봅니다..

딱히 특별한부분은 없습니다. 그냥 구현문제라서요.
"""
class go_stack:
    def __init__(self):
        self.s = []
        self.C = []

    def command_add(self, command):
        self.C.append(command)

    def command_exe(self, num):
        TEST_RANGE = self.TEST_RANGE
        self.s = [num]
        E = 0
        for i in self.C:
            k = i[0]
            if E:
                return 'ERROR'
            if k == 'END':
                if len(self.s) != 1:
                    ans = "ERROR"
                else:
                    ans = self.s[0]
                self.s = []
                return ans
            elif k == 'NUM':
                self.NUM(i[1])
            elif k == 'POP':
                if self.POP() is False:
                    E = 1
            elif k == 'INV':
                if self.INV() is False:
                    E = 1
            elif k == 'DUP':
                if self.DUP() is False:
                    E = 1
            elif k == 'SWP':
                if self.SWP() is False:
                    E = 1
            elif k == 'ADD':
                if self.ADD() is False or TEST_RANGE() is False:
                    E = 1
            elif k == 'SUB':
                if self.SUB() is False or TEST_RANGE() is False:
                    E = 1
            elif k == 'MUL':
                if self.MUL() is False or TEST_RANGE() is False:
                    E = 1
            elif k == 'DIV':
                if self.DIV() is False or TEST_RANGE() is False:
                    E = 1
            elif k == 'MOD':
                if self.MOD() is False or TEST_RANGE() is False:
                    E = 1

    def TEST_RANGE(self):
        s = self.s
        return minus_inf <= s[-1] <= plus_inf
    
    def NUM(self, x):
        self.s.append(int(x))
        return True
        
    def POP(self):
        s = self.s
        return s.pop() if s else False

    def INV(self):
        s = self.s
        if s:
            s[-1] = -s[-1]
            return True
        else:
            return False
    
    def DUP(self):
        s = self.s
        if s:
            s.append(s[-1])
            return True
        else:
            return False

    def SWP(self):
        s = self.s
        if len(s) >= 2:
            s[-2], s[-1] = s[-1], s[-2]
            return True
        else:
            return False
        
    def ADD(self):
        s = self.s
        if len(s) >= 2:
            a, b = s.pop(), s.pop()
            s.append(a+b)
            return True
        else:
            return False

    def SUB(self):
        s = self.s
        if len(s) >= 2:
            a, b = s.pop(), s.pop()
            s.append(b-a)
            return True
        else:
            return False
        
    def MUL(self):
        s = self.s
        if len(s) >= 2:
            a, b = s.pop(), s.pop()
            s.append(a*b)
            return True
        else:
            return False

    def DIV(self):
        s = self.s
        if len(s) >= 2:
            if s[-1] == 0: return False
            a, b = s.pop(), s.pop()
            s.append(abs(b)//abs(a))
            if (b < 0 and a > 0) or (b > 0 and a < 0):
                s[-1] = -s[-1]
            return True
        else:
            return False

    def MOD(self):
        s = self.s
        if len(s) >= 2:
            if s[-1] == 0: return False
            a, b = s.pop(), s.pop()
            s.append(abs(b) % abs(a))
            if b < 0:
                s[-1] = -s[-1]
            return True
        else:
            return False
        
while 1:
    prob = go_stack()
    while not prob.C or prob.C[-1] not in[['END'], ['QUIT']]:
        prob.command_add(list(input().rstrip().split()))
    if prob.C[-1] == ['QUIT']: break
    for _ in ' '*int(input()):
        print(prob.command_exe(int(input())))
    input()
    print()
