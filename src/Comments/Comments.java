package Comments;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import dao.UserDAO;
import dao.commentDAO;
import first.LogInPage;
import vo.UserVO;
import vo.commentsVO;

public class Comments extends JPanel{
	JTextField jtf_comments;
	JPanel p_allComments;
	ArrayList<Vector<String>> list_comments;
	JLabel []comments;
	int i;
	JPopupMenu menu = new JPopupMenu("팝업");
	JMenuItem item = new JMenuItem("메뉴");
	
	public Comments() {
		setLayout(new BorderLayout());
		JPanel p_comments_write = new JPanel();
		
		JLabel jlb_comment = new JLabel("댓글");
		jtf_comments = new JTextField(30);
		JButton btn_commentsApply = new JButton("등록");
		p_comments_write.add(jlb_comment);
		p_comments_write.add(jtf_comments);
		p_comments_write.add(btn_commentsApply);
		
		list_comments = new ArrayList<Vector<String>>();
		p_allComments = new JPanel();
		
		p_allComments.setLayout(new GridLayout(list_comments.size(),1));
		loadData();
			
		add(p_comments_write, BorderLayout.NORTH);
		add(p_allComments, BorderLayout.CENTER);
			
		setSize(400,200);
		setVisible(true);
		
		btn_commentsApply.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String c_content = jtf_comments.getText();
				if(c_content == "") {
					JOptionPane.showMessageDialog(null, "댓글을 입력해주세요.");
				}
				commentsVO voC = new commentsVO(c_content);
				commentDAO daoC = new commentDAO();
				int re = daoC.addComments(voC);
				if(re == 1) {
					JOptionPane.showMessageDialog(null, "댓글이 등록되었습니다.");
				}
				jtf_comments.setText("");
				loadData();
			}
		});
	}
	public void loadData() {
		commentDAO daoC = new commentDAO();
		list_comments = daoC.loadComments();
		comments = new JLabel[list_comments.size()];
		p_allComments.removeAll();
		
		i = 0;
		String commentInform="";
		while(i<list_comments.size()) {
			int j=0;
			JLabel jlb_comments = new JLabel("");
			jlb_comments.setFont(new Font("맑은 고딕", Font.BOLD, 12));
			commentInform="";
			while(j<1) {
				commentInform = list_comments.get(i).get(j);
				j++;
			}
			jlb_comments.setText(commentInform);
			comments[i] = jlb_comments;
			comments[i].addMouseListener(new PopupTriggerListener());
			p_allComments.add(comments[i]);
			i++;
		}
		p_allComments.updateUI();
	}
	
	class PopupTriggerListener extends MouseAdapter {
		
	      public void mousePressed(MouseEvent ev) {
	        if (ev.isPopupTrigger()) {
	        	System.out.println("팝업트리거1");
	          menu.show(ev.getComponent(), ev.getX(), ev.getY());
	        }
	      }

	      public void mouseReleased(MouseEvent ev) {
	        if (ev.isPopupTrigger()) {
	        	int idx_start = ev.getComponent().toString().indexOf("text=")+5; 
	        	int idx_end = ev.getComponent().toString().indexOf(",verticalAlignment"); 
	        	String clicked_comment = ev.getComponent().toString().substring(idx_start, idx_end);
	        	menu.removeAll();
//	        	JMenuItem item = new JMenuItem("댓글수정");
//	        	item.addActionListener(new ActionListener() {
//					
//					@Override
//					public void actionPerformed(ActionEvent e) {
//						commentDAO cDao = new commentDAO();
//						
//						System.out.println("댓글수정됨");
//						
//					}
//				});
//	        	menu.add(item);
	        	JMenuItem item = new JMenuItem("댓글삭제");
	        	item.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						int idx_user_end = clicked_comment.indexOf(" ");

						String user = clicked_comment.substring(0,idx_user_end);
						
						int idx_comment_start = idx_user_end+2;
						int idx_date_start = clicked_comment.indexOf("(");
						int idx_date_end = clicked_comment.indexOf(")");

						
						String comment = clicked_comment.substring(idx_comment_start, idx_date_start-1);			
						String date = clicked_comment.substring(idx_date_start+1, idx_date_end);
						
						UserDAO userDAO = new UserDAO();
						int userNo = userDAO.getNO(user);
						if (userNo==LogInPage.getNO()) {
							commentsVO cVO = new commentsVO(userNo, comment, date);
							commentDAO cDao = new commentDAO();
							int chk = JOptionPane.showConfirmDialog(null, "댓글을 삭제하시겠습니까?", "댓글 삭제", JOptionPane.YES_NO_OPTION);
							if (chk==0) {
								int re = cDao.deleteComments(cVO);
								JOptionPane.showMessageDialog(null, "댓글이 삭제되었습니다.");
								loadData();
							} 
						}else {
							JOptionPane.showMessageDialog(null, "자신의 댓글만 삭제 가능합니다.");
						}

						
					}
				});
	        	menu.add(item);
	        	menu.show(ev.getComponent(), ev.getX(), ev.getY());
	        }
	      }

	      public void mouseClicked(MouseEvent ev) {
	      }
	}

}
