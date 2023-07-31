package dynamic_beat_1;

import javax.swing.JFrame; //라이브러단축키 ctrl + shift + O

public class DynamicBeat extends JFrame{ //그래픽 기반

	public DynamicBeat() {
		setTitle("Dynamic Beat");
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT); //1280 * 720
		setResizable(false); //사용자가 인위적으로 화면을 줄일 수 없도록
		setLocationRelativeTo(null); //실행 시 만든 화면창이 컴퓨터 정중앙에 위치하도록
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //게임창 종료 시 전체 프로그램 종료를 알림
		setVisible(true); //만든 게임창이 출력되도록
	}
}
