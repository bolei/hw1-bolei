package cmu.lti.uima.hw1.cpe.annotator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

import cmu.lti.uima.hw1.type.DocumentLine;

public class DocumentLineAnnotator extends JCasAnnotator_ImplBase {

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    System.out.println("documentline annotator");

    try {
      BufferedReader in = new BufferedReader(new StringReader(aJCas.getDocumentText()));
      String line = null;
      DocumentLine dl;
      while ((line = in.readLine()) != null) {
        dl = buildDocumentLine(aJCas, line);
        dl.addToIndexes();
      }
    } catch (IOException e) {
      throw new AnalysisEngineProcessException(e);
    }
  }

  private DocumentLine buildDocumentLine(JCas aJCas, String line) {
    DocumentLine dl = new DocumentLine(aJCas);
    int pos = line.indexOf(" ");
    dl.setSentenceId(line.substring(0, pos));
    dl.setContent(line.substring(pos + 1));
    return dl;
  }

}
