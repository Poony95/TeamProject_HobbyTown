package boards;

import javax.swing.*;

import Post.postShow;
import dao.BoardDAO;
import dao.PostDAO;
import vo.BoardVO;
import vo.PostVO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

public class UpdateBoard extends JFrame {
    private JLabel title, titleLabel, categoryLabel, contentLabel, applicationLabel, interestLabel;
    private JComboBox<String> categoryComboBox, interestComboBox;
    private JTextField titleField, applyField;
    private JTextArea contentArea;
    private JButton updateButton, cancelButton;
    
    public UpdateBoard(postShow p) {
//        super("게시글 작성");
       
        PostDAO pDao = new PostDAO();
        ArrayList<Object> list_update = pDao.setDate();
        
        // 상단 패널 생성
        JPanel topPanel = new JPanel(new GridLayout(7, 1));

        // 타이틀 레이블
        title = new JLabel("  게시글 수정");
        title.setFont(new Font("맑은 고딕", Font.BOLD, 25));
        
        topPanel.add(title);
        
        // 게시판 카테고리 선택 콤보박스
        JPanel p_top1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel categoryLabel = new JLabel("  카테고리  ");
        categoryLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        String[] categoryOptions = {"함께해요", "동네생활"};
        categoryComboBox = new JComboBox<>(categoryOptions);
        categoryComboBox.setSelectedIndex((int) list_update.get(0));
        p_top1.add(categoryLabel);
        p_top1.add(categoryComboBox);
        topPanel.add(p_top1);

        // 게시판 관심사 선택 콤보박스
        JPanel p_top3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        interestLabel = new JLabel("  관심사     ");
        interestLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        String[] interestOptions = {"건강/운동","음식/요리","영화/공연/전시","미술/공예","노래/음악","재테크","기타"};
        interestComboBox = new JComboBox<>(interestOptions);
        interestComboBox.setSelectedIndex((int) list_update.get(1));
        p_top3.add(interestLabel);
        p_top3.add(interestComboBox);
        topPanel.add(p_top3);
        
        // 게시판 신청인원 선택 필드
        JPanel p_top5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        applicationLabel = new JLabel("  신청인원  ");
        applicationLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        applyField = new JTextField();
        applyField.setText(list_update.get(2)+"");
        
        applyField.setPreferredSize(new Dimension(80, 25));
        applyField.setFont(new Font("SansSerif", Font.PLAIN, 15));
       
        p_top5.add(applicationLabel);
        p_top5.add(applyField);
        p_top5.add(new JLabel("명"));
        topPanel.add(p_top5);
        
        // 제목 입력 필드
        JPanel p_top4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titleLabel = new JLabel("  제목        ");
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        titleField = new JTextField();
        titleField.setPreferredSize(new Dimension(680, 30));
        titleField.setFont(new Font("SansSerif", Font.PLAIN, 15));
        titleField.setText(list_update.get(3)+"");
        p_top4.add(titleLabel);
        p_top4.add(titleField);
        topPanel.add(p_top4);

        // 상단 패널 추가
        add(topPanel, BorderLayout.NORTH);

        // 중단 패널 생성
        JPanel middlePanel = new JPanel(new BorderLayout());

        // 내용 입력 필드
        contentLabel = new JLabel("   내용");
        contentLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        contentArea = new JTextArea();
        contentArea.setText(list_update.get(4)+"");
        contentArea.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        JScrollPane scrollPane = new JScrollPane(contentArea);
        middlePanel.add(contentLabel, BorderLayout.NORTH);
        middlePanel.add(scrollPane, BorderLayout.CENTER);

        // 중단 패널 추가
        add(middlePanel, BorderLayout.CENTER);

        // 하단 패널 생성
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        // 수정 완료 버튼
        updateButton = new JButton("수정");
        bottomPanel.add(updateButton);
        
        // 작성 취소 버튼
        cancelButton = new JButton("취소");
        bottomPanel.add(cancelButton);

        // 하단 패널 추가
        add(bottomPanel, BorderLayout.SOUTH);
        
		setSize(800, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        
        updateButton.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				String category = (String) categoryComboBox.getSelectedItem();
				String interest = (String) interestComboBox.getSelectedItem();
				String title = titleField.getText();
				String apply = applyField.getText();
				String content = contentArea.getText();
				try {			    
					PostVO pVo = new PostVO();
					pVo.setCategory(category);
					pVo.setInterest(interest);
					pVo.setApplication(apply);
					pVo.setTitle(title);
					pVo.setB_content(content);
					pVo.setB_no(Board.postNum);			   
				    
				    int re = pDao.updatePost(pVo);
				    
				    if (re > 0) {
				        JOptionPane.showMessageDialog(null, "게시글이 수정되었습니다.");
				        setVisible(false);
				        p.setVisible(false);
				        new postShow(Board.postNum); 
				        //작성시 테이블 바로 업데이트 되도록 할 수 있었으면 좋겠음
				    } else {
				        JOptionPane.showMessageDialog(null, "게시글 수정에 실패하였습니다.");
				    }
				} catch (Exception ex) {
				    ex.printStackTrace();
				    JOptionPane.showMessageDialog(null, "게시글 수정 중 예외가 발생하였습니다.");
				}
			}
		});
        
        cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
    }
    
//    public static void main(String[] args) {
//        new UpdateBoard(p);
//    }
} 


