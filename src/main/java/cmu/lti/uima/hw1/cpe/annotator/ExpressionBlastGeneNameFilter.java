package cmu.lti.uima.hw1.cpe.annotator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import org.apache.uima.UimaContext;
import org.apache.uima.resource.ResourceInitializationException;

public class ExpressionBlastGeneNameFilter extends AbstractGeneNameFilter {

  private static final String GENE_NAME_FILE = "geneNameFile";

  private LinkedList<String> geneNames;

  @Override
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    try {
      if (geneNames == null) {

        String geneNameFilePath = (String) aContext.getConfigParameterValue(GENE_NAME_FILE);
        File file = new File(geneNameFilePath);

        BufferedReader in = new BufferedReader(new FileReader(file));
        geneNames = new LinkedList<String>();
        String line;
        while ((line = in.readLine()) != null) {
          geneNames.add(line);
        }

      }
    } catch (IOException e) {
      new ResourceInitializationException(e);
    }
  }

  @Override
  protected boolean isGeneName(String name) {
    String stemName = stemmer(name);
    for (String oneName : geneNames) {
      if (oneName.contains(stemName) || stemName.contains(oneName)) {
        return true;
      }
    }
    return false;
  }

}
