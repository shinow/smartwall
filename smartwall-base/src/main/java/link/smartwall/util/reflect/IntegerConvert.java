package link.smartwall.util.reflect;

public class IntegerConvert implements IConvert<Integer> {

    @Override
    public Integer convert(Object value) {
    	if("".equals(value) || null==value){
    		return null;
    	}
        return Integer.parseInt(String.valueOf(value));
    }
}
