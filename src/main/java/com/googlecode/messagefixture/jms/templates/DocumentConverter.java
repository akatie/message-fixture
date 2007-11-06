package com.googlecode.messagefixture.jms.templates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class DocumentConverter {

	
	public Map<String, Map<String, Object>> documentToMap(Document doc) {
		Map<String, Map<String, Object>> rootMap = new HashMap<String, Map<String, Object>>();
		
		Element rootElm = doc.getDocumentElement();
		rootMap.put(rootElm.getNodeName(), elementToMap(rootElm));
		
		return rootMap;
	}
	
	private Map<String, Object> elementToMap(Element elm) {
		
		Node child = elm.getFirstChild();
		
		HashMap<String, List<Map<String, Object>>> childMappings = new HashMap<String, List<Map<String, Object>>>();
		
		StringBuffer text = new StringBuffer();

		// find all Elements and cluster those with the same name
		while(child != null) {
			if(child instanceof Element) {
				Element childElm = (Element) child;
				
				String name = child.getNodeName();
				
				List<Map<String, Object>> elmWithThisName;
				if(childMappings.containsKey(name)) {
					elmWithThisName = childMappings.get(name);
				} else {
					elmWithThisName = new ArrayList<Map<String, Object>>();
					childMappings.put(name, elmWithThisName);
				}
				
				elmWithThisName.add(elementToMap(childElm));
			} else if(child instanceof Text){
				text.append(((Text)child).getNodeValue());
			}
			
			child = child.getNextSibling();
		}
		
		// take all element name based clusters and store as arrays
		Map<String, Object> elmMap = new HashMap<String, Object>();
		for (Entry<String, List<Map<String, Object>>> entry : childMappings.entrySet()) {
			List<Map<String, Object>> childList = entry.getValue();
			
			if(childList.size() > 1) {
				elmMap.put(entry.getKey(), childList.toArray(new Map[0]));
			} else {
				elmMap.put(entry.getKey(), childList.get(0));
			}
		}

		// add text as a special property
		if(text.length() > 0) {
			elmMap.put("text", text);
		}

		// get all attributes
		NamedNodeMap attrs = elm.getAttributes();
		for(int i = 0; i<attrs.getLength(); i++) {
			Node attr = attrs.item(i);
			elmMap.put(attr.getNodeName(), attr.getNodeValue());
		}
		
		return elmMap;
	}
}