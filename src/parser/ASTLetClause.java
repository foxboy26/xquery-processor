/* Generated By:JJTree: Do not edit this line. ASTLetClause.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package parser;

public
class ASTLetClause extends SimpleNode {
  public ASTLetClause(int id) {
    super(id);
  }

  public ASTLetClause(XQueryParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(XQueryParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=49ab3566f5ae9eb2ee67ef81cfd9825a (do not edit this line) */
