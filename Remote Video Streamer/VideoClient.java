import javax.activation.*;
import javax.xml.namespace.*;
import javax.xml.stream.*;
import org.apache.axiom.om.*;
import org.apache.axiom.soap.*;
import org.apache.axis2.client.*;
import org.apache.axis2.addressing.*;
import org.apache.axis2.wsdl.*;
import org.apache.axis2.context.*;
import org.apache.log4j.*;
import java.io.*;

public class VideoClient {
	private static EndpointReference targetEPR = new EndpointReference("http://localhost:8080/axis2/services/FilmService");
	
	public static void main(String[] args) {
		try {
		System.out.println("Client Initiated !");
		ServiceClient client = new ServiceClient();
		Options opts = new Options();
		opts.setTo(targetEPR);
		client.setOptions(opts);
		OperationClient mepClient = client.createClient(ServiceClient.ANON_OUT_IN_OP);
		MessageContext mc = new MessageContext();
		
		SOAPFactory fac = OMAbstractFactory.getSOAP11Factory();
		SOAPEnvelope env = fac.getDefaultEnvelope();
		OMNamespace omns = fac.createOMNamespace("http://video","video");
		OMElement method = fac.createOMElement("getVideo",omns);
		env.getBody().addChild(method);
		mc.setEnvelope(env);
		System.out.println("Request Message Context : "+mc+"\n\n");
		mepClient.addMessageContext(mc);
		mepClient.execute(true);

		MessageContext response = mepClient.getMessageContext(WSDLConstants.MESSAGE_LABEL_IN_VALUE);
		System.out.println("Response : "+response+"\n\n");

		SOAPBody body = response.getEnvelope().getBody();
		System.out.println("Body : "+body+"\n\n");
		OMElement element = body.getFirstChildWithName(new QName("http://video","getVideoResp"));
		System.out.println("Element : "+element+"\n\n");
		OMElement element1 = element.getFirstChildWithName(new QName("http://video","VideoContent"));
		System.out.println("Element1 : "+element1+"\n\n");
		String VideoID = element1.getAttributeValue(new QName("http://video","href"));
		
		System.out.println("VideoID : "+VideoID+"\n\n");
		DataHandler dh = response.getAttachment(VideoID);

		System.out.println("DataHandler : "+dh+"\n\n");
		File f = new File("SongCopy.mp3");
		FileOutputStream fos = new FileOutputStream(f);
		dh.writeTo(fos);
		Runtime rt = Runtime.getRuntime();
		Process p = rt.exec("C:/Program Files/Winamp/winamp.exe "+f.getAbsolutePath());
		System.out.println("Done");
		}
		catch(Exception e) { }	
	}
}