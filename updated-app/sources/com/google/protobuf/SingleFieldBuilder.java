package com.google.protobuf;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.GeneratedMessage.Builder;
import com.google.protobuf.MessageOrBuilder;

public class SingleFieldBuilder<MType extends GeneratedMessage, BType extends GeneratedMessage.Builder, IType extends MessageOrBuilder> implements GeneratedMessage.BuilderParent {
    private BType builder;
    private boolean isClean;
    private MType message;
    private GeneratedMessage.BuilderParent parent;

    public SingleFieldBuilder(MType message2, GeneratedMessage.BuilderParent parent2, boolean isClean2) {
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
            this.message = (GeneratedMessage) this.builder.buildPartial();
        }
        return this.message;
    }

    public MType build() {
        this.isClean = true;
        return getMessage();
    }

    public BType getBuilder() {
        if (this.builder == null) {
            this.builder = (GeneratedMessage.Builder) this.message.newBuilderForType((GeneratedMessage.BuilderParent) this);
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

    public SingleFieldBuilder<MType, BType, IType> setMessage(MType message2) {
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

    public SingleFieldBuilder<MType, BType, IType> mergeFrom(MType value) {
        if (this.builder == null && this.message == this.message.getDefaultInstanceForType()) {
            this.message = value;
        } else {
            getBuilder().mergeFrom((Message) value);
        }
        onChanged();
        return this;
    }

    public SingleFieldBuilder<MType, BType, IType> clear() {
        MType mtype;
        if (this.message != null) {
            mtype = this.message.getDefaultInstanceForType();
        } else {
            mtype = this.builder.getDefaultInstanceForType();
        }
        MType mtype2 = (GeneratedMessage) mtype;
        GeneratedMessage generatedMessage = (GeneratedMessage) mtype2;
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
