# 아이템 10 - equals는 일반 규약을 지켜 재정의하라

Object의 equals 메소드는 그냥 두면 그 클래스의 인스턴스는 오직 자기 자신과만 같게됩니다. (동일성 비교)    
아래 상황 중 하나에 해당한다면 equals 메소드는 재정의하지 않는 것이 최선입니다.   

- 각 인스턴스가 본질적으로 고유하다
- 인스턴스의 '논리적 동치성'(동등성 비교)을 검사할 일이 없다
- 상위 클래스에서 재정의한 equals가 하위 클래스에도 딱 들어맞는다
- 클래스가 private이거나 package-private이고 equals 메소드를 호출할 일이 없다

그렇다면 equals를 재정의해야 할 때는 언제일까요?    
객체 식별성(동일성 비교)가 아니라 논리적 동치성(동등성 비교)를 확인해야 하는데 상위 클래스의 equals 메소드가   
논리적 동치성을 비교하도록 재정의되지 않았을 때입니다.    
예를들면 Integer, String과 같은 값 클래스가 있습니다.     
두 객체가 같은 인스턴스인지 궁금한게 아니라 그 값을 비교하고 싶을 때 재정의해야 합니다.    

## equals 메소드를 재정의할 때는 반드시 일반 규약을 따라야 한다

- 반사성 : null이 아닌 모든 참조 값 x에 대해, x.equals(x)는 true이다.
- 대칭성 : null이 아닌 모든 참조 값 x,y에 대해, x.equals(y)가 true이면 y.equals(x)도 true이다.
- 추이성 : null이 아닌 모든 참조 값 x,y,z에 대해, x.equals(y)가 true이고 y.equals(z)도 true이면 x.equals(z)도 true이다.
- 일관성 : null이 아닌 모든 참조 값 x,y에 대해, x.equals(y)를 반복해서 호출하면 항상 true를 반환하거나 항상 false를 반환한다.
- null-아님 : null이 아닌 모든 참조 값 x에 대해, x.equals(null)은 false이다.

## 정리

꼭 필요한 경우가 아니면 equals를 재정의하지 말아야합니다. 많은 경우에 Object의 equals가 원하는 비교를 정확히 수행해줍니다.         
재정의해야 할 때는 그 클래스의 핵심 필드 모두를 빠짐없이, 다섯 가지 규약을 확실히 지켜가며 비교해야합니다.            
재정의는 직접 작성하는 것보다 IDE에게 맡기는 것이 더 좋은 방법입니다.        

[아이템 10 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter3/item10)                
[아이템 10 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter3/item10)       
