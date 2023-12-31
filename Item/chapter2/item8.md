# 아이템 8 - finalizer와 cleaner 사용을 피하라

자바는 2가지 객체 소멸자를 제공합니다. 그 중 finalizer는 예측할 수 없고 상황에 따라 위험할 수 있어     
일반적으로 불필요합니다. cleaner는 finalizer 보다는 덜 위험하지만 여전히 예측할 수 없고 느립니다.    

## finalizer와 cleaner를 사용해서는 안되는 이유

1. finalizer와 cleaner는 즉시 수행된다는 보장이 없습니다.   

객체에 접근할 수 없게 된 후 finalizer나 cleaner가 실행되기까지 얼마나 걸릴지 알 수 없습니다.  
즉, finalizer와 cleaner로는 제때 실행되어야 하는 작업은 절대 할 수 없습니다.   

2. finalizer와 cleaner는 수행 시점뿐만 아니라 수행 여부조차 보장하지 않습니다.   

접근할 수 없는 일부 객체에 딸린 종료 작업을 전혀 수행하지 못한 채 프로그램이 중단될 수도 있습니다.   
상태를 영구적으로 수정하는 작업에서는 절대 finalizer나 cleaner에 의존해서는 안됩니다.    

3. finalizer 동작 중 발생한 예외는 무시되며 처리할 작업이 남았더라도 그 순간 종료됩니다.   

잡지 못한 예외 때문에 해당 객체는 자칫 마무리가 덜 된 상태로 남을 수 있습니다.   

4. finalizer와 cleanersms 심각한 성능 문제도 동반합니다.   

finalizer가 가비지 컬렉터의 효율을 떨어뜨립니다. cleaner도 클래스의 모든 인스턴스를 수거하는 형태로    
사용하면 성능은 finalizer와 비슷합니다.    

5. finalizer를 사용한 클래스는 finalizer 공격에 노출되어 심각한 보안 문제를 일으킬 수 있습니다.    

생성자나 직렬화 과정에서 예외가 발생하면 이 생성되다 만 객체에서 악의적인 하위 클래스의 finalizer가    
수행될 수 있습니다.       

## 사용해도 될 경우

1. 자원의 소유자가 close 메소드를 호출하지 않는 것에 대비한 안전망 역할    

클라이언트가 하지 않는 자원 회수를 늦게라도 해주는 것이 아예 안하는 것보다는 나으니    
안정망 역할을 할 때 사용할 수 있습니다.    

2. 네이티브 피어와 연결된 객체 

네이티브 피어란 일반 자바 객체가 네이티브 메소드를 통해 기능을 위임한 네이티브 객체를 말합니다.   
네이티브 피어는 자바 객체가 아니니 가비지 컬렉터가 그 존재를 알지 못합니다.    
자바 피어를 회수할 때 네이티브 객체까지는 회수하지 못하므로 cleaner나 finalizer가 나서서   
처리하기에 적당한 작업이라고 할 수 있습니다.    

## 정리

cleaner는 안정망 역할이나 중요하지 않은 네이티브 자원 회수용으로만 사용해야 합니다.      
물론 이런 경우라도 불확실성과 성능 저하에 주의해야 합니다.    

[아이템 8 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter2/item8)                
