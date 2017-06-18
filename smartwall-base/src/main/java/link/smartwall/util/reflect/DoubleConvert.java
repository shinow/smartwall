package link.smartwall.util.reflect;

public class DoubleConvert implements IConvert<Double> {

    @Override
    public Double convert(Object value) {
        return Double.valueOf(String.valueOf(value));
    }
}
