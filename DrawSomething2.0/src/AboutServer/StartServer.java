package AboutServer;

import java.awt.EventQueue;

//�ο�����http://blog.sina.com.cn/s/blog_643e6efa01019ops.html
//      http://blog.163.com/germans@126/blog/static/26972374201210655554651/
//�첽���߳�

public class StartServer {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				RegistServer establishserver = new RegistServer();
			}
		});
		PlayServer gs = new PlayServer();
		
	}
}


