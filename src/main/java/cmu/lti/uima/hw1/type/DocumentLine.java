

/* First created by JCasGen Fri Oct 12 23:50:43 EDT 2012 */
package cmu.lti.uima.hw1.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Fri Oct 12 23:50:43 EDT 2012
 * XML source: /media/win_works/Works/CMU/COURSES/11791-Software Engineering for Information Systems/workspace/hw1-bolei/src/main/resources/descriptor/typeSystem.xml
 * @generated */
public class DocumentLine extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(DocumentLine.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected DocumentLine() {}
    
  /** Internal - constructor used by generator 
   * @generated */
  public DocumentLine(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public DocumentLine(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public DocumentLine(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {}
     
 
    
  //*--------------*
  //* Feature: sentenceId

  /** getter for sentenceId - gets 
   * @generated */
  public String getSentenceId() {
    if (DocumentLine_Type.featOkTst && ((DocumentLine_Type)jcasType).casFeat_sentenceId == null)
      jcasType.jcas.throwFeatMissing("sentenceId", "cmu.lti.uima.hw1.type.DocumentLine");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DocumentLine_Type)jcasType).casFeatCode_sentenceId);}
    
  /** setter for sentenceId - sets  
   * @generated */
  public void setSentenceId(String v) {
    if (DocumentLine_Type.featOkTst && ((DocumentLine_Type)jcasType).casFeat_sentenceId == null)
      jcasType.jcas.throwFeatMissing("sentenceId", "cmu.lti.uima.hw1.type.DocumentLine");
    jcasType.ll_cas.ll_setStringValue(addr, ((DocumentLine_Type)jcasType).casFeatCode_sentenceId, v);}    
   
    
  //*--------------*
  //* Feature: content

  /** getter for content - gets 
   * @generated */
  public String getContent() {
    if (DocumentLine_Type.featOkTst && ((DocumentLine_Type)jcasType).casFeat_content == null)
      jcasType.jcas.throwFeatMissing("content", "cmu.lti.uima.hw1.type.DocumentLine");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DocumentLine_Type)jcasType).casFeatCode_content);}
    
  /** setter for content - sets  
   * @generated */
  public void setContent(String v) {
    if (DocumentLine_Type.featOkTst && ((DocumentLine_Type)jcasType).casFeat_content == null)
      jcasType.jcas.throwFeatMissing("content", "cmu.lti.uima.hw1.type.DocumentLine");
    jcasType.ll_cas.ll_setStringValue(addr, ((DocumentLine_Type)jcasType).casFeatCode_content, v);}    
  }

    