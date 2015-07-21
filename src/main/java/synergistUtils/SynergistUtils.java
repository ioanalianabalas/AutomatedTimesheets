package synergistUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SynergistUtils {
	private String password;
	private String username;

	SynergistUtils(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getResourceCode() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
		SSLContextBuilder builder = new SSLContextBuilder();
	    builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
	    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
	    CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

	    HttpGet httpGet = new HttpGet("https://synergist.caplin.com/xmlapi/timesheets.xml?user=" + username + "&password=" + password + "&company=1&action=resourcelist&version=3");
	    CloseableHttpResponse response = httpclient.execute(httpGet);
	    BufferedReader reader = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
		String line = "";
		String xmlResponse = "";
		while ((line = reader.readLine()) != null) {
			xmlResponse += line + "\n";
		}
		parseXml(xmlResponse);
		return xmlResponse;
	}
	
	public Document parseXml(String xml) {
		xml = cleanUpXml(xml);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    Document doc = null;
	    InputSource is = new InputSource();
	    is.setCharacterStream(new StringReader(xml));
	    try {
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        doc = builder.parse(is);
		    parseDocument(doc);
	    } catch (ParserConfigurationException e) {
	    } catch (SAXException e) {
	    } catch (IOException e) { 
	    }
		return doc;
	}
	
	private String cleanUpXml(String xml) {
		int validStartIndex = xml.indexOf("<?xml");
		return xml.substring(validStartIndex);
	}
	
	private void parseDocument(Document doc) {
		//get the root element
		Element docEle = doc.getDocumentElement();

		//get a nodelist of elements
		NodeList nl = docEle.getElementsByTagName("resource");
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {

				//get the employee element
				Element el = (Element)nl.item(i);
				System.out.println(el);
			}
		}
	}
}
