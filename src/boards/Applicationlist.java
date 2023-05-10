package boards;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import dao.ApplicationDAO;
import dao.UserDAO;
import first.LogInPage;
import vo.BoardVO;
import vo.UserVO;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
//���� ����
public class Applicationlist extends JFrame {
    JLabel title;
    JTable table;
    Vector<Vector<String>> rowdata;
    Vector<String> colname;
    int b_no;

    public Applicationlist(int b_no) {
    	this.b_no=b_no;
        Container c = getContentPane();
        c.setLayout(null);

        // Ÿ��Ʋ ���̺�
        title = new JLabel("��û ����Ʈ");
        title.setFont(new Font("���� ���", Font.BOLD, 30));
        title.setBounds(150, 20, 300, 30);
        c.add(title);

        // ���ƿ� ��� ���̺�
        colname = new Vector<String>();
        colname.add("��ȣ");
        colname.add("��û��");
        colname.add("����");
        colname.add("��û����");
        colname.add("��û��¥");
        
        rowdata = new Vector<Vector<String>>();
        table = new JTable(rowdata,colname);
        JScrollPane jsp = new JScrollPane(table);
        TableColumnModel columnModel = table.getColumnModel();
		TableColumn column3 = columnModel.getColumn(3);
		TableColumn column4 = columnModel.getColumn(4);
		column3.setPreferredWidth(230);
		column4.setPreferredWidth(100);
        table.setRowHeight(30);
        
        jsp.setBounds(20, 80, 440, 230);
        c.add(jsp);

        loadapplylist();
        setTitle("��û ����Ʈ");
        setSize(500, 380);
        setVisible(true);
        setLocationRelativeTo(null);
        
        
    }
	public void loadapplylist() {
		rowdata.clear();
		ApplicationDAO dao = new ApplicationDAO();
		ArrayList<HashMap<String, Object>> list = dao.getApplicantList(LogInPage.getNO(), b_no);
		for(HashMap<String, Object> map:list) {
			Vector<String> v = new Vector<String>();
			v.add(map.get("b_no")+"");
			v.add((String)map.get("name"));
			v.add((String)map.get("gender"));
			v.add((String)map.get("ap_content"));
			v.add(map.get("date_application").toString());
			rowdata.add(v);
		}
		table.updateUI();
	}
	




}
;