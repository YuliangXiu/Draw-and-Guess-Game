package AboutClient;

/**��ͼ���ֵ�panel
 * paint�����������õ����֮�仭��ʵ�֣����彨������ע��
 * draw���ֲο�Demo�϶�
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.*;


public class DrawPanel extends JPanel implements MouseListener,
		MouseMotionListener, ActionListener {
	private int CanvasY = 55;
	private int CanvasHeight = 345;
	private int DrawPanelWide = 500;
	private static int times = 0;
	private int mouseX = 0;
	private int mouseY = 0;
	private int oldMouseX = 0;
	private int oldMouseY = 0;
	private BasicStroke stroke = null;
	private Color color = null;
	
	static int grade = 0;
	int i;
	static int a = 1;
	static int b = 1;
	int c ;
	int count = 0;
	
	String buttontype;
	BufferedImage image = null;
	String answer;
	boolean isTurn = false;

	String playerName = null;
	GameConnection gameNet = null;
	GameInterface gamePad = null;
	JButton button;
	JRadioButton q1, q2, q3, q4;
	JButton sendBtn = null;
	JButton checkBtn = null;
	//JPanel jp = new JPanel();
	
	JTextField jtf = new JTextField("",40);
	
	
	 
	DrawPanel drawBoard = null;
	JProgressBar bar  = new JProgressBar();
	JButton strokeBtn, eraserBtn, colorBtn, repaintBtn;
	JLabel strokeLabel;
	JComboBox strokeBox;

	CanvasTool canvasTool = null;
	Canvas canvas = null;
	QuestionTool questionTool = null;
	Thread thr1;

	// TimeCount timeCount = null;

	public DrawPanel(GameInterface gamePad, String playerName,
			GameConnection gameNet) {

		this.playerName = playerName;
		this.gamePad = gamePad;
		this.gameNet = gameNet;

		addMouseListener(this);
		addMouseMotionListener(this);

		initComponent();

	}

	private void initComponent() {
		setLayout(null);
		canvasTool = new CanvasTool();
		canvas = new Canvas();
		questionTool = new QuestionTool();
		// timeCount = new TimeCount();
	}

	// �ο�http://zhidao.baidu.com/question/288292430.html

	public void paint(Graphics g) {

		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;

		Graphics2D bg = image.createGraphics();// ��ȡ����ͼ�� Graphics2D

		bg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		bg.setStroke(stroke);

		bg.setColor(color);
		if (isTurn) {

			bg.drawLine(oldMouseX, oldMouseY - CanvasY, mouseX, mouseY
					- CanvasY);
		}

		g2d.drawImage(image, 0, CanvasY, this);
	}

	public void update(Graphics g) {
		this.paint(g);
	}

	public void guess() {
		GuessDialog gd = new GuessDialog(gamePad);
	}

	public void mouseDragged(MouseEvent e) {
		this.oldMouseX = this.mouseX;
		this.oldMouseY = this.mouseY;
		this.mouseX = e.getX();
		this.mouseY = e.getY();
		repaint();

		gameNet.sendPicture(image);

	}

	public void mouseEntered(MouseEvent e) {
		this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
	}

	public void mouseExited(MouseEvent e) {
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public void mousePressed(MouseEvent e) {
		this.oldMouseX = this.mouseX = e.getX();
		this.oldMouseY = this.mouseY = e.getY();
		repaint();

		gameNet.sendPicture(image);
	}

	public void mouseReleased(MouseEvent e) {
		this.mouseX = e.getX();
		this.mouseY = e.getY();
		Color defaultColor = color;
		color = defaultColor;
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("color")) {
			Color selectColor = JColorChooser.showDialog(DrawPanel.this,
					"��ɫѡ��", Color.BLACK);
			color = selectColor;
		} else if (cmd.equals("stroke")) {
			color = Color.BLACK;
		} else if (cmd.equals("strokeSize")) {
			String s = (String) strokeBox.getSelectedItem();
			stroke = new BasicStroke((Integer.parseInt(s)));
		} else if (cmd.equals("eraser")) {
			color = Color.WHITE;
		} else if (cmd.equals("sendAnswer")) {
			gameNet.sendAnswer(answer);
		} else if (cmd.equals("repaint")) {
			new Canvas();
			repaint();
			count=0;
		}
	}

	class Canvas {
		Canvas() {
			init();
		}

		private void init() {
			color = new Color(0, 0, 0);
			image = new BufferedImage(DrawPanelWide, CanvasHeight,
					BufferedImage.TYPE_INT_RGB);
			stroke = new BasicStroke(3.0f, BasicStroke.CAP_ROUND,
					BasicStroke.JOIN_ROUND);
			image.getGraphics().setColor(Color.white);
			image.getGraphics().fillRect(0, 0, DrawPanelWide, CanvasHeight);
		}
	}

	class CanvasTool {
		CanvasTool() {
			init();
		}

		private void init() {
			JToolBar drawBar = new JToolBar();
			drawBar.setLayout(new FlowLayout(10));
			drawBar.setBounds(0, 0, DrawPanelWide, 50);
			
			 thr1 = new Thread(new Runnable(){
		   			int count = 0;
		   			public void run(){ 
		   				while(true){
		   					bar.setValue(++count);
		   					
		   					
		   					try{
		   						Thread.sleep(1800);
		   						if(count == 99){
		   					    count = 0;
		   					    JFrame Frame = new JFrame("���ɼ�����");
		   					    Frame.setBounds(400, 300, 200, 240);
		   					    //Frame.setBackground(Color.GREEN);
		   					    //ImageIcon helpImg = new ImageIcon("regulation.jpg");
		   					    JLabel Label = new JLabel();
		   					    if(grade>=0){
		   					    ImageIcon img1 = new ImageIcon("yes.jpg");
		   						JLabel imgLabel = new JLabel(img1);
		   						imgLabel.setBounds(0,20,200,200);
		   						JLabel strLabel = new JLabel("���"+playerName+"���÷�"+grade+"\n"+"�����۽𾦣�");
		   						strLabel.setFont(new Font("����_GB2312", Font.BOLD, 12));
		   						strLabel.setBounds(0,0,200,20);
		   					    Label.add(strLabel);
		   					    Label.add(imgLabel);
		   					    //Label.setBackground(Color.GREEN);
		   					    }else{
		   					    ImageIcon img2 = new ImageIcon("no.jpg");
		   						JLabel imgLabel = new JLabel(img2);
		   						imgLabel.setBounds(0,20,200,200);
		   						JLabel strLabel = new JLabel("���"+playerName+"���÷�"+grade+"\n"+"�����ÿӵ���");
		   						strLabel.setFont(new Font("����_GB2312", Font.BOLD, 12));
		   						strLabel.setBounds(0,0,200,20);
		   					    Label.add(strLabel);
		   					    Label.add(imgLabel);
		   					    //Label.setBackground(Color.RED);
		   					    }
		   					    Frame.add(Label);
		   					    Frame.setVisible(true);
		   					    Frame.setResizable(false);
		   					    
		   						}
		   					}
		   					catch(Exception e){
		   						e.printStackTrace();
		   					}
		   				}
		   			}
		   			
		   		});
		    	   thr1.start();

			Icon i1 = new ImageIcon("����.jpg");
			strokeBtn = new JButton(i1);
			strokeBtn.setToolTipText("����");
			strokeBtn.setBackground(Color.white);

			Icon i2 = new ImageIcon("������ɫ.jpg");
			colorBtn = new JButton(i2);
			colorBtn.setToolTipText("������ɫ");
			colorBtn.setBackground(Color.white);

			strokeLabel = new JLabel("���ʴ�С");

			Icon i3 = new ImageIcon("��Ƥ.jpg");
			eraserBtn = new JButton(i3);
			eraserBtn.setToolTipText("��Ƥ");
			eraserBtn.setBackground(Color.white);

			Icon i4 = new ImageIcon("�ػ�.jpg");
			repaintBtn = new JButton(i4);
			repaintBtn.setToolTipText("�ػ�");
			repaintBtn.setBackground(Color.white);

			String[] strokeSize = { "1", "2", "3", "4", "5", "6", "7", "8",
					"9", "10" };
			strokeBox = new JComboBox(strokeSize);

			strokeBtn.setActionCommand("stroke");
			eraserBtn.setActionCommand("eraser");
			strokeBox.setActionCommand("strokeSize");
			colorBtn.setActionCommand("color");
			repaintBtn.setActionCommand("repaint");

			strokeBtn.addActionListener(DrawPanel.this);
			strokeBox.addActionListener(DrawPanel.this);
			eraserBtn.addActionListener(DrawPanel.this);
			colorBtn.addActionListener(DrawPanel.this);
			repaintBtn.addActionListener(DrawPanel.this);

			drawBar.add(strokeBtn);
			drawBar.add(eraserBtn);
			drawBar.add(strokeLabel);
			drawBar.add(strokeBox);
			drawBar.add(colorBtn);
			drawBar.add(repaintBtn);
			drawBar.add(bar);

			add(drawBar);
		}
	}

	class QuestionTool {
		QuestionTool() {
			init();
		}

		private void init() {
			JMenuBar qBar = new JMenuBar();
			JMenuBar bar = new JMenuBar();
			qBar.setLayout(new BorderLayout());
			JLabel questionLab = new JLabel("��ѡ����ͼ��");

			ButtonGroup btnGroup = new ButtonGroup();
			q1 = new JRadioButton();
			q2 = new JRadioButton();
			q3 = new JRadioButton();
			q4 = new JRadioButton();
			q1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					answer = q1.getText();
					System.out.println("q1 " + answer);
				}
			});
			q2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					answer = q2.getText();
					System.out.println("q2 " + answer);
				}
			});
			q3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					answer = q3.getText();
					System.out.println("q3 " + answer);
				}
			});
			q4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					answer = q4.getText();
					System.out.println("q4 " + answer);
				}
			});

			btnGroup.add(q1);
			btnGroup.add(q2);
			btnGroup.add(q3);
			btnGroup.add(q4);

			bar.add(questionLab);
			bar.add(q1);
			bar.add(q2);
			bar.add(q3);
			bar.add(q4);
			qBar.add(bar, BorderLayout.WEST);

			sendBtn = new JButton("��ɻ���");
			sendBtn.setActionCommand("sendAnswer");
			sendBtn.addActionListener(DrawPanel.this);
			qBar.add(sendBtn, BorderLayout.EAST);

			qBar.setBounds(0,400, DrawPanelWide, 40);
			add(qBar);
		}
	}

	class GuessDialog extends Dialog implements ActionListener {

		String que1, que2, que3, que4;
		String answerStringArray[] = new String[8];
		String totalset[] = new String[16];
		String str ;
		//JButton btn1, btn2, btn3, btn4;		
		int i;

		public GuessDialog(Frame gamePad) {
			super(gamePad, "������ͼ�����ݣ�ƴ����ɴ�", false);
			setSize(500, 150);
			setLocation(500, 500);
			init();
			setVisible(true);
		}

		public void init() {
			
			char[] answerArray = answer.toCharArray();
			for(i=0;i<answerArray.length;i++){
				String answerstring = String.valueOf(answerArray[i]);	
				answerStringArray[i] = answerstring;
			}
			/*que1 = q1.getText();
			char[] answercharArray1 = que1.toCharArray();
			for(i=0;i<answercharArray1.length;i++){
				String answerstring = String.valueOf(answercharArray1[i]);	
				answerStringArray[i] = answerstring;
			}
			
			que2 = q2.getText();
			char[] answercharArray2 = que2.toCharArray();
			for(i=2;i<answercharArray2.length;i++){
				String answerstring = String.valueOf(answercharArray2[i]);	
				answerStringArray[i] = answerstring;
			}
			que3 = q3.getText();
			char[] answercharArray3 = que3.toCharArray();
			for(i=4;i<answercharArray3.length;i++){
				String answerstring = String.valueOf(answercharArray3[i]);	
				answerStringArray[i] = answerstring;
			}
			que4 = q4.getText();
			char[] answercharArray4 = que4.toCharArray();
			for(i=6;i<answercharArray4.length;i++){
				String answerstring = String.valueOf(answercharArray4[i]);	
				answerStringArray[i] = answerstring;
			}*/
			 String list[] ={"��","��","��","��","��","��","��","��","˹","��","�","��","С","��","��","��","��","��","��","��","��","��","��","��"
					 ,"��","��","��","��","Ӧ","��","û","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��",
					 "��","��","��","��","��","��","��","Ӣ","��","��","̫","��"};
			 Random r = new Random();
			 for(int i=0;i<answerStringArray.length;i++){
				 int num1 = r.nextInt(answerStringArray.length);
				 if(totalset[num1] == null){
				 totalset[num1]=answerStringArray[i];
				 }else{totalset[num1+1]=answerStringArray[i];;}
			 }
			 
			 for(int i=0;i<16;i++){
				 
				 int num3 = r.nextInt(list.length);
				 if(totalset[i]!=null){
					 continue;
				 }
				 else{
					 totalset[i]=list[num3];
				 }
				 
			 }
			 for(int i = 0;i<16;i++){
				 str +=totalset[i]; 
				
			 }
			 System.out.print(str);
			 JPanel jp = new JPanel();
			 jp.setSize(100,70);
			 jp.add(jtf);
			 
			 for(c=0;c<16;c++){
				 button =new JButton();
		    	 button.setText(totalset[c]);
		    	 button.setSize(20,10);
		    	 buttontype = button.getText();
		    	 //button.setActionCommand(buttontype);
		    	 button.addActionListener(this);
		    	 jp.add(button);
		    	
		    }
			 
		    //jtf.setSize(80,40);
		    checkBtn = new JButton("�˶Դ�");
		    checkBtn.setSize(40,15);
		    checkBtn.setBackground(Color.ORANGE);
	    	jp.add(checkBtn);
	    	checkBtn.setActionCommand("2");
	    	checkBtn.addActionListener(this);
		    add(jp);
		    
		   
			
			//btn1 = new JButton(que1);
			//btn2 = new JButton(que2);
			//btn3 = new JButton(que3);
			//btn4 = new JButton(que4);
			/*btn1.addActionListener(this);
			btn2.addActionListener(this);
			btn3.addActionListener(this);
			btn4.addActionListener(this);
			btn1.setActionCommand("1");
			btn2.setActionCommand("2");
			btn3.setActionCommand("3");
			btn4.setActionCommand("4");
			jp.add(btn1);
			jp.add(btn2);
			jp.add(btn3);
			jp.add(btn4);*/
			
		}

		public void actionPerformed(ActionEvent e) {
			
			count++;
			String cmd = e.getActionCommand();
			if(count<=2){
			String text =jtf.getText()+cmd;
			jtf.setText(text);
			}else if(cmd.equals("2")){
				guessResult(jtf.getText());
				jtf.setText("");
			}
		}
			/*if (cmd.equals("1")) {
				guessResult(que1);
			} else if (cmd.equals("2")) {
				guessResult(que2);
			} else if (cmd.equals("3")) {
				guessResult(que3);
			} else if (cmd.equals("4")) {
				guessResult(que4);
			}*/
		

		public void guessResult(String guess) {
			if (guess.equals(answer)) {
				JOptionPane.showMessageDialog(null, "��ϲ�㣬�ش���ȷ");
				grade+=2;
				// ��������ȥ��Ŀǰδʵ�֣�����ע�͵�
				// gameNet.sendAddPoint();
			} else
				JOptionPane.showMessageDialog(drawBoard, "�ش������ȷ����  "
						+ answer);
			grade-=1;
			setVisible(false);
		}
	}
}
