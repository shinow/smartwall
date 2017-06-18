package link.smartwall.util.reflect;

public class StringConvert implements IConvert<String> {

    @Override
    public String convert(Object value) {
        return String.valueOf(value);
    }
}
