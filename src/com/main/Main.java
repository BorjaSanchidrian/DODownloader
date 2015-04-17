package com.main;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Main {
	
	public static void main(String[] args) {
		try {
			
			File xmlResources = new File("resources.xml");
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(xmlResources);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("file");
			
			int count = 0;
			for(int i=0; i<nList.getLength(); i++) {
				Node nNode = nList.item(i);
				Element element = (Element) nNode;
				
				String[] locationFolder = element.getAttribute("location").split("_");
				String folderLocal = "";
				String folderRemote = "";
				for(String a : locationFolder) {
					folderLocal += a + "\\";
					folderRemote += a + "/";
				}
				folderLocal += element.getAttribute("id")+"."+element.getAttribute("type");
				folderRemote += element.getAttribute("id")+"."+element.getAttribute("type");
				
				File destination = new File("C:\\DODownloader\\spacemap6\\"+folderLocal);
				if(!destination.exists()) {
					destination.getParentFile().mkdirs();
					destination.createNewFile();
					System.out.println("Downloading: http://pvp.oneultimate.net/spacemap/"+folderRemote);
					
					//ReadableByteChannel in=Channels.newChannel(new URL("http://darkorbit-22.ah.bpcdn.net/spacemap/"+folderRemote).openStream());
					ReadableByteChannel in=Channels.newChannel(new URL("http://pvp.oneultimate.net/spacemap/"+folderRemote).openStream());
					@SuppressWarnings("resource")
					FileChannel out = new FileOutputStream(destination).getChannel();
					out.transferFrom(in, 0, Long.MAX_VALUE);
					count++;
				}
			}
			
			System.out.println("Download Finished! " + count + " files downloaded!");
 			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
