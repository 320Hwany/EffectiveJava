package effective.chapter5.item33;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FavoritesTest {

    @Test
    @DisplayName("타입 안전 이종 컨테이너 패턴")
    void test() {
        // given
        Favorites favorites = new Favorites();

        favorites.putFavorite(String.class, "hello");
        favorites.putFavorite(Integer.class, 1000);
        favorites.putFavorite(Class.class, Favorites.class);

        // when
        String favoriteString = favorites.getFavorite(String.class);
        int favoriteInteger = favorites.getFavorite(Integer.class);
        Class<?> favoriteClass = favorites.getFavorite(Class.class);

        // then
        assertThat(favoriteString).isEqualTo("hello");
        assertThat(favoriteInteger).isEqualTo(1000);
        assertThat(favoriteClass).isEqualTo(Favorites.class);
    }
}