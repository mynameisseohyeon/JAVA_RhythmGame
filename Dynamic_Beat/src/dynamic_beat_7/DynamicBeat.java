package dynamic_beat_7;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame; //라이브러단축키 ctrl + shift + O
import javax.swing.JLabel;

import dynamic_beat_6.Music;

public class DynamicBeat extends JFrame { // 그래픽 기반

	private Image screenImage;
	private Graphics screenGraphic; // 더블 버퍼링을 위해 전체 화면의 이미지를 담는 두 인스턴스

	private Image background = new ImageIcon(Main.class.getResource("../images/IntroBackground.jpg")).getImage();
	// 메인페이지의 위치를 기반으로 해서 background라는 이름의 이미지 변수에 초기화
	private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../images/menuBar.png")));
	
	private Image selectedImage = new ImageIcon(Main.class.getResource("../images/funday Start Image.png")).getImage();
	private Image titleImage = new ImageIcon(Main.class.getResource("../images/funday Title Image.png")).getImage();
	
	private ImageIcon exitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/exitButtonBasic.jpg"));
	private ImageIcon exitButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/exitButtonEnter.jpg"));
	private ImageIcon startButtonBasicImage = new ImageIcon(Main.class.getResource("../images/startButtonBasic.png"));
	private ImageIcon  startButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/startButtonEntered.png"));
	private ImageIcon quitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/quitButtonBasic.png"));
	private ImageIcon quitButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/quitButtonEntered.png"));
	private ImageIcon leftButtonBasicImage = new ImageIcon(Main.class.getResource("../images/leftButtonBasic.png"));
	private ImageIcon leftButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/leftButtonEntered.png"));
	private ImageIcon rightButtonBasicImage = new ImageIcon(Main.class.getResource("../images/rightButtonBasic.png"));
	private ImageIcon rightButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/rightButtonEntered.png"));
	
	
	private JButton exitButton = new JButton(exitButtonBasicImage); //버튼의 기본값으로 Basic버튼을 가져옴
	private JButton startButton = new JButton(startButtonBasicImage); 
	private JButton quitButton = new JButton(quitButtonBasicImage); 
	private JButton leftButton = new JButton(leftButtonBasicImage); 
	private JButton rightButton = new JButton(rightButtonBasicImage); 	
	
	private int mouseX, mouseY; //프로그램 안에서의 마우스 위치값
	
	private boolean isMainScreen = false; //Main값으로 바뀌게 되면 해당 값이 True로 바뀌게 된다
	
	ArrayList<Track> trackList = new ArrayList<Track>(); //변수를 담을 수 있는 배열
	
//	private Image titleImage;
//	private Image selectedImage;
	private Music selectedMusic = new Music("funday.mp3", true); //코드의 함수화를 통해 코드가 더욱 간결화될 수 있도록 수정
	private int nowSelected = 0; //0을 넣어줌으로써 1번쨰 곡을 선택
	
	public DynamicBeat() {
		setUndecorated(true);
		setTitle("Dynamic Beat");
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT); // 1280 * 720
		setResizable(false); // 사용자가 인위적으로 화면을 줄일 수 없도록
		setLocationRelativeTo(null); // 실행 시 만든 화면창이 컴퓨터 정중앙에 위치하도록
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 게임창 종료 시 전체 프로그램 종료를 알림
		setVisible(true); // 만든 게임창이 출력되도록
		setBackground(new Color(0, 0, 0, 0)); // paintColor 시 회색이 아닌 하얀색 출력
		setLayout(null); // 버튼 혹은 JLabel을 했을 시에 그 위치 그대로 적용
		leftButton.setVisible(false); //왼쪽 버튼 비활성화
		rightButton.setVisible(false); //오른쪽 버튼 비활성화

		trackList.add(new Track("funday Title Image.png", "funday Start Image.png", "funday Game Image.png", "funday.mp3"));
		trackList.add(new Track("happpyrock Title Image.png", "happyrock Start Image.png", "happyrock Game Image.png", "happyrock.mp3"));
		trackList.add(new Track("jazzyfrenchy Title Image.png", "jazzyfrenchy Start Image.png", "jazzyfrenchy Game Image.png", "jazzyfrenchy.mp3"));
		//실제 파일들을 Track 클래스의 생성자에 있는 위치대로 설정 /해당 list를 관리할 수 있게 된다
		
		menuBar.setBounds(0, 0, 1280, 30); // 위치와 크기를 정해줌
		menuBar.addMouseListener(new MouseAdapter() {
			@Override //해당 어노테이션을 사용하면, 컴파일러가 해당 메서드가 상위 클래스에서 오버라이드된 메서드인지 확인할 수 있다 / 실수 방지, 코드의 가독성을 높임
			public void mousePressed(MouseEvent e) { //마우스 클릭 이벤트가 발생했을 떄 마우스 x, y 좌표를 가져옴
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) { //마우스 드래그 이벤트 발생 시
				int x = e.getXOnScreen();
				int y = e.getYOnScreen(); //해당 값들의 좌표를 가져옴
				setLocation(x - mouseX, y - mouseY); //가져온 좌표를 통해 메뉴바를 잡고 이동이 가능
			}
		});


		exitButton.setBounds(1240, 0, 30, 30); //x, y좌표
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { //마우스가 닫기 버튼에 올라왔을 경우 이벤트 발생
				exitButton.setIcon(exitButtonEnteredImage); //버튼을 활성화된 이미지로 교체
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); //마우스가 손가락 모양으로 바뀜
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3", false); //버튼 클릭 bgm 재생
				buttonPressedMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) { //마우스가 닫기 버튼에 있지 않은 경우
				exitButton.setIcon(exitButtonBasicImage); //비활성화 버튼 이미지로 교체
				exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); //마우스가 원랴 상태로 돌아오도록
			}
			@Override
			public void mousePressed (MouseEvent e) { //마우스가 닫기 버튼을 클릭했을 경우
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3", false); //버튼 클릭 bgm 재생
				buttonPressedMusic.start();
				try { //정상적으로 bgm이 작동하도록 예외처리
					Thread.sleep(1000);
				} catch (InterruptedException ex){
					ex.printStackTrace();
				}
				System.exit(0); //프로그램 종료
			}
		});
		
		add(exitButton); //exitButton
		
		Music IntroMusic = new Music("IntroMusic.mp3", true); // 프로그램 시작 시 음악이 무한정 반복
		IntroMusic.start();
		add(menuBar);

	
		startButton.setBounds(60, 430, 400, 100); //x, y좌표
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.setFocusPainted(false);
		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { //마우스가 시작하기에 올라왔을 경우 이벤트 발생
				startButton.setIcon(startButtonEnteredImage); //버튼을 활성화된 이미지로 교체
				startButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); //마우스가 손가락 모양으로 바뀜
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3", false); //버튼 클릭 bgm 재생
				buttonPressedMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) { //마우스가 시작 버튼에 있지 않은 경우
				startButton.setIcon(startButtonBasicImage); //비활성화 버튼 이미지로 교체
				startButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); //마우스가 원랴 상태로 돌아오도록
			}
			@Override
			public void mousePressed (MouseEvent e) { //마우스가 시작 버튼을 클릭했을 경우
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3", false); //버튼 클릭 bgm 재생
				buttonPressedMusic.start(); 
				//시작하기 버튼 클릭 시
				IntroMusic.close();
				selectTrack(0); //리스트 중 1번째 index를 기본으로 보여줌
				startButton.setVisible(false); //시작하기 버튼이 보이지 않도록
				quitButton.setVisible(false); //종료하기 버튼이 보이지 않도록
				leftButton.setVisible(true); // 시작버튼 클릭 시 왼쪽 버튼 활성화
				rightButton.setVisible(true); //시작버튼 클릭 시 오른쪽 버튼 활성화
				background = new ImageIcon(Main.class.getResource("../images/mainBackground.jpg")).getImage();
				isMainScreen = true;
			}
		});
		
		add(startButton); //startButton
		

		quitButton.setBounds(60, 560, 400, 100); //x, y좌표
		quitButton.setBorderPainted(false);
		quitButton.setContentAreaFilled(false);
		quitButton.setFocusPainted(false);
		quitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { //마우스가 시작하기에 올라왔을 경우 이벤트 발생
				quitButton.setIcon(quitButtonEnteredImage); //버튼을 활성화된 이미지로 교체
				quitButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); //마우스가 손가락 모양으로 바뀜
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3", false); //버튼 클릭 bgm 재생
				buttonPressedMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) { //마우스가 닫기 버튼에 있지 않은 경우
				quitButton.setIcon(quitButtonBasicImage); //비활성화 버튼 이미지로 교체
				quitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); //마우스가 원랴 상태로 돌아오도록
			}
			@Override
			public void mousePressed (MouseEvent e) { //마우스가 닫기 버튼을 클릭했을 경우
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3", false); //버튼 클릭 bgm 재생
				buttonPressedMusic.start();
				try { //정상적으로 bgm이 작동하도록 예외처리
					Thread.sleep(1000);
				} catch (InterruptedException ex){
					ex.printStackTrace();
				}
				System.exit(0); //프로그램 종료
			}
		});
		
		add(quitButton); //quitButton
	
	leftButton.setBounds(140, 310, 60, 60); //x, y좌표
	leftButton.setBorderPainted(false);
	leftButton.setContentAreaFilled(false);
	leftButton.setFocusPainted(false);
	leftButton.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseEntered(MouseEvent e) { //마우스가 시작하기에 올라왔을 경우 이벤트 발생
			leftButton.setIcon(leftButtonEnteredImage); //버튼을 활성화된 이미지로 교체
			leftButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); //마우스가 손가락 모양으로 바뀜
			Music buttonPressedMusic = new Music("buttonPressedMusic.mp3", false); //버튼 클릭 bgm 재생
			buttonPressedMusic.start();
		}
		@Override
		public void mouseExited(MouseEvent e) { //마우스가 닫기 버튼에 있지 않은 경우
			leftButton.setIcon(leftButtonBasicImage); //비활성화 버튼 이미지로 교체
			leftButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); //마우스가 원랴 상태로 돌아오도록
		}
		@Override
		public void mousePressed (MouseEvent e) { //마우스가 닫기 버튼을 클릭했을 경우
			Music buttonPressedMusic = new Music("buttonPressedMusic.mp3", false); //버튼 클릭 bgm 재생
			buttonPressedMusic.start();
			selectLeft();
		}
	});
	
	add(leftButton); //leftButton 추가
	
	
	rightButton.setBounds(1060, 310, 60, 60); //x, y좌표
	rightButton.setBorderPainted(false);
	rightButton.setContentAreaFilled(false);
	rightButton.setFocusPainted(false);
	rightButton.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseEntered(MouseEvent e) { //마우스가 시작하기에 올라왔을 경우 이벤트 발생
			rightButton.setIcon(rightButtonEnteredImage); //버튼을 활성화된 이미지로 교체
			rightButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); //마우스가 손가락 모양으로 바뀜
			Music buttonPressedMusic = new Music("buttonPressedMusic.mp3", false); //버튼 클릭 bgm 재생
			buttonPressedMusic.start();
		}
		@Override
		public void mouseExited(MouseEvent e) { //마우스가 닫기 버튼에 있지 않은 경우
			rightButton.setIcon(rightButtonBasicImage); //비활성화 버튼 이미지로 교체
			rightButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); //마우스가 원랴 상태로 돌아오도록
		}
		@Override
		public void mousePressed (MouseEvent e) { //마우스가 닫기 버튼을 클릭했을 경우
			Music buttonPressedMusic = new Music("buttonPressedMusic.mp3", false); //버튼 클릭 bgm 재생
			buttonPressedMusic.start();
			selectRight();
		}
	});
	
	add(rightButton); //rightButton 추가
}

	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT); // 프로그램 화면 만큼 이미지를 생성
		screenGraphic = screenImage.getGraphics(); // screenGraphic객체를 통해 이미지를 얻어온다
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics g) { // 프로그램이 종료되기 전까지 해당 작업을 계속 반복 / 현재 화면이 mainScreen인 경우
		g.drawImage(background, 0, 0, null); // 0, 0 위치에 그려줌
		if(isMainScreen) { //true일 경우
			g.drawImage(selectedImage, 340, 100, null); //단순한 이미지를 보여줄 때 사용
			g.drawImage(titleImage, 340, 70, null); //타이틀 이미지
		}
		paintComponents(g); // JLabel과 같은 요소를 JFrame안에 추가해주면 그려주는 것 / 메뉴 바의 경우 항상 존재하는 이미지이기 때문에 paint 사용  / 메인페이지에 추가된 요소들을 보여줌
		this.repaint(); // paint 함수를 불러옴
	}
	
	public void selectTrack(int nowSelected) {
		if(selectedMusic != null) //selectedMusic이 빈 값이 아니라면
			selectedMusic.close();
		
		titleImage = new ImageIcon(Main.class.getResource("../images/" + trackList.get(nowSelected).getTitleImage())).getImage();
		//현재 선택된 곡이 가지고 있는 타이틀 이미지를 보여줌
		selectedImage = new ImageIcon(Main.class.getResource("../images/" + trackList.get(nowSelected).getStartImage())).getImage();
		selectedMusic = new Music(trackList.get(nowSelected).getStartMusic(),true); //곡이 무한반복 될 수 있도록 true로 설정
		selectedMusic.start();
	}
	
	public void selectLeft() { //왼쪽 버튼을 클릭했을 때
		if(nowSelected == 0)
			nowSelected = trackList.size() - 1; //가장 오른족에 있는 곡 선택
		else //기징 왼쪽에 있는 곡이 아닐 경우
			nowSelected--; //변수에서 -1
		selectTrack(nowSelected);
	}
	
	public void selectRight() { //오른쪽 버튼을 클릭했을 때
		if(nowSelected == trackList.size() -1) //가장 오른쪽에 있는 곡이 선택되어 있다면
			nowSelected = 0; //가장 왼쪽에 있는 곡 선택
		else //기징 오른족에 있는 곡이 아닐 경우
			nowSelected--; //변수에서 -1
		selectTrack(nowSelected);
	}
}