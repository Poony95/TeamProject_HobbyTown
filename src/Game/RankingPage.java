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
      
      
      // ��© �ֱ�
            try {
                ImageIcon icon = new ImageIcon("Correct.gif");
                JLabel label = new JLabel(icon);
                JPanel panel = new JPanel(new BorderLayout());
                panel.add(label, BorderLayout.CENTER);
                panel.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
                c.add(panel);
            } catch (Exception e) {
                System.out.println("���ܹ߻�" + e.getMessage());
            }
            
      
      // Ÿ��Ʋ ���� - STARTPAGE�� ����.
      JLabel title_1to45 = new JLabel("1  to  45");
      title_1to45.setHorizontalAlignment(JLabel.CENTER);
      title_1to45.setFont(new Font("Trebuchet MS", Font.BOLD, 40));
      title_1to45.setBounds(215, 0, 400, 100);
      c.add(title_1to45);
      
      //rowData ����
      rowData = new Vector<Vector<String>>();
      
      // �÷� ����
      colNames = new Vector<String>();
      colNames.add("����");
      colNames.add("�г���");
      colNames.add("������");
      colNames.add("�ð�");

      //���̺� ����
      table = new JTable(rowData, colNames);
      JScrollPane jsp = new JScrollPane(table);
      jsp.setBounds(135, 158, 629, 258); // JScrollPane ��ġ�� ũ�� ����
      c.add(jsp);
      table.setDefaultEditor(Object.class, null); // �� ���� �Ұ����ϰ� ����
      
      table.addMouseListener(new MouseAdapter() {
           public void mouseClicked(MouseEvent e) {
               if (e.getClickCount() == 2) { // ����Ŭ���̸�
               int row = table.getSelectedRow(); //2�����������´�
               HashMap<String, Object> map = list.get(row); 
               int num = Integer.parseInt(map.get("user_no")+"");
               String name = new UserDAO().getName(num);
              int result = JOptionPane.showConfirmDialog(null, name +"�̴�Ȩ�Ƿ� �̵��Ͻðڽ��ϱ�?");   
                 if(result == JOptionPane.YES_OPTION) { // ����ڰ� "��"�� ������ ���
                     try {
                        new FriendRoom(num);
                     } catch (Exception ex) {
                        System.out.println("���ܹ߻�" + ex.getMessage());
                     }
                 }
              }
           }
       });
      
      JButton btn_Exit = new JButton("������");
      btn_Exit.setFont(new Font("�޸�����ü", Font.BOLD, 20));
      btn_Exit.setBounds(724, 34, 150, 50);
      btn_Exit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
         new GameMain();
         setVisible(false);
      }
   });
      c.add(btn_Exit);
      
      // �޺��ڽ��� ������ ������ �ִ´�.
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
         

// �������� ���� ��ŷ ������ �ε� �޼ҵ�
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