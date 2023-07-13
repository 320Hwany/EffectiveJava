# 아이템 45 - 스트림은 주의해서 사용하라

스트림 API는 다량의 데이터 처리 작업을 돕고자 자바 8에 추가되었습니다.        
이 API가 제공하는 추상 개념 중 핵심은 2가지 입니다.    
첫 번째인 '스트림'은 데이터 원소의 유한 혹은 무한 시퀀스를 뜻합니다.     
두 번째인 '파이프라인'은 이 원소들로 수행하는 연산 단계를 표현하는 개념입니다.    

스트림 파이프라인은 소스 스트림에서 시작해 중간 연산이 있고 종단 연산으로 끝납니다.   
중간 연산들은 모두 한 스트림을 다른 스트림으로 변환합니다.     
종단 연산은 마지막 중간 연산이 내놓은 스트림에 최후의 연산을 가합니다.        
예를 들어 원소를 정렬해 컬렉션에 담거나 특정 원소 하나를 선택하거나 모든 원소를 출력하는 것입니다.     

스트림 파이프라인은 지연 평가됩니다. 평가는 종단 연산이 호출될 때 이뤄지며 종단 연산에 쓰이지 않는   
데이터 원소는 계산에 쓰이지 않습니다.      

## Anagrams

Anagram은 철자를 구성하는 알파벳이 같고 순서만 다른 단어를 말합니다.   
맵의 키를 그 단어를 구성하는 철자들을 알파벳 순으로 정렬한 값입니다.     

````
public class Anagrams {

    public static void main(String[] args) throws IOException {
        File dictionary = new File(args[0]);
        int minGroupSize = Integer.parseInt(args[1]);

        Map<String, Set<String>> groups = new HashMap<>();
        try (Scanner s = new Scanner(dictionary)) {
            while (s.hasNext()) {
                String word = s.next();
                groups.computeIfAbsent(alphabetize(word),
                        (unused) -> new TreeSet<>()).add(word);
            }
        }

        for (Set<String> group : groups.values()) {
            if (group.size() >= minGroupSize) {
                System.out.println(group.size() + ": " + group);
            }
        }
    }

    private static String alphabetize(String s) {
        char[] a = s.toCharArray();
        Arrays.sort(a);
        return new String(a);
    }
}
````

## AnagramsBetter

위 코드를 스트림을 적절히 활용하면 깔끔하고 명료해집니다.      

````
public class AnagramsBetter {

    public static void main(String[] args) throws IOException {
        Path dictionary = Paths.get(args[0]);
        int minGroupSize = Integer.parseInt(args[1]);

        try (Stream<String> words = Files.lines(dictionary)) {
            words.collect(Collectors.groupingBy(word -> alphabetize(word)))
                    .values().stream()
                    .filter(group -> group.size() >= minGroupSize)
                    .forEach(group -> System.out.println(group.size() + ": " + group));
        }
    }

    private static String alphabetize(String s) {
        char[] a = s.toCharArray();
        Arrays.sort(a);
        return new String(a);
    }
}
````

람다에서는 타입 이름을 자주 생략하기 때문에 매개변수 이름을 잘 지어야 스트림 파이프라인의 가독성이 유지됩니다.   
또한 alphabetize과 같은 도우미 메소드를 적절히 활용하는 것의 중요성은 스트림 파이프라인에서 더욱 커집니다.      

## 기존 코드를 사용해야할 때

스트림의 남용은 가독성을 저해할 수 있습니다.     
따라서 기존 코드를 스트림을 사용하도록 리팩토링하되 새 코드가 더 나아 보일 때만 반영해야합니다.    

코드 블록에서는 범위 안의 지역 변수를 읽고 수정할 수 있지만 람다에서는 final이거나 사실상 final인 변수만   
읽을 수 있고 지역 변수를 수정하는 건 불가능합니다.    
또한 코드 블록에서는 return 문을 사용해 메소드에서 빠져나가거나 break, continue 문으로 블록 바깥의  
반복문을 종료하거나 반복을 한번 건너 뛸 수 있지만 람다로는 이 중 어떤 것도 할 수 없습니다.     

## 스트림을 사용해야할 때 

1. 원소들의 시퀀스를 일관되게 반환한다
2. 원소들의 시퀀스를 필터링한다
3. 원소들의 시퀀스를 하나의 연산을 사용해 결합한다(더하기, 연결하기, 최솟값 구하기 등)
4. 원소들의 시퀀스를 컬렉션에 모은다
5. 원소들의 시퀀스에서 특정 조건을 만족하는 원소를 찾는다

이러한 일 중 하나를 수행하는 로직이라면 스트림을 적용하기에 좋은 후보입니다.  

## 정리

스트림을 사용해야 멋지게 처리할 수 있는 일이 있고 반복 방식이 더 알맞은 일도 있습니다.    
대부분 이 둘을 조합했을 때 가장 멋지게 해결됩니다.    
스트림과 반복 중 어느 쪽이 나은지 확신하기 어렵다면 둘 다 해보고 더 나은 쪽을 선택하도록 해야합니다.    

[아이템 45 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter7/item45)                                                                         
[아이템 45 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter7/item45)         

