# 아이템 53 - 가변인수는 신중히 사용하라

가변인수 메소드는 명시한 타입의 인수를 0개 이상 받을 수 있습니다.     
가변인수 메소드를 호출하면 가장 먼저 인수의 갯수와 길이가 같은 배열을 만들고     
인수들을 이 배열에 저장하여 가변인수 메소드에 건네줍니다.      

## 간단한 가변인수 활용 예

````java
public class VariableArgument {
    
    public static int sum(int... args) {
        int sum = 0;
        for (int arg : args) {
            sum += arg;
        }
        return sum;
    }
    
    ...
}
````

## 인수가 1개 이상이어야 하는 가변인수 메소드 - 잘못 구현한 예!

이 방식의 문제점은 인수를 0개만 넣어 호출하면 컴파일 타임이 아님 런타임에 실패한다는 점입니다.      

````java
public class VariableArgument {

    ...
    
    public static int minBad(int... args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("인수가 1개 이상 필요합니다");
        }
        int min = args[0];
        for (int i = 1; i < args.length; i++) {
            if (args[i] < min) {
                min = args[i];
            }
        }
        return min;
    }
    
    ...
}
````

## 인수가 1개 이상이어야 할 때 가변인수를 제대로 사용하는 방법

첫 번째는 필수 인수를 1개받고 두 번째는 가변인수르 받으면 인수를 0개 넣었을 경우    
컴파일 시점에 에러가 발생합니다.       

````java
public class VariableArgument {
    
    ... 
    
    public static int minGood(int firstArg, int... remainingArgs) {
        int min = firstArg;
        for (int arg : remainingArgs) {
            if (arg < min) {
                min = arg;
            }
        }
        return min;
    }
}
````
 
성능에 민감한 상황이라면 가변인수가 걸림돌이 될 수 있으므로 오버로딩을 하여 여러 메소드를 정의할 수 있습니다.        

## 정리

인수 개수가 일정하지 않은 메소드를 정의해야 한다면 가변인수가 반드시 필요합니다.   
메소드를 정의할 때 필수 매개변수는 가변인수 앞에 두고 가변인수를 사용할 때는 성능문제까지 고려해야 합니다.    

[아이템 53 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter8/item53)                                                                                       
[아이템 53 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter8/item53)       