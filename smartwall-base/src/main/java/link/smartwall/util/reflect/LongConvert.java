package link.smartwall.util.reflect;

public class LongConvert implements IConvert<Long> {

    @Override
    public Long convert(Object value) {
        return Long.valueOf(String.valueOf(value));
    }
}
