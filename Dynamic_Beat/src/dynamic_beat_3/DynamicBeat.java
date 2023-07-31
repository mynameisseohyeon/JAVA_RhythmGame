package dynamic_beat_3;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame; //라이브러단축키 ctrl + shift + O

public class DynamicBeat extends JFrame{ //그래픽 기반

	private Image screenImage;
	private Graphics screenGraphic; //더블 버퍼링을 위해 전체 화면의 이미지를 담는 두 인스턴스
	
	private Image IntroBackground;
	
	public DynamicBeat() {
		setTitle("Dynamic Beat");
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT); //1280 * 720
		setResizable(false); //사용자가 인위적으로 화면을 줄일 수 없도록
		setLocationRelativeTo(null); //실행 시 만든 화면창이 컴퓨터 정중앙에 위치하도록
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //게임창 종료 시 전체 프로그램 종료를 알림
		setVisible(true); //만든 게임창이 출력되도록
		
		IntroBackground = new ImageIcon(Main.class.getResource("../images/IntroBackground.jpg")).getImage();
		//메인페이지의 위치를 기반으로 해서 IntroBackground라는 이름의 이미지 변수에 초기화
		
		Music IntroMusic = new Music("IntroMusic.mp3", true); //프로그램 시작 시 음악이 무한정 반복
		IntroMusic.start();
	}
	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT); //프로그램 화면 만큼 이미지를 생성
		screenGraphic = screenImage.getGraphics(); //screenGraphic객체를 통해 이미지를 얻어온다
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}
	
	public void screenDraw(Graphics g) { //프로그램이 종료되기 전까지 해당 작업을 계속 반복
		g.drawImage(IntroBackground, 0, 0, null); //0, 0 위치에 그려줌
		this.repaint(); //paint 함수를 불러옴
	}
}