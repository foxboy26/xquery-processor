/* Generated By:JJTree: Do not edit this line. ASTForClause.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package parser;

public
class ASTForClause extends SimpleNode {
  public ASTForClause(int id) {
    super(XQueryParserTreeConstants.JJTFORCLAUSE);
  }

  public ASTForClause(XQueryParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(XQueryParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=a9a54f953e19489e809ebdf55dd04280 (do not edit this line) */
