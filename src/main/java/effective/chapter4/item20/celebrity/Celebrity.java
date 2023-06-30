package effective.chapter4.item20.celebrity;

public class Celebrity implements Singer, SongWriter {
    @Override
    public void dance() {
        System.out.println("dance");
    }

    @Override
    public void compose() {
        System.out.println("compose");
    }
}
