import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.Timer;

public class CBallMaze extends JFrame implements ActionListener{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JPanel Panel1, Panel2, Panel3;
	private JButton bAct,bRun,bReset;
	private JButton left,right,up,down,blankuptwo,blankdowntwo,blankup,blankdown,blankmiddle;
	private JButton firstOption,secondOption,thirdOption,exitOption;
	private JMenuBar Menu;
	private JTextField optionField, squareField, directionField;
	private JLabel optionLabel, squareLabel, directionLabel,timerLabel;
	private JLabel firstDash,secondDash,sliderLabel, iCompass;
	private JTextField secsField, minsField, hoursField;
	private int ticks = 0;
	private Timer timer, tim;
	private JMenu op,nf,pn,ex,of,toEdit,nxtfile,toControl,cgcontrol,toHelp,tt,hcontent;
	private JSlider slider;
	private File play;

	private int bX = 15; // X- axis of Ball
	private int bY = 0;  // Y-axis of Ball
	//code for Image

	private ImageIcon sImage = new ImageIcon("..\\PSP\\sand.jpg"); // sImage is for the image of sand
	private ImageIcon bImage = new ImageIcon("..\\PSP\\gold-ball.png"); // bImage is for the image of Ball
	private ImageIcon wImage = new ImageIcon("..\\PSP\\white32x32.jpg");// wImage is the white sand image
	private ImageIcon northImage = new ImageIcon("..\\PSP\\north.jpg");//northimage is the direction image of north side
	private ImageIcon southImage = new ImageIcon("..\\PSP\\south.jpg");//southimage is the direction image of south side
	private ImageIcon eastImage = new ImageIcon("..\\PSP\\east.jpg");//eastimage is the direction image of east side
	private ImageIcon westImage = new ImageIcon("..\\PSP\\west.jpg");//westimage is the direction image of west side
	private ImageIcon sandstoneImage = new ImageIcon("..\\PSP\\sandstone.jpg");// Image for sandstone

	private JLabel[][] road = new JLabel[16][13];

	GridBagConstraints gdbag = new GridBagConstraints();

	public static void main(String args[]) {
		CBallMaze frame = new CBallMaze();
		frame.setSize(775,650);
		frame.createGUI();
		frame.setVisible(true);
	}
	private void createGUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container window = getContentPane();
		window.setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);

		// Adding the Menu Bar in the frame
		Menu = new JMenuBar();
		Menu.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		Menu.setBounds(0, 0, 765, 29);
		window.add(Menu);

		op = new JMenu("Scenario");
		Menu.add(op);
		nf = new JMenu("Create New File");
		op.add(nf);
		of = new JMenu("Open New File");
		op.add(of);
		pn = new JMenu("Print File");
		op.add(pn);
		ex = new JMenu("Exit");
		op.add(ex);

		toEdit = new JMenu("Edit");
		Menu.add(toEdit);
		nxtfile = new JMenu("Edit File in Next Page");
		toEdit.add(nxtfile);

		toControl = new JMenu("Control");
		Menu.add(toControl);
		cgcontrol = new JMenu("Change the control settings");
		toControl.add(cgcontrol);

		toHelp = new JMenu("Help");
		Menu.add(toHelp);
		hcontent = new JMenu("Help Contents");
		toHelp.add(hcontent);
		tt = new JMenu("Tips and Tricks");
		toHelp.add(tt);

		Panel1 = new JPanel();		
		Panel1.setLayout(new GridLayout());
		Panel1.setBackground(Color.WHITE);
		Panel1.setBounds(00, 00, 530, 537);
		window.add(Panel1);

		Panel1.setLayout(new GridBagLayout());

		Panel2 = new JPanel();
		Panel2.setBackground(Color.LIGHT_GRAY);
		//					Panel2.setBounds(500, 00, 272, 561);
		Panel2.setBounds(500, 28, 272, 509);
		window.add(Panel2);
		Panel2.setLayout(null);

		Panel3 = new JPanel();
		Panel3.setBackground(Color.GRAY);
		Panel3.setBounds(00, 537, 775, 75);
		window.add(Panel3);
		Panel3.setLayout(null);

		// Setting image for the Panel3 buttons
		ImageIcon bActImage = new ImageIcon("..\\PSP\\step.png");
		ImageIcon bRunImage = new ImageIcon("..\\PSP\\run.png");
		ImageIcon bResetImage = new ImageIcon("..\\PSP\\reset.png");
		bAct = new JButton("Act", bActImage);
		bAct.setBounds(55,15 ,78, 28);
		Panel3.add(bAct);
		bAct.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent e) {
				
				gotoleft();
				gotodown();
				timer.start();
				if(bX==0 && bY==12) {
					JOptionPane.showMessageDialog(null,"Congratulations!! you've won the game");
				}
			}

		});

		bRun = new JButton("Run", bRunImage);
		bRun.setBounds(155, 15, 78, 28);
		Panel3.add(bRun);
		bRun.addActionListener(this);
		
		

		bReset = new JButton("Reset",bResetImage);
		bReset.setBounds(255, 15, 86, 28);
		Panel3.add(bReset);
		bReset.addActionListener(this);


		// Panel 2 starts:

		optionLabel = new JLabel("Option:");
		optionLabel.setBounds(70,0, 90, 40);
		Panel2.add(optionLabel);

		optionField = new JTextField(5);
		optionField.setBounds(158,5, 90, 26);
		Panel2.add(optionField);

		squareLabel = new JLabel("Square:");
		squareLabel.setBounds(70, 32, 110, 50);
		Panel2.add(squareLabel); 

		squareField = new JTextField(6);
		squareField.setBounds(158, 43, 90, 26);
		Panel2.add(squareField);

		directionLabel = new JLabel("Direction:");
		directionLabel.setBounds(70, 64, 130, 60);
		Panel2.add(directionLabel);

		directionField = new JTextField(6);
		directionField.setBounds(158, 81, 90, 26);
		Panel2.add(directionField);

		timerLabel = new JLabel("DIGITAL TIMER");
		timerLabel.setBounds(109, 120, 112, 10);
		Panel2.add(timerLabel);

		hoursField = new JTextField("");
		hoursField.setBackground(Color.BLACK);
		hoursField.setForeground(Color.WHITE);
		hoursField.setBounds(78, 143, 28, 23);
		Panel2.add(hoursField);

		minsField = new JTextField("");
		minsField.setBackground(Color.BLACK);
		minsField.setForeground(Color.WHITE);
		minsField.setBounds(140, 143, 28, 23);
		Panel2.add(minsField);

		secsField = new JTextField("");
		secsField.setBackground(Color.BLACK);
		secsField.setForeground(Color.WHITE);
		secsField.setBounds(201, 143, 28, 23);
		Panel2.add(secsField);
		timer = new Timer(500,this);
		//		timer.stop();

		firstDash = new JLabel(":");
		firstDash.setBounds(119, 143, 25, 23);
		Panel2.add(firstDash);

		secondDash = new JLabel(":");
		secondDash.setBounds(183, 143, 25, 23);
		Panel2.add(secondDash);

		blankup= new JButton();
		blankup.setBounds(68, 181, 59, 40);
		Panel2.add(blankup);

		blankuptwo = new JButton();
		blankuptwo.setBounds(185, 181, 59, 40);
		Panel2.add(blankuptwo);

		blankdown = new JButton();
		blankdown.setBounds(68, 258, 59, 40);
		Panel2.add(blankdown);

		blankdowntwo = new JButton();
		blankdowntwo.setBounds(185, 258, 59,40);
		Panel2.add(blankdowntwo);

		blankmiddle = new JButton();
		blankmiddle.setBounds(127, 219, 59, 40);
		Panel2.add(blankmiddle);

		up = new JButton("^");
		up.setBounds(126, 181, 60, 40);
		Panel2.add(up);
		up.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(road[bX][bY-1].getIcon().equals(sImage)) {
					road[bX][bY-1].setIcon(bImage);
					road[bX][bY].setIcon(sImage);
					bY--;

					iCompass.setIcon(northImage);
					directionField.setText("North");
					squareField.setText(Integer.toString(bY));
				}
			}
		});
		down = new JButton("v");
		down.setBounds(126, 258, 60, 40);
		Panel2.add(down);
		down.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == down) {
					gotodown();
					Soundtrack(play);
				}
			}
		});
		//Right Button
		right = new JButton(">");
		right.setBounds(184, 219, 60, 40);
		Panel2.add(right);
		right.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(road[bX+1][bY].getIcon().equals(sImage)) {
					road[bX +1][bY].setIcon(bImage);
					road[bX][bY].setIcon(sImage);
					bX++;

					iCompass.setIcon(eastImage);
					directionField.setText("East");
					squareField.setText(Integer.toString(bX));
				}
			}
		});
		left = new JButton("<");
		left.setBounds(68, 219, 60, 40);
		Panel2.add(left);
		left.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == left) {
					gotoleft();
					timer.start();
				}


			}
		});
		firstOption = new JButton("Option 1");
		firstOption.setBounds(60, 317, 84, 37);
		Panel2.add(firstOption);
		firstOption.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				optionField.setText("Option1");

			}
		});
		//		firstOption.addActionListener(this);

		secondOption = new JButton("Option 2");
		secondOption.setBounds(169, 317, 84, 37);
		Panel2.add(secondOption);
		secondOption.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				optionField.setText("Option2");
			}
		});

		secondOption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				road[6][4].setIcon(sImage);
				road[5][8].setIcon(sImage);
				road[6][11].setIcon(sImage);
				road[9][2].setIcon(sImage);
				road[1][1].setIcon(sImage);
				road[1][8].setIcon(sImage);
				road[bX][bY].setIcon(sImage);

				road[15][0].setIcon(bImage);
				bX=15;
				bY=0;
				optionField.setText("Option 2");
				Soundtrack(play);
				tim.start();
				
				
			}

		});


		thirdOption = new JButton("Option 3");
		thirdOption.setBounds(60, 372, 84, 35);
		Panel2.add(thirdOption);
		Panel2.add(thirdOption);
		thirdOption.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				optionField.setText("Option3");
				timer.start();
			}
		});
		
		thirdOption.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent e) {
					road[5][0].setIcon(wImage);
					road[6][4].setIcon(wImage);
					road[11][6].setIcon(wImage);
					road[2][11].setIcon(wImage);
				
			}
			
		});

		exitOption = new JButton("Exit");
		exitOption.setBounds(169, 372, 84, 35);
		Panel2.add(exitOption);
		exitOption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		iCompass = new JLabel(northImage);
		iCompass.setBounds(110, 413, 90, 90);
		Panel2.add(iCompass);

		//PANEL 3 STARTS

		sliderLabel = new JLabel("Speed");
		sliderLabel.setBounds(500, 7, 60, 30);
		Panel3.add(sliderLabel);

		slider = new JSlider(0,10,1);
		slider.setBounds(555, 8, 150, 25);
		Panel3.add(slider);


		for(int r = 0; r < 16; r++ ) {
			for(int c = 0; c < 13; c++) {
				gdbag.gridx = r;
				gdbag.gridy = c;
				road[r][c]=new JLabel(wImage);
				Panel1.add(road[r][c],gdbag);
			}
		}
		for(int r=0; r<16; r++) {
			road[r][0].setIcon(sImage);
			road[r][3].setIcon(sImage);
			road[15][0].setIcon(bImage);
			road[r][6].setIcon(sImage);
			road[r][9].setIcon(sImage);
			road[r][12].setIcon(sImage);
			//road[15][0].setIcon(bImage);
			road[0][12].setIcon(sandstoneImage);
		}
		for(int c = 0; c < 13; c++) {
			road[1][1].setIcon(sImage);
			road[1][2].setIcon(sImage);
			road[2][4].setIcon(sImage);
			road[2][5].setIcon(sImage);
			road[5][1].setIcon(sImage);
			road[5][2].setIcon(sImage);
			road[9][1].setIcon(sImage);
			road[9][2].setIcon(sImage);
			road[6][4].setIcon(sImage);
			road[6][5].setIcon(sImage);
			road[10][4].setIcon(sImage);
			road[10][5].setIcon(sImage);
			road[1][7].setIcon(sImage);
			road[1][8].setIcon(sImage);
			road[5][7].setIcon(sImage);
			road[5][8].setIcon(sImage);
			road[11][7].setIcon(sImage);
			road[11][8].setIcon(sImage);
			road[2][10].setIcon(sImage);
			road[2][11].setIcon(sImage);
			road[6][10].setIcon(sImage);
			road[6][11].setIcon(sImage);
		}

		tim = new Timer(500,new ActionListener() {


			public void actionPerformed(ActionEvent e) {
				if(road[bX][bY+1].getIcon()==(sImage)) {
					road[bX][bY+1].setIcon(bImage);
					road[bX][bY].setIcon(sImage);
					bY++;
					squareField.setText(bX+","+bY);
					//					JOptionPane.showMessageDialog(null, "YAYAYAYA");

					Soundtrack(play);
				} 
			}


		});



	}
	//	public void autogoDown(JLabel[][]road) {
	//		Timer t = new Timer(1000, new ActionListener() {
	//
	//			public void actionPerformed(ActionEvent e) {
	//				try {
	//					autogoDown(road);
	//				}
	//				catch(Exception e1)
	//				{
	//
	//				}
	//			}
	//
	//		});
	//		t.start();
	//	}


	
	
	
	public void Soundtrack(File sound) {
		try
		{
			Clip cp = AudioSystem.getClip();
			cp.open(AudioSystem.getAudioInputStream(sound));
			cp.start();
		} catch(Exception e)
		{

		}
		play = new File("Thunder.wav");
	}


	public void autoRun(JLabel[][] road) {	
		timer.start();
		Timer timer= new Timer(500, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				gotoleft();
				gotodown();
				
				if(bX==0 && bY==12) {
					JOptionPane.showMessageDialog(null,"Congratulations!! you've won the game");
				}
			}
		});
		timer.start();
	}

	// created a new method to go left to make the ball fall down automatically
	public void gotoleft() {
		if(road[bX-1][bY].getIcon().equals(sImage)) {
			road[bX-1][bY].setIcon(bImage);
			road[bX][bY].setIcon(sImage);
			bX--;
			iCompass.setIcon(westImage);
			directionField.setText("West");
			squareField.setText(Integer.toString(bX));
		} else if(road[bX-1][bY].getIcon().equals(sandstoneImage)){
			road[bX-1][bY].setIcon(bImage);
			road[bX][bY].setIcon(sImage);
			bX--;
			JOptionPane.showMessageDialog(null, "Congratulation!!!! YOU WIN THE GAME");
		}

	}



	// created a new method to move the ball down automatically
	public void gotodown() {

		if(road[bX][bY+1].getIcon().equals(sImage)) {
			road[bX][bY+1].setIcon(bImage);
			road[bX][bY].setIcon(sImage);
			bY++;



			iCompass.setIcon(southImage);
			directionField.setText("South");
			squareField.setText(Integer.toString(bX));

			Soundtrack(play);
		}

	}


	public void actionPerformed(ActionEvent e) {
		hoursField.setText(Integer.toString(ticks / 3600));
		minsField.setText(Integer.toString(ticks / 60));
		secsField.setText(Integer.toString(ticks % 60));
		ticks = ticks + 1;

		if(e.getSource()==bReset) {
			dispose();
			CBallMaze.main(null);
		}
		if(e.getSource()==bRun) {
			autoRun(road);
		}
	}
}