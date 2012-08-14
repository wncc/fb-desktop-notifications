public class Demo {
	public static String userName;
	public static String password;
	
	
	Demo() throws Exception{
		this.run();
	}
	public void run() throws Exception{
		// TODO Auto-generated method stub
		
		OAuthSession session = new OAuthSession();
			session.startSession(userName, password);	
		System.out.println(session.getAccessCode());
		try {
		JSONQuery.Enquire(session.getAccessCode());
		} catch (com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException e1) {
			System.out.println("Access_Token Expired!!");
			this.run();
		} 
	}
}
