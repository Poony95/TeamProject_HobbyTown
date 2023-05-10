package home;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import dao.FriendDAO;
import dao.UserDAO;
import first.LogInPage;

import utillist.Utils;

public class FriendTab extends JTabbedPane implements MouseListener{
	private Vector<String> colNames;
	private Vector<Vector<String>> rowData;
	private JTextField jtf_input_id;
	private ArrayList<String> list;
	private JTable jtb_friend;
	
	public FriendTab() {
		// 1st tab 친구 목록 테이블 생성
		colNames = new Vector<String>();
		colNames.add("친구 목록");
		rowData = new Vector<Vector<String>>();
		jtb_friend = new JTable(rowData, colNames);
		loadFriendList();
		JScrollPane jsp_friend = new JScrollPane(jtb_friend);
		
		// 2nd tab 신청목록 탭 생성
		JPanel pan_setting = new JPanel();
		pan_setting.setLayout(new BoxLayout(pan_setting, BoxLayout.Y_AXIS));
		ArrayList<String> w_list = new FriendDAO().getWaitinglist(LogInPage.getNO());
		String[] arr_w = w_list.toArray(new String[w_list.size()]);
		JComboBox<String> jcb_list_w = new JComboBox<String>(arr_w);
		JButton btn_accept = new JButton("수락");
		JPanel pan_btn_accept = new JPanel();
		pan_btn_accept.add(btn_accept);
		JLabel jlb_setting = new JLabel("수락을 대기중인 친구 목록입니다");
		jlb_setting.setAlignmentX(Component.CENTER_ALIGNMENT);
		pan_setting.add(Box.createRigidArea(new Dimension(0, 30)));
		pan_setting.add(jlb_setting);
		pan_setting.add(Box.createRigidArea(new Dimension(0, 10)));
		pan_setting.add(jcb_list_w);
		pan_setting.add(Box.createRigidArea(new Dimension(0, 10)));
		pan_setting.add(pan_btn_accept);
		pan_setting.add(Box.createRigidArea(new Dimension(0, 50)));
		
		// 3rd tab 친구 추가 탭 생성
		JButton btn_add = new JButton("친구 추가");
		btn_add.setAlignmentX(Component.CENTER_ALIGNMENT);
		JPanel pan_add = new JPanel();
		pan_add.setLayout(new BoxLayout(pan_add, BoxLayout.Y_AXIS));
		jtf_input_id = new JTextField(10);
		JLabel jlb_add = new JLabel("친구로 추가할 회원의  ID를 입력하세요.");
		jlb_add.setAlignmentX(Component.CENTER_ALIGNMENT);
		pan_add.add(Box.createRigidArea(new Dimension(0, 30)));
		pan_add.add(jlb_add);
		pan_add.add(Box.createRigidArea(new Dimension(0, 10)));
		pan_add.add(jtf_input_id);
		pan_add.add(Box.createRigidArea(new Dimension(0, 10)));
		pan_add.add(btn_add);
		pan_add.add(Box.createRigidArea(new Dimension(0, 50)));
		
		// 4th tab 친구 삭제 탭 생성
		JButton btn_delete = new JButton("친구 삭제");
		JPanel pan_btn_delete = new JPanel();
		pan_btn_delete.add(btn_delete);
		JPanel pan_delete = new JPanel();
		pan_delete.setLayout(new BoxLayout(pan_delete, BoxLayout.Y_AXIS));
		ArrayList<String> f_list = new FriendDAO().getFriendlist(LogInPage.getNO());
		String[] arr = f_list.toArray(new String[f_list.size()]);
		JComboBox<String> jcb_list = new JComboBox<String>(arr);
		JLabel jlb_delete = new JLabel("삭제할 친구를 선택해주세요.");
		jlb_add.setAlignmentX(Component.CENTER_ALIGNMENT);
		pan_delete.add(Box.createRigidArea(new Dimension(0, 30)));
		pan_delete.add(jlb_delete);
		pan_delete.add(Box.createRigidArea(new Dimension(0, 10)));
		pan_delete.add(jcb_list);
		pan_delete.add(Box.createRigidArea(new Dimension(0, 10)));
		pan_delete.add(pan_btn_delete);
		pan_delete.add(Box.createRigidArea(new Dimension(0, 50)));
		
		// 탭 추가
		addTab("친구 목록", jsp_friend);
		addTab("신청 목록", pan_setting);
		addTab("친구 추가", pan_add);
		addTab("친구 삭제", pan_delete);
		
		// 친구 수락 버튼 actionlistener 생성
		btn_accept.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String ID = Utils.getID(arr_w[jcb_list_w.getSelectedIndex()]);
				String name = new UserDAO().getName(ID);
				if(JOptionPane.showConfirmDialog(null, name+"님을 친구로 추가하시겠습니까?")!=0) return;
				// 친구 추가 dao 실행
				int re = new FriendDAO().acceptFriend(LogInPage.getNO(), new UserDAO().getNO(ID));
				if(re==-1) {
					JOptionPane.showMessageDialog(null, "오류가 발생하여 작업이 실행되지 않았습니다.");
				} else {
					JOptionPane.showMessageDialog(null, "친구 추가에 성공했습니다.");
					loadFriendList();	// 친구목록 업데이트
				}
			 
			}
		});
		// 친구 삭제 버튼 actionlistener
		btn_delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String ID = Utils.getID(arr[jcb_list.getSelectedIndex()]);
				String name = new UserDAO().getName(ID);
				if(JOptionPane.showConfirmDialog(null, name+"님을 친구 삭제하시겠습니까?")==1) return;
				// 친구 삭제 dao 실행
				int re = new FriendDAO().deleteFriend(LogInPage.getNO(), new UserDAO().getNO(ID));
				if(re==-1) {
					JOptionPane.showMessageDialog(null, "오류가 발생하여 삭제되지 않았습니다.");
				} else {
					JOptionPane.showMessageDialog(null, "친구 삭제되었습니다.");
					loadFriendList();	// 친구 목록 업데이트
				}
			} 
		});
		
		// 친구 추가 버튼 actionlistener
		btn_add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String ID = jtf_input_id.getText();
				if(ID.length()==0) {
					JOptionPane.showMessageDialog(null, "ID를 입력해주세요");
					return;
				}
				// 아이디 검사 dao 실행 ( 존재하지 않는 아이디 -> 메서드 종료 )
				int re = new UserDAO().checkID(ID);
				if(re==0) {
					JOptionPane.showMessageDialog(null, "존재하지 않는 ID 입니다.");
					jtf_input_id.setText("");
					return;
				}
				String name = new UserDAO().getName(ID);
				int f_no = new UserDAO().getNO(ID);

				if(JOptionPane.showConfirmDialog(null, name+"님을 친구로 추가하시겠습니까?")!=0) return;
				
				// 친구 추가 dao 실행
				re = new FriendDAO().addFriend(LogInPage.getNO(), f_no);
				if(re==1) {
					JOptionPane.showMessageDialog(null, "친구 신청이 완료되었습니다.");
					loadFriendList();
				} else JOptionPane.showMessageDialog(null, "신청에 실패했습니다.");
			}
		});
		
		jtb_friend.addMouseListener(this);
		addMouseListener(this);
	}
	// 친구 목록을 업데이트하는 메서드
	public void loadFriendList() {
		rowData.clear();
		list = new FriendDAO().getFriendlist(LogInPage.getNO());
		for(String s:list) {
			Vector<String> v = new Vector<String>();
			v.add(s);
			rowData.add(v);
		} 
		jtb_friend.updateUI();
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(this.getSelectedIndex()==0) {
			loadFriendList();
		}
		if(e.getSource()==jtb_friend) {
			int row = jtb_friend.getSelectedRow();
			TableModel model = jtb_friend.getModel();
			String ID = Utils.getID((String)model.getValueAt(row, 0));
			new FriendRoom(new UserDAO().getNO(ID));
			
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	
}
