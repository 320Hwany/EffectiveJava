# 아이템 37 - ordinal 인덱싱 대신 EnumMap을 사용하라

## Plant
````java
public class Plant {

    enum LifeCycle {
        ANNUAL, PERENNIAL, BIENNIAL
    }

    final String name;
    final LifeCycle lifeCycle;

    public Plant(String name, LifeCycle lifeCycle) {
        this.name = name;
        this.lifeCycle = lifeCycle;
    }

    @Override
    public String toString() {
        return name;
    }
}
````

위와 같이 Plant 클래스가 있을 때 정원에 심은 Plant들을 배열 하나로 관리하고 이들의 LifeCycle 별로  
묶는다고 해보겠습니다. 생애주기별로 총 3개의 집합을 만들고 정원을 한바퀴 돌며 각 식물을 해당 집합에 넣는 것입니다.      
이때 집합들을 배열 하나에 넣고 생애주기의 ordinal 값을 그 배열의 인덱스로 사용하는 경우에 동작은 하지만   
문제가 많은 코드입니다.    
배열은 제네릭과 호환되지 않으니 비검사 형변환을 수행해야하고 열거 타입과 달리 타입 안전하지 않습니다.   

## EnumMap을 사용
 
위와 같은 문제의 해결책으로 EnumMap이 있습니다. EnumMap은 열거 타입을 키로 사용하도록 설계한 아주 빠른 Map 구현체입니다.     

````java
// given
List<Plant> garden =
        List.of(new Plant("hello1", Plant.LifeCycle.ANNUAL),
                new Plant("hello2", Plant.LifeCycle.BIENNIAL),
                new Plant("hello3", Plant.LifeCycle.BIENNIAL));

Map<Plant.LifeCycle, Set<Plant>> plantsByLifCycle = new EnumMap<>(Plant.LifeCycle.class);

// when
for (Plant.LifeCycle lc : Plant.LifeCycle.values()) {
    plantsByLifCycle.put(lc, new HashSet<>());
}

for (Plant p : garden) {
    plantsByLifCycle.get(p.lifeCycle).add(p);
}
````

EnumMap의 생성자가 받는 키 타입의 Class 객체는 한정적 타입 토큰으로 런타임 제네릭 타입 정보를 제공합니다.      

## 스트림을 사용한 코드 1 - EnumMap을 사용하지 않음

````java
Map<Plant.LifeCycle, List<Plant>> plantsByLifCycle =
        Arrays.stream(gardenArray).collect(groupingBy(p -> p.lifeCycle));
````

## 스트림을 사용한 코드 2 - EnumMap을 이용해 데이터와 열거 타입을 매핑

````java
EnumMap<Plant.LifeCycle, Set<Plant>> plantsByLifCycle =
            Arrays.stream(gardenArray).collect(groupingBy(p -> p.lifeCycle,
                () -> new EnumMap<>(Plant.LifeCycle.class), toSet()));
````

Enum 버전은 언제나 식물의 생애주기당 하나씩의 중첩 맵을 만들지만 스트림 버전에서는 해당 생애주기에  
속하는 식물이 있을 때만 만듭니다.   

## 중첩 EnumMap으로 데이터와 열거 타입 쌍을 연결

### Phase

````java
public enum Phase {

    SOLID, LIQUID, GAS;

    public enum Transition {
        MELT(SOLID, LIQUID), FREEZE(LIQUID, SOLID),
        BOIL(LIQUID, GAS), CONDENSE(GAS, LIQUID);

        private final Phase from;
        private final Phase to;

        Transition(Phase from, Phase to) {
            this.from = from;
            this.to = to;
        }

        // 상전이 맵을 초기화한다
        private static final Map<Phase, Map<Phase, Transition>> m =
                Stream.of(values()).collect(
                        groupingBy(t -> t.from, () -> new EnumMap<>(Phase.class),
                                toMap(t -> t.to, t -> t, (x, y) -> y,
                                        () -> new EnumMap<>(Phase.class))));

        public static Transition from(Phase from, Phase to) {
            return m.get(from).get(to);
        }
    }
}
````

코드가 복잡해보이는데 이 맵의 타입인 `Map<Phase, Map<Phase, Transition>>`은    
"이전 상태에서 '이후 상태에서 전이로의 맵'에 대응시키는 맵"이라는 뜻입니다.      

## 정리

배열의 인덱스를 얻기 위해 ordinal을 쓰는 것은 일반적으로 좋지 않으니 대신 EnumMap을 사용해야 합니다.   
다차원 관계는 `EnumMap<..., EnumMap<...>>` 으로 사용할 수 있습니다.    

[아이템 37 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter6/item37)                                                       
[아이템 37 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter6/item37)            