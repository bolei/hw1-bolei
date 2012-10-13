package cmu.lti.uima.hw1.cpe;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.DocumentAnnotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;

public class Hw1CollectionReader extends CollectionReader_ImplBase {

  private static final String PARAM_INPUTDIR = "InputFilePath";

  private static final String PARAM_ENCODING = "Encoding";

  private static final String PARAM_LANGUAGE = "Language";

  private String mEncoding;

  private String mLanguage;

  private boolean hasMore = true;

  File mFile;

  private JCas jcas;

  @Override
  public void initialize() throws ResourceInitializationException {
    mFile = new File((String) getConfigParameterValue(PARAM_INPUTDIR));
    mEncoding = (String) getConfigParameterValue(PARAM_ENCODING);
    mLanguage = (String) getConfigParameterValue(PARAM_LANGUAGE);
  }

  @Override
  public void getNext(CAS aCAS) throws IOException, CollectionException {
    try {
      jcas = aCAS.getJCas();
    } catch (CASException e) {
      throw new CollectionException(e);
    }
    // open input stream to file
    BufferedInputStream fis = new BufferedInputStream(new FileInputStream(mFile));
    try {
      byte[] contents = new byte[(int) mFile.length()];
      fis.read(contents);
      String text;
      if (mEncoding != null) {
        text = new String(contents, mEncoding);
      } else {
        text = new String(contents);
      }
      // put document in CAS
      jcas.setDocumentText(text);
    } finally {
      if (fis != null)
        fis.close();
    }
    // set language if it was explicitly specified
    // as a configuration parameter
    if (mLanguage != null) {
      ((DocumentAnnotation) jcas.getDocumentAnnotationFs()).setLanguage(mLanguage);
    }
    hasMore = false;
  }

  @Override
  public void close() throws IOException {

  }

  @Override
  public Progress[] getProgress() {
    return new Progress[] { new ProgressImpl(1, 1, Progress.BYTES) };
  }

  @Override
  public boolean hasNext() throws IOException, CollectionException {
    return hasMore;
  }

}
