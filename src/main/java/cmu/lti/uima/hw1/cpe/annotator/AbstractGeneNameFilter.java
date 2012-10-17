package cmu.lti.uima.hw1.cpe.annotator;

import java.util.LinkedList;
import java.util.List;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import cmu.lti.uima.hw1.type.GeneName;

/**
 * This class uses the method "isGeneName()" to test if the given GeneName annotation actually
 * represents a gene name. All non-geneNames are removed from index of the Cas.
 * 
 * @author bolei
 * 
 */
public abstract class AbstractGeneNameFilter extends JCasAnnotator_ImplBase {

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    System.out.println("begine geneNameFilter");
    FSIterator<Annotation> it = aJCas.getAnnotationIndex(GeneName.type).iterator();
    List<Annotation> toBeRemoved = new LinkedList<Annotation>();
    while (it.hasNext()) {
      GeneName gname = (GeneName) it.next();
      boolean result = isGeneName(gname.getName());
      if (result == false) {
        toBeRemoved.add(gname);
      }
    }
    for (Annotation elem : toBeRemoved) {
      elem.removeFromIndexes();
    }
  }

  /**
   * Determine if the input string is a gene name.
   * 
   * @param name
   *          the name to be tested
   * @return true if the input is a gene name
   */
  protected abstract boolean isGeneName(String name);

  /**
   * a simple stemmer that drop the tail of plural nouns.
   * 
   * @param name
   * @return
   */
  protected String stemmer(String name) {
    if (name.endsWith("ies")) {
      return name.substring(0, name.lastIndexOf("ies")) + "y";
    }
    if (name.endsWith("oes") || name.endsWith("xes") || name.endsWith("ses")) {
      return name.substring(0, name.lastIndexOf("es"));
    }
    if (name.endsWith("es")) {
      return name.substring(0, name.lastIndexOf("es")) + "e";
    }
    if (name.endsWith("s")) {
      return name.substring(0, name.lastIndexOf("s"));
    }
    return name;
  }

}
