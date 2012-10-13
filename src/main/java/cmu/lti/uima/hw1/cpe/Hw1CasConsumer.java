package cmu.lti.uima.hw1.cpe;

import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.resource.ResourceProcessException;

public class Hw1CasConsumer extends CasConsumer_ImplBase {

  @Override
  public void processCas(CAS aCAS) throws ResourceProcessException {
    System.out.println("consumer!");
  }

}
