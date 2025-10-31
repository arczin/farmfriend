package com.google.protobuf;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessageV3;
import java.io.IOException;
import java.io.InputStream;

public final class FieldMask extends GeneratedMessageV3 implements FieldMaskOrBuilder {
    private static final FieldMask DEFAULT_INSTANCE = new FieldMask();
    /* access modifiers changed from: private */
    public static final Parser<FieldMask> PARSER = new AbstractParser<FieldMask>() {
        public FieldMask parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new FieldMask(input, extensionRegistry);
        }
    };
    public static final int PATHS_FIELD_NUMBER = 1;
    private static final long serialVersionUID = 0;
    private byte memoizedIsInitialized;
    /* access modifiers changed from: private */
    public LazyStringList paths_;

    private FieldMask(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }

    private FieldMask() {
        this.memoizedIsInitialized = -1;
        this.paths_ = LazyStringArrayList.EMPTY;
    }

    public final UnknownFieldSet getUnknownFields() {
        return UnknownFieldSet.getDefaultInstance();
    }

    private FieldMask(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        this();
        int mutable_bitField0_ = 0;
        boolean done = false;
        while (!done) {
            try {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        done = true;
                        break;
                    case 10:
                        String s = input.readStringRequireUtf8();
                        if ((mutable_bitField0_ & 1) != 1) {
                            this.paths_ = new LazyStringArrayList();
                            mutable_bitField0_ |= 1;
                        }
                        this.paths_.add(s);
                        break;
                    default:
                        if (input.skipField(tag)) {
                            break;
                        } else {
                            done = true;
                            break;
                        }
                }
            } catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (IOException e2) {
                throw new InvalidProtocolBufferException(e2).setUnfinishedMessage(this);
            } catch (Throwable th) {
                if ((mutable_bitField0_ & 1) == 1) {
                    this.paths_ = this.paths_.getUnmodifiableView();
                }
                makeExtensionsImmutable();
                throw th;
            }
        }
        if ((mutable_bitField0_ & 1) == 1) {
            this.paths_ = this.paths_.getUnmodifiableView();
        }
        makeExtensionsImmutable();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return FieldMaskProto.internal_static_google_protobuf_FieldMask_descriptor;
    }

    /* access modifiers changed from: protected */
    public GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return FieldMaskProto.internal_static_google_protobuf_FieldMask_fieldAccessorTable.ensureFieldAccessorsInitialized(FieldMask.class, Builder.class);
    }

    public ProtocolStringList getPathsList() {
        return this.paths_;
    }

    public int getPathsCount() {
        return this.paths_.size();
    }

    public String getPaths(int index) {
        return (String) this.paths_.get(index);
    }

    public ByteString getPathsBytes(int index) {
        return this.paths_.getByteString(index);
    }

    public final boolean isInitialized() {
        byte isInitialized = this.memoizedIsInitialized;
        if (isInitialized == 1) {
            return true;
        }
        if (isInitialized == 0) {
            return false;
        }
        this.memoizedIsInitialized = 1;
        return true;
    }

    public void writeTo(CodedOutputStream output) throws IOException {
        for (int i = 0; i < this.paths_.size(); i++) {
            GeneratedMessageV3.writeString(output, 1, this.paths_.getRaw(i));
        }
    }

    public int getSerializedSize() {
        int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        int dataSize = 0;
        for (int i = 0; i < this.paths_.size(); i++) {
            dataSize += computeStringSizeNoTag(this.paths_.getRaw(i));
        }
        int size2 = 0 + dataSize + (getPathsList().size() * 1);
        this.memoizedSize = size2;
        return size2;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FieldMask)) {
            return super.equals(obj);
        }
        FieldMask other = (FieldMask) obj;
        if (1 == 0 || !getPathsList().equals(other.getPathsList())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        int hash = (41 * 19) + getDescriptorForType().hashCode();
        if (getPathsCount() > 0) {
            hash = (((hash * 37) + 1) * 53) + getPathsList().hashCode();
        }
        int hash2 = (hash * 29) + this.unknownFields.hashCode();
        this.memoizedHashCode = hash2;
        return hash2;
    }

    public static FieldMask parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static FieldMask parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static FieldMask parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static FieldMask parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static FieldMask parseFrom(InputStream input) throws IOException {
        return (FieldMask) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static FieldMask parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (FieldMask) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static FieldMask parseDelimitedFrom(InputStream input) throws IOException {
        return (FieldMask) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static FieldMask parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (FieldMask) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static FieldMask parseFrom(CodedInputStream input) throws IOException {
        return (FieldMask) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static FieldMask parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (FieldMask) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(FieldMask prototype) {
        return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
        if (this == DEFAULT_INSTANCE) {
            return new Builder();
        }
        return new Builder().mergeFrom(this);
    }

    /* access modifiers changed from: protected */
    public Builder newBuilderForType(GeneratedMessageV3.BuilderParent parent) {
        return new Builder(parent);
    }

    public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements FieldMaskOrBuilder {
        private int bitField0_;
        private LazyStringList paths_;

        public static final Descriptors.Descriptor getDescriptor() {
            return FieldMaskProto.internal_static_google_protobuf_FieldMask_descriptor;
        }

        /* access modifiers changed from: protected */
        public GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return FieldMaskProto.internal_static_google_protobuf_FieldMask_fieldAccessorTable.ensureFieldAccessorsInitialized(FieldMask.class, Builder.class);
        }

        private Builder() {
            this.paths_ = LazyStringArrayList.EMPTY;
            maybeForceBuilderInitialization();
        }

        private Builder(GeneratedMessageV3.BuilderParent parent) {
            super(parent);
            this.paths_ = LazyStringArrayList.EMPTY;
            maybeForceBuilderInitialization();
        }

        private void maybeForceBuilderInitialization() {
            boolean z = GeneratedMessageV3.alwaysUseFieldBuilders;
        }

        public Builder clear() {
            super.clear();
            this.paths_ = LazyStringArrayList.EMPTY;
            this.bitField0_ &= -2;
            return this;
        }

        public Descriptors.Descriptor getDescriptorForType() {
            return FieldMaskProto.internal_static_google_protobuf_FieldMask_descriptor;
        }

        public FieldMask getDefaultInstanceForType() {
            return FieldMask.getDefaultInstance();
        }

        public FieldMask build() {
            FieldMask result = buildPartial();
            if (result.isInitialized()) {
                return result;
            }
            throw newUninitializedMessageException(result);
        }

        public FieldMask buildPartial() {
            FieldMask result = new FieldMask((GeneratedMessageV3.Builder) this);
            int i = this.bitField0_;
            if ((this.bitField0_ & 1) == 1) {
                this.paths_ = this.paths_.getUnmodifiableView();
                this.bitField0_ &= -2;
            }
            LazyStringList unused = result.paths_ = this.paths_;
            onBuilt();
            return result;
        }

        public Builder clone() {
            return (Builder) super.clone();
        }

        public Builder setField(Descriptors.FieldDescriptor field, Object value) {
            return (Builder) super.setField(field, value);
        }

        public Builder clearField(Descriptors.FieldDescriptor field) {
            return (Builder) super.clearField(field);
        }

        public Builder clearOneof(Descriptors.OneofDescriptor oneof) {
            return (Builder) super.clearOneof(oneof);
        }

        public Builder setRepeatedField(Descriptors.FieldDescriptor field, int index, Object value) {
            return (Builder) super.setRepeatedField(field, index, value);
        }

        public Builder addRepeatedField(Descriptors.FieldDescriptor field, Object value) {
            return (Builder) super.addRepeatedField(field, value);
        }

        public Builder mergeFrom(Message other) {
            if (other instanceof FieldMask) {
                return mergeFrom((FieldMask) other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(FieldMask other) {
            if (other == FieldMask.getDefaultInstance()) {
                return this;
            }
            if (!other.paths_.isEmpty()) {
                if (this.paths_.isEmpty()) {
                    this.paths_ = other.paths_;
                    this.bitField0_ &= -2;
                } else {
                    ensurePathsIsMutable();
                    this.paths_.addAll(other.paths_);
                }
                onChanged();
            }
            onChanged();
            return this;
        }

        public final boolean isInitialized() {
            return true;
        }

        public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            try {
                FieldMask parsedMessage = (FieldMask) FieldMask.PARSER.parsePartialFrom(input, extensionRegistry);
                if (parsedMessage != null) {
                    mergeFrom(parsedMessage);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                FieldMask parsedMessage2 = (FieldMask) e.getUnfinishedMessage();
                throw e.unwrapIOException();
            } catch (Throwable th) {
                if (0 != 0) {
                    mergeFrom((FieldMask) null);
                }
                throw th;
            }
        }

        private void ensurePathsIsMutable() {
            if ((this.bitField0_ & 1) != 1) {
                this.paths_ = new LazyStringArrayList(this.paths_);
                this.bitField0_ |= 1;
            }
        }

        public ProtocolStringList getPathsList() {
            return this.paths_.getUnmodifiableView();
        }

        public int getPathsCount() {
            return this.paths_.size();
        }

        public String getPaths(int index) {
            return (String) this.paths_.get(index);
        }

        public ByteString getPathsBytes(int index) {
            return this.paths_.getByteString(index);
        }

        public Builder setPaths(int index, String value) {
            if (value != null) {
                ensurePathsIsMutable();
                this.paths_.set(index, value);
                onChanged();
                return this;
            }
            throw new NullPointerException();
        }

        public Builder addPaths(String value) {
            if (value != null) {
                ensurePathsIsMutable();
                this.paths_.add(value);
                onChanged();
                return this;
            }
            throw new NullPointerException();
        }

        public Builder addAllPaths(Iterable<String> values) {
            ensurePathsIsMutable();
            AbstractMessageLite.Builder.addAll(values, this.paths_);
            onChanged();
            return this;
        }

        public Builder clearPaths() {
            this.paths_ = LazyStringArrayList.EMPTY;
            this.bitField0_ &= -2;
            onChanged();
            return this;
        }

        public Builder addPathsBytes(ByteString value) {
            if (value != null) {
                AbstractMessageLite.checkByteStringIsUtf8(value);
                ensurePathsIsMutable();
                this.paths_.add(value);
                onChanged();
                return this;
            }
            throw new NullPointerException();
        }

        public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
            return this;
        }

        public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
            return this;
        }
    }

    public static FieldMask getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<FieldMask> parser() {
        return PARSER;
    }

    public Parser<FieldMask> getParserForType() {
        return PARSER;
    }

    public FieldMask getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }
}
