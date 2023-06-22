# 아이템 12 - toString을 항상 재정의하라

Object의 기본 toString 메소드가 우리가 작성한 클래스에 적합한 문자열을 반환하는 경우는 거의 없습니다.    
이 메소드는 Member@adbbd 처럼 단순히 클래스_이름@16진수로_표시한_해시코드를 반환합니다.    
toString의 일반 규약에 따르면 '간결하면서 사람이 읽기 쉬운 형태의 유익한 정보'를 반환해야 합니다.    
toString의 규약은 '모든 하위 클래스에서 이 메소드를 재정의하라'입니다.    

## 재정의하는 것이 좋은 경우

정적 유틸리티 클래스은 toString을 제공할 이유가 없고 대부분의 열거 타입도 자바가 이미 완벽한 toString을    
제공하니 따로 재정의하지 않아도 됩니다.    
하지만 하위 클래스들이 공유해야 할 문자열 표현이 있는 추상 클래스라면 toString을 재정의해줘야 합니다.   
대다수의 컬렉션 구현체는 추상 컬렉션 클래들의 toString 메소드를 상속해서 사용합니다.    

## 정리

상위 클래스에서 이미 알맞게 정의한 경우를 제외하고 모든 구체 클래스에서 Object의 toString을 재정의하자.        
toString을 재정의한 클래스는 사용하기도 즐겁고 그 클래스를 사용한 시스템을 디버깅하기 쉽게해준다.    
toString은 해당 객체에 관한 명확하고 유용한 정보를 읽기 좋은 형태로 반환해야한다.         

Before       
<img width="400" alt="image" src="https://github.com/320Hwany/EffectiveJava/assets/84896838/5e6148e4-e256-43bc-86c1-01ddcc09d094">

After         
<img width="500" alt="image" src="https://github.com/320Hwany/EffectiveJava/assets/84896838/152dcce1-9509-444e-8575-e2a52d14dd37">      

[아이템 12 - 예제 코드](https://github.com/320Hwany/EffectiveJava/blob/main/src/main/java/effective/item12/Member.java)      
[아이템 12 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/blob/main/src/test/java/effective/item12/MemberTest.java)     

