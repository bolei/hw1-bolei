package cmu.lti.uima.hw1.cpe;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;

import cmu.lti.uima.hw1.type.GeneName;

public class Hw1CasConsumer extends CasConsumer_ImplBase {

  private static final String PARAM_OUTPUTPATH = "outFilePath";

  private File outFile;

  @Override
  public void initialize() throws ResourceInitializationException {
    outFile = new File((String) getConfigParameterValue(PARAM_OUTPUTPATH));
    if (!outFile.exists()) {
      try {
        outFile.createNewFile();
      } catch (IOException e) {
        throw new ResourceInitializationException(e);
      }
    }
  }

  @Override
  public void processCas(CAS aCAS) throws ResourceProcessException {
    JCas jcas;
    FileWriter out = null;
    try {
      jcas = aCAS.getJCas();
    } catch (CASException e) {
      throw new ResourceProcessException(e);
    }
    FSIterator<Annotation> it = jcas.getAnnotationIndex(GeneName.type).iterator();
    try {
      out = new FileWriter(outFile, true);
      while (it.hasNext()) {
        Annotation geneName = it.next();
        out.write(geneName.toString() + "\n");
      }
      out.flush();
    } catch (IOException e) {
      throw new ResourceProcessException(e);
    } finally {
      if (out != null) {
        try {
          out.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

  }
}
