import java.awt.*;
import java.awt.event.*;


public class Bayes extends Frame{
	
	private double PA,PB,PC, p1,p2,p3, criteria;
	private String izvor, pamti;
	private int eins;
	private int played=0;
	private long n, k;
	private TextField paf,pbf,pcf,p1f,p2f,p3f,izvorf,nf,kf, critf;
	private Button dugme;
	private TextArea pad,pbd,pcd,numit, eins_area; //ili Lista
	private Label rez_izv,error_label;
	private Dialog welcome;
	
	private Bayes() {
		super("Bayes Iteration Simulator 2019");
		setBounds(0,0,1920,1080);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		Panel ploca= new Panel(new GridLayout(4,6));
		Label label;
		ploca.add(label=new Label("Apriorna verovatnoca izvora A:"));
		label.setFont(new Font("TimesRoman",Font.PLAIN, 12));
		ploca.add(paf=new TextField());
		ploca.add(label=new Label("Apriorna verovatnoca izvora B:"));
		label.setFont(new Font("TimesRoman",Font.PLAIN, 12));
		ploca.add(pbf=new TextField());
		ploca.add(label=new Label("Apriorna verovatnoca izvora C:"));
		label.setFont(new Font("TimesRoman",Font.PLAIN, 12));
		ploca.add(pcf=new TextField());
		ploca.add(label=new Label("Verovatnoca pojavljvanja jednice sa izvora A:"));
		label.setFont(new Font("TimesRoman",Font.PLAIN, 12));
		ploca.add(p1f=new TextField());
		ploca.add(label=new Label("Verovatnoca pojavljvanja jednice sa izvora B:"));
		label.setFont(new Font("TimesRoman",Font.PLAIN, 12));
		ploca.add(p2f=new TextField());
		ploca.add(label=new Label("Verovatnoca pojavljvanja jednice u sa izvora C:"));
		label.setFont(new Font("TimesRoman",Font.PLAIN, 12));
		ploca.add(p3f=new TextField());
		ploca.add(label=new Label("Unesite zeljeni izvor, A, B ili C:"));
		label.setFont(new Font("TimesRoman",Font.PLAIN, 12));
		ploca.add(izvorf= new TextField());
		ploca.add(label=new Label("Unesite broj znakova u binarnom signalu:"));
		label.setFont(new Font("TimesRoman",Font.PLAIN, 12));
		ploca.add(nf= new TextField());
		ploca.add(label=new Label("Unesite zeljeni broj jedinica u prvom pristiglom signalu:"));
		label.setFont(new Font("TimesRoman",Font.PLAIN, 12));
		ploca.add(kf=new TextField());
		ploca.add(label=new Label("Unesite zeljeni kriterijum tacnosti:"));
		label.setFont(new Font("TimesRoman",Font.PLAIN, 12));
		ploca.add(critf=new TextField());
		ploca.add(label=new Label(""));
		
		ploca.add(dugme=new Button());
		dugme.setLabel("Pokreni Iteraciju");
		dugme.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				iterate();
			}
		});
		paf.addTextListener(new TextListener() {
			public void textValueChanged(TextEvent dog) {//proveri za getSource
				PA=Double.parseDouble(paf.getText());
			}
		});
		pbf.addTextListener(new TextListener() {
			public void textValueChanged(TextEvent dog) {//proveri za getSource
				PB=Double.parseDouble(pbf.getText());
			}
		});
		pcf.addTextListener(new TextListener() {
			public void textValueChanged(TextEvent dog) {//proveri za getSource
				PC=Double.parseDouble(pcf.getText());
			}
		});
		p1f.addTextListener(new TextListener() {
			public void textValueChanged(TextEvent dog) {//proveri za getSource
				p1=Double.parseDouble(p1f.getText());
			}
		});
		p2f.addTextListener(new TextListener() {
			public void textValueChanged(TextEvent dog) {//proveri za getSource
				p2=Double.parseDouble(p2f.getText());
			}
		});
		p3f.addTextListener(new TextListener() {
			public void textValueChanged(TextEvent dog) {//proveri za getSource
				p3=Double.parseDouble(p3f.getText());
			}
		});
		// nf kf critf
		izvorf.addTextListener(new TextListener() {
			public void textValueChanged(TextEvent dog) {//proveri za getSource
				izvor=izvorf.getText();
			}
		});
		nf.addTextListener(new TextListener() {
			public void textValueChanged(TextEvent dog) {//proveri za getSource
				n=Long.parseLong(nf.getText());
			}
		});
		kf.addTextListener(new TextListener() {
			public void textValueChanged(TextEvent dog) {//proveri za getSource
				k=Long.parseLong(kf.getText());
			}
		});
		critf.addTextListener(new TextListener() {
			public void textValueChanged(TextEvent dog) {//proveri za getSource
				criteria=Double.parseDouble(critf.getText());
			}
		});
		add(ploca, "North");
		ploca=new Panel(new GridLayout(2,5));
		ploca.add(label=new Label("Broj iteracija:"));
		label.setFont(new Font("TimesRoman",Font.PLAIN, 30));
		label.setForeground(Color.BLUE);
		ploca.add(label=new Label("Broj jedinica:"));
		label.setFont(new Font("TimesRoman",Font.PLAIN, 30));
		label.setForeground(Color.BLUE);
		ploca.add(label=new Label("Aposteriorna vervatnoca izvora A:"));
		label.setFont(new Font("TimesRoman",Font.PLAIN, 27));
		label.setForeground(Color.BLUE);
		ploca.add(label=new Label("Aposteriorna vervatnoca izvora B:"));
		label.setFont(new Font("TimesRoman",Font.PLAIN, 27));
		label.setForeground(Color.BLUE);
		ploca.add(label=new Label("Aposteriorna vervatnoca izvora C:"));
		label.setFont(new Font("TimesRoman",Font.PLAIN, 27));
		label.setForeground(Color.BLUE);
		ploca.add(numit=new TextArea());
		ploca.add(eins_area=new TextArea());
		//numit.setSize(480, 800);
		ploca.add(pad=new TextArea());
		ploca.add(pbd=new TextArea());
		ploca.add(pcd=new TextArea());
        add(ploca,"Center");
        ploca=new Panel(new GridLayout(1,2));
        rez_izv=new Label("Nakon izvrsavanja iteracija, odabran je izvor:");//+pamti
        rez_izv.setFont(new Font("TimesRoman",Font.BOLD,20));        
        label=new Label("Izvestaj o greskama:");
        label.setFont(new Font("TimesRoman",Font.BOLD,20));
        label.setForeground(Color.RED);
        error_label=new Label("");
        error_label.setFont(new Font("TimesRoman",Font.BOLD,20));
        error_label.setForeground(Color.RED);
        
        
        ploca.add(rez_izv);
        ploca.add(label);
        ploca.add(error_label);
        add(ploca, "South");
        
        welcome=new Dialog(this,"This project is sponsored by Nordeus", true);
        welcome.setSize(600,500);
        welcome.setLayout(new BorderLayout());
        Label dobrodoslica= new Label("Welcome to Bayes' iteration simulator 2019");
        dobrodoslica.setSize(300,200);
        dobrodoslica.setFont(new Font("TimesRoman",Font.CENTER_BASELINE, 24));
        dobrodoslica.setForeground(Color.BLUE);
        welcome.add(dobrodoslica,"Center");
        Panel pnl=new Panel(new GridLayout(2,1));
        //Label press_next;
        //pnl.add(press_next = new Label("Press NEXT to continue"));
        //press_next.setFont(new Font("TimesRoman",Font.CENTER_BASELINE,20));
        Label press_next=new Label("Press NEXT to continue");
        press_next.setFont(new Font("TimesRoman",Font.CENTER_BASELINE,20));
        pnl.add(press_next);
        //neefikasno dodavanje press_next, zbog setovanja fonta
        Button butt=new Button("NEXT");
        butt.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent d) {
        		dispose();
        	}
        });
        butt.setSize(75,200);
        butt.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 40));
        pnl.add(butt);
        welcome.add(pnl,"South");
        welcome.setLocation(640,341);
        welcome.setBackground(Color.ORANGE);
        welcome.setVisible(true);
        
        
		
		
		
		
		setResizable(false);
		setVisible(true);
	}
	
	
	public static void main(String[] args) {
		new Bayes();
	}
	
	
	public void iterate() {
		//sta sve imam
		//private double PA,PB,PC, p1,p2,p3, criteria;
		//private String izvor, pamti;
		//private int n, k;
		//private TextField paf,pbf,pcf,p1f,p2f,p3f,izvorf,nf,kf, critf;
		//private Button dugme;
		//private TextArea pad,pbd,pcd,numit; //ili Lista
		
		try {
			
			//samo sam stvarima koje se menjaju opet ukucavao vrednost, mada kada bih obrisao parametre pa opet dokucao iste, onda ovaj deo nije potreban
			 if (played>0) {
				 numit.setText("");
				 eins_area.setText("");
			     pad.setText("");
				 pbd.setText("");
				 pcd.setText("");
				 rez_izv.setText("Nakon izvrsavanja iteracija, odabran je izvor:      ");
				 PA=Double.parseDouble(paf.getText());
				 PB=Double.parseDouble(pbf.getText());
				 PC=Double.parseDouble(pcf.getText());
				 k=Long.parseLong(kf.getText());
				 
			 }
			 if(p1>1||p2>1||p3>1||p1<0||p2<0||p3<0) 
				 throw new WrongValue();
			 if(!((izvor.equals("A")||(izvor.equals("B"))||(izvor.equals("C"))))) 
			    	throw new WrongSource();
			 if(n<=0) 
				   throw new LessThanZero();
			 if(criteria>1||criteria<0) //<=0, mada teoretski mmoze 0, ali je onda to besmisleno
				  throw new WrongCriteria();
			  //POCINJEMO SA RACUNANJEM
			 double PDA=Kombinacija(n, k)*Math.pow(p1,k)*Math.pow(1-p1,n-k);
			  //racunam da se desio dogadjaj D pod uslovom da se desio dogadjaj A
			  
			 double PDB=Kombinacija(n,k)*Math.pow(p2,k)*Math.pow(1-p2, n-k);
			  //racunam da se desio dogadjaj D pod uslovom da se desio dogadjaj B
			  
			 double PDC=Kombinacija(n,k)*Math.pow(p3,k)*Math.pow(1-p3, n-k);
			  //racunam da se desio dodadjaj D pod uslovom da se desio dogadjaj C
			  
			 double PD=PA*PDA+PB*PDB+PC*PDC;
			 
			 //racunam totalnu verovatnocu PD
			 
			 double PAD=(PA*PDA)/PD;
			 double PBD=(PB*PDB)/PD;
			 double PCD=(PC*PDC)/PD;
			//private double PA,PB,PC, p1,p2,p3, criteria;
				//private String izvor, pamti;
				//private int n, k;
				//private TextField paf,pbf,pcf,p1f,p2f,p3f,izvorf,nf,kf, critf;
				//private Button dugme;
				//private TextArea pad,pbd,pcd,numit; //ili Lista
				
			 
				  	   
			 //System.out.print("                1|             "+k+"|       "+PAD+"|         "+PBD+"|         "+PCD+"|\n");
			 numit.setText(numit.getText()+"1\n");
			 eins_area.setText(eins_area.getText()+""+k+"\n");
			 pad.setText(pad.getText()+""+PAD+"\n");
			 pbd.setText(pbd.getText()+""+PBD+"\n");
			 pcd.setText(pcd.getText()+""+PCD+"\n");
			 //OLD CODE
			 //k=Math.round(Math.random()*n);//od 1 do n, bilo ranije ukoud  od 1 do 10
			 
			 //NEW CODE!!!
			 double help_k;
			 // k promena
			 /*if(help_k<0.1) {
				 k=0;
			 }
			 else {
				 k=Math.round(n*help_k);
			 }*/
			 
			 // pocetak novo k
			 k = 0;
			 double pizvor;
			 if(izvor.equals("A")) pizvor = p1;
			 else if(izvor.equals("B")) pizvor = p2;
			 else pizvor = p3;
			 for(int ii=0;ii<n;ii++) {
				 help_k =Math.random();
				 if(help_k<pizvor)k++;
			 }
			 // kraj novo k
			 double i=0;
			 
			 for(int j=2;i<criteria;j++) {
				 
				 PDA=Kombinacija(n, k)*Math.pow(p1,k)*Math.pow(1-p1,n-k);
			     PDB=Kombinacija(n,k)*Math.pow(p2,k)*Math.pow(1-p2, n-k);		 
				 PDC=Kombinacija(n,k)*Math.pow(p3,k)*Math.pow(1-p3, n-k);
				 
				  PD=PA*PDA+PB*PDB+PC*PDC;
				 
				 //upisivanje aposteriornih verovatnoca u promenljive koje predstalvjaju apriorne za sledeci ciklus
				 PA=PAD=(PA*PDA)/PD;
				 PB=PBD=(PB*PDB)/PD;
				 PC=PCD=(PC*PDC)/PD;
				 //OLD CODE
				 //k=Math.round(Math.random()*n);//od 1 do n//bilo je nesto kastovano u int, nzm zasto
				 
				 //NEW CODE
				 // k promena
				 /*help_k=Math.random();
				 if(help_k<0.1) {
					 k=0;
				 }
				 else {
					 k=Math.round(n*help_k);
				 }*/
				 
				 // pocetak novo k
				 
				 k=0;
				 for(int ii=0;ii<n;ii++) {
					 help_k =Math.random();
					 if(help_k<pizvor)k++;
				 }
				 
				 //kraj novo k
				 
				 
				 if(PAD>=PBD) {
					 if(PAD>=PCD) {
						 i=PAD;
						 pamti="A";
					 }
					 else {
						 i=PCD;
					     pamti="C";
					 }
				 } else {
					 if(PBD>PCD) {
						 i=PBD;
						 pamti="B";
					 }		 
				 else {
					 i=PCD;
					 pamti="C";
				 }
				 }
				 numit.setText(numit.getText()+""+j+"\n");
				 eins_area.setText(eins_area.getText()+""+k+"\n");
				 pad.setText(pad.getText()+""+PAD+"\n");
				 pbd.setText(pbd.getText()+""+PBD+"\n");
				 pcd.setText(pcd.getText()+""+PCD+"\n");
			     if(j==500) {
			    	 error_label.setText("Na osnovu unetih parametara, nemoguce je resiti problem pomocu Bajesovskih iteracija");
					 break;				
			     }
			   }
			 rez_izv.setText(rez_izv.getText()+pamti);
			 played++;
			 	
		}
		catch (WrongCombination wc ) {
			error_label.setText(wc.toString());
			played++;
		}
		catch (WrongValue wv) {
			error_label.setText(wv.toString());
			played++;
		}
		catch(WrongSource ws) {
			error_label.setText(ws.toString());
			played++;
			}
		catch(LessThanZero ltz) {
			error_label.setText(ltz.toString());
			played++;
			}
		catch(WrongCriteria wc) {
			error_label.setText(wc.toString());
			played++;
			}
		
		
		
		
		
		
		
		
		
		
		
	}
	public static long Kombinacija(long n, long k) throws WrongCombination{
		long nf=1, kf=1;
		if(n==0) nf=1;
		else {
			for(int i=1;i<=n;i++) {
				nf*=i;
			}			
		}
		if(k==0) kf=1;
		else {
			for(int i=1;i<=k;i++) {
				kf*=i;
			}			
		}
		long nk=n-k;
		if(k>n) {
			throw new WrongCombination();
		}
		long nfk=1;
		if(nk!=0) {
			for(int i=1;i<=nk;i++) nfk*=i;
		}
		long rezultat=nf/kf;
		rezultat=rezultat/nfk;
		return rezultat;	
		
	}

	
	
	
	
	
	

}
