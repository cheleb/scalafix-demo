package fix

import scalafix._
import scala.meta._
import scalafix.util._

final case class Reponame_v1_0(index: SemanticdbIndex)
    extends SemanticRule(index, "Reponame_v1_0") {

  def fixCoproduc(ctx: RuleCtx): Patch =
    ctx.replaceSymbols("_root_.cats.data.Coproduct." -> "_root_.cats.data.EitherK.")
  def fixLitInt(ctx: RuleCtx) : Patch =
    ctx.tree.collect {
      case t @ Lit.Int(value) =>
        //ctx.removeTokens(t.tokens) + ctx.addRight(t, (value+1).toString)
        ctx.replaceTree(t, Lit.Int(value+1).syntax)
    }.asPatch


  private val pureExpressions = index.messages.collect {
    case Message(pos, _, msg )
      if(msg.startsWith("a pure expression does nothing")) => pos


  }.toSet

  def fixPureExpression(ctx: RuleCtx ) : Patch =
    ctx.tree.collect {
      case t: Tree if pureExpressions.contains(t.pos) =>
        ctx.removeTokens(t.tokens) + {
          for {
            head <- t.tokens.headOption
            newLine <- ctx.tokenList.leading(head).find(_.is[Newline])
          } yield ctx.tokenList.slice(ctx.tokenList.prev(newLine), head).map(ctx.removeToken).asPatch
        }
    }.asPatch

  override def fix(ctx: RuleCtx): Patch = {
//    ctx.tree.collect{
//      case n @ Name("Coproduct") => println(index.symbol(n))
//    }
    fixLitInt(ctx) + fixCoproduc(ctx) + fixPureExpression(ctx)
  }

}
