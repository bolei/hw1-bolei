package cmu.lti.uima.hw1.cpe.annotator;

import java.util.LinkedList;
import java.util.List;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import cmu.lti.uima.hw1.type.DocumentLine;
import cmu.lti.uima.hw1.type.GeneName;

public class GeneNameAnnotator extends JCasAnnotator_ImplBase {

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    FSIterator<Annotation> it = aJCas.getAnnotationIndex(DocumentLine.type).iterator();
    DocumentLine dl;
    while (it.hasNext()) {
      dl = (DocumentLine) it.next();

      List<GeneName> geneNameList = annotateGeneNames(aJCas, dl.getContent());
      for (GeneName geneName : geneNameList) {
        geneName.setSentenceId(dl.getSentenceId());
        geneName.addToIndexes();
      }
    }
  }

  private List<GeneName> annotateGeneNames(JCas aJCas, String text) {
    // TODO set begin, end, geneName
    List<GeneName> geneNameList = new LinkedList<GeneName>();
    GeneName gn = new GeneName(aJCas);

    gn.setBegin(0);
    gn.setEnd(10);
    gn.setName(text);

    geneNameList.add(gn);
    return geneNameList;
  }

}
