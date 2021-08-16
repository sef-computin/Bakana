package bakanaWebBrowser;

import java.net.URL;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.awt.event.*;

public class BakanaBrowser extends JPanel{
	
	private JEditorPane editPane;

	private JTextField locationInput;



	public static void main(String[] args){
		JFrame window = new JFrame("BakanaBrowser");
      	BakanaBrowser content = new BakanaBrowser();
      	window.setContentPane(content);
      	window.setSize(600,500);
      	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    window.setLocation( (screenSize.width - window.getWidth())/2,
            (screenSize.height - window.getHeight())/2 );
      	window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      	window.setVisible(true);
	}

	public BakanaBrowser(){
		setBackground(Color.BLACK);
		setLayout(new BorderLayout(1,1));
		setBorder(BorderFactory.createLineBorder(Color.BLACK,1));

		editPane = new JEditorPane();
     	editPane.setEditable(false);
     	editPane.addHyperlinkListener(new LinkListener());
     	add(new JScrollPane(editPane),BorderLayout.CENTER);

     	JToolBar toolbar = new JToolBar();
      	toolbar.setFloatable(false);
      	add(toolbar,BorderLayout.NORTH);
      	ActionListener goListener = new GoListener();
      	locationInput = new JTextField("www.google.com", 40);
      	locationInput.addActionListener(goListener);
      	JButton goButton = new JButton(" Go ");
      	goButton.addActionListener(goListener);
      	toolbar.add( new JLabel(" Location: "));
      	toolbar.add(locationInput);
      	toolbar.addSeparator(new Dimension(5,0));
      	toolbar.add(goButton);
	}

	private class LinkListener implements HyperlinkListener {
      public void hyperlinkUpdate(HyperlinkEvent evt) {
         if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            loadURL(evt.getURL());
         }
      }
   }

   private void loadURL(URL url) {
      try {
         editPane.setPage(url);
      }
      catch (Exception e) {
         editPane.setContentType("text/plain");
         editPane.setText( "Sorry, the requested document was not found\n"
               +"or cannot be displayed.\n\nError:" + e);
      }
   }


   private class GoListener implements ActionListener {
      public void actionPerformed(ActionEvent evt) {
         URL url;
         try {
            String location = locationInput.getText().trim();
            if (location.length() == 0)
               throw new Exception();
            if (! location.contains("://"))
               location = "http://" + location;
            url = new URL(location);
         }
         catch (Exception e) {
            JOptionPane.showMessageDialog(BakanaBrowser.this, 
                  "The Location input box does not\nccontain a legal URL.");
            return;
         }
         loadURL(url);
         locationInput.selectAll();
         locationInput.requestFocus();
      }
   }

}