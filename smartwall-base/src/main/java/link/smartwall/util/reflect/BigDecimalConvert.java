package link.smartwall.util.reflect;

import java.math.BigDecimal;

public class BigDecimalConvert  implements IConvert<BigDecimal> {

	@Override
	public BigDecimal convert(Object value) {
		if("".equals(value) || null==value){
    		return null;
    	}
		return BigDecimal.valueOf(Double.valueOf(value.toString()));
	}
}
