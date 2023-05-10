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
//        super("�Խñ� �ۼ�");
       
        PostDAO pDao = new PostDAO();
        ArrayList<Object> list_update = pDao.setDate();
        
        // ��� �г� ����
        JPanel topPanel = new JPanel(new GridLayout(7, 1));

        // Ÿ��Ʋ ���̺�
        title = new JLabel("  �Խñ� ����");
        title.setFont(new Font("���� ���", Font.BOLD, 25));
        
        topPanel.add(title);
        
        // �Խ��� ī�װ� ���� �޺��ڽ�
        JPanel p_top1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel categoryLabel = new JLabel("  ī�װ�  ");
        categoryLabel.setFont(new Font("���� ���", Font.BOLD, 15));
        String[] categoryOptions = {"�Բ��ؿ�", "���׻�Ȱ"};
        categoryComboBox = new JComboBox<>(categoryOptions);
        categoryComboBox.setSelectedIndex((int) list_update.get(0));
        p_top1.add(categoryLabel);
        p_top1.add(categoryComboBox);
        topPanel.add(p_top1);

        // �Խ��� ���ɻ� ���� �޺��ڽ�
        JPanel p_top3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        interestLabel = new JLabel("  ���ɻ�     ");
        interestLabel.setFont(new Font("���� ���", Font.BOLD, 15));
        String[] interestOptions = {"�ǰ�/�","����/�丮","��ȭ/����/����","�̼�/����","�뷡/����","����ũ","��Ÿ"};
        interestComboBox = new JComboBox<>(interestOptions);
        interestComboBox.setSelectedIndex((int) list_update.get(1));
        p_top3.add(interestLabel);
        p_top3.add(interestComboBox);
        topPanel.add(p_top3);
        
        // �Խ��� ��û�ο� ���� �ʵ�
        JPanel p_top5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        applicationLabel = new JLabel("  ��û�ο�  ");
        applicationLabel.setFont(new Font("���� ���", Font.BOLD, 15));
        applyField = new JTextField();
        applyField.setText(list_update.get(2)+"");
        
        applyField.setPreferredSize(new Dimension(80, 25));
        applyField.setFont(new Font("SansSerif", Font.PLAIN, 15));
       
        p_top5.add(applicationLabel);
        p_top5.add(applyField);
        p_top5.add(new JLabel("��"));
        topPanel.add(p_top5);
        
        // ���� �Է� �ʵ�
        JPanel p_top4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titleLabel = new JLabel("  ����        ");
        titleLabel.setFont(new Font("���� ���", Font.BOLD, 15));
        titleField = new JTextField();
        titleField.setPreferredSize(new Dimension(680, 30));
        titleField.setFont(new Font("SansSerif", Font.PLAIN, 15));
        titleField.setText(list_update.get(3)+"");
        p_top4.add(titleLabel);
        p_top4.add(titleField);
        topPanel.add(p_top4);

        // ��� �г� �߰�
        add(topPanel, BorderLayout.NORTH);

        // �ߴ� �г� ����
        JPanel middlePanel = new JPanel(new BorderLayout());

        // ���� �Է� �ʵ�
        contentLabel = new JLabel("   ����");
        contentLabel.setFont(new Font("���� ���", Font.BOLD, 15));
        contentArea = new JTextArea();
        contentArea.setText(list_update.get(4)+"");
        contentArea.setFont(new Font("���� ���", Font.PLAIN, 15));
        JScrollPane scrollPane = new JScrollPane(contentArea);
        middlePanel.add(contentLabel, BorderLayout.NORTH);
        middlePanel.add(scrollPane, BorderLayout.CENTER);

        // �ߴ� �г� �߰�
        add(middlePanel, BorderLayout.CENTER);

        // �ϴ� �г� ����
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        // ���� �Ϸ� ��ư
        updateButton = new JButton("����");
        bottomPanel.add(updateButton);
        
        // �ۼ� ��� ��ư
        cancelButton = new JButton("���");
        bottomPanel.add(cancelButton);

        // �ϴ� �г� �߰�
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
				        JOptionPane.showMessageDialog(null, "�Խñ��� �����Ǿ����ϴ�.");
				        setVisible(false);
				        p.setVisible(false);
				        new postShow(Board.postNum); 
				        //�ۼ��� ���̺� �ٷ� ������Ʈ �ǵ��� �� �� �־����� ������
				    } else {
				        JOptionPane.showMessageDialog(null, "�Խñ� ������ �����Ͽ����ϴ�.");
				    }
				} catch (Exception ex) {
				    ex.printStackTrace();
				    JOptionPane.showMessageDialog(null, "�Խñ� ���� �� ���ܰ� �߻��Ͽ����ϴ�.");
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


