package effective.chapter7.item45;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TWO;
import static org.assertj.core.api.Assertions.assertThat;

public class StreamTest {

    @Test
    @DisplayName("메르센 소수를 도우미 메소드와 스트림을 이용해서 구한다")
    void test1() {
        // when
        List<BigInteger> collect = primes().map(p -> TWO.pow(p.intValueExact()).subtract(ONE))
                .filter(mersenne -> mersenne.isProbablePrime(50))
                .limit(20)
                .toList();

        // then
        assertThat(collect.size()).isEqualTo(20);
    }

    private static Stream<BigInteger> primes() {
        return Stream.iterate(TWO, BigInteger::nextProbablePrime);
    }
}
