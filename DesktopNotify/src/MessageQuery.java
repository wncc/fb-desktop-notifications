
import org.json.*;
import com.gargoylesoftware.htmlunit.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import notifier.NotifierDialog;

public class MessageQuery {
	static WebClient webClient;
	String picURL;
	String thread_id;
	String snippet;
	String snippet_author_name; 
	String snippet_author_id;
	Image profile_pic;
	Integer message_count;
	boolean NOTIFICATION_STATUS;
	
	public void setNamePicURL(String AccessCode) throws Exception {
		String query = "https://graph.facebook.com/fql?q=SELECT+name+,+pic_square+FROM+profile+WHERE+id="+ snippet_author_id + "&access_token=" + AccessCode;
		JavaScriptPage page= webClient.getPage(query);
		String jsonObj = page.getContent();
		
		JSONObject json = new JSONObject(jsonObj.toString());
        snippet_author_name = json.getJSONArray("data").getJSONObject(0).getString("name");
		picURL = json.getJSONArray("data").getJSONObject(0).getString("pic_square");
		
		System.out.println(snippet_author_name + " : " + snippet);
	}
	
	public void loadImage(Display display) throws Exception{
		Page page= webClient.getPage(picURL);
		UnexpectedPage img = new UnexpectedPage(page.getWebResponse(), page.getEnclosingWindow());
		profile_pic = new Image(display, img.getInputStream());
	}
	
	MessageQuery(JSONObject jsonData, String AccessCode) throws Exception {
		
		snippet_author_id = jsonData.getString("snippet_author");
		snippet = jsonData.getString("snippet");
		message_count = jsonData.getInt("message_count");
		thread_id = jsonData.getString("thread_id");
		Shell shell = new Shell(Display.getCurrent());
		setNamePicURL(AccessCode);
		loadImage(Display.getCurrent());
		NotifierDialog.notify(shell, snippet_author_name, snippet, profile_pic);
		shell.open();        
        while (!shell.isDisposed()) {
            if (!Display.getCurrent().readAndDispatch()) Display.getCurrent().sleep();
        }
	}
}
