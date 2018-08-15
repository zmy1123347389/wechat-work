package com.article.util;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Consts;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Tools {
	/**
	 * 检测字符串是否不为空(null,"","null")
	 * 
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String s) {
		return s != null && !"".equals(s) && !"null".equals(s);
	}

	/**
	 * 检测字符串是否为空(null,"","null")
	 * 
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(String s) {
		return s == null || "".equals(s) || "null".equals(s);
	}

	/**
	 * 字符串转换为字符串数组
	 * 
	 * @param str
	 *            字符串
	 * @param splitRegex
	 *            分隔符
	 * @return
	 */
	public static String[] str2StrArray(String str, String splitRegex) {
		if (isEmpty(str)) {
			return null;
		}
		return str.split(splitRegex);
	}

	/**
	 * 用默认的分隔符(,)将字符串转换为字符串数组
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String[] str2StrArray(String str) {
		return str2StrArray(str, ",\\s*");
	}

	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
	 * 
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String date2Str(Date date) {
		return date2Str(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date str2Date(String date) {
		if (notEmpty(date)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new Date();
		} else {
			return null;
		}
	}

	/**
	 * 按照参数format的格式，日期转字符串
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date, String format) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} else {
			return "";
		}
	}

	/**
	 * 生成count位的随机数
	 *
	 * @param count
	 *            要生成的位数
	 * @return
	 */
	public static String getRandomNumber(int count) {
		StringBuffer ss = new StringBuffer(count);
		for (int i = 0; i < count; i++) {
			int a = (int) (Math.random() * 10);
			ss.append(a);
		}
		return ss.toString();
	}

	/**
	 * 元转分
	 *
	 * @param amount
	 * @return
	 */
	public static String changeY2F(String amount) {
		String currency = amount.replaceAll("\\$|\\￥|\\,", ""); // 处理包含, ￥
		// 或者$的金额
		int index = currency.indexOf(".");
		int length = currency.length();
		Long amLong = 0l;
		if (index == -1) {
			amLong = Long.valueOf(currency + "00");
		} else if (length - index >= 3) {
			amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
		} else if (length - index == 2) {
			amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
		} else {
			amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
		}
		return amLong.toString();
	}

	/**
	 * 将分为单位的转换为元 （除100）
	 *
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public static String changeF2Y(String amount) throws Exception {

		return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100)).toString();
	}

	/**
	 * 获取本地ip
	 * 
	 * @return
	 */
	public static String getLocalIP() {
		InetAddress address = null;
		try {
			address = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 获取的是本地的IP地址
		String hostAddress = address.getHostAddress();
		return hostAddress;
	}

	/**
	 * 将微信支付所需参数拼接为xml字符串
	 *
	 * @param <T>
	 *
	 * @param paramsMap
	 *            参数map
	 * @return xml字符串
	 * @throws Exception
	 */
	public static <T> String getXmlFromParamsMap(Map<String, T> paramsMap) throws Exception {
		if (paramsMap != null && paramsMap.size() > 0) {
			Map<String, Object> params = new TreeMap<String, Object>(new Comparator<String>() {
				public int compare(String s1, String s2) {
					return s1.compareTo(s2);
				}
			});
			params.putAll(paramsMap);
			StringBuffer ss = new StringBuffer("<xml>");
			for (Map.Entry<String, Object> param : params.entrySet()) {
				if (!"key".equals(param.getKey())) {
					ss.append("<" + param.getKey() + "><![CDATA[").append(param.getValue())
							.append("]]></" + param.getKey() + ">");
				}
			}
			String sign = getSignFromParamMap(params);
			ss.append("<sign>" + sign + "</sign>");
			ss.append("</xml>");
			String xmlString = ss.toString();
			return new String(xmlString.getBytes(), Consts.UTF_8);
		}
		return null;
	}

	/**
	 * 从map中获取签名sign
	 *
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public static <T> String getSignFromParamMap(Map<String, T> paramsMap) throws Exception {
		if (paramsMap != null && paramsMap.size() > 0) {
			Map<String, T> params = new TreeMap<String, T>(new Comparator<String>() {
				public int compare(String s1, String s2) {
					return s1.compareTo(s2);
				}

			});
			params.putAll(paramsMap);
			StringBuffer tempStr = new StringBuffer();
			for (Map.Entry<String, T> param : params.entrySet()) {
				// 去掉带sign的项
				if (!"sign".equals(param.getKey()) && !"key".equals(param.getKey()) && !"".equals(param.getValue())
						&& param.getValue() != null) {
					tempStr.append(param.getKey() + "=" + param.getValue() + "&");
				}
			}
			String temp = tempStr.toString().concat("key=" + params.get("key"));
			return MD5(temp).toUpperCase();
		}
		return null;
	}

	private final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(s.getBytes("UTF-8"));
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获得一个UUID
	 * 
	 * @return String UUID
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		// 去掉“-”符号
		return uuid.replaceAll("-", "");
	}

	/**
	 * 签名认证
	 *
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public static <T> boolean checkSign(Map<String, T> paramsMap) throws Exception {
		String sign = getSignFromParamMap(paramsMap);
		return paramsMap.get("sign").equals(sign);
	}

	/**
	 * 从xml字符串中解析参数
	 * 
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getParamsMapFromXml(InputStream xml) throws Exception {
		Map<String, String> params = new HashMap<String, String>(0);
		SAXReader saxReader = new SAXReader();
		saxReader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
		saxReader.setFeature("http://xml.org/sax/features/external-general-entities", false);
		saxReader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
		Document read = saxReader.read(xml);
		Element node = read.getRootElement();
		listNodes(node, params);
		return params;
	}

	@SuppressWarnings({ "unchecked" })
	public static void listNodes(Element node, Map<String, String> params) {
		// 获取当前节点的所有属性节点
		List<Attribute> list = node.attributes();
		// 遍历属性节点
		if ((list == null || list.size() == 0) && !(node.getTextTrim().equals(""))) {
			if (node.getTextTrim().contains("<![CDATA[")) {
				String[] split = node.getTextTrim().split("<![CDATA[");
				split[1].replaceAll("]]>", "");
				params.put(node.getName(), split[1]);
			} else {
				params.put(node.getName(), node.getTextTrim());
			}
		}
		// 当前节点下面子节点迭代器
		Iterator<Element> it = node.elementIterator();
		// 遍历
		while (it.hasNext()) {
			// 获取某个子节点对象
			Element e = it.next();
			// 对子节点进行遍历
			listNodes(e, params);
		}
	}

	/**
	 * 返回给微信的消息
	 * 
	 * @param return_code
	 * @param return_msg
	 * @return
	 */
	public static String setXML(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg
				+ "]]></return_msg></xml>";
	}

	public static String remove(String resource, char ch) {
		StringBuffer buffer = new StringBuffer();
		int position = 0;
		char currentChar;
		while (position < resource.length()) {
			currentChar = resource.charAt(position++);
			// 如果当前字符不是要去除的字符，则将当前字符加入到StringBuffer中
			if (currentChar != ch) {
				buffer.append(currentChar);
			}
		}
		return buffer.toString();
	}

	/**
	 * 去除字符串中的空格、回车、换行符、制表符等
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceSpecialStr(String str) {
		String repl = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			repl = m.replaceAll("");
		}
		return repl;
	}

	/**
	 * 从list中随机抽取元素
	 *
	 * @param list
	 * @param n
	 * @return void
	 * @throws @Title:
	 *             createRandomList
	 * @Description: TODO
	 */
	public static List createRandomList(List list, int n) {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		List listNew = new ArrayList();
		if (list.size() <= n) {
			return list;
		} else {
			while (map.size() < n) {
				int random = (int) (Math.random() * list.size());
				if (!map.containsKey(random)) {
					map.put(random, "");
					System.out.println(random + "===========" + list.get(random));
					listNew.add(list.get(random));
				}
			}
			return listNew;
		}
	}

	public static void main(String[] args) {
		String str = "afdsfdf 了   2012   0407 Alamps 老师";
		char c = ' ';
		System.out.println("结果为：" + Tools.remove(str, c));
	}
}
