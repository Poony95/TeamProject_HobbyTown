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
      
      
      //유저님 환영합니다! 문구와 함께, 데이터 받기
      jlb_welcome = new JLabel();   
      jlb_welcome = new JLabel( new GameDAO().getUserInfo(LogInPage.getNO())+ "님 환영합니다! ");
      jlb_welcome.setHorizontalAlignment(JLabel.CENTER);
      jlb_welcome.setOpaque(true);
      jlb_welcome.setFont(new Font("휴먼편지체", Font.BOLD, 20));
      jlb_welcome.setBounds(61, 86, 351, 60);
      c.add(jlb_welcome);
      
      // 타이틀 설정
      JLabel title_1to45 = new JLabel("1  to  45");
      title_1to45.setHorizontalAlignment(JLabel.CENTER);
      title_1to45.setFont(new Font("Trebuchet MS", Font.BOLD, 40));
      title_1to45.setBounds(215, 0, 400, 100);
      c.add(title_1to45);
      
      
      // 움짤 넣기
      try {
          ImageIcon icon = new ImageIcon("Start_Size.gif");
          JLabel label = new JLabel(icon);
          label.setBounds(450, 150, icon.getIconWidth(), icon.getIconHeight());
          c.add(label);
      } catch (Exception e) {
          System.out.println("예외발생" + e.getMessage());
      }
      
      //시작하기버튼, 게임시작과 연동
      JButton btn_Start = new JButton("시작하기");
      btn_Start.setFont(new Font("휴먼편지체", Font.BOLD, 20));
      btn_Start.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            new GamePage();
            setVisible(false);
         }
      });
      btn_Start.setBounds(61, 174, 150, 50);
      c.add(btn_Start);
       
      //순위보기버튼, 순위보기페이지와 연동
      JButton btn_Ranking = new JButton("순위보기");
      btn_Ranking.setFont(new Font("휴먼편지체", Font.BOLD, 20));
      btn_Ranking.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            // 순위보기 페이지로 연동
            new RankingPage();
            setVisible(false);
         }
      });
      btn_Ranking.setBounds(155, 263, 150, 50);
      c.add(btn_Ranking);
      
      
//      JButton btn_Setting = new JButton("설정하기");
//      btn_Setting.setFont(new Font("휴먼편지체", Font.BOLD, 20));
//      btn_Setting.addActionListener(new ActionListener() {
//         public void actionPerformed(ActionEvent e) {
//            // 설정 페이지로 이동
//         }
//      });
//      btn_Setting.setBounds(189, 294, 150, 50);
//      c.add(btn_Setting);
      
      
      JButton btn_Exit = new JButton("나가기");
      btn_Exit.setFont(new Font("휴먼편지체", Font.BOLD, 20));
      btn_Exit.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            dispose();
         }
         //나가기 페이지로 이동
      });
      btn_Exit.setBounds(274, 354, 150, 50);
      c.add(btn_Exit);

      
      setLocation(LOCATION_X,LOCATION_Y);
      setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
      setVisible(true);
   }
}