/**	BiDiServer - a library that provides bi-directional communication between
	a server and clients.
	
    Copyright (C) 2022 Michael Schweitzer, spielwitz@icloud.com
	https://github.com/spielwitz/biDiServer
	
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.
    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>. **/

package test.testServerAndClient;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class MessageSendDialog extends JDialog implements ActionListener
{
	private JTextField tfMessage;
	private JCheckBox[] cbRecipients;
	private JButton butSend;
	
	private IClientTesterCallback clientTester;
	
	public MessageSendDialog(IClientTesterCallback clientTester)
	{
		super();
		
		this.clientTester = clientTester;
		
		this.setTitle("Send Messages");
		
		this.setLayout(new BorderLayout(10, 10));
		
		JPanel panRecipients = new JPanel(new GridLayout(3, 4));
		
		String[] userIds = TestData.getUserIds();
		this.cbRecipients = new JCheckBox[userIds.length];
		
		for (int i = 0; i < userIds.length; i++)
		{
			this.cbRecipients[i] = new JCheckBox(userIds[i]);
			panRecipients.add(this.cbRecipients[i]);
		}
		
		this.add(panRecipients, BorderLayout.NORTH);
		
		JPanel panText = new JPanel(new BorderLayout(10, 10));
		
		this.tfMessage = new JTextField();
		panText.add(this.tfMessage, BorderLayout.CENTER);
		
		this.butSend = new JButton("Send");
		this.butSend.addActionListener(this);
		panText.add(this.butSend, BorderLayout.EAST);
		
		this.add(panText, BorderLayout.SOUTH);
		
		KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		this.getRootPane().registerKeyboardAction(this, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		this.pack();
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.getRootPane())
		{
			this.setVisible(false);
			this.dispose();
		}
		else if (e.getSource() == this.butSend)
		{
			ArrayList<String> recipients = new ArrayList<String>();
			
			for (JCheckBox cb: this.cbRecipients)
			{
				if (cb.isSelected())
				{
					recipients.add(cb.getText());
				}
			}
			
			if (recipients.size() > 0)
			{
				this.clientTester.sendMessage(recipients, this.tfMessage.getText());
			}
		}
	}

}
