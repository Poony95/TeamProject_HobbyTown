package boards;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Console;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import Post.postShow;
import dao.BoardDAO;
import vo.BoardVO;
//수정 예정
public class Board extends JFrame {
	int row = 1;
	int totalCount;
	int totalPages;
	int currentPage;
	int pagesize = 10;
	JTextField jtf_search;
	JComboBox<String> jcb_option;	//같이해요, 우리동네 선택
	String []interest = {"건강/운동","음식/요리","영화/공연/전시","미술/공예","노래/음악","재테크","기타"};
	JCheckBox []jcb = new JCheckBox[interest.length];
	JTable table;
	Vector<String> colNames;
	Vector<Vector<String>> rowData;
	JRadioButton jrb_option1;
	JRadioButton jrb_option2;
	ArrayList<BoardVO> list;
	postShow pR;	//게시글 읽어오는 객체
	
	public static int postNum;	//선택된 게시글
		
	public Board() {
		BoardDAO dao = new BoardDAO();
		list = dao.viewAllList(1);
		
		JPanel p_main = new JPanel();	//게시판 메인 화면
		p_main.setLayout(new BorderLayout());
		JPanel p_search1 = new JPanel();	//검색창 전체 패널
		JPanel p_search2 = new JPanel();	//검색창 상단
		JPanel p_search3 = new JPanel();	//검색창 하단
		JPanel p_interest = new JPanel();	//관심사 검색 패널
		p_interest.setLayout(new GridLayout(7,1));
		JPanel p_etc = new JPanel();	//글쓰기, 페이지 넘김
		p_etc.setLayout(new GridLayout(1,3));
		JPanel p_etc1 = new JPanel();
		JPanel p_etc2 = new JPanel();
		JPanel p_etc3 = new JPanel();
		p_etc.add(p_etc1);
		p_etc.add(p_etc2);
		p_etc.add(p_etc3);
		
		p_search1.setLayout(new GridLayout(3,1));
		jcb_option = new JComboBox<String>();
		jcb_option.addItem("함께해요");
		jcb_option.addItem("동네생활");
		
		jtf_search = new JTextField(20);
		JButton btn_search = new JButton("검색");
		JButton btn_clear = new JButton("초기화");
		jrb_option1 = new JRadioButton("인기순") ;	//인기순 정렬
		jrb_option2 = new JRadioButton("최신순");	//최신순 정렬
		ButtonGroup bg = new ButtonGroup();
		bg.add(jrb_option1);
		bg.add(jrb_option2);
		
		p_search2.add(jcb_option);
		p_search2.add(jtf_search);
		p_search2.add(btn_search);
		p_search2.add(btn_clear);
		p_search3.add(jrb_option1);
		p_search3.add(jrb_option2);
		p_search1.add(p_search2);
		p_search1.add(p_search3);
		
		p_main.add(p_search1, BorderLayout.NORTH);
	
		//체크박스 선택하여 관심사별 목록 구성
		for(int i=0; i<jcb.length ; i++) {
			jcb[i] = new JCheckBox(interest[i]);
			p_interest.add(jcb[i]);
			
			jcb[i].addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			        rowData.clear();
			        String data = "";
			        for (int j = 0; j < jcb.length; j++) {
			            if (jcb[j].isSelected() == true) {
			                data += jcb[j].getText();
			            }
			        }
			        list = dao.interestList(data);
			        for (BoardVO b : list) {
			            Vector<String> v = new Vector<>();
			            v.add(b.getB_no() + "");
			            v.add(b.getAddress());
			            v.add(b.getCategory());
			            v.add(b.getInterest());
			            v.add(b.getTitle());
			            v.add(b.getDate_board() + "");
			            v.add(b.getAppilcation());
			            v.add(b.getB_cnt() + "");
			            v.add(b.getL_cnt() + "");
			            rowData.add(v);
			        }
			        table.updateUI();
			    }
			});
		}
		p_main.add(p_interest, BorderLayout.WEST);
		
		colNames = new Vector<String>();
		rowData = new Vector<Vector<String>>();
		
		colNames.add("번호");
		colNames.add("위치");
		colNames.add("카테고리");
		colNames.add("관심사");
		colNames.add("제목");
		colNames.add("작성날짜");
		colNames.add("모집인원");
		colNames.add("조회수");
		colNames.add("♥");
		table = new JTable(rowData, colNames);
		JScrollPane jsp = new JScrollPane(table);
		TableColumnModel columnModel = table.getColumnModel();
		TableColumn column3 = columnModel.getColumn(3);
		TableColumn column4 = columnModel.getColumn(4);
		TableColumn column5 = columnModel.getColumn(5);
		column3.setPreferredWidth(100);
		column4.setPreferredWidth(350);
		column5.setPreferredWidth(110);
		table.revalidate();
		table.setRowHeight(50);
		p_main.add(jsp, BorderLayout.CENTER);
		
		JButton btn_write = new JButton("게시글 작성");
		p_etc3.add(btn_write);
		p_main.add(p_etc, BorderLayout.SOUTH);
		add(p_main);
		
		loadList(row); 
		for(int i =1; i<=totalPages+1; i++) {
			JButton btn = new JButton(i+" ");
			btn.setBorder(null);
			
			p_etc2.add(btn);
			
			btn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int page = Integer.parseInt(e.getActionCommand().trim());
					loadList(page);
				}
			});
		}
		
		setTitle("커뮤니티 게시판");
		setSize(800, 708);
		setLocationRelativeTo(null);
		setVisible(true);
	
		// 게시글 작성 버튼
		btn_write.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				WriteBoard writepage = new WriteBoard();
				writepage.setVisible(true);
			}
		});
		
		// 초기화 버튼 
		btn_clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BoardDAO dao = new BoardDAO();
				list = dao.viewAllList(1);
				loadList(row);
			}
		});
		
		// 검색 이벤트 설정
		btn_search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				rowData.clear();
				BoardDAO dao = new BoardDAO();
				String search = jtf_search.getText();
				String category = (String) jcb_option.getSelectedItem();
				
				 list = dao.SearchList(search);
				
				if (jrb_option2.isSelected()) {
					list = dao.dateSearchList(search);
				}
				if (jrb_option1.isSelected()) {
					list = dao.likedSearchList(search);
				}
				if (jcb_option.getSelectedItem() != null) {
					list = dao.categorySearchList(search, category);
				}
				
				for( BoardVO b :list) {
					Vector<String> v = new Vector<>();
					v.add(b.getB_no()+"");
					v.add(b.getAddress());
					v.add(b.getCategory());
					v.add(b.getInterest());
					v.add(b.getTitle());
					v.add(b.getDate_board()+"");
					v.add(b.getAppilcation());
					v.add(b.getB_cnt()+"");
					v.add(b.getL_cnt()+"");
					rowData.add(v);
				}
				table.updateUI();
			}
		});
				//라디오 버튼 인기순 정렬
				jrb_option1.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						rowData.clear();
						if(jrb_option1.isSelected()) {
							BoardDAO dao = new BoardDAO();
							ArrayList<BoardVO> list = dao.viewLikedList();
							
							for( BoardVO b :list) {
								Vector<String> v = new Vector<>();
								v.add(b.getB_no()+"");
								v.add(b.getAddress());
								v.add(b.getCategory());
								v.add(b.getInterest());
								v.add(b.getTitle());
								v.add(b.getDate_board()+"");
								v.add(b.getAppilcation());
								v.add(b.getB_cnt()+"");
								v.add(b.getL_cnt()+"");
								rowData.add(v);
							}
							table.updateUI();
						}
					}
				});
				
				//라디오 버튼 최신순 정렬
				jrb_option2.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						rowData.clear();
						if(jrb_option2.isSelected()) {
							BoardDAO dao = new BoardDAO();
							ArrayList<BoardVO> list = dao.viewNewestList();
							for( BoardVO b :list) {
								Vector<String> v = new Vector<>();
								v.add(b.getB_no()+"");
								v.add(b.getAddress());
								v.add(b.getCategory());
								v.add(b.getInterest());
								v.add(b.getTitle());
								v.add(b.getDate_board()+"");
								v.add(b.getAppilcation());
								v.add(b.getB_cnt()+"");
								v.add(b.getL_cnt()+"");
								rowData.add(v);
							}
							table.updateUI();
						}
					}
				});
				
				//게시글 더블클릭시 게시글 상세페이지 팝업(수진_0508추가)
				table.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent mouseEvent) {
				JTable table = (JTable)mouseEvent.getSource();
				Point point = mouseEvent.getPoint();
				int idx = table.rowAtPoint(point);
				if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
				postNum = Integer.parseInt(rowData.get(idx).get(0));
				pR = new postShow(postNum);	
						}
					}
					
				});

			table.addMouseListener(new MouseListener() {

		    @Override
		    public void mouseReleased(MouseEvent e) {
		    }

		    @Override
		    public void mousePressed(MouseEvent e) {
		        // 상세페이지로 연결 예정
		        if (e.getClickCount() == 2) {
		            updatehits();
		            BoardDAO dao = new BoardDAO();
		    		list = dao.viewAllList(1);
		            loadList(currentPage);
		        }
		    }
		    @Override
		    public void mouseExited(MouseEvent e) {
		    }
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    }
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    }
		});
	}
	
	public void loadList(int page) {
	    rowData.clear();
	    totalCount = list.size(); // 전체 게시글 수 가져오기
		totalPages = (int) Math.ceil(totalCount / pagesize); // 총 페이지 수 계산
		currentPage = page;
		int start = (currentPage -1)*pagesize;
		int end = start + pagesize;
		if (end > totalCount) { // 끝 인덱스가 전체 게시글 수보다 크면
		    end = totalCount; // 끝 인덱스를 전체 게시글 수로 설정
		}
	   
	    for(int i=start; i<end; i++) {
	    	BoardVO b = list.get(i);
	        Vector<String> v = new Vector<>();
	        v.add(b.getB_no() + "");
	        v.add(b.getAddress());
	        v.add(b.getCategory());
	        v.add(b.getInterest());
	        v.add(b.getTitle());
	        v.add(b.getDate_board() + "");
	        v.add(b.getAppilcation());
	        v.add(b.getB_cnt() + "");
	        v.add(b.getL_cnt() + "");
	        rowData.add(v);
	    }

	    table.updateUI();
	}

	public void updatehits() {
	    int row = table.getSelectedRow();
	    Vector<String> v = rowData.get(row);
	    BoardDAO dao = new BoardDAO();
	    BoardVO vo = new BoardVO();
	    int hits = Integer.parseInt(v.get(7));	// 조회수 번호 설정
	    vo.setB_no(Integer.parseInt(v.get(0))); // 게시글 번호 설정
	    vo.setB_cnt(hits);
	    int re = dao.updateHits(vo);
	}
	
	public static void main(String[] args) {
		new Board();
	} 

}
;