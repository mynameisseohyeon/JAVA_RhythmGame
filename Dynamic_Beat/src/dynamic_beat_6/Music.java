package dynamic_beat_6;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class Music extends Thread{ //thread란 하나의 작은 프로그램
	
	private Player player; //라이브러리를 말함
	private boolean isLoop; //mp3파일이 무한 반복인지 한 번만 반복인지
	private File file;
	private FileInputStream fis;
	private BufferedInputStream bis;
	
	public Music(String name, boolean isLoop ) {
		try {
			this.isLoop = isLoop;
			file = new File(Main.class.getResource("./music/IntroMusic.mp3").toURI());
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			player = new Player(bis);
		} catch (Exception e) { //예외 처리를 위해 사용
			System.out.println(e.getMessage());
		}
	}
	public int getTime() {
		if (player == null)  //0.001초 단위까지 알려줌
			return 0;
		return player.getPosition();
	}
	public void close() { //언제 실행되던 항상 종료할 수 있게 해줌
		isLoop = false;
		player.close(); //해당 곡이 안정적으로 종료될 수 있도록 도움
		this.interrupt();
	}
	
	@Override
	public void run() {
		try { //곡 실행
			do {
				player.play(); //곡 실행
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				player = new Player(bis);
			} while (isLoop); //isLoop의 값이 true일 경우 무한반복
		} catch(Exception e) { //오류 메세지 출력
			System.out.println(e.getMessage()); //오류가 발생한 경우 해당 오류 메세지 발생
		}
	}
}
