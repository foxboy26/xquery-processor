/* Generated By:JJTree: Do not edit this line. ASTAbsSlash.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package parser;

public
class ASTAbsSlash extends SimpleNode {
  public ASTAbsSlash(int id) {
    super(id);
  }

  public ASTAbsSlash(XQueryParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(XQueryParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=21495038176d678a8867097c84508f2e (do not edit this line) */