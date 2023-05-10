package customer;

 
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Frequent_QnA extends JPanel {
	JRadioButton rb[] = new JRadioButton[6];
	String build[] = {"회원탈퇴는 어떻게 하나요?",
			"개인정보 수정은 어떻게 하나요?",
			"모임은 지역별로만 만들 수 있나요?",
			"좋아요를 누른 회원들은 어떻게 확인하나요?",
			"쪽지는 영구보관인가요?",
			"계정 신고가 궁금해요"};
	String text[] = {"\""+"개인정보 수정화면에서 탈퇴버튼을 눌러 탈퇴하실 수 있습니다."+"\"",
			"\""+"관리탭을 선택하여 개인정보를 수정하실 수 있습니다. "+"\"",
			"\""+"현재는 가입하신 지역별로 소모임운영이 가능합니다."+"\"",
			"\""+"현재는 좋아요를 누른 회원을 확인하실 수 없습니다."+"\"",
			"\""+"쪽지는 탈퇴하기 전까지 보관됩니다. 삭제하고싶은 쪽지가 있다면 삭제버튼을 눌러주세요."+"\"",
			"\""+"신고 사유가 신고 기준에 부합할 때 계정 사용이 중지됩니다"+"\""};
	
	JLabel la;

	public Frequent_QnA() {
		la = new JLabel();
	
		ButtonGroup bg = new ButtonGroup();
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(3,2));
		JPanel p1 = new JPanel();
		for(int i=0 ; i<build.length ; i++) {
			rb[i] = new JRadioButton(build[i]);
			rb[i].addItemListener(new MyItemListener());
			bg.add(rb[i]);
			p.add(rb[i]);
		}
		p1.add(la);
		add(p);
		add(p1);
		
		setSize(600,500);
		setVisible(true);
	}
	class MyItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				for(int i=0 ; i<text.length ; i++) {
					if(e.getItem() == rb[i]) {
						la.setText(text[i]);
						la.setFont(new Font("맑은 고딕",getFont().BOLD, 15));
					}
				}
			}
		}
	
	
	}
}
