package Game;

import javax.swing.JFrame;
import java.awt.Window.Type;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dao.GameDAO;
import first.LogInPage;
import Game.GamePage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

public class GameMain extends JFrame {
   
   private Image screenImage;
   private Graphics screenGraphic;
   private Image MainImage;
   private JLabel jlb_welcome;
   public static final int SCREEN_WIDTH = 900;
   public static final int SCREEN_HEIGHT = 500;
   public static final int LOCATION_X = 300;
   public static final int LOCATION_Y = 100;

   Container c = getContentPane();
   
   public GameMain() {
      setTitle("Main");
      c.setLayout(null);
      
      
      //������ ȯ���մϴ�! ������ �Բ�, ������ �ޱ�
      jlb_welcome = new JLabel();   
      jlb_welcome = new JLabel( new GameDAO().getUserInfo(LogInPage.getNO())+ "�� ȯ���մϴ�! ");
      jlb_welcome.setHorizontalAlignment(JLabel.CENTER);
      jlb_welcome.setOpaque(true);
      jlb_welcome.setFont(new Font("�޸�����ü", Font.BOLD, 20));
      jlb_welcome.setBounds(61, 86, 351, 60);
      c.add(jlb_welcome);
      
      // Ÿ��Ʋ ����
      JLabel title_1to45 = new JLabel("1  to  45");
      title_1to45.setHorizontalAlignment(JLabel.CENTER);
      title_1to45.setFont(new Font("Trebuchet MS", Font.BOLD, 40));
      title_1to45.setBounds(215, 0, 400, 100);
      c.add(title_1to45);
      
      
      // ��© �ֱ�
      try {
          ImageIcon icon = new ImageIcon("Start_Size.gif");
          JLabel label = new JLabel(icon);
          label.setBounds(450, 150, icon.getIconWidth(), icon.getIconHeight());
          c.add(label);
      } catch (Exception e) {
          System.out.println("���ܹ߻�" + e.getMessage());
      }
      
      //�����ϱ��ư, ���ӽ��۰� ����
      JButton btn_Start = new JButton("�����ϱ�");
      btn_Start.setFont(new Font("�޸�����ü", Font.BOLD, 20));
      btn_Start.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            new GamePage();
            setVisible(false);
         }
      });
      btn_Start.setBounds(61, 174, 150, 50);
      c.add(btn_Start);
       
      //���������ư, ���������������� ����
      JButton btn_Ranking = new JButton("��������");
      btn_Ranking.setFont(new Font("�޸�����ü", Font.BOLD, 20));
      btn_Ranking.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            // �������� �������� ����
            new RankingPage();
            setVisible(false);
         }
      });
      btn_Ranking.setBounds(155, 263, 150, 50);
      c.add(btn_Ranking);
      
      
//      JButton btn_Setting = new JButton("�����ϱ�");
//      btn_Setting.setFont(new Font("�޸�����ü", Font.BOLD, 20));
//      btn_Setting.addActionListener(new ActionListener() {
//         public void actionPerformed(ActionEvent e) {
//            // ���� �������� �̵�
//         }
//      });
//      btn_Setting.setBounds(189, 294, 150, 50);
//      c.add(btn_Setting);
      
      
      JButton btn_Exit = new JButton("������");
      btn_Exit.setFont(new Font("�޸�����ü", Font.BOLD, 20));
      btn_Exit.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            dispose();
         }
         //������ �������� �̵�
      });
      btn_Exit.setBounds(274, 354, 150, 50);
      c.add(btn_Exit);

      
      setLocation(LOCATION_X,LOCATION_Y);
      setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
      setVisible(true);
   }
}