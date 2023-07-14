# 아이템 46 - 스트림에서는 부작용 없는 함수를 사용하라

스트림 패러다임의 핵심은 계산을 일련의 변환으로 재구성하는 부분입니다.   
이때 각 변환 단계는 가능한 한 이전 단계의 결과를 받아 처리하는 순수 함수여야 합니다.        
순수 함수란 오직 입력만이 결과에 영향을 주는 함수를 말합니다.    
다른 가변 상태를 참조하지 않고 함수 스스로도 다른 상태를 변경하지 않아야 합니다.    

## Collector - 수집기

수집기를 사용하면 스트림의 원소를 손쉽게 컬렉션으로 모을 수 있습니다.         
수집기는 총 3가지로 toList(), toSet(), toCollection(collectionFactor)가 있습니다.    

### 빈도표에서 가장 흔한 단어 10개를 뽑아내는 파이프라인

````
@Test
void test1() {
    // given
    Map<String, Integer> freq = new HashMap<>();

    // 단어와 빈도수 추가
    freq.put("apple", 5);
    freq.put("banana", 3);
    freq.put("orange", 7);
    freq.put("grape", 2);
    freq.put("kiwi", 4);
    freq.put("pear", 6);
    freq.put("pineapple", 8);
    freq.put("mango", 9);
    freq.put("strawberry", 10);
    freq.put("cherry", 1);

    // when
    List<String> topTen = freq.keySet().stream()
            .sorted(comparing(freq::get).reversed())
            .limit(10)
            .toList();

    // then
    assertThat(topTen.size()).isEqualTo(10);
    assertThat(topTen.get(0)).isEqualTo("strawberry");
    assertThat(topTen.get(9)).isEqualTo("cherry");
}
````

### toMap 수집기를 사용하여 문자열을 열거 타입 상수에 매핑한다

````
@Test
void test2() {
    // given && when
    Map<String, Operation> stringToEnum = Stream.of(Operation.values())
            .collect(toMap(Objects::toString, e -> e));

    // then
    assertThat(stringToEnum.get("+")).isEqualTo(Operation.PLUS);
    assertThat(stringToEnum.get("-")).isEqualTo(Operation.MINUS);
    assertThat(stringToEnum.get("*")).isEqualTo(Operation.TIMES);
    assertThat(stringToEnum.get("/")).isEqualTo(Operation.DIVIDE);
}
````

### 각 키와 해당 키의 특정 원소를 연관 짓는 맵을 생성하는 수집기

````
@Test
void test3() {
    // given
    Artist artist1 = new Artist("artist1");
    Artist artist2 = new Artist("artist2");

    Albums album1 = new Albums(artist1, "album1", 10000);
    Albums album2 = new Albums(artist1, "album2", 20000);
    Albums album3 = new Albums(artist1, "album3", 30000);
    Albums album4 = new Albums(artist2, "album4", 40000);
    Albums album5 = new Albums(artist2, "album5", 50000);

    List<Albums> albums = List.of(album1, album2, album3, album4, album5);

    // when
    Map<Artist, Albums> topHits = albums.stream()
            .collect(toMap(Albums::getArtist, a -> a, maxBy(comparing(Albums::getSales))));

    // then
    Albums artist1Album = topHits.get(artist1);
    Albums artist2Album = topHits.get(artist2);
    assertThat(artist1Album.getAlbumName()).isEqualTo("album3");
    assertThat(artist2Album.getAlbumName()).isEqualTo("album5");
}
````

### 마지막에 쓴 값을 취하는 수집기

````
@Test
void test4() {
    // given
    Artist artist1 = new Artist("artist1");
    Artist artist2 = new Artist("artist2");

    Albums album1 = new Albums(artist1, "album1", 10000);
    Albums album2 = new Albums(artist1, "album2", 20000);
    Albums album3 = new Albums(artist1, "album3", 30000);
    Albums album4 = new Albums(artist2, "album4", 40000);
    Albums album5 = new Albums(artist2, "album5", 50000);

    List<Albums> albums = List.of(album1, album2, album3, album4, album5);

    // when
    Map<Artist, String> collect = albums.stream()
            .collect(toMap(Albums::getArtist, Albums::getAlbumName, (oldVal, newVal) -> newVal));

    // then
    assertThat(collect.get(artist1)).isEqualTo("album3");
    assertThat(collect.get(artist2)).isEqualTo("album5");
}
````

### groupingBy 분류 함수 하나를 인수로 받아 맵을 반환한다

````
@Test
void test5() {
    // given
    Artist artist1 = new Artist("artist1");
    Artist artist2 = new Artist("artist2");

    Albums album1 = new Albums(artist1, "album1", 10000);
    Albums album2 = new Albums(artist1, "album2", 20000);
    Albums album3 = new Albums(artist1, "album3", 30000);
    Albums album4 = new Albums(artist2, "album4", 40000);
    Albums album5 = new Albums(artist2, "album5", 50000);

    List<Albums> albums = List.of(album1, album2, album3, album4, album5);

    // when
    Map<Artist, List<Albums>> artistGroup = albums.stream()
            .collect(groupingBy(Albums::getArtist));

    // then
    List<Albums> artist1Group = artistGroup.get(artist1);
    List<Albums> artist2Group = artistGroup.get(artist2);

    assertThat(artistGroup.size()).isEqualTo(2);
    assertThat(artist1Group.size()).isEqualTo(3);
    assertThat(artist2Group.size()).isEqualTo(2);
}
````

## 정리

스트림 파이프라인 프로그래밍의 핵심은 부작용 없는 함수 객체에 있습니다.       
스트림뿐 아니라 스트림 관련 객체에 건네지는 모든 함수 객체가 부작용이 없어야 합니다.       
종단 연산 중 forEach는 스트림이 수행한 계산 결과를 보고할 때만 이용해야 합니다.    
계산 자체에는 이용하지 말아야 합니다.     
스트림을 잘 사용하려면 수집기를 알아둬야 하는데 가장 중요한 수집기 팩토리에는   
toList, toSet, toMap, groupingBy, joining이 있습니다.    

[아이템 46 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter7/item46)                                                                          
[아이템 46 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter7/item46)         