package cmu.lti.uima.hw1.cpe.annotator;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.SAXParserFactory;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.uima.UimaContext;
import org.apache.uima.resource.ResourceInitializationException;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This class is used to test if a string is a gene name by looking up the online NCBI database 2
 * steps are done:</br> - use the base query to get the list of IDs that are relevant to the query
 * string</br> - looking up all the IDs in NCBI database using summary query and see if the string
 * is presented in the returned XML string.
 * 
 * @author bolei
 * 
 */
public class NcbiGeneNameFilter extends AbstractGeneNameFilter {

  private HttpClient httpclient;

  private javax.xml.parsers.SAXParser sp;

  private LinkedList<String> idList;

  private Set<String> nameCache = new HashSet<String>();

  @Override
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    SAXParserFactory spf = SAXParserFactory.newInstance();
    httpclient = new DefaultHttpClient();
    // get a new instance of parser
    try {
      sp = spf.newSAXParser();
    } catch (Exception e) {
      throw new ResourceInitializationException(e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see cmu.lti.uima.hw1.cpe.annotator.AbstractGeneNameFilter#isGeneName(java.lang.String)
   */
  protected boolean isGeneName(String name) {
    System.out.println("filtering:" + name);
    if (nameCache.contains(name)) {// first check the cache
      return true;
    }

    idList = new LinkedList<String>();
    // gname.removeFromIndexes();
    try {// do the first look up using Basic query.
      HttpGet httpNcbiBasicGet = new HttpGet(getNcbiBasicUrl(name));
      ResponseHandler<String> responseHandler = new BasicResponseHandler();
      String responseBody = httpclient.execute(httpNcbiBasicGet, responseHandler);
      sp.parse(new InputSource(new StringReader(responseBody)), new BasicQueryResultXmlHandler());

      // do the second look up using summary query.
      HttpGet httpNcbiSummaryGet = new HttpGet(getNcbiSummaryUrl(idList));
      responseBody = httpclient.execute(httpNcbiSummaryGet, responseHandler);
      SummaryQueryResultXmlHandler sqrhandler = new SummaryQueryResultXmlHandler(name);
      sp.parse(new InputSource(new StringReader(responseBody)), sqrhandler);
      return sqrhandler.isGeneName;

    } catch (IOException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    return false;
  }

  private URI getNcbiBasicUrl(String term) throws URISyntaxException {
    List<NameValuePair> formParams = new LinkedList<NameValuePair>();
    formParams.add(new BasicNameValuePair("db", "gene"));
    formParams.add(new BasicNameValuePair("term", term));
    URI uri = URIUtils.createURI("http", "eutils.ncbi.nlm.nih.gov", -1,
            "/entrez/eutils/esearch.fcgi", URLEncodedUtils.format(formParams, "UTF-8"), null);
    return uri;
  }

  private URI getNcbiSummaryUrl(List<String> idList) throws URISyntaxException {

    StringBuilder sb = new StringBuilder();
    for (String id : idList) {
      sb.append(id + ",");
    }

    List<NameValuePair> formParams = new LinkedList<NameValuePair>();
    formParams.add(new BasicNameValuePair("db", "gene"));
    formParams.add(new BasicNameValuePair("id", sb.toString()));
    URI uri = URIUtils.createURI("http", "eutils.ncbi.nlm.nih.gov", -1,
            "/entrez/eutils/esummary.fcgi", URLEncodedUtils.format(formParams, "UTF-8"), null);
    return uri;
  }

  private class BasicQueryResultXmlHandler extends DefaultHandler {
    private String tempVal;

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
      if (qName.equalsIgnoreCase("Id")) {
        idList.add(tempVal);
      }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
      tempVal = new String(ch, start, length);
    }

  }

  private class SummaryQueryResultXmlHandler extends DefaultHandler {
    private boolean isGeneName = false;

    private String tempVal;

    private String tempItemName;

    private String name;

    public SummaryQueryResultXmlHandler(String name) {
      this.name = name;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
      tempVal = new String(ch, start, length);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
      if (qName.equalsIgnoreCase("Item")) {
        tempItemName = attributes.getValue("Name");
      }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
      if (isGeneName == true) {
        return;
      }
      String[] tokens = null;
      if (qName.equalsIgnoreCase("Item")) {
        if (tempItemName.equalsIgnoreCase("Description")) {
          tokens = tempVal.trim().split(",");
          // put tokens into local cache
        } else if (tempItemName.equalsIgnoreCase("OtherDesignations")) {
          tokens = tempVal.split("\\|");
        }
        if (tokens != null) {
          for (String str : tokens) {
            nameCache.add(str);
            if (str.contains(stemmer(name)) || name.contains(str)) {
              isGeneName = true;
              return;
            }
          }
        }
      }
    }

  }
}
