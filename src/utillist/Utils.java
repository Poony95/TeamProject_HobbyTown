package utillist;

import java.util.Arrays;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JTable;

public class Utils {
	public final static String []interest = {"건강/운동","음식/요리","영화/공연/전시","미술/공예","노래/음악","재테크","기타"};
	public final static String[] address = 
		{"서울", "부산", "대구", "인천", "광주", "대전", "울산", "세종", "경기", "강원", "충청북도", "충청남도", "전라북도", "전라남도", "경상북도", "경상남도", "제주"};
	private Utils() {}
	// name(id)형태의 String에서 id를 추출하는 메서드
	public static String getID(String s) {
		return s.substring(s.indexOf("(")+1, s.length()-1);
	}
	public static int getAddrIndex(String addr) {
		return Arrays.asList(address).indexOf(addr);
	}
	public static int getInterestIndex(String inter) {
		return Arrays.asList(interest).indexOf(inter);
	}
	public static JTable makeJTable(String[] arr, Vector<Vector<String>> rowData, Vector<String> colNames) {
		for(String s:arr) {
			colNames.add(s);
		}
		return new JTable(rowData, colNames);
	}
}
