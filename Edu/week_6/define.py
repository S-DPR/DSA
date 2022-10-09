"""
파이썬 함수 정의는 매우 쉽다.
그냥 def쓰고 이름 쓰고 인수 쓰고 : 쓴다음 들여쓰기 해서 함수내용 쓰면된다.

파이썬은 동일한 이름의 함수를 허용하지 않는다.
factorial(k, r) 함수가 있는데, 인수 다르다고
def factorial(k): 이러면 덮어씌워진다.
"""

def factorial(k, r): # 이 함수는 무시되고
    print(k, r)

def factorial(k): # 나중에 선언된 이 함수를 적용하게 된다.
    return k*factorial(k-1) if k > 1 else 1

print(factorial(4)) # result : 24

#print(factorial(3, 5))
# TypeError : factorial() takes 1 positional argument but 2 were given
# 타입에러 : factorial()은 1개의 인수를 받지만 2개가 주어졌습니다.

"""
'하아니 그러면 파이썬은 읽는사람이 알아서 함수 반환 타입같은거 다 추측해야함?
양아치 아님????' 할 수 있겠지만,
우리가 안써서 그렇지 타입이랑 반환타입 다 쓸 수 있다.
def [Name](Variable:Type, ...) -> returnType:
대신 강제가 아니다.
"""

def myReturnIsVoidButILoveInt(a:int, b:int) -> int:
    print(a+b) # 문제없음.
myReturnIsVoidButILoveInt(3, 5) # result : 8
myReturnIsVoidButILoveInt('Love', 'Int') # 문제 없음, result : LoveInt
