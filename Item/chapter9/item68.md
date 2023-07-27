# 아이템 68 - 일반적으로 통용되는 명명 규칙을 따르라

자바 플랫폼은 명명 규칙이 잘 정립되어 있으며, 그 중 많은 것이 자바 언어 명세에 기술되어 있습니다.     
철자 규칙은 패키지, 클래스, 인터페이스, 메소드, 필드, 타입 변수의 이름을 다룹니다.       

- 패키지와 모듈 : org.junit.jupiter.api, com.google.common.collect
- 클래스와 인터페이스 : Stream, FutureTask, LinkedHashMap, HttpClient
- 메소드와 필드 : remove, groupingBy, getCrc
- 상수 필드 : MIN_VALUE, NEGATIVE_INFINITY
- 지역 변수 : i, denom, houseNum
- 타입 매개변수 : T, E, K, V, X, R, U, V, T1, T2

패키지와 모듈이름은 점으로 구분하여 계층적으로 짓습니다.   
패키지 이름의 각 요소는 일반적으로 8자 이하의 짧은 단어로 합니다.     
클래스와 인터페이스의 이름은 하나 이상의 단어로 이뤄지며 각 단어는 대문자로 시작합니다.     
메소드와 필드 이름은 첫 글자를 소문자로 쓴다는 점만 빼면 클래스 명명 규칙과 같습니다.    
상수 필드는 구성하는 단어를 모두 대문자로 쓰며 단어 사이는 밑줄로 구분합니다.     
지역 변수에는 다른 멤버와 비슷한 명명 규칙을 적용하지만 약어를 써도 괜찮습니다.    
타입 매개변수 이름은 보통 한 문자로 표현하며 임의 타입은 T, 컬렉션 원소의 타입은 E,    
맵의 키와 값에는 K와 V를, 예외에는 X, 메소드의 반환 타입에는 R을 사용합니다.     

## 정리

표준 명명 규칙을 지키도록 해야합니다. 하지만 더 중요한 것은 속한 조직에서 사용하는 컨벤션을 따르는 것입니다.     

