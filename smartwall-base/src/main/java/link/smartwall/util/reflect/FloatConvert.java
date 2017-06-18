package link.smartwall.util.reflect;

public class FloatConvert implements IConvert<Float> {

    @Override
    public Float convert(Object value) {
        return Float.valueOf(String.valueOf(value));
    }
}
