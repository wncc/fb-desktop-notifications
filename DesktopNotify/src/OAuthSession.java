import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class OAuthSession {
	 
	static WebClient webClient;
	String access_code;
	int expires_in;
	
	//Methods 
	public String getAccessCode() {
		return access_code;
	}
	
	public void setAccessCode(String access_code) {
		this.access_code = access_code;   
	}
	
	public int getExpiryTime() {
		return expires_in;
	}
	
	public void setExpiryTime(int expires_in) {
		this.expires_in = expires_in;   
	}
	
	public void setParameters(String Url) {
		String delim = "[=,&,#]";
		String[] tokens = Url.split(delim);
		if (tokens[1].equals("access_token") & tokens[3].equals("expires_in")) {
			setAccessCode(tokens[2]);
			setExpiryTime(Integer.parseInt(tokens[4]));		
		 }
	}
		
	public boolean startSession(String userName, String password) throws Exception 
	{

	    // Get the first page
	    final HtmlPage page1 = webClient.getPage("https://facebook.com/dialog/oauth?client_id=408724279178654&redirect_uri=https://www.facebook.com/connect/login_success.html&response_type=token&scope=read_mailbox");
	    
	   
	    // Get the form that we are dealing with and within that form, 
	    // find the submit button and the field that we want to change.
	    final HtmlForm form = page1.getHtmlElementById("login_form");
	    final HtmlSubmitInput button = form.getInputByName("login");
	    final HtmlTextInput email = form.getInputByName("email");
	    final HtmlPasswordInput pass = form.getInputByName("pass");
	    
	    // Change the value of the text field
	    email.setValueAttribute(userName);
	    pass.setValueAttribute(password);
			    
	    // Now submit the form by clicking the button and get back the second page.
	    final HtmlPage page2 = button.click();
	    
	    String str  = "access_token";
	    if(page2.getUrl().toString().contains(str)) {
	    	setParameters(page2.getUrl().toString());
	    	System.out.println("Success at page2");
	    	return true;
	    } else {
	    	access_code = null;
	    	expires_in = 0;
	    }
	    
	    //Initialization of App with the User
	    System.out.println("Basic Info Page");
	    final HtmlForm form2 = page2.getHtmlElementById("uiserver_form");
	    final HtmlSubmitInput button2 = form2.getInputByName("grant_required_clicked");
	    
	    final HtmlPage page3 = button2.click();
	    System.out.println("Extended Perm Page");
	    final HtmlForm form3 = page3.getHtmlElementById("uiserver_form");
	    final HtmlSubmitInput button3 = form3.getInputByName("grant_clicked");
	    
	    System.out.println("Success at Page4");
	    final HtmlPage page4 = button3.click();
	    if(page4.getUrl().toString().contains(str)) {
	    	setParameters(page4.getUrl().toString());
	    	return true;
	    } else {
	    	access_code = null;
	    	expires_in = 0;
	    	return false;
	    }
	}
	    
}



