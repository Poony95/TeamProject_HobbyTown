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
    private int delay = 10; // 10ms 마다 실행될 작업
    private double[] timeElapsed = {000}; // 경과된 시간
    private double savedTime = 0;
    private int n = 0;
    private JPanel GPanel;
    private JPanel IPanel;
    //00 기본 세팅
    int count = 1; // 눌러야되는 숫자
    int x = 0;
    int [] first = new int[9];
    int [] second = new int[9];
    int [] third = new int[9];
    int [] fourth = new int[9];
    int [] fifth = new int[9];

    Container c = getContentPane(); 

    JLabel countLabel = new JLabel(Integer.toString(count));

    //01 버튼, 배열 및, 랜덤숫자 List 생성,
    
    //02 3 * 3 로 만들며, 45까지만 예정
    ArrayList<Integer> list1 = new ArrayList<>(9);
    ArrayList<Integer> list2 = new ArrayList<>(9);
    ArrayList<Integer> list3 = new ArrayList<>(9);
    ArrayList<Integer> list4 = new ArrayList<>(9);
    ArrayList<Integer> list5 = new ArrayList<>(9);
    public GamePage() {
       setTitle("Game2");
       c.setLayout(null);

       // 배열이 들어가기위한 판넬 설정
       JPanel GPanel = new JPanel(new BorderLayout());
       GPanel.add(GameLogic(),BorderLayout.CENTER);
       GPanel.setBounds(362, 150, 183, 205);
       c.add(GPanel);
       
       //움짤 설정
       MyFrame();
       
       // 다음숫자 라벨
       JLabel Count = new JLabel("다음 숫자");
       Count.setFont(new Font("휴먼편지체", Font.BOLD, 40));
       Count.setHorizontalAlignment(JLabel.CENTER);
       Count.setBounds(362, 27, 183, 46);
       c.add(Count);
       countLabel.setFont(new Font("휴먼편지체", Font.BOLD, 30));

       // 숫자가 증가하는 라벨은 메소드에서도 쓰기 때문에 멤버변수로 선언
       countLabel.setHorizontalAlignment(JLabel.CENTER);
       countLabel.setBounds(362, 73, 183, 46);
       c.add(countLabel);

      
      // 시간 라벨 
      JLabel Time = new JLabel("시 간");
      Time.setFont(new Font("휴먼편지체", Font.BOLD, 40));
      Time.setHorizontalAlignment(JLabel.CENTER);
      Time.setBounds(697, 27, 119, 46);
      c.add(Time);

       //Timer 출력
       startTimer();
       timeLabel.setFont(new Font("휴먼편지체", Font.BOLD, 30));
       timeLabel.setHorizontalAlignment(JLabel.RIGHT);
       timeLabel.setBounds(697, 73, 119, 46);
       c.add(timeLabel);

       // 다시하기 버튼
       JButton btn_restart = new JButton("다시하기");
       btn_restart.setFont(new Font("휴먼편지체", Font.BOLD, 20));
       btn_restart.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             new GamePage();
             setVisible(false);
          }
       });
       btn_restart.setBounds(666, 150, 150, 50);
       c.add(btn_restart);

       
       // 순위보기 버튼
       JButton btn_Ranking = new JButton("순위보기");
       btn_Ranking.setFont(new Font("휴먼편지체", Font.BOLD, 20));
       btn_Ranking.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             new RankingPage();
             setVisible(false);
          }
       });
       btn_Ranking.setBounds(666, 227, 150, 50);
       c.add(btn_Ranking);

       
       // 나가기 버튼
       JButton btn_Exit = new JButton("나가기");
       btn_Exit.setFont(new Font("휴먼편지체", Font.BOLD, 20));
       btn_Exit.setBounds(666, 307, 150, 50);
       btn_Exit.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              new GameMain();
              setVisible(false);
           }
        });
       c.add(btn_Exit);
       
       
       
       addKeyListener(new MyKeyListener()); // KeyListener 등록
       
       setLocation(GameMain.LOCATION_X,GameMain.LOCATION_Y);
       setFocusable(true); // 포커스 요청
       setResizable(false);
       setSize(GameMain.SCREEN_WIDTH,GameMain.SCREEN_HEIGHT);
       setVisible(true);

    }
    
    // 패널 삭제 및 입력을 위해 담을 메소드
    public void MyFrame() {
       IPanel = new JPanel(new BorderLayout());
       IPanel.setBounds(50, 80, 300, 300);
       c.add(IPanel);
       
    } 
    
    // 처음 이미지
    public void Image_Start() {
       try {
          ImageIcon icon = new ImageIcon("Start_Size.gif");
          JLabel label = new JLabel(icon);
          IPanel.add(label, BorderLayout.CENTER);
          IPanel.revalidate(); // 패널을 다시 그리기 위해 호출
       } catch (Exception e) {
          System.out.println("예외발생" + e.getMessage());
       }
    }

    //버튼이 맞았을 때 출력하는 판넬
    public void Image_Correct() {
       // 패널 초기화
       IPanel.removeAll();
       try {
          ImageIcon icon = new ImageIcon("OK.gif");
          JLabel label = new JLabel(icon);
          IPanel.add(label, BorderLayout.CENTER);
          IPanel.revalidate(); // 패널을 다시 그리기 위해 호출
       } catch (Exception e) {
          System.out.println("예외발생" + e.getMessage());
       }
    }   
    
    //버튼이 틀렸을 때 출력하는 판넬
    public void Image_Wrong() {
       // 패널 초기화
       IPanel.removeAll();
       try {
          ImageIcon icon = new ImageIcon("Wrong.gif");
          JLabel label = new JLabel(icon);
          IPanel.add(label, BorderLayout.CENTER);
          IPanel.revalidate(); // 패널을 다시 그리기 위해 호출
       } catch (Exception e) {
          System.out.println("예외발생" + e.getMessage());
       }
    } 
    
    //타이머 로직
    public Timer startTimer() {
        Timer timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (count >= 2 && count < 46) {
                        timeElapsed[0] = timeElapsed[0] + (double) delay / 1000; // 1초 = 1000ms
                        String timeText = String.format("%06.2f", timeElapsed[0]);
                        timeLabel.setText(timeText); // 수정된 부분
                    } else if (count == 46) { // 게임이 끝났을 때 시간이 멈추기 위함
                        savedTime = timeElapsed[0]; // savedTime 변수를 갱신함
                        String timeText = String.format("%06.2f", timeElapsed[0]);
                        timeLabel.setText(timeText); // count가 45일 때 timeLabel의 텍스트를 변경함
                        JOptionPane.showMessageDialog(null, "축하합니다!");
                        count ++; //Dialog를 끄기위한 장치. 
                           int re = new GameDAO().record(LogInPage.getNO(), savedTime);
                           System.out.println(re);
                    } else {
                        timeLabel.setText("000.00"); // 수정된 부분
                    }
                } catch (Exception e2) {
                    System.out.println("예외발생" + e2.getMessage());
                }
            }
        });

        timer.start();
        return timer;
    }
    
    

    
    // savedTime을 반환하는 메소드
    public double getSavedTime() {
        return savedTime;
    }
    private void updateCountLabel() {
        countLabel.setText(Integer.toString(count));
        if(count == 46) {
            countLabel.setText("E N D");
        }
    }
    
    //시간 멈춤 메소드
    private void stopTimer() {
        savedTime = timeElapsed[0]; // final 변수에 값을 할당할 수 없으므로 에러 발생
       timer.stop();
       // 종료 이벤트 처리 코드 추가
    }

    public JPanel GameLogic() {
        //03 숫자를 리스트에 추가
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

                //04 리스트를 랜덤하게 섞음
        Collections.shuffle(list1);
        Collections.shuffle(list2);
        Collections.shuffle(list3);
        Collections.shuffle(list4);
        Collections.shuffle(list5);
        System.out.println(list1);
        System.out.println(list2);

        //05 리스트를 배열과 연결
        for(int i = 0; i < 9 ; i++) {
          first[i]  = list1.get(i);
          second[i] = list2.get(i);
          third[i]  = list3.get(i);
          fourth[i] = list4.get(i);
          fifth[i]  = list5.get(i);
        }
        // 버튼에다가 배열 연결
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
    
    // key값을 받는 메소드
    
    private class MyKeyListener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
           

           int[] keyCodes = { KeyEvent.VK_NUMPAD7, KeyEvent.VK_NUMPAD8, KeyEvent.VK_NUMPAD9,
                 KeyEvent.VK_NUMPAD4, KeyEvent.VK_NUMPAD5, KeyEvent.VK_NUMPAD6,
                 KeyEvent.VK_NUMPAD1, KeyEvent.VK_NUMPAD2, KeyEvent.VK_NUMPAD3 };

           try {   

                for (int i = 0; i < btnarr.length; i++) {
                    if (e.getKeyCode() == keyCodes[i] ) {                   // 소리 파일 로드
                   File soundFile = new File("btn_sound.wav");
                   AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                   // 클립 생성 및 재생
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
        // 다른 KeyListener 메소드들은 구현하지 않음

        @Override
        public void keyTyped(KeyEvent e) {
            
        }

        @Override
        public void keyReleased(KeyEvent e) {
            
        }
    }
}

        