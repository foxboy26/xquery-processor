/* Generated By:JJTree: Do not edit this line. ASTCondEmpty.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package parser;

public
class ASTCondEmpty extends SimpleNode {
  public ASTCondEmpty(int id) {
    super(id);
  }

  public ASTCondEmpty(XQueryParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(XQueryParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=02b20cbff9bf48aaa852cbfc95e11899 (do not edit this line) */
