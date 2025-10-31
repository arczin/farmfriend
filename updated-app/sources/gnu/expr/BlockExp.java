package gnu.expr;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.ExitableBlock;
import gnu.bytecode.Label;
import gnu.mapping.CallContext;
import gnu.mapping.OutPort;

public class BlockExp extends Expression {
    Expression body;
    Expression exitBody;
    Target exitTarget;
    ExitableBlock exitableBlock;
    Declaration label;

    public void setBody(Expression body2) {
        this.body = body2;
    }

    public void setBody(Expression body2, Expression exitBody2) {
        this.body = body2;
        this.exitBody = exitBody2;
    }

    public void setLabel(Declaration label2) {
        this.label = label2;
    }

    /* access modifiers changed from: protected */
    public boolean mustCompile() {
        return false;
    }

    public void apply(CallContext ctx) throws Throwable {
        Object result;
        try {
            result = this.body.eval(ctx);
        } catch (BlockExitException ex) {
            if (ex.exit.block == this) {
                result = ex.exit.result;
                if (this.exitBody != null) {
                    result = this.exitBody.eval(ctx);
                }
            } else {
                throw ex;
            }
        }
        ctx.writeValue(result);
    }

    public void compile(Compilation comp, Target target) {
        CodeAttr code = comp.getCode();
        this.exitableBlock = code.startExitableBlock((this.exitBody != null || !(target instanceof StackTarget)) ? null : target.getType(), true);
        this.exitTarget = this.exitBody == null ? target : Target.Ignore;
        this.body.compileWithPosition(comp, target);
        if (this.exitBody != null) {
            Label doneLabel = new Label(code);
            code.emitGoto(doneLabel);
            code.endExitableBlock();
            this.exitBody.compileWithPosition(comp, target);
            doneLabel.define(code);
        } else {
            code.endExitableBlock();
        }
        this.exitableBlock = null;
    }

    /* access modifiers changed from: protected */
    public <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        return visitor.visitBlockExp(this, d);
    }

    /* access modifiers changed from: protected */
    public <R, D> void visitChildren(ExpVisitor<R, D> visitor, D d) {
        this.body = visitor.visitAndUpdate(this.body, d);
        if (visitor.exitValue == null && this.exitBody != null) {
            this.exitBody = visitor.visitAndUpdate(this.exitBody, d);
        }
    }

    public void print(OutPort out) {
        out.startLogicalBlock("(Block", ")", 2);
        if (this.label != null) {
            out.print(' ');
            out.print(this.label.getName());
        }
        out.writeSpaceLinear();
        this.body.print(out);
        if (this.exitBody != null) {
            out.writeSpaceLinear();
            out.print("else ");
            this.exitBody.print(out);
        }
        out.endLogicalBlock(")");
    }
}
