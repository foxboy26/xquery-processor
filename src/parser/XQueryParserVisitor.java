/* Generated By:JavaCC: Do not edit this line. XQueryParserVisitor.java Version 5.0 */
package parser;

public interface XQueryParserVisitor
{
  public Object visit(SimpleNode node, Object data);
  public Object visit(ASTStart node, Object data);
  public Object visit(ASTDoc node, Object data);
  public Object visit(ASTAbsSlash node, Object data);
  public Object visit(ASTAbsDSlash node, Object data);
  public Object visit(ASTRelSlash node, Object data);
  public Object visit(ASTRelDSlash node, Object data);
  public Object visit(ASTRelFilter node, Object data);
  public Object visit(ASTRelComma node, Object data);
  public Object visit(ASTStar node, Object data);
  public Object visit(ASTDot node, Object data);
  public Object visit(ASTDdot node, Object data);
  public Object visit(ASTText node, Object data);
  public Object visit(ASTParen node, Object data);
  public Object visit(ASTFilterAnd node, Object data);
  public Object visit(ASTFilterOr node, Object data);
  public Object visit(ASTFilterEq node, Object data);
  public Object visit(ASTFilterIs node, Object data);
  public Object visit(ASTFilterRelPath node, Object data);
  public Object visit(ASTFilterParen node, Object data);
  public Object visit(ASTFilterNot node, Object data);
  public Object visit(ASTForClause node, Object data);
  public Object visit(ASTIn node, Object data);
  public Object visit(ASTLetClause node, Object data);
  public Object visit(ASTAssign node, Object data);
  public Object visit(ASTWhereClause node, Object data);
  public Object visit(ASTReturnClause node, Object data);
  public Object visit(ASTCondAnd node, Object data);
  public Object visit(ASTCondOr node, Object data);
  public Object visit(ASTCondEq node, Object data);
  public Object visit(ASTCondIs node, Object data);
  public Object visit(ASTCondEmpty node, Object data);
  public Object visit(ASTCondSome node, Object data);
  public Object visit(ASTVar node, Object data);
  public Object visit(ASTXQueryComma node, Object data);
  public Object visit(ASTXQuerySlash node, Object data);
  public Object visit(ASTString node, Object data);
  public Object visit(ASTNewtag node, Object data);
  public Object visit(ASTJoin node, Object data);
  public Object visit(ASTFLWR node, Object data);
  public Object visit(ASTLX node, Object data);
  public Object visit(ASTJoinList node, Object data);
  public Object visit(ASTTagName node, Object data);
}
/* JavaCC - OriginalChecksum=c27567b057a12eacdb6a89410d87e1bc (do not edit this line) */
