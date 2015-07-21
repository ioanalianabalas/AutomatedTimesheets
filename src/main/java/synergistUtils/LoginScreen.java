package synergistUtils;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.client.ClientProtocolException;
import org.xml.sax.SAXException;

public class LoginScreen {
	
	public static void createLoginScreen() {
		JFrame frame = new JFrame("Synergist Login");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		int i = 4;
		int j = 2;
		JPanel[][] panelHolder = new JPanel[i][j];    
		frame.setLayout(new GridLayout(i,j));
		frame.setPreferredSize(new Dimension(750, 180));
		for(int m = 0; m < i; m++) {
			for(int n = 0; n < j; n++) {
				panelHolder[m][n] = new JPanel();
				frame.add(panelHolder[m][n]);
			}
		}

		panelHolder[0][0].add(new JLabel("Login"));
		panelHolder[1][0].add(new JLabel("Username"));
		panelHolder[2][0].add(new JLabel("Password"));
		
		final JTextField username = new JTextField(); 
		username.setPreferredSize(new Dimension(200, 24));
		final JPasswordField password = new JPasswordField(); 
		password.setPreferredSize(new Dimension(200, 24));
		panelHolder[1][1].add(username);
		panelHolder[2][1].add(password);
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				final String usernameString = username.getText();
				final char[] passwordText = password.getPassword();
				final String passwordString = String.valueOf(passwordText);
				SynergistUtils synergist = new SynergistUtils(usernameString, passwordString);
				try {
					synergist.getResourceCode();
				} catch (KeyManagementException | NoSuchAlgorithmException
						| KeyStoreException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});    
		panelHolder[3][1].add(loginButton);
		frame.pack();
		frame.setVisible(true);
	}
}
