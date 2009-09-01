import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ertuo.htmlutil.webclient.WebClientLocal;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlHiddenInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

/**
 * ����19lou
 * @author mo.duanm
 *
 */
public class WebUtilTest extends TestCase {
	private static final WebClientLocal webClient = new WebClientLocal();
	//��½��ַ
	private static String login_url = "http://www.19lou.com/passportlogin.php?action=login";
	
	//��½action
	private static String login_action = "http://www.19lou.com/passportlogin.php?action=login&referer=http%3A%2F%2Fwww.19lou.com%2F";
	
	//�����б�
	private static String news_list_url = "http://www.19lou.com/";
	
	//��������
	private static String guanggao="����֧��¥����";
		//"<span style='font-weight: bold;'>�����ֻ�����ֱ��<br><br><font size='4'><span style='color: Red;'>http://item.taobao.com/auction/item_detail-db1-02e27c331e299008cb3f672e190d4953.htm</span><br><br></font></span>";
		
		
    //"�����ֻ�����ֱ��http://item.taobao.com/auction/item_detail-db1-02e27c331e299008cb3f672e190d4953.htm";
 
	

	public void test_19lou() {
		// ��¼
		this.login();
		// ��������б�
		// �ظ����Ż���
		this.answer();
	}

	/**
	 * ��¼
	 */
	private void login() {
		HtmlPage page1 = webClient.getHtmlPageByUrl(login_url);
		final HtmlForm form = page1.getFormByName("login");
		form.setActionAttribute(this.login_action);

		final HtmlSubmitInput button = (HtmlSubmitInput) form
				.getInputByName("loginsubmit");
		final HtmlTextInput username = (HtmlTextInput) form
				.getInputByName("username");
		final HtmlPasswordInput password = (HtmlPasswordInput) form
				.getInputByName("password");
		final HtmlHiddenInput formhash = (HtmlHiddenInput) form
				.getInputByName("formhash");
		formhash.setValueAttribute("a31eb5c8");
		username.setValueAttribute("summersnow88888");
		password.setValueAttribute("19854171985");
		webClient.getClickHtmlPage(button);
	}

	private void answer() {
		HtmlPage loginAfterPagee=webClient.getHtmlPageByUrl("http://www.19lou.com/post.php?action=reply&fid=132&tid=20808073&extra=page%3D1");
		 
		HtmlForm postform = (HtmlForm) loginAfterPagee
				.getElementById("postform");
		// ����
		HtmlTextInput subject = (HtmlTextInput) postform.getInputByName("subject");
		// ����
		HtmlTextArea message = postform.getTextAreaByName("message");

		// ����value
		message.focus();
		message.setText(guanggao);
		message.blur();

		System.out.println(message.getOnBlurAttribute());

		//subject.setValueAttribute("�������ܳ�!");

		final HtmlSubmitInput replysubmit = (HtmlSubmitInput) postform
				.getInputByName("replysubmit");
		webClient.getClickHtmlPage(replysubmit);
	}
	
	

}
