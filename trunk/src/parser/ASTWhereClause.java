/* Generated By:JJTree: Do not edit this line. ASTWhereClause.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package parser;

public
class ASTWhereClause extends SimpleNode {
  public ASTWhereClause(int id) {
    super(XQueryParserTreeConstants.JJTWHERECLAUSE);
  }

  public ASTWhereClause(XQueryParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(XQueryParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=a9314fd02b6a6cce873ed31204ea6dd3 (do not edit this line) */
