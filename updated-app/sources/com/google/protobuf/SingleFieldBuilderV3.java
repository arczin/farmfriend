package com.google.protobuf;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.AbstractMessage.Builder;
import com.google.protobuf.MessageOrBuilder;

public class SingleFieldBuilderV3<MType extends AbstractMessage, BType extends AbstractMessage.Builder, IType extends MessageOrBuilder> implements AbstractMessage.BuilderParent {
    private BType builder;
    private boolean isClean;
    private MType message;
    private AbstractMessage.BuilderParent parent;

    public SingleFieldBuilderV3(MType message2, AbstractMessage.BuilderParent parent2, boolean isClean2) {
        if (message2 != null) {
            this.message = message2;
            this.parent = parent2;
            this.isClean = isClean2;
            return;
        }
        throw new NullPointerException();
    }

    public void dispose() {
        this.parent = null;
    }

    public MType getMessage() {
        if (this.message == null) {
            this.message = (AbstractMessage) this.builder.buildPartial();
        }
        return this.message;
    }

    public MType build() {
        this.isClean = true;
        return getMessage();
    }

    public BType getBuilder() {
        if (this.builder == null) {
            this.builder = (AbstractMessage.Builder) this.message.newBuilderForType(this);
            this.builder.mergeFrom((Message) this.message);
            this.builder.markClean();
        }
        return this.builder;
    }

    public IType getMessageOrBuilder() {
        if (this.builder != null) {
            return this.builder;
        }
        return this.message;
    }

    public SingleFieldBuilderV3<MType, BType, IType> setMessage(MType message2) {
        if (message2 != null) {
            this.message = message2;
            if (this.builder != null) {
                this.builder.dispose();
                this.builder = null;
            }
            onChanged();
            return this;
        }
        throw new NullPointerException();
    }

    public SingleFieldBuilderV3<MType, BType, IType> mergeFrom(MType value) {
        if (this.builder == null && this.message == this.message.getDefaultInstanceForType()) {
            this.message = value;
        } else {
            getBuilder().mergeFrom((Message) value);
        }
        onChanged();
        return this;
    }

    public SingleFieldBuilderV3<MType, BType, IType> clear() {
        MType mtype;
        if (this.message != null) {
            mtype = this.message.getDefaultInstanceForType();
        } else {
            mtype = this.builder.getDefaultInstanceForType();
        }
        MType mtype2 = (AbstractMessage) mtype;
        AbstractMessage abstractMessage = (AbstractMessage) mtype2;
        this.message = mtype2;
        if (this.builder != null) {
            this.builder.dispose();
            this.builder = null;
        }
        onChanged();
        return this;
    }

    private void onChanged() {
        if (this.builder != null) {
            this.message = null;
        }
        if (this.isClean && this.parent != null) {
            this.parent.markDirty();
            this.isClean = false;
        }
    }

    public void markDirty() {
        onChanged();
    }
}
