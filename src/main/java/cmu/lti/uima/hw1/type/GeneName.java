/* First created by JCasGen Fri Oct 12 21:08:21 EDT 2012 */
package cmu.lti.uima.hw1.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;

/** 
 * Updated by JCasGen Fri Oct 12 23:50:43 EDT 2012
 * XML source: /media/win_works/Works/CMU/COURSES/11791-Software Engineering for Information Systems/workspace/hw1-bolei/src/main/resources/descriptor/typeSystem.xml
 * @generated */
public class GeneName extends Annotation {
  /**
   * @generated
   * @ordered
   */
  public final static int typeIndexID = JCasRegistry.register(GeneName.class);

  /**
   * @generated
   * @ordered
   */
  public final static int type = typeIndexID;

  /** @generated */
  public int getTypeIndexID() {return typeIndexID;}
 
  /**
   * Never called. Disable default constructor
   * 
   * @generated
   */
  protected GeneName() {}
    
  /**
   * Internal - constructor used by generator
   * 
   * @generated
   */
  public GeneName(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public GeneName(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */
  public GeneName(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc --> Write your own initialization here <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {
  }

  // *--------------*
  // * Feature: sentenceId

  /**
   * getter for sentenceId - gets
   * 
   * @generated
   */
  public String getSentenceId() {
    if (GeneName_Type.featOkTst && ((GeneName_Type)jcasType).casFeat_sentenceId == null)
      jcasType.jcas.throwFeatMissing("sentenceId", "cmu.lti.uima.hw1.type.GeneName");
    return jcasType.ll_cas.ll_getStringValue(addr, ((GeneName_Type)jcasType).casFeatCode_sentenceId);}
    
  /**
   * setter for sentenceId - sets
   * 
   * @generated
   */
  public void setSentenceId(String v) {
    if (GeneName_Type.featOkTst && ((GeneName_Type)jcasType).casFeat_sentenceId == null)
      jcasType.jcas.throwFeatMissing("sentenceId", "cmu.lti.uima.hw1.type.GeneName");
    jcasType.ll_cas.ll_setStringValue(addr, ((GeneName_Type)jcasType).casFeatCode_sentenceId, v);}    
   
    
  // *--------------*
  // * Feature: name

  /**
   * getter for name - gets
   * 
   * @generated
   */
  public String getName() {
    if (GeneName_Type.featOkTst && ((GeneName_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "cmu.lti.uima.hw1.type.GeneName");
    return jcasType.ll_cas.ll_getStringValue(addr, ((GeneName_Type)jcasType).casFeatCode_name);}
    
  /**
   * setter for name - sets
   * 
   * @generated
   */
  public void setName(String v) {
    if (GeneName_Type.featOkTst && ((GeneName_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "cmu.lti.uima.hw1.type.GeneName");
    jcasType.ll_cas.ll_setStringValue(addr, ((GeneName_Type)jcasType).casFeatCode_name, v);}    
    @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getSentenceId());
    sb.append("|");
    sb.append(getBegin() + " " + getEnd());
    sb.append("|");
    sb.append(getName());
    return sb.toString();
  }
}
