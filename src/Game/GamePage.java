package Game;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.security.DomainLoadStoreParameter;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import dao.GameDAO;
import first.LogInPage;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePage extends JFrame {

    private JButton[] btnarr;
    private Timer timer;
    private JLabel Time;
    private JLabel timeLabel = new JLabel("000.00");
    private int delay = 10; // 10ms ���� ����� �۾�
    private double[] timeElapsed = {000}; // ����� �ð�
    private double savedTime = 0;
    private int n = 0;
    private JPanel GPanel;
    private JPanel IPanel;
    //00 �⺻ ����
    int count = 1; // �����ߵǴ� ����
    int x = 0;
    int [] first = new int[9];
    int [] second = new int[9];
    int [] third = new int[9];
    int [] fourth = new int[9];
    int [] fifth = new int[9];

    Container c = getContentPane(); 

    JLabel countLabel = new JLabel(Integer.toString(count));

    //01 ��ư, �迭 ��, �������� List ����,
    
    //02 3 * 3 �� �����, 45������ ����
    ArrayList<Integer> list1 = new ArrayList<>(9);
    ArrayList<Integer> list2 = new ArrayList<>(9);
    ArrayList<Integer> list3 = new ArrayList<>(9);
    ArrayList<Integer> list4 = new ArrayList<>(9);
    ArrayList<Integer> list5 = new ArrayList<>(9);
    public GamePage() {
       setTitle("Game2");
       c.setLayout(null);

       // �迭�� �������� �ǳ� ����
       JPanel GPanel = new JPanel(new BorderLayout());
       GPanel.add(GameLogic(),BorderLayout.CENTER);
       GPanel.setBounds(362, 150, 183, 205);
       c.add(GPanel);
       
       //��© ����
       MyFrame();
       
       // �������� ��
       JLabel Count = new JLabel("���� ����");
       Count.setFont(new Font("�޸�����ü", Font.BOLD, 40));
       Count.setHorizontalAlignment(JLabel.CENTER);
       Count.setBounds(362, 27, 183, 46);
       c.add(Count);
       countLabel.setFont(new Font("�޸�����ü", Font.BOLD, 30));

       // ���ڰ� �����ϴ� ���� �޼ҵ忡���� ���� ������ ��������� ����
       countLabel.setHorizontalAlignment(JLabel.CENTER);
       countLabel.setBounds(362, 73, 183, 46);
       c.add(countLabel);

      
      // �ð� �� 
      JLabel Time = new JLabel("�� ��");
      Time.setFont(new Font("�޸�����ü", Font.BOLD, 40));
      Time.setHorizontalAlignment(JLabel.CENTER);
      Time.setBounds(697, 27, 119, 46);
      c.add(Time);

       //Timer ���
       startTimer();
       timeLabel.setFont(new Font("�޸�����ü", Font.BOLD, 30));
       timeLabel.setHorizontalAlignment(JLabel.RIGHT);
       timeLabel.setBounds(697, 73, 119, 46);
       c.add(timeLabel);

       // �ٽ��ϱ� ��ư
       JButton btn_restart = new JButton("�ٽ��ϱ�");
       btn_restart.setFont(new Font("�޸�����ü", Font.BOLD, 20));
       btn_restart.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             new GamePage();
             setVisible(false);
          }
       });
       btn_restart.setBounds(666, 150, 150, 50);
       c.add(btn_restart);

       
       // �������� ��ư
       JButton btn_Ranking = new JButton("��������");
       btn_Ranking.setFont(new Font("�޸�����ü", Font.BOLD, 20));
       btn_Ranking.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             new RankingPage();
             setVisible(false);
          }
       });
       btn_Ranking.setBounds(666, 227, 150, 50);
       c.add(btn_Ranking);

       
       // ������ ��ư
       JButton btn_Exit = new JButton("������");
       btn_Exit.setFont(new Font("�޸�����ü", Font.BOLD, 20));
       btn_Exit.setBounds(666, 307, 150, 50);
       btn_Exit.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              new GameMain();
              setVisible(false);
           }
        });
       c.add(btn_Exit);
       
       
       
       addKeyListener(new MyKeyListener()); // KeyListener ���
       
       setLocation(GameMain.LOCATION_X,GameMain.LOCATION_Y);
       setFocusable(true); // ��Ŀ�� ��û
       setResizable(false);
       setSize(GameMain.SCREEN_WIDTH,GameMain.SCREEN_HEIGHT);
       setVisible(true);

    }
    
    // �г� ���� �� �Է��� ���� ���� �޼ҵ�
    public void MyFrame() {
       IPanel = new JPanel(new BorderLayout());
       IPanel.setBounds(50, 80, 300, 300);
       c.add(IPanel);
       
    } 
    
    // ó�� �̹���
    public void Image_Start() {
       try {
          ImageIcon icon = new ImageIcon("Start_Size.gif");
          JLabel label = new JLabel(icon);
          IPanel.add(label, BorderLayout.CENTER);
          IPanel.revalidate(); // �г��� �ٽ� �׸��� ���� ȣ��
       } catch (Exception e) {
          System.out.println("���ܹ߻�" + e.getMessage());
       }
    }

    //��ư�� �¾��� �� ����ϴ� �ǳ�
    public void Image_Correct() {
       // �г� �ʱ�ȭ
       IPanel.removeAll();
       try {
          ImageIcon icon = new ImageIcon("OK.gif");
          JLabel label = new JLabel(icon);
          IPanel.add(label, BorderLayout.CENTER);
          IPanel.revalidate(); // �г��� �ٽ� �׸��� ���� ȣ��
       } catch (Exception e) {
          System.out.println("���ܹ߻�" + e.getMessage());
       }
    }   
    
    //��ư�� Ʋ���� �� ����ϴ� �ǳ�
    public void Image_Wrong() {
       // �г� �ʱ�ȭ
       IPanel.removeAll();
       try {
          ImageIcon icon = new ImageIcon("Wrong.gif");
          JLabel label = new JLabel(icon);
          IPanel.add(label, BorderLayout.CENTER);
          IPanel.revalidate(); // �г��� �ٽ� �׸��� ���� ȣ��
       } catch (Exception e) {
          System.out.println("���ܹ߻�" + e.getMessage());
       }
    } 
    
    //Ÿ�̸� ����
    public Timer startTimer() {
        Timer timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (count >= 2 && count < 46) {
                        timeElapsed[0] = timeElapsed[0] + (double) delay / 1000; // 1�� = 1000ms
                        String timeText = String.format("%06.2f", timeElapsed[0]);
                        timeLabel.setText(timeText); // ������ �κ�
                    } else if (count == 46) { // ������ ������ �� �ð��� ���߱� ����
                        savedTime = timeElapsed[0]; // savedTime ������ ������
                        String timeText = String.format("%06.2f", timeElapsed[0]);
                        timeLabel.setText(timeText); // count�� 45�� �� timeLabel�� �ؽ�Ʈ�� ������
                        JOptionPane.showMessageDialog(null, "�����մϴ�!");
                        count ++; //Dialog�� �������� ��ġ. 
                           int re = new GameDAO().record(LogInPage.getNO(), savedTime);
                           System.out.println(re);
                    } else {
                        timeLabel.setText("000.00"); // ������ �κ�
                    }
                } catch (Exception e2) {
                    System.out.println("���ܹ߻�" + e2.getMessage());
                }
            }
        });

        timer.start();
        return timer;
    }
    
    

    
    // savedTime�� ��ȯ�ϴ� �޼ҵ�
    public double getSavedTime() {
        return savedTime;
    }
    private void updateCountLabel() {
        countLabel.setText(Integer.toString(count));
        if(count == 46) {
            countLabel.setText("E N D");
        }
    }
    
    //�ð� ���� �޼ҵ�
    private void stopTimer() {
        savedTime = timeElapsed[0]; // final ������ ���� �Ҵ��� �� �����Ƿ� ���� �߻�
       timer.stop();
       // ���� �̺�Ʈ ó�� �ڵ� �߰�
    }

    public JPanel GameLogic() {
        //03 ���ڸ� ����Ʈ�� �߰�
        for (int i = 1; i <= 9; i++) 
            list1.add(i);
        for (int i = 10; i <= 18; i++) 
            list2.add(i);
        for (int i = 19; i <= 27; i++) 
           list3.add(i);
        for (int i = 28; i <= 36; i++) 
           list4.add(i);
        for (int i = 37; i <= 45; i++) 
           list5.add(i);

                //04 ����Ʈ�� �����ϰ� ����
        Collections.shuffle(list1);
        Collections.shuffle(list2);
        Collections.shuffle(list3);
        Collections.shuffle(list4);
        Collections.shuffle(list5);
        System.out.println(list1);
        System.out.println(list2);

        //05 ����Ʈ�� �迭�� ����
        for(int i = 0; i < 9 ; i++) {
          first[i]  = list1.get(i);
          second[i] = list2.get(i);
          third[i]  = list3.get(i);
          fourth[i] = list4.get(i);
          fifth[i]  = list5.get(i);
        }
        // ��ư���ٰ� �迭 ����
        JPanel p = new JPanel();
        p.setBounds(294, 5, 0, 0);
        p.setLayout(new GridLayout(3,3));
        
        btnarr = new JButton[9];
        for (int i = 0 ; i < 9 ; i++) {
            btnarr[i] = new JButton(Integer.toString(first[i]));
            btnarr[i].setFont(new Font("Arial", Font.BOLD, 25));
            btnarr[i].addKeyListener(new MyKeyListener());
            p.add(btnarr[i]);
        }
        return p;
    }
    
    // key���� �޴� �޼ҵ�
    
    private class MyKeyListener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
           

           int[] keyCodes = { KeyEvent.VK_NUMPAD7, KeyEvent.VK_NUMPAD8, KeyEvent.VK_NUMPAD9,
                 KeyEvent.VK_NUMPAD4, KeyEvent.VK_NUMPAD5, KeyEvent.VK_NUMPAD6,
                 KeyEvent.VK_NUMPAD1, KeyEvent.VK_NUMPAD2, KeyEvent.VK_NUMPAD3 };

           try {   

                for (int i = 0; i < btnarr.length; i++) {
                    if (e.getKeyCode() == keyCodes[i] ) {                   // �Ҹ� ���� �ε�
                   File soundFile = new File("btn_sound.wav");
                   AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                   // Ŭ�� ���� �� ���
                   Clip clip = AudioSystem.getClip();
                   clip.open(audioIn);
                   clip.start();
                    } 
                }
           }
            catch (Exception ex) {
                   ex.printStackTrace();
               }
                
               
               
              for (int i = 0; i < btnarr.length; i++) {
                 if (e.getKeyCode() == keyCodes[i]) {
                   btnarr[i].doClick();
                   break;
               }
           }
           
              if (count <= 9) {
                  for (int i = 0; i < btnarr.length; i++) {
                      if (e.getKeyCode() == keyCodes[i] && 
                            Integer.toString(count).equals(Integer.toString(first[i]))) {
                          btnarr[i].setText(Integer.toString(second[i]));
                          count = count + 1;
                          Image_Correct();    
                          updateCountLabel();
                          return;
                      } else {
                         Image_Wrong();
                      }
                  }
              }
            
            else if (count >= 10 && count <= 18) {
                for (int i = 0; i < btnarr.length; i++) {
                     if (e.getKeyCode() == keyCodes[i] && 
                           Integer.toString(count).equals(Integer.toString(second[i]))) {
                         btnarr[i].setText(Integer.toString(third[i]));
                         count = count + 1;
                         Image_Correct();    
                         updateCountLabel();
                         return;
                     } else {
                        Image_Wrong();
                     }
                 }
            }
            else if (count >= 19 && count <= 27) {
                for (int i = 0; i < btnarr.length; i++) {
                     if (e.getKeyCode() == keyCodes[i] && 
                           Integer.toString(count).equals(Integer.toString(third[i]))) {
                         btnarr[i].setText(Integer.toString(fourth[i]));
                         count = count + 1;
                         Image_Correct();    
                         updateCountLabel();
                         return;
                     } else {
                        Image_Wrong();
                     }
                 }
            }
            
            else if (count >= 28 && count <= 36) {
                for (int i = 0; i < btnarr.length; i++) {
                     if (e.getKeyCode() == keyCodes[i] && 
                           Integer.toString(count).equals(Integer.toString(fourth[i]))) {
                         btnarr[i].setText(Integer.toString(fifth[i]));
                         count = count + 1;
                         Image_Correct();    
                         updateCountLabel();
                         return;
                     } else {
                        Image_Wrong();
                     }
                 }
            }
            
            else if (count >= 37 && count <= 45) {
                for (int i = 0; i < btnarr.length; i++) {
                     if (e.getKeyCode() == keyCodes[i] && 
                           Integer.toString(count).equals(Integer.toString(fifth[i]))) {
                         btnarr[i].setText("0");
                         count = count + 1;
                         Image_Correct();    
                         updateCountLabel();
                         return;
                     } else {
                        Image_Wrong();
                     }
                 }
            }
        }
        // �ٸ� KeyListener �޼ҵ���� �������� ����

        @Override
        public void keyTyped(KeyEvent e) {
            
        }

        @Override
        public void keyReleased(KeyEvent e) {
            
        }
    }
}

        