package util;

import java.text.DecimalFormat;

public class DoubleUtil {

	public static double formatNumericBig(double value) {
		DecimalFormat df = new DecimalFormat("0000");
		String result = df.format(value);
		double var_result = Double.valueOf(result.substring(0, 4));

		return var_result;
	}

}
