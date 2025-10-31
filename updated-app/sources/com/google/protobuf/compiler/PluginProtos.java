package com.google.protobuf.compiler;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.LazyStringArrayList;
import com.google.protobuf.LazyStringList;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtocolStringList;
import com.google.protobuf.RepeatedFieldBuilderV3;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class PluginProtos {
    /* access modifiers changed from: private */
    public static Descriptors.FileDescriptor descriptor;
    /* access modifiers changed from: private */
    public static final Descriptors.Descriptor internal_static_google_protobuf_compiler_CodeGeneratorRequest_descriptor = getDescriptor().getMessageTypes().get(0);
    /* access modifiers changed from: private */
    public static final GeneratedMessageV3.FieldAccessorTable internal_static_google_protobuf_compiler_CodeGeneratorRequest_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_protobuf_compiler_CodeGeneratorRequest_descriptor, new String[]{"FileToGenerate", "Parameter", "ProtoFile"});
    /* access modifiers changed from: private */
    public static final Descriptors.Descriptor internal_static_google_protobuf_compiler_CodeGeneratorResponse_File_descriptor = internal_static_google_protobuf_compiler_CodeGeneratorResponse_descriptor.getNestedTypes().get(0);
    /* access modifiers changed from: private */
    public static final GeneratedMessageV3.FieldAccessorTable internal_static_google_protobuf_compiler_CodeGeneratorResponse_File_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_protobuf_compiler_CodeGeneratorResponse_File_descriptor, new String[]{"Name", "InsertionPoint", "Content"});
    /* access modifiers changed from: private */
    public static final Descriptors.Descriptor internal_static_google_protobuf_compiler_CodeGeneratorResponse_descriptor = getDescriptor().getMessageTypes().get(1);
    /* access modifiers changed from: private */
    public static final GeneratedMessageV3.FieldAccessorTable internal_static_google_protobuf_compiler_CodeGeneratorResponse_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_protobuf_compiler_CodeGeneratorResponse_descriptor, new String[]{"Error", "File"});

    public interface CodeGeneratorRequestOrBuilder extends MessageOrBuilder {
        String getFileToGenerate(int i);

        ByteString getFileToGenerateBytes(int i);

        int getFileToGenerateCount();

        List<String> getFileToGenerateList();

        String getParameter();

        ByteString getParameterBytes();

        DescriptorProtos.FileDescriptorProto getProtoFile(int i);

        int getProtoFileCount();

        List<DescriptorProtos.FileDescriptorProto> getProtoFileList();

        DescriptorProtos.FileDescriptorProtoOrBuilder getProtoFileOrBuilder(int i);

        List<? extends DescriptorProtos.FileDescriptorProtoOrBuilder> getProtoFileOrBuilderList();

        boolean hasParameter();
    }

    public interface CodeGeneratorResponseOrBuilder extends MessageOrBuilder {
        String getError();

        ByteString getErrorBytes();

        CodeGeneratorResponse.File getFile(int i);

        int getFileCount();

        List<CodeGeneratorResponse.File> getFileList();

        CodeGeneratorResponse.FileOrBuilder getFileOrBuilder(int i);

        List<? extends CodeGeneratorResponse.FileOrBuilder> getFileOrBuilderList();

        boolean hasError();
    }

    private PluginProtos() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        registerAllExtensions((ExtensionRegistryLite) registry);
    }

    public static final class CodeGeneratorRequest extends GeneratedMessageV3 implements CodeGeneratorRequestOrBuilder {
        private static final CodeGeneratorRequest DEFAULT_INSTANCE = new CodeGeneratorRequest();
        public static final int FILE_TO_GENERATE_FIELD_NUMBER = 1;
        public static final int PARAMETER_FIELD_NUMBER = 2;
        @Deprecated
        public static final Parser<CodeGeneratorRequest> PARSER = new AbstractParser<CodeGeneratorRequest>() {
            public CodeGeneratorRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new CodeGeneratorRequest(input, extensionRegistry);
            }
        };
        public static final int PROTO_FILE_FIELD_NUMBER = 15;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public LazyStringList fileToGenerate_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public volatile Object parameter_;
        /* access modifiers changed from: private */
        public List<DescriptorProtos.FileDescriptorProto> protoFile_;

        private CodeGeneratorRequest(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private CodeGeneratorRequest() {
            this.memoizedIsInitialized = -1;
            this.fileToGenerate_ = LazyStringArrayList.EMPTY;
            this.parameter_ = "";
            this.protoFile_ = Collections.emptyList();
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private CodeGeneratorRequest(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            int mutable_bitField0_ = 0;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 10:
                            ByteString bs = input.readBytes();
                            if ((mutable_bitField0_ & 1) != 1) {
                                this.fileToGenerate_ = new LazyStringArrayList();
                                mutable_bitField0_ |= 1;
                            }
                            this.fileToGenerate_.add(bs);
                            break;
                        case 18:
                            ByteString bs2 = input.readBytes();
                            this.bitField0_ |= 1;
                            this.parameter_ = bs2;
                            break;
                        case 122:
                            if ((mutable_bitField0_ & 4) != 4) {
                                this.protoFile_ = new ArrayList();
                                mutable_bitField0_ |= 4;
                            }
                            this.protoFile_.add(input.readMessage(DescriptorProtos.FileDescriptorProto.PARSER, extensionRegistry));
                            break;
                        default:
                            if (parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
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
                        this.fileToGenerate_ = this.fileToGenerate_.getUnmodifiableView();
                    }
                    if ((mutable_bitField0_ & 4) == 4) {
                        this.protoFile_ = Collections.unmodifiableList(this.protoFile_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((mutable_bitField0_ & 1) == 1) {
                this.fileToGenerate_ = this.fileToGenerate_.getUnmodifiableView();
            }
            if ((mutable_bitField0_ & 4) == 4) {
                this.protoFile_ = Collections.unmodifiableList(this.protoFile_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return PluginProtos.internal_static_google_protobuf_compiler_CodeGeneratorRequest_descriptor;
        }

        /* access modifiers changed from: protected */
        public GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return PluginProtos.internal_static_google_protobuf_compiler_CodeGeneratorRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(CodeGeneratorRequest.class, Builder.class);
        }

        public ProtocolStringList getFileToGenerateList() {
            return this.fileToGenerate_;
        }

        public int getFileToGenerateCount() {
            return this.fileToGenerate_.size();
        }

        public String getFileToGenerate(int index) {
            return (String) this.fileToGenerate_.get(index);
        }

        public ByteString getFileToGenerateBytes(int index) {
            return this.fileToGenerate_.getByteString(index);
        }

        public boolean hasParameter() {
            return (this.bitField0_ & 1) == 1;
        }

        public String getParameter() {
            Object ref = this.parameter_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.parameter_ = s;
            }
            return s;
        }

        public ByteString getParameterBytes() {
            Object ref = this.parameter_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.parameter_ = b;
            return b;
        }

        public List<DescriptorProtos.FileDescriptorProto> getProtoFileList() {
            return this.protoFile_;
        }

        public List<? extends DescriptorProtos.FileDescriptorProtoOrBuilder> getProtoFileOrBuilderList() {
            return this.protoFile_;
        }

        public int getProtoFileCount() {
            return this.protoFile_.size();
        }

        public DescriptorProtos.FileDescriptorProto getProtoFile(int index) {
            return this.protoFile_.get(index);
        }

        public DescriptorProtos.FileDescriptorProtoOrBuilder getProtoFileOrBuilder(int index) {
            return this.protoFile_.get(index);
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            for (int i = 0; i < getProtoFileCount(); i++) {
                if (!getProtoFile(i).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            for (int i = 0; i < this.fileToGenerate_.size(); i++) {
                GeneratedMessageV3.writeString(output, 1, this.fileToGenerate_.getRaw(i));
            }
            if ((this.bitField0_ & 1) == 1) {
                GeneratedMessageV3.writeString(output, 2, this.parameter_);
            }
            for (int i2 = 0; i2 < this.protoFile_.size(); i2++) {
                output.writeMessage(15, this.protoFile_.get(i2));
            }
            this.unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            int dataSize = 0;
            for (int i = 0; i < this.fileToGenerate_.size(); i++) {
                dataSize += computeStringSizeNoTag(this.fileToGenerate_.getRaw(i));
            }
            int size2 = 0 + dataSize + (getFileToGenerateList().size() * 1);
            if ((this.bitField0_ & 1) == 1) {
                size2 += GeneratedMessageV3.computeStringSize(2, this.parameter_);
            }
            for (int i2 = 0; i2 < this.protoFile_.size(); i2++) {
                size2 += CodedOutputStream.computeMessageSize(15, this.protoFile_.get(i2));
            }
            int size3 = size2 + this.unknownFields.getSerializedSize();
            this.memoizedSize = size3;
            return size3;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof CodeGeneratorRequest)) {
                return super.equals(obj);
            }
            CodeGeneratorRequest other = (CodeGeneratorRequest) obj;
            boolean result = (1 != 0 && getFileToGenerateList().equals(other.getFileToGenerateList())) && hasParameter() == other.hasParameter();
            if (hasParameter()) {
                result = result && getParameter().equals(other.getParameter());
            }
            if (!(result && getProtoFileList().equals(other.getProtoFileList())) || !this.unknownFields.equals(other.unknownFields)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = (41 * 19) + getDescriptorForType().hashCode();
            if (getFileToGenerateCount() > 0) {
                hash = (((hash * 37) + 1) * 53) + getFileToGenerateList().hashCode();
            }
            if (hasParameter() != 0) {
                hash = (((hash * 37) + 2) * 53) + getParameter().hashCode();
            }
            if (getProtoFileCount() > 0) {
                hash = (((hash * 37) + 15) * 53) + getProtoFileList().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static CodeGeneratorRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static CodeGeneratorRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static CodeGeneratorRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static CodeGeneratorRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static CodeGeneratorRequest parseFrom(InputStream input) throws IOException {
            return (CodeGeneratorRequest) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static CodeGeneratorRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CodeGeneratorRequest) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static CodeGeneratorRequest parseDelimitedFrom(InputStream input) throws IOException {
            return (CodeGeneratorRequest) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static CodeGeneratorRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CodeGeneratorRequest) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static CodeGeneratorRequest parseFrom(CodedInputStream input) throws IOException {
            return (CodeGeneratorRequest) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static CodeGeneratorRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CodeGeneratorRequest) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(CodeGeneratorRequest prototype) {
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

        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements CodeGeneratorRequestOrBuilder {
            private int bitField0_;
            private LazyStringList fileToGenerate_;
            private Object parameter_;
            private RepeatedFieldBuilderV3<DescriptorProtos.FileDescriptorProto, DescriptorProtos.FileDescriptorProto.Builder, DescriptorProtos.FileDescriptorProtoOrBuilder> protoFileBuilder_;
            private List<DescriptorProtos.FileDescriptorProto> protoFile_;

            public static final Descriptors.Descriptor getDescriptor() {
                return PluginProtos.internal_static_google_protobuf_compiler_CodeGeneratorRequest_descriptor;
            }

            /* access modifiers changed from: protected */
            public GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return PluginProtos.internal_static_google_protobuf_compiler_CodeGeneratorRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(CodeGeneratorRequest.class, Builder.class);
            }

            private Builder() {
                this.fileToGenerate_ = LazyStringArrayList.EMPTY;
                this.parameter_ = "";
                this.protoFile_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.fileToGenerate_ = LazyStringArrayList.EMPTY;
                this.parameter_ = "";
                this.protoFile_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (CodeGeneratorRequest.alwaysUseFieldBuilders) {
                    getProtoFileFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                this.fileToGenerate_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= -2;
                this.parameter_ = "";
                this.bitField0_ &= -3;
                if (this.protoFileBuilder_ == null) {
                    this.protoFile_ = Collections.emptyList();
                    this.bitField0_ &= -5;
                } else {
                    this.protoFileBuilder_.clear();
                }
                return this;
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return PluginProtos.internal_static_google_protobuf_compiler_CodeGeneratorRequest_descriptor;
            }

            public CodeGeneratorRequest getDefaultInstanceForType() {
                return CodeGeneratorRequest.getDefaultInstance();
            }

            public CodeGeneratorRequest build() {
                CodeGeneratorRequest result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public CodeGeneratorRequest buildPartial() {
                CodeGeneratorRequest result = new CodeGeneratorRequest((GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    this.fileToGenerate_ = this.fileToGenerate_.getUnmodifiableView();
                    this.bitField0_ &= -2;
                }
                LazyStringList unused = result.fileToGenerate_ = this.fileToGenerate_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ = 0 | 1;
                }
                Object unused2 = result.parameter_ = this.parameter_;
                if (this.protoFileBuilder_ == null) {
                    if ((this.bitField0_ & 4) == 4) {
                        this.protoFile_ = Collections.unmodifiableList(this.protoFile_);
                        this.bitField0_ &= -5;
                    }
                    List unused3 = result.protoFile_ = this.protoFile_;
                } else {
                    List unused4 = result.protoFile_ = this.protoFileBuilder_.build();
                }
                int unused5 = result.bitField0_ = to_bitField0_;
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
                if (other instanceof CodeGeneratorRequest) {
                    return mergeFrom((CodeGeneratorRequest) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(CodeGeneratorRequest other) {
                if (other == CodeGeneratorRequest.getDefaultInstance()) {
                    return this;
                }
                if (!other.fileToGenerate_.isEmpty()) {
                    if (this.fileToGenerate_.isEmpty()) {
                        this.fileToGenerate_ = other.fileToGenerate_;
                        this.bitField0_ &= -2;
                    } else {
                        ensureFileToGenerateIsMutable();
                        this.fileToGenerate_.addAll(other.fileToGenerate_);
                    }
                    onChanged();
                }
                if (other.hasParameter()) {
                    this.bitField0_ |= 2;
                    this.parameter_ = other.parameter_;
                    onChanged();
                }
                if (this.protoFileBuilder_ == null) {
                    if (!other.protoFile_.isEmpty()) {
                        if (this.protoFile_.isEmpty()) {
                            this.protoFile_ = other.protoFile_;
                            this.bitField0_ &= -5;
                        } else {
                            ensureProtoFileIsMutable();
                            this.protoFile_.addAll(other.protoFile_);
                        }
                        onChanged();
                    }
                } else if (!other.protoFile_.isEmpty()) {
                    if (this.protoFileBuilder_.isEmpty()) {
                        this.protoFileBuilder_.dispose();
                        RepeatedFieldBuilderV3<DescriptorProtos.FileDescriptorProto, DescriptorProtos.FileDescriptorProto.Builder, DescriptorProtos.FileDescriptorProtoOrBuilder> repeatedFieldBuilderV3 = null;
                        this.protoFileBuilder_ = null;
                        this.protoFile_ = other.protoFile_;
                        this.bitField0_ &= -5;
                        if (CodeGeneratorRequest.alwaysUseFieldBuilders) {
                            repeatedFieldBuilderV3 = getProtoFileFieldBuilder();
                        }
                        this.protoFileBuilder_ = repeatedFieldBuilderV3;
                    } else {
                        this.protoFileBuilder_.addAllMessages(other.protoFile_);
                    }
                }
                mergeUnknownFields(other.unknownFields);
                onChanged();
                return this;
            }

            public final boolean isInitialized() {
                for (int i = 0; i < getProtoFileCount(); i++) {
                    if (!getProtoFile(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                try {
                    CodeGeneratorRequest parsedMessage = CodeGeneratorRequest.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    CodeGeneratorRequest parsedMessage2 = (CodeGeneratorRequest) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (0 != 0) {
                        mergeFrom((CodeGeneratorRequest) null);
                    }
                    throw th;
                }
            }

            private void ensureFileToGenerateIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.fileToGenerate_ = new LazyStringArrayList(this.fileToGenerate_);
                    this.bitField0_ |= 1;
                }
            }

            public ProtocolStringList getFileToGenerateList() {
                return this.fileToGenerate_.getUnmodifiableView();
            }

            public int getFileToGenerateCount() {
                return this.fileToGenerate_.size();
            }

            public String getFileToGenerate(int index) {
                return (String) this.fileToGenerate_.get(index);
            }

            public ByteString getFileToGenerateBytes(int index) {
                return this.fileToGenerate_.getByteString(index);
            }

            public Builder setFileToGenerate(int index, String value) {
                if (value != null) {
                    ensureFileToGenerateIsMutable();
                    this.fileToGenerate_.set(index, value);
                    onChanged();
                    return this;
                }
                throw new NullPointerException();
            }

            public Builder addFileToGenerate(String value) {
                if (value != null) {
                    ensureFileToGenerateIsMutable();
                    this.fileToGenerate_.add(value);
                    onChanged();
                    return this;
                }
                throw new NullPointerException();
            }

            public Builder addAllFileToGenerate(Iterable<String> values) {
                ensureFileToGenerateIsMutable();
                AbstractMessageLite.Builder.addAll(values, this.fileToGenerate_);
                onChanged();
                return this;
            }

            public Builder clearFileToGenerate() {
                this.fileToGenerate_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= -2;
                onChanged();
                return this;
            }

            public Builder addFileToGenerateBytes(ByteString value) {
                if (value != null) {
                    ensureFileToGenerateIsMutable();
                    this.fileToGenerate_.add(value);
                    onChanged();
                    return this;
                }
                throw new NullPointerException();
            }

            public boolean hasParameter() {
                return (this.bitField0_ & 2) == 2;
            }

            public String getParameter() {
                Object ref = this.parameter_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.parameter_ = s;
                }
                return s;
            }

            public ByteString getParameterBytes() {
                Object ref = this.parameter_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.parameter_ = b;
                return b;
            }

            public Builder setParameter(String value) {
                if (value != null) {
                    this.bitField0_ |= 2;
                    this.parameter_ = value;
                    onChanged();
                    return this;
                }
                throw new NullPointerException();
            }

            public Builder clearParameter() {
                this.bitField0_ &= -3;
                this.parameter_ = CodeGeneratorRequest.getDefaultInstance().getParameter();
                onChanged();
                return this;
            }

            public Builder setParameterBytes(ByteString value) {
                if (value != null) {
                    this.bitField0_ |= 2;
                    this.parameter_ = value;
                    onChanged();
                    return this;
                }
                throw new NullPointerException();
            }

            private void ensureProtoFileIsMutable() {
                if ((this.bitField0_ & 4) != 4) {
                    this.protoFile_ = new ArrayList(this.protoFile_);
                    this.bitField0_ |= 4;
                }
            }

            public List<DescriptorProtos.FileDescriptorProto> getProtoFileList() {
                if (this.protoFileBuilder_ == null) {
                    return Collections.unmodifiableList(this.protoFile_);
                }
                return this.protoFileBuilder_.getMessageList();
            }

            public int getProtoFileCount() {
                if (this.protoFileBuilder_ == null) {
                    return this.protoFile_.size();
                }
                return this.protoFileBuilder_.getCount();
            }

            public DescriptorProtos.FileDescriptorProto getProtoFile(int index) {
                if (this.protoFileBuilder_ == null) {
                    return this.protoFile_.get(index);
                }
                return this.protoFileBuilder_.getMessage(index);
            }

            public Builder setProtoFile(int index, DescriptorProtos.FileDescriptorProto value) {
                if (this.protoFileBuilder_ != null) {
                    this.protoFileBuilder_.setMessage(index, value);
                } else if (value != null) {
                    ensureProtoFileIsMutable();
                    this.protoFile_.set(index, value);
                    onChanged();
                } else {
                    throw new NullPointerException();
                }
                return this;
            }

            public Builder setProtoFile(int index, DescriptorProtos.FileDescriptorProto.Builder builderForValue) {
                if (this.protoFileBuilder_ == null) {
                    ensureProtoFileIsMutable();
                    this.protoFile_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.protoFileBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addProtoFile(DescriptorProtos.FileDescriptorProto value) {
                if (this.protoFileBuilder_ != null) {
                    this.protoFileBuilder_.addMessage(value);
                } else if (value != null) {
                    ensureProtoFileIsMutable();
                    this.protoFile_.add(value);
                    onChanged();
                } else {
                    throw new NullPointerException();
                }
                return this;
            }

            public Builder addProtoFile(int index, DescriptorProtos.FileDescriptorProto value) {
                if (this.protoFileBuilder_ != null) {
                    this.protoFileBuilder_.addMessage(index, value);
                } else if (value != null) {
                    ensureProtoFileIsMutable();
                    this.protoFile_.add(index, value);
                    onChanged();
                } else {
                    throw new NullPointerException();
                }
                return this;
            }

            public Builder addProtoFile(DescriptorProtos.FileDescriptorProto.Builder builderForValue) {
                if (this.protoFileBuilder_ == null) {
                    ensureProtoFileIsMutable();
                    this.protoFile_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.protoFileBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addProtoFile(int index, DescriptorProtos.FileDescriptorProto.Builder builderForValue) {
                if (this.protoFileBuilder_ == null) {
                    ensureProtoFileIsMutable();
                    this.protoFile_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.protoFileBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllProtoFile(Iterable<? extends DescriptorProtos.FileDescriptorProto> values) {
                if (this.protoFileBuilder_ == null) {
                    ensureProtoFileIsMutable();
                    AbstractMessageLite.Builder.addAll(values, this.protoFile_);
                    onChanged();
                } else {
                    this.protoFileBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearProtoFile() {
                if (this.protoFileBuilder_ == null) {
                    this.protoFile_ = Collections.emptyList();
                    this.bitField0_ &= -5;
                    onChanged();
                } else {
                    this.protoFileBuilder_.clear();
                }
                return this;
            }

            public Builder removeProtoFile(int index) {
                if (this.protoFileBuilder_ == null) {
                    ensureProtoFileIsMutable();
                    this.protoFile_.remove(index);
                    onChanged();
                } else {
                    this.protoFileBuilder_.remove(index);
                }
                return this;
            }

            public DescriptorProtos.FileDescriptorProto.Builder getProtoFileBuilder(int index) {
                return getProtoFileFieldBuilder().getBuilder(index);
            }

            public DescriptorProtos.FileDescriptorProtoOrBuilder getProtoFileOrBuilder(int index) {
                if (this.protoFileBuilder_ == null) {
                    return this.protoFile_.get(index);
                }
                return this.protoFileBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends DescriptorProtos.FileDescriptorProtoOrBuilder> getProtoFileOrBuilderList() {
                if (this.protoFileBuilder_ != null) {
                    return this.protoFileBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.protoFile_);
            }

            public DescriptorProtos.FileDescriptorProto.Builder addProtoFileBuilder() {
                return getProtoFileFieldBuilder().addBuilder(DescriptorProtos.FileDescriptorProto.getDefaultInstance());
            }

            public DescriptorProtos.FileDescriptorProto.Builder addProtoFileBuilder(int index) {
                return getProtoFileFieldBuilder().addBuilder(index, DescriptorProtos.FileDescriptorProto.getDefaultInstance());
            }

            public List<DescriptorProtos.FileDescriptorProto.Builder> getProtoFileBuilderList() {
                return getProtoFileFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<DescriptorProtos.FileDescriptorProto, DescriptorProtos.FileDescriptorProto.Builder, DescriptorProtos.FileDescriptorProtoOrBuilder> getProtoFileFieldBuilder() {
                if (this.protoFileBuilder_ == null) {
                    this.protoFileBuilder_ = new RepeatedFieldBuilderV3<>(this.protoFile_, (this.bitField0_ & 4) == 4, getParentForChildren(), isClean());
                    this.protoFile_ = null;
                }
                return this.protoFileBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        public static CodeGeneratorRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<CodeGeneratorRequest> parser() {
            return PARSER;
        }

        public Parser<CodeGeneratorRequest> getParserForType() {
            return PARSER;
        }

        public CodeGeneratorRequest getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class CodeGeneratorResponse extends GeneratedMessageV3 implements CodeGeneratorResponseOrBuilder {
        private static final CodeGeneratorResponse DEFAULT_INSTANCE = new CodeGeneratorResponse();
        public static final int ERROR_FIELD_NUMBER = 1;
        public static final int FILE_FIELD_NUMBER = 15;
        @Deprecated
        public static final Parser<CodeGeneratorResponse> PARSER = new AbstractParser<CodeGeneratorResponse>() {
            public CodeGeneratorResponse parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new CodeGeneratorResponse(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public volatile Object error_;
        /* access modifiers changed from: private */
        public List<File> file_;
        private byte memoizedIsInitialized;

        public interface FileOrBuilder extends MessageOrBuilder {
            String getContent();

            ByteString getContentBytes();

            String getInsertionPoint();

            ByteString getInsertionPointBytes();

            String getName();

            ByteString getNameBytes();

            boolean hasContent();

            boolean hasInsertionPoint();

            boolean hasName();
        }

        private CodeGeneratorResponse(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private CodeGeneratorResponse() {
            this.memoizedIsInitialized = -1;
            this.error_ = "";
            this.file_ = Collections.emptyList();
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private CodeGeneratorResponse(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            int mutable_bitField0_ = 0;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 10:
                            ByteString bs = input.readBytes();
                            this.bitField0_ |= 1;
                            this.error_ = bs;
                            break;
                        case 122:
                            if ((mutable_bitField0_ & 2) != 2) {
                                this.file_ = new ArrayList();
                                mutable_bitField0_ |= 2;
                            }
                            this.file_.add(input.readMessage(File.PARSER, extensionRegistry));
                            break;
                        default:
                            if (parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
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
                    if ((mutable_bitField0_ & 2) == 2) {
                        this.file_ = Collections.unmodifiableList(this.file_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((mutable_bitField0_ & 2) == 2) {
                this.file_ = Collections.unmodifiableList(this.file_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return PluginProtos.internal_static_google_protobuf_compiler_CodeGeneratorResponse_descriptor;
        }

        /* access modifiers changed from: protected */
        public GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return PluginProtos.internal_static_google_protobuf_compiler_CodeGeneratorResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(CodeGeneratorResponse.class, Builder.class);
        }

        public static final class File extends GeneratedMessageV3 implements FileOrBuilder {
            public static final int CONTENT_FIELD_NUMBER = 15;
            private static final File DEFAULT_INSTANCE = new File();
            public static final int INSERTION_POINT_FIELD_NUMBER = 2;
            public static final int NAME_FIELD_NUMBER = 1;
            @Deprecated
            public static final Parser<File> PARSER = new AbstractParser<File>() {
                public File parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new File(input, extensionRegistry);
                }
            };
            private static final long serialVersionUID = 0;
            /* access modifiers changed from: private */
            public int bitField0_;
            /* access modifiers changed from: private */
            public volatile Object content_;
            /* access modifiers changed from: private */
            public volatile Object insertionPoint_;
            private byte memoizedIsInitialized;
            /* access modifiers changed from: private */
            public volatile Object name_;

            private File(GeneratedMessageV3.Builder<?> builder) {
                super(builder);
                this.memoizedIsInitialized = -1;
            }

            private File() {
                this.memoizedIsInitialized = -1;
                this.name_ = "";
                this.insertionPoint_ = "";
                this.content_ = "";
            }

            public final UnknownFieldSet getUnknownFields() {
                return this.unknownFields;
            }

            private File(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                this();
                UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
                boolean done = false;
                while (!done) {
                    try {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0:
                                done = true;
                                break;
                            case 10:
                                ByteString bs = input.readBytes();
                                this.bitField0_ |= 1;
                                this.name_ = bs;
                                break;
                            case 18:
                                ByteString bs2 = input.readBytes();
                                this.bitField0_ |= 2;
                                this.insertionPoint_ = bs2;
                                break;
                            case 122:
                                ByteString bs3 = input.readBytes();
                                this.bitField0_ |= 4;
                                this.content_ = bs3;
                                break;
                            default:
                                if (parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
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
                        this.unknownFields = unknownFields.build();
                        makeExtensionsImmutable();
                        throw th;
                    }
                }
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
            }

            public static final Descriptors.Descriptor getDescriptor() {
                return PluginProtos.internal_static_google_protobuf_compiler_CodeGeneratorResponse_File_descriptor;
            }

            /* access modifiers changed from: protected */
            public GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return PluginProtos.internal_static_google_protobuf_compiler_CodeGeneratorResponse_File_fieldAccessorTable.ensureFieldAccessorsInitialized(File.class, Builder.class);
            }

            public boolean hasName() {
                return (this.bitField0_ & 1) == 1;
            }

            public String getName() {
                Object ref = this.name_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.name_ = s;
                }
                return s;
            }

            public ByteString getNameBytes() {
                Object ref = this.name_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.name_ = b;
                return b;
            }

            public boolean hasInsertionPoint() {
                return (this.bitField0_ & 2) == 2;
            }

            public String getInsertionPoint() {
                Object ref = this.insertionPoint_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.insertionPoint_ = s;
                }
                return s;
            }

            public ByteString getInsertionPointBytes() {
                Object ref = this.insertionPoint_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.insertionPoint_ = b;
                return b;
            }

            public boolean hasContent() {
                return (this.bitField0_ & 4) == 4;
            }

            public String getContent() {
                Object ref = this.content_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.content_ = s;
                }
                return s;
            }

            public ByteString getContentBytes() {
                Object ref = this.content_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.content_ = b;
                return b;
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
                if ((this.bitField0_ & 1) == 1) {
                    GeneratedMessageV3.writeString(output, 1, this.name_);
                }
                if ((this.bitField0_ & 2) == 2) {
                    GeneratedMessageV3.writeString(output, 2, this.insertionPoint_);
                }
                if ((this.bitField0_ & 4) == 4) {
                    GeneratedMessageV3.writeString(output, 15, this.content_);
                }
                this.unknownFields.writeTo(output);
            }

            public int getSerializedSize() {
                int size = this.memoizedSize;
                if (size != -1) {
                    return size;
                }
                int size2 = 0;
                if ((this.bitField0_ & 1) == 1) {
                    size2 = 0 + GeneratedMessageV3.computeStringSize(1, this.name_);
                }
                if ((this.bitField0_ & 2) == 2) {
                    size2 += GeneratedMessageV3.computeStringSize(2, this.insertionPoint_);
                }
                if ((this.bitField0_ & 4) == 4) {
                    size2 += GeneratedMessageV3.computeStringSize(15, this.content_);
                }
                int size3 = size2 + this.unknownFields.getSerializedSize();
                this.memoizedSize = size3;
                return size3;
            }

            public boolean equals(Object obj) {
                if (obj == this) {
                    return true;
                }
                if (!(obj instanceof File)) {
                    return super.equals(obj);
                }
                File other = (File) obj;
                boolean result = 1 != 0 && hasName() == other.hasName();
                if (hasName()) {
                    result = result && getName().equals(other.getName());
                }
                boolean result2 = result && hasInsertionPoint() == other.hasInsertionPoint();
                if (hasInsertionPoint()) {
                    result2 = result2 && getInsertionPoint().equals(other.getInsertionPoint());
                }
                boolean result3 = result2 && hasContent() == other.hasContent();
                if (hasContent()) {
                    result3 = result3 && getContent().equals(other.getContent());
                }
                if (!result3 || !this.unknownFields.equals(other.unknownFields)) {
                    return false;
                }
                return true;
            }

            public int hashCode() {
                if (this.memoizedHashCode != 0) {
                    return this.memoizedHashCode;
                }
                int hash = (41 * 19) + getDescriptorForType().hashCode();
                if (hasName() != 0) {
                    hash = (((hash * 37) + 1) * 53) + getName().hashCode();
                }
                if (hasInsertionPoint() != 0) {
                    hash = (((hash * 37) + 2) * 53) + getInsertionPoint().hashCode();
                }
                if (hasContent() != 0) {
                    hash = (((hash * 37) + 15) * 53) + getContent().hashCode();
                }
                int hash2 = (hash * 29) + this.unknownFields.hashCode();
                this.memoizedHashCode = hash2;
                return hash2;
            }

            public static File parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data);
            }

            public static File parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data, extensionRegistry);
            }

            public static File parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data);
            }

            public static File parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data, extensionRegistry);
            }

            public static File parseFrom(InputStream input) throws IOException {
                return (File) GeneratedMessageV3.parseWithIOException(PARSER, input);
            }

            public static File parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (File) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
            }

            public static File parseDelimitedFrom(InputStream input) throws IOException {
                return (File) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
            }

            public static File parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (File) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
            }

            public static File parseFrom(CodedInputStream input) throws IOException {
                return (File) GeneratedMessageV3.parseWithIOException(PARSER, input);
            }

            public static File parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (File) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
            }

            public Builder newBuilderForType() {
                return newBuilder();
            }

            public static Builder newBuilder() {
                return DEFAULT_INSTANCE.toBuilder();
            }

            public static Builder newBuilder(File prototype) {
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

            public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements FileOrBuilder {
                private int bitField0_;
                private Object content_;
                private Object insertionPoint_;
                private Object name_;

                public static final Descriptors.Descriptor getDescriptor() {
                    return PluginProtos.internal_static_google_protobuf_compiler_CodeGeneratorResponse_File_descriptor;
                }

                /* access modifiers changed from: protected */
                public GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                    return PluginProtos.internal_static_google_protobuf_compiler_CodeGeneratorResponse_File_fieldAccessorTable.ensureFieldAccessorsInitialized(File.class, Builder.class);
                }

                private Builder() {
                    this.name_ = "";
                    this.insertionPoint_ = "";
                    this.content_ = "";
                    maybeForceBuilderInitialization();
                }

                private Builder(GeneratedMessageV3.BuilderParent parent) {
                    super(parent);
                    this.name_ = "";
                    this.insertionPoint_ = "";
                    this.content_ = "";
                    maybeForceBuilderInitialization();
                }

                private void maybeForceBuilderInitialization() {
                    boolean unused = File.alwaysUseFieldBuilders;
                }

                public Builder clear() {
                    super.clear();
                    this.name_ = "";
                    this.bitField0_ &= -2;
                    this.insertionPoint_ = "";
                    this.bitField0_ &= -3;
                    this.content_ = "";
                    this.bitField0_ &= -5;
                    return this;
                }

                public Descriptors.Descriptor getDescriptorForType() {
                    return PluginProtos.internal_static_google_protobuf_compiler_CodeGeneratorResponse_File_descriptor;
                }

                public File getDefaultInstanceForType() {
                    return File.getDefaultInstance();
                }

                public File build() {
                    File result = buildPartial();
                    if (result.isInitialized()) {
                        return result;
                    }
                    throw newUninitializedMessageException(result);
                }

                public File buildPartial() {
                    File result = new File((GeneratedMessageV3.Builder) this);
                    int from_bitField0_ = this.bitField0_;
                    int to_bitField0_ = 0;
                    if ((from_bitField0_ & 1) == 1) {
                        to_bitField0_ = 0 | 1;
                    }
                    Object unused = result.name_ = this.name_;
                    if ((from_bitField0_ & 2) == 2) {
                        to_bitField0_ |= 2;
                    }
                    Object unused2 = result.insertionPoint_ = this.insertionPoint_;
                    if ((from_bitField0_ & 4) == 4) {
                        to_bitField0_ |= 4;
                    }
                    Object unused3 = result.content_ = this.content_;
                    int unused4 = result.bitField0_ = to_bitField0_;
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
                    if (other instanceof File) {
                        return mergeFrom((File) other);
                    }
                    super.mergeFrom(other);
                    return this;
                }

                public Builder mergeFrom(File other) {
                    if (other == File.getDefaultInstance()) {
                        return this;
                    }
                    if (other.hasName()) {
                        this.bitField0_ |= 1;
                        this.name_ = other.name_;
                        onChanged();
                    }
                    if (other.hasInsertionPoint()) {
                        this.bitField0_ |= 2;
                        this.insertionPoint_ = other.insertionPoint_;
                        onChanged();
                    }
                    if (other.hasContent()) {
                        this.bitField0_ |= 4;
                        this.content_ = other.content_;
                        onChanged();
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                    return this;
                }

                public final boolean isInitialized() {
                    return true;
                }

                public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                    try {
                        File parsedMessage = File.PARSER.parsePartialFrom(input, extensionRegistry);
                        if (parsedMessage != null) {
                            mergeFrom(parsedMessage);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        File parsedMessage2 = (File) e.getUnfinishedMessage();
                        throw e.unwrapIOException();
                    } catch (Throwable th) {
                        if (0 != 0) {
                            mergeFrom((File) null);
                        }
                        throw th;
                    }
                }

                public boolean hasName() {
                    return (this.bitField0_ & 1) == 1;
                }

                public String getName() {
                    Object ref = this.name_;
                    if (ref instanceof String) {
                        return (String) ref;
                    }
                    ByteString bs = (ByteString) ref;
                    String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.name_ = s;
                    }
                    return s;
                }

                public ByteString getNameBytes() {
                    Object ref = this.name_;
                    if (!(ref instanceof String)) {
                        return (ByteString) ref;
                    }
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    this.name_ = b;
                    return b;
                }

                public Builder setName(String value) {
                    if (value != null) {
                        this.bitField0_ |= 1;
                        this.name_ = value;
                        onChanged();
                        return this;
                    }
                    throw new NullPointerException();
                }

                public Builder clearName() {
                    this.bitField0_ &= -2;
                    this.name_ = File.getDefaultInstance().getName();
                    onChanged();
                    return this;
                }

                public Builder setNameBytes(ByteString value) {
                    if (value != null) {
                        this.bitField0_ |= 1;
                        this.name_ = value;
                        onChanged();
                        return this;
                    }
                    throw new NullPointerException();
                }

                public boolean hasInsertionPoint() {
                    return (this.bitField0_ & 2) == 2;
                }

                public String getInsertionPoint() {
                    Object ref = this.insertionPoint_;
                    if (ref instanceof String) {
                        return (String) ref;
                    }
                    ByteString bs = (ByteString) ref;
                    String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.insertionPoint_ = s;
                    }
                    return s;
                }

                public ByteString getInsertionPointBytes() {
                    Object ref = this.insertionPoint_;
                    if (!(ref instanceof String)) {
                        return (ByteString) ref;
                    }
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    this.insertionPoint_ = b;
                    return b;
                }

                public Builder setInsertionPoint(String value) {
                    if (value != null) {
                        this.bitField0_ |= 2;
                        this.insertionPoint_ = value;
                        onChanged();
                        return this;
                    }
                    throw new NullPointerException();
                }

                public Builder clearInsertionPoint() {
                    this.bitField0_ &= -3;
                    this.insertionPoint_ = File.getDefaultInstance().getInsertionPoint();
                    onChanged();
                    return this;
                }

                public Builder setInsertionPointBytes(ByteString value) {
                    if (value != null) {
                        this.bitField0_ |= 2;
                        this.insertionPoint_ = value;
                        onChanged();
                        return this;
                    }
                    throw new NullPointerException();
                }

                public boolean hasContent() {
                    return (this.bitField0_ & 4) == 4;
                }

                public String getContent() {
                    Object ref = this.content_;
                    if (ref instanceof String) {
                        return (String) ref;
                    }
                    ByteString bs = (ByteString) ref;
                    String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.content_ = s;
                    }
                    return s;
                }

                public ByteString getContentBytes() {
                    Object ref = this.content_;
                    if (!(ref instanceof String)) {
                        return (ByteString) ref;
                    }
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    this.content_ = b;
                    return b;
                }

                public Builder setContent(String value) {
                    if (value != null) {
                        this.bitField0_ |= 4;
                        this.content_ = value;
                        onChanged();
                        return this;
                    }
                    throw new NullPointerException();
                }

                public Builder clearContent() {
                    this.bitField0_ &= -5;
                    this.content_ = File.getDefaultInstance().getContent();
                    onChanged();
                    return this;
                }

                public Builder setContentBytes(ByteString value) {
                    if (value != null) {
                        this.bitField0_ |= 4;
                        this.content_ = value;
                        onChanged();
                        return this;
                    }
                    throw new NullPointerException();
                }

                public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                    return (Builder) super.setUnknownFields(unknownFields);
                }

                public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                    return (Builder) super.mergeUnknownFields(unknownFields);
                }
            }

            public static File getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<File> parser() {
                return PARSER;
            }

            public Parser<File> getParserForType() {
                return PARSER;
            }

            public File getDefaultInstanceForType() {
                return DEFAULT_INSTANCE;
            }
        }

        public boolean hasError() {
            return (this.bitField0_ & 1) == 1;
        }

        public String getError() {
            Object ref = this.error_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.error_ = s;
            }
            return s;
        }

        public ByteString getErrorBytes() {
            Object ref = this.error_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.error_ = b;
            return b;
        }

        public List<File> getFileList() {
            return this.file_;
        }

        public List<? extends FileOrBuilder> getFileOrBuilderList() {
            return this.file_;
        }

        public int getFileCount() {
            return this.file_.size();
        }

        public File getFile(int index) {
            return this.file_.get(index);
        }

        public FileOrBuilder getFileOrBuilder(int index) {
            return this.file_.get(index);
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
            if ((this.bitField0_ & 1) == 1) {
                GeneratedMessageV3.writeString(output, 1, this.error_);
            }
            for (int i = 0; i < this.file_.size(); i++) {
                output.writeMessage(15, this.file_.get(i));
            }
            this.unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            int size2 = 0;
            if ((this.bitField0_ & 1) == 1) {
                size2 = 0 + GeneratedMessageV3.computeStringSize(1, this.error_);
            }
            for (int i = 0; i < this.file_.size(); i++) {
                size2 += CodedOutputStream.computeMessageSize(15, this.file_.get(i));
            }
            int size3 = size2 + this.unknownFields.getSerializedSize();
            this.memoizedSize = size3;
            return size3;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof CodeGeneratorResponse)) {
                return super.equals(obj);
            }
            CodeGeneratorResponse other = (CodeGeneratorResponse) obj;
            boolean result = 1 != 0 && hasError() == other.hasError();
            if (hasError()) {
                result = result && getError().equals(other.getError());
            }
            if (!(result && getFileList().equals(other.getFileList())) || !this.unknownFields.equals(other.unknownFields)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = (41 * 19) + getDescriptorForType().hashCode();
            if (hasError() != 0) {
                hash = (((hash * 37) + 1) * 53) + getError().hashCode();
            }
            if (getFileCount() > 0) {
                hash = (((hash * 37) + 15) * 53) + getFileList().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static CodeGeneratorResponse parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static CodeGeneratorResponse parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static CodeGeneratorResponse parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static CodeGeneratorResponse parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static CodeGeneratorResponse parseFrom(InputStream input) throws IOException {
            return (CodeGeneratorResponse) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static CodeGeneratorResponse parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CodeGeneratorResponse) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static CodeGeneratorResponse parseDelimitedFrom(InputStream input) throws IOException {
            return (CodeGeneratorResponse) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static CodeGeneratorResponse parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CodeGeneratorResponse) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static CodeGeneratorResponse parseFrom(CodedInputStream input) throws IOException {
            return (CodeGeneratorResponse) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static CodeGeneratorResponse parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CodeGeneratorResponse) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(CodeGeneratorResponse prototype) {
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

        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements CodeGeneratorResponseOrBuilder {
            private int bitField0_;
            private Object error_;
            private RepeatedFieldBuilderV3<File, File.Builder, FileOrBuilder> fileBuilder_;
            private List<File> file_;

            public static final Descriptors.Descriptor getDescriptor() {
                return PluginProtos.internal_static_google_protobuf_compiler_CodeGeneratorResponse_descriptor;
            }

            /* access modifiers changed from: protected */
            public GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return PluginProtos.internal_static_google_protobuf_compiler_CodeGeneratorResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(CodeGeneratorResponse.class, Builder.class);
            }

            private Builder() {
                this.error_ = "";
                this.file_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.error_ = "";
                this.file_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (CodeGeneratorResponse.alwaysUseFieldBuilders) {
                    getFileFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                this.error_ = "";
                this.bitField0_ &= -2;
                if (this.fileBuilder_ == null) {
                    this.file_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                } else {
                    this.fileBuilder_.clear();
                }
                return this;
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return PluginProtos.internal_static_google_protobuf_compiler_CodeGeneratorResponse_descriptor;
            }

            public CodeGeneratorResponse getDefaultInstanceForType() {
                return CodeGeneratorResponse.getDefaultInstance();
            }

            public CodeGeneratorResponse build() {
                CodeGeneratorResponse result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public CodeGeneratorResponse buildPartial() {
                CodeGeneratorResponse result = new CodeGeneratorResponse((GeneratedMessageV3.Builder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                Object unused = result.error_ = this.error_;
                if (this.fileBuilder_ == null) {
                    if ((this.bitField0_ & 2) == 2) {
                        this.file_ = Collections.unmodifiableList(this.file_);
                        this.bitField0_ &= -3;
                    }
                    List unused2 = result.file_ = this.file_;
                } else {
                    List unused3 = result.file_ = this.fileBuilder_.build();
                }
                int unused4 = result.bitField0_ = to_bitField0_;
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
                if (other instanceof CodeGeneratorResponse) {
                    return mergeFrom((CodeGeneratorResponse) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(CodeGeneratorResponse other) {
                if (other == CodeGeneratorResponse.getDefaultInstance()) {
                    return this;
                }
                if (other.hasError()) {
                    this.bitField0_ |= 1;
                    this.error_ = other.error_;
                    onChanged();
                }
                if (this.fileBuilder_ == null) {
                    if (!other.file_.isEmpty()) {
                        if (this.file_.isEmpty()) {
                            this.file_ = other.file_;
                            this.bitField0_ &= -3;
                        } else {
                            ensureFileIsMutable();
                            this.file_.addAll(other.file_);
                        }
                        onChanged();
                    }
                } else if (!other.file_.isEmpty()) {
                    if (this.fileBuilder_.isEmpty()) {
                        this.fileBuilder_.dispose();
                        RepeatedFieldBuilderV3<File, File.Builder, FileOrBuilder> repeatedFieldBuilderV3 = null;
                        this.fileBuilder_ = null;
                        this.file_ = other.file_;
                        this.bitField0_ &= -3;
                        if (CodeGeneratorResponse.alwaysUseFieldBuilders) {
                            repeatedFieldBuilderV3 = getFileFieldBuilder();
                        }
                        this.fileBuilder_ = repeatedFieldBuilderV3;
                    } else {
                        this.fileBuilder_.addAllMessages(other.file_);
                    }
                }
                mergeUnknownFields(other.unknownFields);
                onChanged();
                return this;
            }

            public final boolean isInitialized() {
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                try {
                    CodeGeneratorResponse parsedMessage = CodeGeneratorResponse.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    CodeGeneratorResponse parsedMessage2 = (CodeGeneratorResponse) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (0 != 0) {
                        mergeFrom((CodeGeneratorResponse) null);
                    }
                    throw th;
                }
            }

            public boolean hasError() {
                return (this.bitField0_ & 1) == 1;
            }

            public String getError() {
                Object ref = this.error_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.error_ = s;
                }
                return s;
            }

            public ByteString getErrorBytes() {
                Object ref = this.error_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.error_ = b;
                return b;
            }

            public Builder setError(String value) {
                if (value != null) {
                    this.bitField0_ |= 1;
                    this.error_ = value;
                    onChanged();
                    return this;
                }
                throw new NullPointerException();
            }

            public Builder clearError() {
                this.bitField0_ &= -2;
                this.error_ = CodeGeneratorResponse.getDefaultInstance().getError();
                onChanged();
                return this;
            }

            public Builder setErrorBytes(ByteString value) {
                if (value != null) {
                    this.bitField0_ |= 1;
                    this.error_ = value;
                    onChanged();
                    return this;
                }
                throw new NullPointerException();
            }

            private void ensureFileIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.file_ = new ArrayList(this.file_);
                    this.bitField0_ |= 2;
                }
            }

            public List<File> getFileList() {
                if (this.fileBuilder_ == null) {
                    return Collections.unmodifiableList(this.file_);
                }
                return this.fileBuilder_.getMessageList();
            }

            public int getFileCount() {
                if (this.fileBuilder_ == null) {
                    return this.file_.size();
                }
                return this.fileBuilder_.getCount();
            }

            public File getFile(int index) {
                if (this.fileBuilder_ == null) {
                    return this.file_.get(index);
                }
                return this.fileBuilder_.getMessage(index);
            }

            public Builder setFile(int index, File value) {
                if (this.fileBuilder_ != null) {
                    this.fileBuilder_.setMessage(index, value);
                } else if (value != null) {
                    ensureFileIsMutable();
                    this.file_.set(index, value);
                    onChanged();
                } else {
                    throw new NullPointerException();
                }
                return this;
            }

            public Builder setFile(int index, File.Builder builderForValue) {
                if (this.fileBuilder_ == null) {
                    ensureFileIsMutable();
                    this.file_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.fileBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addFile(File value) {
                if (this.fileBuilder_ != null) {
                    this.fileBuilder_.addMessage(value);
                } else if (value != null) {
                    ensureFileIsMutable();
                    this.file_.add(value);
                    onChanged();
                } else {
                    throw new NullPointerException();
                }
                return this;
            }

            public Builder addFile(int index, File value) {
                if (this.fileBuilder_ != null) {
                    this.fileBuilder_.addMessage(index, value);
                } else if (value != null) {
                    ensureFileIsMutable();
                    this.file_.add(index, value);
                    onChanged();
                } else {
                    throw new NullPointerException();
                }
                return this;
            }

            public Builder addFile(File.Builder builderForValue) {
                if (this.fileBuilder_ == null) {
                    ensureFileIsMutable();
                    this.file_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.fileBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addFile(int index, File.Builder builderForValue) {
                if (this.fileBuilder_ == null) {
                    ensureFileIsMutable();
                    this.file_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.fileBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllFile(Iterable<? extends File> values) {
                if (this.fileBuilder_ == null) {
                    ensureFileIsMutable();
                    AbstractMessageLite.Builder.addAll(values, this.file_);
                    onChanged();
                } else {
                    this.fileBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearFile() {
                if (this.fileBuilder_ == null) {
                    this.file_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                    onChanged();
                } else {
                    this.fileBuilder_.clear();
                }
                return this;
            }

            public Builder removeFile(int index) {
                if (this.fileBuilder_ == null) {
                    ensureFileIsMutable();
                    this.file_.remove(index);
                    onChanged();
                } else {
                    this.fileBuilder_.remove(index);
                }
                return this;
            }

            public File.Builder getFileBuilder(int index) {
                return getFileFieldBuilder().getBuilder(index);
            }

            public FileOrBuilder getFileOrBuilder(int index) {
                if (this.fileBuilder_ == null) {
                    return this.file_.get(index);
                }
                return this.fileBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends FileOrBuilder> getFileOrBuilderList() {
                if (this.fileBuilder_ != null) {
                    return this.fileBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.file_);
            }

            public File.Builder addFileBuilder() {
                return getFileFieldBuilder().addBuilder(File.getDefaultInstance());
            }

            public File.Builder addFileBuilder(int index) {
                return getFileFieldBuilder().addBuilder(index, File.getDefaultInstance());
            }

            public List<File.Builder> getFileBuilderList() {
                return getFileFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<File, File.Builder, FileOrBuilder> getFileFieldBuilder() {
                if (this.fileBuilder_ == null) {
                    this.fileBuilder_ = new RepeatedFieldBuilderV3<>(this.file_, (this.bitField0_ & 2) == 2, getParentForChildren(), isClean());
                    this.file_ = null;
                }
                return this.fileBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        public static CodeGeneratorResponse getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<CodeGeneratorResponse> parser() {
            return PARSER;
        }

        public Parser<CodeGeneratorResponse> getParserForType() {
            return PARSER;
        }

        public CodeGeneratorResponse getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = new Descriptors.FileDescriptor.InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(Descriptors.FileDescriptor root) {
                Descriptors.FileDescriptor unused = PluginProtos.descriptor = root;
                return null;
            }
        };
        Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n%google/protobuf/compiler/plugin.proto\u0012\u0018google.protobuf.compiler\u001a google/protobuf/descriptor.proto\"}\n\u0014CodeGeneratorRequest\u0012\u0018\n\u0010file_to_generate\u0018\u0001 \u0003(\t\u0012\u0011\n\tparameter\u0018\u0002 \u0001(\t\u00128\n\nproto_file\u0018\u000f \u0003(\u000b2$.google.protobuf.FileDescriptorProto\"ª\u0001\n\u0015CodeGeneratorResponse\u0012\r\n\u0005error\u0018\u0001 \u0001(\t\u0012B\n\u0004file\u0018\u000f \u0003(\u000b24.google.protobuf.compiler.CodeGeneratorResponse.File\u001a>\n\u0004File\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\u0012\u0017\n\u000finsertion_point\u0018\u0002 \u0001(\t\u0012\u000f\n\u0007content\u0018\u000f \u0001(\tB", "7\n\u001ccom.google.protobuf.compilerB\fPluginProtosZ\tplugin_go"}, new Descriptors.FileDescriptor[]{DescriptorProtos.getDescriptor()}, assigner);
        DescriptorProtos.getDescriptor();
    }
}
