/* Generated By:JJTree: Do not edit this line. ASTXQuerySlash.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package parser;

public
class ASTXQuerySlash extends SimpleNode {
  public ASTXQuerySlash(int id) {
    super(id);
  }

  public ASTXQuerySlash(XQueryParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(XQueryParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=4b0cf0f6a9610fbd7c245f0515472262 (do not edit this line) */
