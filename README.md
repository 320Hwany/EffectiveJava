# 이펙티브 자바

이펙티브 자바를 읽고 정리하며 예제 코드와 학습 테스트를 만들어보았습니다.   

## 객체 생성과 파괴

'객체 생성과 파괴' 챕터에서는 객체를 만들어야 할 때와 만들지 말아야할 때를 구분하는 방법         
객체 생성 방법과 불필요한 생성을 피하는 방법, 제때 파괴됨을 보장하고 파괴 전에 수행해야 할 정리 작업을         
관리하는 방법에 대해 정리해보았습니다.   


[아이템 1](https://github.com/320Hwany/EffectiveJava/blob/main/Item/chapter2/item1.md)       
[아이템 2](https://github.com/320Hwany/EffectiveJava/blob/main/Item/chapter2/item2.md)          
[아이템 3](https://github.com/320Hwany/EffectiveJava/blob/main/Item/chapter2/item3.md)              
[아이템 4](https://github.com/320Hwany/EffectiveJava/blob/main/Item/chapter2/item4.md)        
[아이템 5](https://github.com/320Hwany/EffectiveJava/blob/main/Item/chapter2/item5.md)        
[아이템 6](https://github.com/320Hwany/EffectiveJava/blob/main/Item/chapter2/item6.md)         
[아이템 7](https://github.com/320Hwany/EffectiveJava/blob/main/Item/chapter2/item7.md)         
[아이템 8](https://github.com/320Hwany/EffectiveJava/blob/main/Item/chapter2/item8.md)              
[아이템 9](https://github.com/320Hwany/EffectiveJava/blob/main/Item/chapter2/item9.md)           

## 모든 객체의 공통 메소드

Object에서 final이 아닌 메소드(equals, hashCode, toString, clone, finalize)는 모두 재정의를    
염두에 두고 설계된 것이라 재정의시 지켜야 하는 일반 규약이 명확이 정의되어 있습니다.    
Object를 상속하는 클래스, 즉 모든 클래스는 이 메소드들을 일반 규약에 맞게 재정의해야 합니다.   
일반 규약을 지키지 않으면 HashMap, HashSet과 같은 클래스가 제대로 동작하지 않을 수 있습니다.     
이번 챕터에서는 이러한 메소드들을 어떻게 재정의해야 하는 지에 대해 정리해보았습니다.      

[아이템 10](https://github.com/320Hwany/EffectiveJava/blob/main/Item/chapter3/item10.md)             
[아이템 11](https://github.com/320Hwany/EffectiveJava/blob/main/Item/chapter3/item11.md)         
[아이템 12](https://github.com/320Hwany/EffectiveJava/blob/main/Item/chapter3/item12.md)        
[아이템 13](https://github.com/320Hwany/EffectiveJava/blob/main/Item/chapter3/item13.md)         
[아이템 14](https://github.com/320Hwany/EffectiveJava/blob/main/Item/chapter3/item14.md)     

## 클래스와 인터페이스

추상화의 기본 단위인 클래스와 인터페이스는 자바 언어의 핵심입니다. 그래서 자바 언어에는 클래스와 인터페이스 설계에   
사용하는 강력한 요소가 많이 있습니다. 이번 챕터에서는 이런 요소들을 적절히 활용하여 클래스와 인터페이스를  
쓰기 편하고, 견고하며, 유연하게 만드는 방법에 대해 정리해보았습니다.    

[아이템 15](https://github.com/320Hwany/EffectiveJava/blob/main/Item/chapter4/item15.md)        
[아이템 16](https://github.com/320Hwany/EffectiveJava/blob/main/Item/chapter4/item16.md)        
[아이템 17](https://github.com/320Hwany/EffectiveJava/blob/main/Item/chapter4/item17.md)             
[아이템 18](https://github.com/320Hwany/EffectiveJava/blob/main/Item/chapter4/item18.md)                  
[아이템 19](https://github.com/320Hwany/EffectiveJava/blob/main/Item/chapter4/item19.md)                   
[아이템 20](https://github.com/320Hwany/EffectiveJava/blob/main/Item/chapter4/item20.md)                   
[아이템 21](https://github.com/320Hwany/EffectiveJava/blob/main/Item/chapter4/item21.md)                   

