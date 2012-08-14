import org.json.*;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.JavaScriptPage;
import java.util.*;

public class JSONQuery {
	static List<String> history = new ArrayList<String>();
	static WebClient webClient;
	
	public static int JSONParse(String jsonObj, String AccessCode) throws Exception{
		JSONObject json = new JSONObject(jsonObj);	
		JSONArray jsonMsgArray = json.getJSONArray("data"); 
		for(int i=0; i<jsonMsgArray.length();i++) {
			JSONObject current = jsonMsgArray.getJSONObject(i);
			String current_id = new String(current.getString("thread_id") + "." + current.getString("message_count"));
			if (history.contains(current_id)) {
				System.out.println("Ignored Message");
			} else {
				history.add(current_id);
				new MessageQuery(current, AccessCode);
			}

		}
		return jsonMsgArray.length();
	}
	
	public static void Enquire(String AccessCode) throws Exception {
		int cont;
	do {
		//System.out.println("Entered loop in JSONQuery");
			//final com.gargoylesoftware.htmlunit.WebClient webClient = new WebClient();
		
		JavaScriptPage page= webClient.getPage("https://graph.facebook.com/fql?q=SELECT+thread_id+,+message_count+,+snippet+,+snippet_author+FROM+thread+WHERE+folder_id=0+and+unread!=0&access_token="+AccessCode);
		String jsonObj = page.getContent();
		cont = JSONParse(jsonObj, AccessCode);
		if(cont==0) {
			System.out.println("No new messages");
		} 
		Thread.currentThread().sleep(10000);
	}while(cont!=-1);
}
}
