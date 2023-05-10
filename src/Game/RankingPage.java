package Game;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import dao.GameDAO;
import dao.UserDAO;
import home.FriendRoom;
import utillist.Utils;

public class RankingPage extends JFrame{
   JTable table;
   Vector<String> colNames;
   Vector<Vector<String>> rowData;
   JTextField jtf;
   JComboBox box_address;
   ArrayList<HashMap<String, Object>> list;
   public RankingPage(){
      
      JPanel p = new JPanel();
      Container c = getContentPane();
      setTitle("RANKINGPAGE");
      getContentPane().setLayout(null);
      
      
      // 움짤 넣기
            try {
                ImageIcon icon = new ImageIcon("Correct.gif");
                JLabel label = new JLabel(icon);
                JPanel panel = new JPanel(new BorderLayout());
                panel.add(label, BorderLayout.CENTER);
                panel.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
                c.add(panel);
            } catch (Exception e) {
                System.out.println("예외발생" + e.getMessage());
            }
            
      
      // 타이틀 설정 - STARTPAGE와 같음.
      JLabel title_1to45 = new JLabel("1  to  45");
      title_1to45.setHorizontalAlignment(JLabel.CENTER);
      title_1to45.setFont(new Font("Trebuchet MS", Font.BOLD, 40));
      title_1to45.setBounds(215, 0, 400, 100);
      c.add(title_1to45);
      
      //rowData 설정
      rowData = new Vector<Vector<String>>();
      
      // 컬럼 설정
      colNames = new Vector<String>();
      colNames.add("순위");
      colNames.add("닉네임");
      colNames.add("거주지");
      colNames.add("시간");

      //테이블 생성
      table = new JTable(rowData, colNames);
      JScrollPane jsp = new JScrollPane(table);
      jsp.setBounds(135, 158, 629, 258); // JScrollPane 위치와 크기 설정
      c.add(jsp);
      table.setDefaultEditor(Object.class, null); // 셀 편집 불가능하게 설정
      
      table.addMouseListener(new MouseAdapter() {
           public void mouseClicked(MouseEvent e) {
               if (e.getClickCount() == 2) { // 더블클릭이면
               int row = table.getSelectedRow(); //2번쨰열가져온다
               HashMap<String, Object> map = list.get(row); 
               int num = Integer.parseInt(map.get("user_no")+"");
               String name = new UserDAO().getName(num);
              int result = JOptionPane.showConfirmDialog(null, name +"미니홈피로 이동하시겠습니까?");   
                 if(result == JOptionPane.YES_OPTION) { // 사용자가 "예"를 선택한 경우
                     try {
                        new FriendRoom(num);
                     } catch (Exception ex) {
                        System.out.println("예외발생" + ex.getMessage());
                     }
                 }
              }
           }
       });
      
      JButton btn_Exit = new JButton("나가기");
      btn_Exit.setFont(new Font("휴먼편지체", Font.BOLD, 20));
      btn_Exit.setBounds(724, 34, 150, 50);
      btn_Exit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
         new GameMain();
         setVisible(false);
      }
   });
      c.add(btn_Exit);
      
      // 콤보박스에 거주지 정보를 넣는다.
      JComboBox<String> box_address = new JComboBox<String>(Utils.address);

      box_address.setBounds(361, 110, 120, 23);
      c.add(box_address);
      box_address.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
           loadRecordByAddress(box_address.getSelectedItem().toString());
      }
   });

      setLocation(GameMain.LOCATION_X,GameMain.LOCATION_Y);
      setSize(GameMain.SCREEN_WIDTH,GameMain.SCREEN_HEIGHT);
      setVisible(true);
      loadRecord();
   }
         

// 거주지에 따른 랭킹 데이터 로드 메소드
   public void loadRecordByAddress(String address) {
       rowData.clear();
       GameDAO dao = new GameDAO();
       list = dao.recordaddresslist(address);
       for (HashMap<String, Object> map : list) {
           Vector<String> v = new Vector<String>();
           v.add(map.get("Ranking") + "");
           v.add((String) map.get("name"));
           v.add((String) map.get("address"));
           v.add(String.format("%06.2f", Double.parseDouble(map.get("score") + "")));
           rowData.add(v);
       }
       table.updateUI();
   }
   public void loadRecord() {
       rowData.clear();
       GameDAO dao = new GameDAO();
       list = dao.recordlist();
       for(HashMap<String, Object> map :list) {
          Vector<String> v= new Vector<String>();
          v.add(map.get("Ranking")+"");
          v.add((String)map.get("name"));
          v.add((String)map.get("address"));
          v.add(String.format("%06.2f", Double.parseDouble(map.get("score")+"")));
          rowData.add(v);
       }
       
       table.updateUI();
   }
}