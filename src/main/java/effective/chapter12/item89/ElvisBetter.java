package effective.chapter12.item89;

import java.util.Arrays;

// 열거 타입 싱글톤 - 전통적인 싱글톤보다 우수하다
public enum ElvisBetter {

    INSTANCE;

    private String[] favoriteSongs = {"Hound Dogs", "Heartbreak Hotel"};

    public void printFavorites() {
        System.out.println(Arrays.toString(favoriteSongs));
    }
}
