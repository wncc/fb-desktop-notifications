import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.*;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;

public class GUIMain{
	static Display display;
	static Shell shell;
	boolean auth = false;
	boolean proxy = false;
	WebClient webClient;
	public void createWidgets() throws Exception {
		// TODO Auto-generated method stub
		TabFolder folder = new TabFolder(shell, SWT.NONE);
		TabItem tab1 = new TabItem(folder, SWT.NONE);
		TabItem tab2 = new TabItem(folder, SWT.NONE);
		tab1.setText("Profile");
		Composite ProfileComposite = createProfileTab(folder);
		tab1.setControl(ProfileComposite);
		
		tab2.setText("Connection");
		Composite ConnectionComposite = createConnectionTab(folder);
		tab2.setControl(ConnectionComposite);
	}
	
	public Composite createProfileTab(TabFolder folder) throws Exception{
		Composite composite = new Composite(folder, SWT.NULL);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GridLayout layout= new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		
		final Label label1 = new Label(composite, SWT.RIGHT);
		label1.setText("Email :");
		
		final Text text1 = new Text(composite, SWT.BORDER);
		GridData gridData1 = new GridData();
		gridData1.widthHint = 200;
		gridData1.horizontalSpan = 1;
		text1.setLayoutData(gridData1);
		text1.setData("index", 2);
		//addTextListener(text3);
		
		final Label label2 = new Label(composite, SWT.RIGHT);
		label2.setText("Password :");
	
		final Text text2 = new Text(composite, SWT.BORDER | SWT.PASSWORD);
		GridData gridData2 = new GridData();
		gridData2.widthHint = 200;
		gridData2.horizontalSpan=1;
		text2.setLayoutData(gridData2);
		
		Button button1 = new Button(composite, SWT.PUSH);
		button1.setText("Login");
		GridData gridData3 = new GridData(SWT.CENTER, SWT.NONE, true, true, 1,1);
		gridData3.horizontalSpan=2;
		button1.setLayoutData(gridData3);
		
		button1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Demo.userName = text1.getText();
				Demo.password = text2.getText();
				try {
					shell.dispose();
					new Demo();
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		return composite;
	}
	
	public Composite createConnectionTab(TabFolder folder) {
		Composite composite = new Composite(folder, SWT.NULL);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GridLayout layout= new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		
		final Button AutoConfig = new Button(composite, SWT.RADIO);
		AutoConfig.setText("Use automatic configuration");
		GridData gridData0 = new GridData();
		gridData0.horizontalSpan=2;
		AutoConfig.setLayoutData(gridData0);
		AutoConfig.setSelection(true);
		
		
		final Button ManualConfig = new Button(composite, SWT.RADIO);
		ManualConfig.setText("Use following manual configuration");
		GridData gridData10 = new GridData();
		gridData10.horizontalSpan=2;
		ManualConfig.setLayoutData(gridData10);
		
		
		
		final Label label1 = new Label(composite, SWT.RIGHT);
		label1.setText("Proxy :");
		label1.setEnabled(false);
		
		final Text text1 = new Text(composite, SWT.BORDER);
		GridData gridData1 = new GridData();
		gridData1.widthHint = 200;
		gridData1.horizontalSpan = 1;
		text1.setLayoutData(gridData1);
		text1.setEnabled(false);
		
		//addTextListener(text1);
		
		final Label label2 = new Label(composite, SWT.RIGHT);
		label2.setText("Port :");
		label2.setEnabled(false);
		final Text text2 = new Text(composite, SWT.BORDER);
		GridData gridData2 = new GridData();
		gridData2.horizontalSpan=1;
		text2.setLayoutData(gridData2);
		text2.setEnabled(false);
		//addTextListener(text2);

		final Button button1 = new Button(composite, SWT.CHECK);
		button1.setText("Use Proxy Authentication");
		GridData gridData3 = new GridData();
		gridData3.horizontalSpan=2;
		button1.setLayoutData(gridData3);
		button1.setEnabled(false);
		
		
		final Label label3 = new Label(composite, SWT.RIGHT);
		label3.setText("Username :");
		label3.setEnabled(false);
		
		final Text text3 = new Text(composite, SWT.BORDER);
		GridData gridData4 = new GridData();
		gridData4.widthHint = 200;
		gridData4.horizontalSpan = 1;
		text3.setLayoutData(gridData4);
		text3.setData("index", 2);
		text3.setEnabled(false);
		//addTextListener(text3);
		
		final Label label4 = new Label(composite, SWT.RIGHT);
		label4.setText("Password :");
		label4.setEnabled(false);
		
		final Text text4 = new Text(composite, SWT.BORDER | SWT.PASSWORD);
		GridData gridData5 = new GridData();
		gridData5.widthHint = 200;
		gridData5.horizontalSpan=1;
		text4.setLayoutData(gridData5);
		text4.setEnabled(false);
		//addTextListener(text4);
		
		button1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				label3.setEnabled(!label3.isEnabled()); 
				label4.setEnabled(!label4.isEnabled());
				text3.setEnabled(!text3.isEnabled());
				text4.setEnabled(!text4.isEnabled());
				auth = !auth;
			}
		});
		
		final Button button2 = new Button(composite, SWT.PUSH |SWT.CENTER);
		button2.setText("OK");
		GridData gridData6 = new GridData(SWT.CENTER, SWT.NONE, true,true, 1,1);
		gridData6.horizontalSpan=2;
		button2.setLayoutData(gridData6);
		
		button2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				
				if(!proxy) {
					//WebClient webClient = new WebClient();
					JSONQuery.webClient = webClient;
					MessageQuery.webClient = webClient;
					OAuthSession.webClient = webClient;
				} else{
					//System.out.println("Entered Proxy Settings");
					//WebClient webClient = new WebClient();
					String PROXY_HOST = text1.getText();
					//System.out.println(PROXY_HOST);
					Integer PROXY_PORT = Integer.valueOf(text2.getText());
					//System.out.println(PROXY_PORT);
					ProxyConfig proxy = new ProxyConfig(PROXY_HOST, PROXY_PORT);
					webClient.setProxyConfig(proxy);
					if(auth) {
						DefaultCredentialsProvider credentialsProvider = new DefaultCredentialsProvider();
			            credentialsProvider.addProxyCredentials(text3.getText(), text4.getText() , PROXY_HOST, PROXY_PORT);
			            webClient.setCredentialsProvider(credentialsProvider);
					}
					JSONQuery.webClient = webClient;
					MessageQuery.webClient = webClient;
					OAuthSession.webClient = webClient;
					//System.out.println("Entered Proxy Settings");
				}
		    }
		});
		
		AutoConfig.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				label1.setEnabled(false);
				label2.setEnabled(false);
				text1.setEnabled(false);
				text2.setEnabled(false);
				button1.setSelection(false);
				button1.setEnabled(false);
				label3.setEnabled(false); 
				label4.setEnabled(false);
				text3.setEnabled(false);
				text4.setEnabled(false);
				proxy = false;
			}
		});
		
		ManualConfig.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				label1.setEnabled(true);
				label2.setEnabled(true);
				text1.setEnabled(true);
				text2.setEnabled(true);
				button1.setEnabled(true);
				proxy = true;
			}
		});
		return composite;
	}
	
public static void main(String[] args) throws Exception{
		display = new Display();
		shell = new Shell(display);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		shell.setLayout(layout);
		
		GUIMain main =  new GUIMain();
		
		main.webClient = new WebClient();
		JSONQuery.webClient = main.webClient;
		MessageQuery.webClient = main.webClient;
		OAuthSession.webClient = main.webClient;
		final Label label = new Label(shell, SWT.CENTER);
		label.setText("DESKTOP NOTIFY 1.0");
		label.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, true, 1 ,1));
		
		main.createWidgets();
		
		
		
		shell.pack();
		shell.open();
		while(!(shell.isDisposed())){
			if(!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
		System.exit(0);
	}

}
