package id.sis.service.accounting.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SISUtil {
	
	public String nullToString(String str) {
		if (str == null) {
			return "";
		}
		return str;
	}

	public static String addSingleQuote(String str) {
		if (str == null)
			return null;
		return String.format("'%s'", str.replace("'", "''"));
	}

	public static String addZero(int str, int total) {
		return String.format("%0" + total + "d", str);
	}

	public static boolean isEmptyString(String str) {
		if (str == null || "".equalsIgnoreCase(str)) {
			return true;
		}
		return false;
	}

	public static Date getDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date dates = null;
		try {
			dates = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dates;
	}
	
	public static Timestamp getDateyyMMdd(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		Timestamp dates = null;
		try {
			dates = new Timestamp(sdf.parse(date).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dates;
	}

	public static String getStringDate(Timestamp date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date);
	}

	public static String getStringDateDmy(Timestamp date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(date);
	}

	public static String getStringPeriod(Timestamp date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		return sdf.format(date);
	}

	public Timestamp getCurrentTime() {
		return new Timestamp((new Date().getTime()));
	}

	public List<List<Map<String, Object>>> getSplitThreadData(int p_TotalThread, List<Map<String, Object>> listData) {
		int countMultiThreading = p_TotalThread;
		int tempCountAccount = 0;
		int splitter = listData.size() / countMultiThreading;

		List<List<Map<String, Object>>> listResult = new ArrayList<>();
		List<Map<String, Object>> listTemp = new ArrayList<>();
		for (Map<String, Object> mapData : listData) {
			if (tempCountAccount > splitter || tempCountAccount == 0) {
				tempCountAccount = 0;
				listTemp = new ArrayList<>();
				listResult.add(listTemp);
			}
			listResult.get(listResult.size() - 1).add(mapData);
			tempCountAccount += 1;
		}

		return listResult;
	}

	public List<List<String>> getSplitThreadDataString(int p_TotalThread, List<String> listData) {
		int countMultiThreading = p_TotalThread;
		int tempCountAccount = 0;
		int splitter = listData.size() / countMultiThreading;

		List<List<String>> listResult = new ArrayList<>();
		List<String> listTemp = new ArrayList<>();
		for (String data : listData) {
			if (tempCountAccount > splitter || tempCountAccount == 0) {
				tempCountAccount = 0;
				listTemp = new ArrayList<>();
				listResult.add(listTemp);
			}
			listResult.get(listResult.size() - 1).add(data);
			tempCountAccount += 1;
		}

		return listResult;
	}

	public List<List<Integer>> getSplitThreadDataInt(int p_TotalThread, List<Integer> listData) {
		int countMultiThreading = p_TotalThread;
		int tempCountAccount = 0;
		int splitter = listData.size() / countMultiThreading;

		List<List<Integer>> listResult = new ArrayList<>();
		List<Integer> listTemp = new ArrayList<>();
		for (int data : listData) {
			if (tempCountAccount > splitter || tempCountAccount == 0) {
				tempCountAccount = 0;
				listTemp = new ArrayList<>();
				listResult.add(listTemp);
			}
			listResult.get(listResult.size() - 1).add(data);
			tempCountAccount += 1;
		}

		return listResult;
	}
	
	public static BigDecimal getBigDecimal(Object value) {
		BigDecimal ret = new BigDecimal(0);
		if (value != null) {
			if (value instanceof BigDecimal) {
				ret = (BigDecimal) value;
			} else if (value instanceof String) {
				ret = new BigDecimal((String) value);
			} else if (value instanceof BigInteger) {
				ret = new BigDecimal((BigInteger) value);
			} else if (value instanceof Double) {
				ret = new BigDecimal((Double) value);
			} else if (value instanceof Number) {
				ret = new BigDecimal(((Number) value).doubleValue());
			} else if (value instanceof Integer) {
				ret = new BigDecimal(((Integer) value).doubleValue());
			} else {
				throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass()
						+ " into a BigDecimal.");
			}
		}
		return ret;
	}

	public static BigDecimal getBigDecimal(Object value, int scale) {
		return getBigDecimal(value).setScale(scale, BigDecimal.ROUND_HALF_UP);
	}
	
	public static boolean cekIsNull (Object o) {
		if (o == null) {
			return true;
		}
		if (o instanceof Integer) {
			if ((int)o == 0) {
				return true;
			}
		}
		if (o instanceof BigDecimal) {
			if (getBigDecimal(o).signum() == 0) {
				return true;
			}
		}
		if (o instanceof String) {
			if (isEmptyString((String)o)) {
				return true;
			}
		}
		return false;
	}
	
	public static String getStringDashFromTimeStampTime(Timestamp date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String result = sdf.format(date);
		return result;
	}
	
	public String getRefNoTime() {
		Timestamp date = getCurrentTime();
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String result = sdf.format(date);
		return result;
	}
	
}
