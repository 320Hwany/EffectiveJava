# 아이템 9 : try-finally 보다는 try-with-resources를 사용하라

자바 라이브러리에는 close 메소드를 호출해 직접 닫아줘야 하는 자원이 많습니다.   
예를들어 InputStream, OutputStream, java.sql.Connection 등이 있습니다.     
자원 닫기는 클라이언트가 놓치기 쉬워서 예측할 수 없는 성능 문제로 이어지기도 합니다.    

전통적인 방식으로는 자원이 제대로 닫힘을 보장하는 수단으로 try-finally를 사용했습니다.   

## 단순 try-finally 방식 

### firstLineOfFile
```java
static String firstLineOfFile(String path) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(path));
    try {
        return br.readLine();
    } finally {
        br.close();
    }
}
```
자원을 하나 더 사용해보겠습니다.  

### copy
```java
static void copy(String src, String dst) throws IOException {
    InputStream in = new FileInputStream(src);
    try {
        OutputStream out = new FileOutputStream(dst);
        try {
            byte[] buf = new byte[10000];
            int n;
            while ((n = in.read(buf)) >= 0) {
                out.write(buf, 0, n);
            }
        } finally {
            out.close();
        }
    } finally{
        in.close();
    }
}
```

코드 가독성이 굉장히 떨어지는 것도 문제지만 두번째 예외가 첫번째 예외를 집어삼켜 첫번째 예외에 관한 정보가 남지 않습니다.   

이러한 문제는 try-with-resources를 사용하여 해결해보겠습니다.   

### copy
```java
static void copy(String src, String dst) throws IOException {
    try (InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst)) {
        byte[] buf = new byte[10000];
        int n;
        while ((n = in.read(buf)) >= 0) {  
        out.write(buf, 0, n);
        }
    }
}
```
가독성이 훨씬 좋아졌고 예외를 진단하기도 훨씬 좋습니다.   
또한 try-with-resources에도 catch 절을 사용할 수 있습니다.    

### firstLineOfFile
```java
static String firstLineOfFile(String path) throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
        return br.readLine();
    } catch (IOException e) {
        throw new IllegalArgumentException("파일 읽기에 실패했습니다");
    }
}
```

## 정리

꼭 회수해야 하는 자원을 다룰 때는 try-finally 말고 try-with-resources를 사용하자.    
코드의 가독성은 더 좋아지고 만들어지는 예외 정보도 훨씬 유용하다.   

[아이템 9 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter2/item9)          
