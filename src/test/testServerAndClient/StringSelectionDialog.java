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

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class StringSelectionDialog extends Dialog implements ActionListener
{
	private JComboBox<String> combo;
	private Button butOk;
	
	static String selectedChoice;
	
	public StringSelectionDialog(Frame owner, String[] choices)
	{
		super(owner, true);
		
		this.setLocationRelativeTo(owner);
		
		this.setLayout(new FlowLayout());
		
		this.combo = new JComboBox<String>(choices);
		this.combo.setPrototypeDisplayValue( new String(new char[20]).replace('\0', ' '));
		this.add(this.combo);
		
		this.butOk = new Button("OK");
		this.butOk.addActionListener(this);
		this.add(this.butOk);
		
		this.pack();
		
		if (selectedChoice != null)
		{
			this.combo.setSelectedItem(selectedChoice);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.butOk)
		{
			selectedChoice = (String) this.combo.getSelectedItem();
			this.setVisible(false);
		}
		
	}

}
