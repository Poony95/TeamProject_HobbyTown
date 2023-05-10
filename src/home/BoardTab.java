package home;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import Post.postShow;
import boards.Applicationlist;
import boards.Board;
import dao.ApplicationDAO;
import dao.LikedDAO;
import dao.MiniroomDAO;
import first.LogInPage;
import utillist.Utils;
import vo.BoardVO;
// Ȩ ȭ�鿡 ���� �Խñ� ��
public class BoardTab extends JTabbedPane implements MouseListener{
	private Vector<String>AddrcolNames;
	private Vector<Vector<String>> AddrrowData;
	private Vector<String>InterestcolNames;
	private Vector<Vector<String>> InterestrowData;
	private Vector<String>LikedcolNames;
	private Vector<Vector<String>> LikedrowData;
	private Vector<String>AppcolNames;
	private Vector<Vector<String>> ApprowData;
	private JTable jtb_Liked;
	private JTable jtb_addr;
	private JTable jtb_interest;
	private JTable jtb_app;
	private ArrayList<BoardVO> Interest_list;
	private ArrayList<BoardVO> Addr_list;
	private ArrayList<BoardVO> Application_list;
	private ArrayList<BoardVO> Liked_list;
	public BoardTab() { 
		// ������ ���̺� ����
		AddrcolNames = new Vector<String>();
		AddrrowData = new Vector<Vector<String>>();
		jtb_addr = Utils.makeJTable(new String[] {"���ɻ�", "����", "�Խ�����"}, AddrrowData, AddrcolNames);
		// ���ɻ纰 ���̺� ����
		InterestcolNames = new Vector<String>();
		InterestrowData = new Vector<Vector<String>>();
		jtb_interest = Utils.makeJTable(new String[] {"����", "����", "�Խ�����"}, InterestrowData, InterestcolNames);
		// ���� ���ƿ� �� �Խñ� ���̺� ����
		LikedcolNames = new Vector<String>();
		LikedrowData = new Vector<Vector<String>>();
		jtb_Liked = Utils.makeJTable(new String[] {"����", "���ƿ� ���", "�Խ�����"}, LikedrowData, LikedcolNames);
		// ��û ���̺� ����
		AppcolNames = new Vector<String>();
		ApprowData = new Vector<Vector<String>>();
		jtb_app = Utils.makeJTable(new String[] {"�۹�ȣ", "����", "�ۼ� ����"}, ApprowData, AppcolNames);
		
		// �� �߰�
		addTab("���ɻ纰 �α��", new JScrollPane(jtb_interest));
		addTab("������ �α��", new JScrollPane(jtb_addr));
		addTab("���ƿ� �Խñ�", new JScrollPane(jtb_Liked));
		addTab("������û ���", new JScrollPane(jtb_app));
		
		addMouseListener(this);
		jtb_addr.addMouseListener(this);
		jtb_app.addMouseListener(this);
		jtb_interest.addMouseListener(this);
		jtb_Liked.addMouseListener(this);
		
	}
	public void loadApplication() {
		ApprowData.clear();
		Application_list =
				new ApplicationDAO().loadApplicationList(LogInPage.getNO());
		for(BoardVO b:Application_list) {
			Vector<String> v = new Vector<String>();
			v.add(b.getB_no()+"");
			v.add(b.getTitle());
			v.add(b.getDate_board().toString());
			ApprowData.add(v);
		}
		jtb_app.updateUI();
	}
	public void loadAddrBoard() {
		AddrrowData.clear();
		Addr_list = new MiniroomDAO().getAddrBoard(LogInPage.getNO());
		for(BoardVO b:Addr_list) {
			Vector<String> v = new Vector<String>();
			v.add(b.getInterest());
			v.add(b.getTitle());
			v.add(b.getDate_board().toString());
			AddrrowData.add(v);
		}
		jtb_addr.updateUI();
	}
	public void loadInterestBoard() {
		InterestrowData.clear();
		Interest_list = new MiniroomDAO().getInterestBoard(LogInPage.getNO());
		for(BoardVO b:Interest_list) {
			Vector<String> v = new Vector<String>();
			v.add(b.getAddress());
			v.add(b.getTitle());
			v.add(b.getDate_board().toString());
			InterestrowData.add(v);
		}
		jtb_interest.updateUI();
	}
	public void loadliked() {
		LikedrowData.clear();
		LikedDAO dao = new LikedDAO();
		Liked_list = dao.myLikedlist(LogInPage.getNO());
		for(BoardVO b:Liked_list) {
			Vector<String> v = new Vector<>();
			v.add(b.getAddress());
			v.add(b.getTitle());
			v.add(b.getDate_board().toString());
			LikedrowData.add(v);
		}
		jtb_Liked.updateUI();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(this.getSelectedIndex()==0) {
			loadInterestBoard();
		} else if(this.getSelectedIndex()==1) {
			loadAddrBoard();
		} else if(this.getSelectedIndex()==2) {
			loadliked();
		} else if(this.getSelectedIndex()==3) {
			loadApplication();
		}
		
		if(e.getSource()==jtb_addr) {
			Board.postNum = Addr_list.get(jtb_addr.getSelectedRow()).getB_no();
			new postShow(Board.postNum);
		} else if(e.getSource()==jtb_interest) {
			Board.postNum = Interest_list.get(jtb_interest.getSelectedRow()).getB_no();
			new postShow(Board.postNum);
		} else if(e.getSource()==jtb_app) {
			int b_no = Application_list.get(jtb_app.getSelectedRow()).getB_no();
			new Applicationlist(b_no);
		} else if(e.getSource()==jtb_Liked) {
			Board.postNum = Liked_list.get(jtb_Liked.getSelectedRow()).getB_no();
			new postShow(Board.postNum);
		}
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
}
