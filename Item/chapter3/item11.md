# 아이템 11 - equals를 재정의하려거든 hashCode도 재정의하라

equals를 재정의한 클래스 모두에서 hashCode도 재정의해야 합니다.    
그렇지 않으면 hashCode 일반 규약을 어기게 되어 해당 클래스의 인스턴스를 HashMap, HashSet 같은 컬렉션 원소로    
사용될 때 문제를 일으킵니다.   

## hashCode 일반 규약

다음은 Object 명세에서 발췌한 규약입니다.    

- equals 비교에 사용되는 정보가 변경되지 않았다면, 애플리케이션이 실행되는 동안 그 객체의 hashCode 메소드는         
  항상 같은 값을 반환해야 합니다.
- equals가 두 객체를 같다고 판단했다면, 두 객체의 hashCode는 똑같은 값을 반환해야 합니다.
- equals가 두 객체를 다르다고 판단했더라도, 두 객체의 hashCode가 서로 다른 값을 반환할 필요는 없지만
  다른 객체에 대해서는 다른 값을 반환해야 해시테이블의 성능이 좋아집니다.

특히 2번째 규약에서 알 수 있듯이 논리적으로 같은 객체라면 같은 해시코드를 반환해야하며    
3번째 규약에서 알 수 있듯이 좋은 해시 함수라면 서로 다른 인스턴스에 다른 해시코드를 반환해야 합니다.     
이상적인 해시 함수는 주어진 인스턴스들을 32비트 정수 범위에 균일하게 분배해야 합니다.    

## Objects의 hash

Objects 클래스는 임의의 개수만큼 객체를 받아 해시코드를 계산해주는 정적 메소드인 hash를 제공합니다.    
이 메소드는 단 한 줄로 hashCode 메소드를 완성할 수 있게 해주지만 속도는 더 느립니다.      
입력 인수를 담기 위한 배열이 만들어지고 기본 타입이 있다면 박싱과 언박싱을 거치기 때문입니다.   
그러므로 성능에 민감하지 않은 상황에서만 사용해야 합니다.   

## 정리

equals를 재정의할 때는 hashCode도 반드시 재정의해야 합니다. 그렇지 않으면 프로그램이 제대로 동작하지 않을 수 있습니다.     
재정의한 hashCode는 Object의 API 문서에 기술된 일반 규약을 따라야하며 서로 다른 인스턴스라면 되도록 해시코드도    
서로 다르게 구현해야 합니다. equals와 마찬가지로 IDE에서 hashCode의 구현도 도와주기 때문에 적극적으로 사용하는 것이    
좋은 방법입니다.     

[아이템 11 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter3/item11)             
[아이템 11 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter3/item11)    




