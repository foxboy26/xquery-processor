/* Generated By:JJTree: Do not edit this line. ASTFLWR.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package parser;

public
class ASTFLWR extends SimpleNode {
  public ASTFLWR(int id) {
    super(XQueryParserTreeConstants.JJTFLWR);
  }

  public ASTFLWR(XQueryParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(XQueryParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=d824639b1f4b3fcacf02ab941667ffb7 (do not edit this line) */
