package utillist;

import java.util.Arrays;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JTable;

public class Utils {
	public final static String []interest = {"�ǰ�/�","����/�丮","��ȭ/����/����","�̼�/����","�뷡/����","����ũ","��Ÿ"};
	public final static String[] address = 
		{"����", "�λ�", "�뱸", "��õ", "����", "����", "���", "����", "���", "����", "��û�ϵ�", "��û����", "����ϵ�", "���󳲵�", "���ϵ�", "��󳲵�", "����"};
	private Utils() {}
	// name(id)������ String���� id�� �����ϴ� �޼���
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
