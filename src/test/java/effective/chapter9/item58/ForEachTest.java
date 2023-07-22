package effective.chapter9.item58;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.*;


public class ForEachTest {

    @Test
    @DisplayName("문제는 없지만 반복자를 사용해야 해서 보기 좋진 않다")
    void test1() {
        // given
        Collection<Suit> suits = List.of(Suit.values());
        Collection<Rank> ranks = List.of(Rank.values());

        // when
        List<Card> deck = new ArrayList<>();

        // then
        for (Iterator<Suit> i = suits.iterator(); i.hasNext(); ) {
            Suit suit = i.next();
            for (Iterator<Rank> j = ranks.iterator(); j.hasNext(); ) {
                deck.add(new Card(suit, j.next()));
            }
        }
        assertThat(deck.size()).isEqualTo(4 * 13);
    }

    @Test
    @DisplayName("컬렉션이나 배열의 중첩 반복을 위한 권장 관용구")
    void test2() {
        // given
        Collection<Suit> suits = List.of(Suit.values());
        Collection<Rank> ranks = List.of(Rank.values());

        // when
        List<Card> deck = new ArrayList<>();

        // then
        for (Suit suit : suits) {
            for (Rank rank : ranks) {
                deck.add(new Card(suit, rank));
            }
        }
        assertThat(deck.size()).isEqualTo(4 * 13);
    }
}
