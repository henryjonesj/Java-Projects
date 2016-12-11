package video;

import org.apache.axis2.*;
import javax.activation.*;
import org.apache.axiom.om.*;
import org.apache.axis2.wsdl.*;
import javax.xml.stream.*;
import org.apache.axis2.context.*;
import java.io.*;

public class VideoServer {
	
	public OMElement getVideo(OMElement element) throws Exception {
		
		System.out.println("Service Invoked");
		File f = new File("D:/Videos/Song.mp3");
		System.out.println("Preparing Response !");
		FileDataSource fds = new FileDataSource(f);
		DataHandler dh = new DataHandler(fds);
		System.out.println("Content Type : "+dh.getContentType());		

		MessageContext inMessageContext = MessageContext.getCurrentMessageContext();

		OperationContext operationContext = inMessageContext.getOperationContext();
		MessageContext outMessageContext = operationContext.getMessageContext(WSDLConstants.MESSAGE_LABEL_OUT_VALUE);
		outMessageContext.addAttachment("Eden",dh);
		String VideoID = "Eden";
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omns = fac.createOMNamespace("http://video","video");
		OMElement response = fac.createOMElement("getVideoResp",omns);
		OMElement videoelement = fac.createOMElement("VideoContent",omns);
		videoelement.addAttribute("href",VideoID,omns);
		response.addChild(videoelement);
		return response;
	}
}
		