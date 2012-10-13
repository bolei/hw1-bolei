package cmu.lti.uima.hw1.cpe.annotator;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

import cmu.lti.uima.hw1.type.GeneName;

public class SimpleAnnotator extends JCasAnnotator_ImplBase {

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    for (int i = 0; i < 10; i += 2) {
      GeneName geneName = new GeneName(aJCas);
      geneName.setBegin(i);
      geneName.setEnd(i + 1);
      geneName.setName("gene" + i);
      geneName.setSentenceId("sentence" + i);
      geneName.addToIndexes();
    }
  }

}
