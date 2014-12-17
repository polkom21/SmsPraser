
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by polkom21 on 2014-09-30 14:35.
 * Package: PACKAGE_NAME
 * Project: SmsPraser
 */
public class SmsReader {
    //public Map<String, Message> messages = new HashMap<String, Message>();
    public ArrayList<Message> messages = new ArrayList<Message>();
    public ArrayList<String> authors = new ArrayList<String>();

    public SmsReader(String filePath) {
        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream in = new FileInputStream(filePath);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

            Message tempMessage = null;

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();

                    if (startElement.getName().getLocalPart() == "sms") {
                        tempMessage = new Message();

                        Iterator<Attribute> attributes = startElement.getAttributes();
                        while (attributes.hasNext()) {
                            Attribute attr = attributes.next();
                            /*if (attr.getName().toString().equals("date")) {
                                System.out.println(attr.getValue());
                            }*/
                            if(attr.getName().toString().equals("address")) tempMessage.number = attr.getValue();
                            if(attr.getName().toString().equals("type")) tempMessage.type = Integer.parseInt(attr.getValue());
                            if(attr.getName().toString().equals("readable_date")) tempMessage.date = attr.getValue();
                            if(attr.getName().toString().equals("body")) tempMessage.body = attr.getValue();
                            if(attr.getName().toString().equals("contact_name")) {
                                tempMessage.contact_name = attr.getValue();
                                if(!authors.contains(attr.getValue())) authors.add(attr.getValue());
                            }
                        }
                        //messages.put(tempMessage.contact_name, tempMessage);
                        messages.add(tempMessage);
                        //System.out.println(messages.size());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

    }

    public String getMessages() {
        String msgs = new String();
        String newLine = "\n";
        for(int i = 0; i < messages.size(); i++) {
            Message m = messages.get(i);
            if(m.type == 1) msgs += "Wiadomość od: " + m.contact_name + "(" + m.number + ")";
            else if(m.type == 2) msgs += "Wiadomość do: " + m.contact_name + "(" + m.number + ")";
            msgs += newLine;
            msgs += "Data: " + m.date + newLine;
            msgs += m.body + newLine + newLine;
        }
        return msgs;
    }

}
