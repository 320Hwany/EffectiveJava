# 이펙티브 자바

이펙티브 자바를 읽고 정리하며 예제 코드와 학습 테스트를 만들어보았습니다.   

## 객체 생성과 파괴

'객체 생성과 파괴' 챕터에서는 객체를 만들어야 할 때와 만들지 말아야할 때를 구분하는 방법         
객체 생성 방법과 불필요한 생성을 피하는 방법, 제때 파괴됨을 보장하고 파괴 전에 수행해야 할 정리 작업을         
관리하는 방법에 대해 정리해보았습니다.   


[아이템 1](https://github.com/320Hwany/EffectiveJava/blob/main/Item/item1.md)     
[아이템 2](https://github.com/320Hwany/EffectiveJava/blob/main/Item/item2.md)   
[아이템 3](https://github.com/320Hwany/EffectiveJava/blob/main/Item/item3.md)    
[아이템 4](https://github.com/320Hwany/EffectiveJava/blob/main/Item/item4.md)   
[아이템 5](https://github.com/320Hwany/EffectiveJava/blob/main/Item/item5.md)   
[아이템 6](https://github.com/320Hwany/EffectiveJava/blob/main/Item/item6.md)   
[아이템 7](https://github.com/320Hwany/EffectiveJava/blob/main/Item/item7.md)     
[아이템 8](https://github.com/320Hwany/EffectiveJava/blob/main/Item/item8.md)    
[아이템 9](https://github.com/320Hwany/EffectiveJava/blob/main/Item/item9.md)        

## 모든 객체의 공통 메소드

Object에서 final이 아닌 메소드(equals, hashCode, toString, clone, finalize)는 모두 재정의를    
염두에 두고 설계된 것이라 재정의시 지켜야 하는 일반 규약이 명확이 정의되어 있습니다.    
Object를 상속하는 클래스, 즉 모든 클래스는 이 메소드들을 일반 규약에 맞게 재정의해야 합니다.   
일반 규약을 지키지 않으면 HashMap, HashSet과 같은 클래스가 제대로 동작하지 않을 수 있습니다.     
이번 챕터에서는 이러한 메소드들을 어떻게 재정의해야 하는 지에 대해 정리해보았습니다.      

[아이템 10](https://github.com/320Hwany/EffectiveJava/blob/main/Item/item10.md)        
[아이템 11](https://github.com/320Hwany/EffectiveJava/blob/main/Item/item11.md)    
[아이템 12](https://github.com/320Hwany/EffectiveJava/blob/main/Item/item12.md)   
[아이템 13](https://github.com/320Hwany/EffectiveJava/blob/main/Item/item13.md)    
[아이템 14](https://github.com/320Hwany/EffectiveJava/blob/main/Item/item14.md)     

