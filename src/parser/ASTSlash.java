/* Generated By:JJTree: Do not edit this line. ASTSlash.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package parser;

public
class ASTSlash extends SimpleNode {
  public ASTSlash(int id) {
    super(id);
  }

  public ASTSlash(XQueryParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(XQueryParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=823b54fd79819f586ffee8aac4dc75f6 (do not edit this line) */
