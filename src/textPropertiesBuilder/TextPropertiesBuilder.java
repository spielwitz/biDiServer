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

package textPropertiesBuilder;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class TextPropertiesBuilder extends JFrame implements WindowListener, ActionListener
{
	private Properties properties;
	
	private String propertiesFile = "";
	private String outputClassName = "";
	private String outputPath = "";
	
	private JTextField tfPropertiesFile;

	private JTextField tfOutputClassName;
	private JTextField tfOutputPath;
	
	private JButton butBrowsePropertiesFile;
	private JButton butBrowseOutputClassPath;
	
	private JButton butCreate;
	private JButton butCancel;
	
	private static int insets = 5;
	
	transient private static final String PROPERTIES_FILE_NAME = "TextPropertiesBuilder.prop";
	transient private static final String PROPERTY_NAME_PROPERTIES_FILE = "propertiesFile";
	transient private static final String PROPERTY_NAME_OUTPUT_CLASS_NAME = "outputClassName";
	transient private static final String PROPERTY_NAME_OUTPUT_PATH = "outputPath";
	
	public static void main(String[] args) 
	{
		new TextPropertiesBuilder();
	}
	
	public TextPropertiesBuilder()
	{
		super("Text Properties Builder (c) M. Schweitzer");
		
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		JPanel panBaseFrame = new JPanel(new GridBagLayout());
		this.add(panBaseFrame);
		
		this.addWindowListener(this);
		
		JPanel panBase = new JPanel(new BorderLayout(insets, insets));

		this.properties = this.getProperties();
		
		// -----
		JPanel panMain = new JPanel(new GridBagLayout());
		GridBagConstraints cPanMain = new GridBagConstraints();
		
		cPanMain.insets = new Insets(insets, insets, insets, insets);
		cPanMain.weightx = 0;
		cPanMain.weighty = 0.5;
		cPanMain.gridwidth = 1;
		cPanMain.fill = GridBagConstraints.HORIZONTAL;
		
		cPanMain.gridx = 0;
		cPanMain.gridy = 0;
		
		panMain.add(new JLabel("Properties File"), cPanMain);
				
		cPanMain.gridx = 6;
		cPanMain.gridy = 0;
		this.butBrowsePropertiesFile = new JButton("Browse...");
		this.butBrowsePropertiesFile.addActionListener(this);
		panMain.add(this.butBrowsePropertiesFile, cPanMain);
		
		cPanMain.gridx = 0;
		cPanMain.gridy = 1;
		panMain.add(new JLabel("Class Name"), cPanMain);
		
		cPanMain.gridx = 0;
		cPanMain.gridy = 2;
		panMain.add(new JLabel("Output Directory"), cPanMain);
		
		cPanMain.gridx = 6;
		cPanMain.gridy = 2;
		this.butBrowseOutputClassPath = new JButton("Browse...");
		this.butBrowseOutputClassPath.addActionListener(this);
		panMain.add(this.butBrowseOutputClassPath, cPanMain);
		
		cPanMain.weightx = 1.0;
		cPanMain.gridwidth = 3;
		
		cPanMain.gridx = 1;
		cPanMain.gridy = 0;
		this.tfPropertiesFile = new JTextField(this.propertiesFile, 100);
		panMain.add(this.tfPropertiesFile, cPanMain);

		cPanMain.gridx = 1;
		cPanMain.gridy = 1;
		this.tfOutputClassName = new JTextField(this.outputClassName, 100);
		panMain.add(this.tfOutputClassName, cPanMain);
		
		cPanMain.gridx = 1;
		cPanMain.gridy = 2;
		this.tfOutputPath = new JTextField(this.outputPath, 100);
		panMain.add(this.tfOutputPath, cPanMain);
		
		panBase.add(panMain, BorderLayout.CENTER);
		
		// -----
		JPanel panButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		this.butCancel = new JButton("Quit");
		this.butCancel.addActionListener(this);
		panButtons.add(this.butCancel);
		
		this.butCreate = new JButton("Create");
		this.butCreate.addActionListener(this);
		panButtons.add(this.butCreate);
		
		panBase.add(panButtons, BorderLayout.SOUTH);
		
		GridBagConstraints cBase = new GridBagConstraints();
		cBase.insets = new Insets(10, 10, 10, 10);
		cBase.gridx = 0;
		cBase.gridy = 0;
		cBase.weightx = 1;
		cBase.weighty = 1;
		cBase.fill = GridBagConstraints.BOTH;
		
		panBaseFrame.add(panBase, cBase);
		
		this.pack();
		this.setVisible(true);
	}

	private Properties getProperties()
	{
		Reader reader = null;
		Properties prop = new Properties(); 

		try
		{
		  reader = new FileReader(PROPERTIES_FILE_NAME);

		  prop.load( reader );
		}
		catch ( Exception e )
		{
		}
		finally
		{
		  try { reader.close(); } catch ( Exception e ) { }
		}
		
		if (prop.containsKey(PROPERTY_NAME_PROPERTIES_FILE))
			this.propertiesFile = prop.getProperty(PROPERTY_NAME_PROPERTIES_FILE);
		
		if (prop.containsKey(PROPERTY_NAME_OUTPUT_CLASS_NAME))
			this.outputClassName = prop.getProperty(PROPERTY_NAME_OUTPUT_CLASS_NAME);
		
		if (prop.containsKey(PROPERTY_NAME_OUTPUT_PATH))
			this.outputPath = prop.getProperty(PROPERTY_NAME_OUTPUT_PATH);
		
		return prop;
	}

	private void create()
	{
		this.outputClassName = this.tfOutputClassName.getText().trim();
		this.outputPath = this.tfOutputPath.getText().trim();
		this.propertiesFile = this.tfPropertiesFile.getText().trim();
		
		if (outputClassName.length() == 0 || outputPath.length() == 0 || propertiesFile.length() == 0)
		{
			JOptionPane.showMessageDialog(this,
				    "Please fill out all text fields!",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		File f = null;
		
		try
		{
			f = new File(this.propertiesFile);
		}
		catch (Exception x)
		{
			JOptionPane.showMessageDialog(this,
				    "The property file does not exist!",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		String propFileName = f.getName();
		
		int i = propFileName.indexOf(".properties");
		
		if (i < 0)
		{
			JOptionPane.showMessageDialog(this,
				    "The name of the property file has to match the following pattern:\n[Name]_[Language]_[Country].properties\nfor example, MyApp_de_DE.properties",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		propFileName = propFileName.substring(0, i);
		
		List<String> list = new ArrayList<>();
		
		String[] parts = propFileName.split("_");
		
		if (parts.length != 3)
		{
			JOptionPane.showMessageDialog(this,
				    "The name of the property file has to match the following pattern:\\n[Name]_[Language]_[Country].properties\\nfor example, MyApp_de_DE.properties",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		String resourceBundleName = parts[0];
		String defaultLanguage = parts[1];
		String defaultCountry = parts[2];

		try (Stream<String> stream = Files.lines(Paths.get(this.propertiesFile))) {

			list = stream
					.filter(line -> !line.startsWith("#") && line.length() > 0)
					.collect(Collectors.toList());

		} catch (IOException e) 
		{
			JOptionPane.showMessageDialog(this,
				    "The property file cannot be read!",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			return;
		}

		StringBuilder sb = new StringBuilder();
		
		String packageName = "";
		String className = "";
		
		int pos = this.outputClassName.lastIndexOf(".");
		
		if (pos >= 0)
		{
			packageName = this.outputClassName.substring(0, pos);
			className = this.outputClassName.substring(pos +1, this.outputClassName.length());
		}
		else
		{
			className = this.outputClassName;
		}
		
		if (packageName.length() > 0)
			sb.append("package "+packageName+";\n\n");
		sb.append("import java.text.MessageFormat;\n");
		sb.append("import java.util.Locale;\n");
		sb.append("import java.util.ResourceBundle;\n\n");
		
		sb.append("/**\n");
		sb.append("   * This class was created with the Text Properties Builder from the resource file\n");
		sb.append("   *\n");
		sb.append("   *   "+propFileName+"\n");
		sb.append("   *\n");
		sb.append("   * The resource file is maintained with the Eclipse-Plugin ResourceBundle Editor.\n");
		sb.append("   */\n");
		sb.append("class "+className+" \n{\n");
				
		sb.append("\tprivate static String languageCode;\n");
		sb.append("\tprivate static ResourceBundle messages;\n\n");
		sb.append("\tstatic {\n");
		sb.append("\t\tsetLocale(\""+defaultLanguage+"-"+defaultCountry+"\");\n");
		sb.append("\t}\n\n");
		sb.append("\tstatic void setLocale(String newLanguageCode){\n");
		sb.append("\t\tlanguageCode = newLanguageCode;\n");
		sb.append("\t\tString[] language = languageCode.split(\"-\");\n");
		sb.append("\t\tLocale currentLocale = new Locale(language[0], language[1]);\n");
		sb.append("\t\tmessages = ResourceBundle.getBundle(\""+resourceBundleName+"\", currentLocale);\n");
		sb.append("\t}\n\n");
		sb.append("\tstatic String getLocale(){\n");
		sb.append("\t\treturn languageCode;\n");
		sb.append("\t}\n\n");
		sb.append("\tstatic String getMessageText(TextProperty textProperty){\n");
		sb.append("\t\tif (textProperty != null)\n");
		sb.append("\t\t\treturn MessageFormat.format(messages.getString(textProperty.getKey()), textProperty.getArgs());\n");
		sb.append("\t\telse\n");
		sb.append("\t\t\treturn null;\n");
		sb.append("\t}\n");
		
		for (String line: list)
		{
			int posEquals = line.indexOf('=');
			if (posEquals < 0)
				continue;
			
			String key = line.substring(0, posEquals).trim();
			String text = line.substring(posEquals + 1).trim();
									
			int numArgs = this.getNumArguments(text);
			
			sb.append("\n\t/**\n");
			sb.append("\t   * "+text+"\n");
			sb.append("\t   */\n");
			sb.append("\tstatic TextProperty "+key+this.getArgs(numArgs)+" {\n");
			sb.append("\t\treturn "+this.getMethodSignature(key, numArgs)+";\n");
			sb.append("\t}\n");
		}
		
		sb.append("}");

		String subPath = "";
		if (packageName.length() > 0)
		{
			subPath = packageName.replace(".", System.getProperty("file.separator"));
		}
		
		Path path = Paths.get(this.outputPath, subPath, className + ".java");
		 
		try (BufferedWriter writer = Files.newBufferedWriter(path))
		{
		    writer.write(sb.toString());
		}
		catch (Exception x)
		{
			JOptionPane.showMessageDialog(this,
				    "The file\n"+path.getFileName()+"\ncould not be written.",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		JOptionPane.showMessageDialog(this,
			    "Success!",
			    "Success",
			    JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		this.close();
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	private void setProperties()
	{
		this.properties.setProperty(PROPERTY_NAME_OUTPUT_CLASS_NAME, this.tfOutputClassName.getText());
		this.properties.setProperty(PROPERTY_NAME_OUTPUT_PATH, this.tfOutputPath.getText());
		this.properties.setProperty(PROPERTY_NAME_PROPERTIES_FILE, this.tfPropertiesFile.getText());
		
		Writer writer = null;

		try
		{
		  writer = new FileWriter(PROPERTIES_FILE_NAME);

		  properties.store( writer, "Resource Bunde Utlity" );
		}
		catch ( IOException e )
		{
		  e.printStackTrace();
		}
		finally
		{
		  try { writer.close(); } catch ( Exception e ) { }
		}

	}
	
	private int getNumArguments(String text)
	{
		int count = 0;
		
		do
		{
			if (text.indexOf("{"+count+"}") < 0)
				break;
			
			count++;
			
		} while (true);
		
		return count;
	}
	
	private String getArgs(int numArgs)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("(");
		
		for (int i = 0; i < numArgs; i++)
		{
			if (i > 0)
				sb.append(", ");
			
			sb.append("String arg"+i);
		}
		
		sb.append(")");
		
		return sb.toString();
	}
	
	private String getMethodSignature(String key, int numArgs)
	{
		StringBuilder sb = new StringBuilder();
		
		if (numArgs == 0)
			sb.append("new TextProperty(\""+key+"\")");
		else
		{
			sb.append("new TextProperty(\""+key+"\", new String[] {");
			
			for (int i = 0; i < numArgs; i++)
			{
				if (i > 0)
					sb.append(", ");
					
				sb.append("arg" + i);
			}
			
			sb.append("})");
		}
		
		return sb.toString();
	}
	
	
	
	private void close()
	{
		if (this.confirmClose())
		{
			this.dispose();
			System.exit(0);
		}
	}

	private boolean confirmClose()
	{
		this.setProperties();
		return true;
	}
	

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().getClass() == JButton.class)
		{
			this.buttonClicked((JButton)e.getSource());
		}
		
	}

	public void buttonClicked(JButton source)
	{
		if (source == this.butCancel)
			this.close();
		else if (source == this.butBrowsePropertiesFile)
		{
			JFileChooser chooser = new JFileChooser();
			
			if (this.propertiesFile != null)
				chooser.setCurrentDirectory(new File(this.propertiesFile));
			
		    chooser.setDialogTitle("Properties File");
		    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		    {
		    	this.propertiesFile = chooser.getSelectedFile().getAbsolutePath();
		    	this.tfPropertiesFile.setText(this.propertiesFile);
		    }
		}
		else if (source == this.butBrowseOutputClassPath)
		{
			JFileChooser chooser = new JFileChooser();
			
			if (this.outputPath != null)
				chooser.setCurrentDirectory(new File(this.outputPath));
		    chooser.setDialogTitle("Output Directory");
		    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    chooser.setAcceptAllFileFilterUsed(false);

		    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		    {
		    	this.outputPath = chooser.getSelectedFile().getAbsolutePath();
		    	this.tfOutputPath.setText(this.outputPath);
		    }
		}
		else if (source == this.butCreate)
			this.create();
	}
}
