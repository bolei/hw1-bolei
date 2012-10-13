package cmu.lti.uima.hw1.cpe.annotator;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import cmu.lti.uima.hw1.type.DocumentLine;
import cmu.lti.uima.hw1.type.GeneName;

public class GeneNameAnnotator extends JCasAnnotator_ImplBase {

  private PosTagNamedEntityRecognizer posTaggerAnno;

  public GeneNameAnnotator() throws ResourceInitializationException {
    posTaggerAnno = new PosTagNamedEntityRecognizer();
  }

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
    List<GeneName> geneNameList = new LinkedList<GeneName>();
    Map<Integer, Integer> spans = posTaggerAnno.getGeneSpans(text);
    Set<Entry<Integer, Integer>> entrySet = spans.entrySet();
    String name;
    for (Entry<Integer, Integer> entry : entrySet) {
      int begin = entry.getKey();
      int end = entry.getValue();
      GeneName gn = new GeneName(aJCas);
      name = text.substring(begin, end);

      gn.setBegin(countNonSpace(text, begin));
      gn.setEnd(countNonSpace(text, end) - 1);
      gn.setName(name);
      geneNameList.add(gn);
    }

    return geneNameList;
  }

  private int countNonSpace(String text, int pos) {
    int count = 0, index = 0;
    while (index < pos) {
      if (text.charAt(index) != ' ') {
        count++;
      }
      index++;
    }

    return count;
  }

}
