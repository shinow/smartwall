package link.smartwall.util.reflect;

public class BooleanConvert implements IConvert<Boolean> {

    @Override
    public Boolean convert(Object value) {
        return Boolean.valueOf(String.valueOf(value));
    }
}
