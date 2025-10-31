package com.google.protobuf;

import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message;
import com.google.protobuf.MessageLite;
import com.google.protobuf.UnknownFieldSet;
import com.google.protobuf.WireFormat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class MessageReflection {

    interface MergeTarget {

        public enum ContainerType {
            MESSAGE,
            EXTENSION_SET
        }

        MergeTarget addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object obj);

        MergeTarget clearField(Descriptors.FieldDescriptor fieldDescriptor);

        MergeTarget clearOneof(Descriptors.OneofDescriptor oneofDescriptor);

        ExtensionRegistry.ExtensionInfo findExtensionByName(ExtensionRegistry extensionRegistry, String str);

        ExtensionRegistry.ExtensionInfo findExtensionByNumber(ExtensionRegistry extensionRegistry, Descriptors.Descriptor descriptor, int i);

        Object finish();

        ContainerType getContainerType();

        Descriptors.Descriptor getDescriptorForType();

        Object getField(Descriptors.FieldDescriptor fieldDescriptor);

        Descriptors.FieldDescriptor getOneofFieldDescriptor(Descriptors.OneofDescriptor oneofDescriptor);

        WireFormat.Utf8Validation getUtf8Validation(Descriptors.FieldDescriptor fieldDescriptor);

        boolean hasField(Descriptors.FieldDescriptor fieldDescriptor);

        boolean hasOneof(Descriptors.OneofDescriptor oneofDescriptor);

        MergeTarget newMergeTargetForField(Descriptors.FieldDescriptor fieldDescriptor, Message message);

        Object parseGroup(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite, Descriptors.FieldDescriptor fieldDescriptor, Message message) throws IOException;

        Object parseMessage(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite, Descriptors.FieldDescriptor fieldDescriptor, Message message) throws IOException;

        Object parseMessageFromBytes(ByteString byteString, ExtensionRegistryLite extensionRegistryLite, Descriptors.FieldDescriptor fieldDescriptor, Message message) throws IOException;

        MergeTarget setField(Descriptors.FieldDescriptor fieldDescriptor, Object obj);

        MergeTarget setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i, Object obj);
    }

    MessageReflection() {
    }

    static void writeMessageTo(Message message, Map<Descriptors.FieldDescriptor, Object> fields, CodedOutputStream output, boolean alwaysWriteRequiredFields) throws IOException {
        boolean isMessageSet = message.getDescriptorForType().getOptions().getMessageSetWireFormat();
        if (alwaysWriteRequiredFields) {
            fields = new TreeMap<>(fields);
            for (Descriptors.FieldDescriptor field : message.getDescriptorForType().getFields()) {
                if (field.isRequired() && !fields.containsKey(field)) {
                    fields.put(field, message.getField(field));
                }
            }
        }
        for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : fields.entrySet()) {
            Descriptors.FieldDescriptor field2 = entry.getKey();
            Object value = entry.getValue();
            if (!isMessageSet || !field2.isExtension() || field2.getType() != Descriptors.FieldDescriptor.Type.MESSAGE || field2.isRepeated()) {
                FieldSet.writeField(field2, value, output);
            } else {
                output.writeMessageSetExtension(field2.getNumber(), (Message) value);
            }
        }
        UnknownFieldSet unknownFields = message.getUnknownFields();
        if (isMessageSet) {
            unknownFields.writeAsMessageSetTo(output);
        } else {
            unknownFields.writeTo(output);
        }
    }

    static int getSerializedSize(Message message, Map<Descriptors.FieldDescriptor, Object> fields) {
        int size = 0;
        boolean isMessageSet = message.getDescriptorForType().getOptions().getMessageSetWireFormat();
        for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : fields.entrySet()) {
            Descriptors.FieldDescriptor field = entry.getKey();
            Object value = entry.getValue();
            if (!isMessageSet || !field.isExtension() || field.getType() != Descriptors.FieldDescriptor.Type.MESSAGE || field.isRepeated()) {
                size += FieldSet.computeFieldSize(field, value);
            } else {
                size += CodedOutputStream.computeMessageSetExtensionSize(field.getNumber(), (Message) value);
            }
        }
        UnknownFieldSet unknownFields = message.getUnknownFields();
        if (isMessageSet) {
            return size + unknownFields.getSerializedSizeAsMessageSet();
        }
        return size + unknownFields.getSerializedSize();
    }

    static String delimitWithCommas(List<String> parts) {
        StringBuilder result = new StringBuilder();
        for (String part : parts) {
            if (result.length() > 0) {
                result.append(", ");
            }
            result.append(part);
        }
        return result.toString();
    }

    static boolean isInitialized(MessageOrBuilder message) {
        for (Descriptors.FieldDescriptor field : message.getDescriptorForType().getFields()) {
            if (field.isRequired() && !message.hasField(field)) {
                return false;
            }
        }
        for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : message.getAllFields().entrySet()) {
            Descriptors.FieldDescriptor field2 = entry.getKey();
            if (field2.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                if (field2.isRepeated()) {
                    for (Message element : (List) entry.getValue()) {
                        if (!element.isInitialized()) {
                            return false;
                        }
                    }
                    continue;
                } else if (!((Message) entry.getValue()).isInitialized()) {
                    return false;
                }
            }
        }
        return true;
    }

    private static String subMessagePrefix(String prefix, Descriptors.FieldDescriptor field, int index) {
        StringBuilder result = new StringBuilder(prefix);
        if (field.isExtension()) {
            result.append('(').append(field.getFullName()).append(')');
        } else {
            result.append(field.getName());
        }
        if (index != -1) {
            result.append('[').append(index).append(']');
        }
        result.append('.');
        return result.toString();
    }

    private static void findMissingFields(MessageOrBuilder message, String prefix, List<String> results) {
        for (Descriptors.FieldDescriptor field : message.getDescriptorForType().getFields()) {
            if (field.isRequired() && !message.hasField(field)) {
                results.add(prefix + field.getName());
            }
        }
        for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : message.getAllFields().entrySet()) {
            Descriptors.FieldDescriptor field2 = entry.getKey();
            Object value = entry.getValue();
            if (field2.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                if (field2.isRepeated()) {
                    int i = 0;
                    for (MessageOrBuilder findMissingFields : (List) value) {
                        findMissingFields(findMissingFields, subMessagePrefix(prefix, field2, i), results);
                        i++;
                    }
                } else if (message.hasField(field2)) {
                    findMissingFields((MessageOrBuilder) value, subMessagePrefix(prefix, field2, -1), results);
                }
            }
        }
    }

    static List<String> findMissingFields(MessageOrBuilder message) {
        List<String> results = new ArrayList<>();
        findMissingFields(message, "", results);
        return results;
    }

    static class BuilderAdapter implements MergeTarget {
        private final Message.Builder builder;

        public Descriptors.Descriptor getDescriptorForType() {
            return this.builder.getDescriptorForType();
        }

        public BuilderAdapter(Message.Builder builder2) {
            this.builder = builder2;
        }

        public Object getField(Descriptors.FieldDescriptor field) {
            return this.builder.getField(field);
        }

        public boolean hasField(Descriptors.FieldDescriptor field) {
            return this.builder.hasField(field);
        }

        public MergeTarget setField(Descriptors.FieldDescriptor field, Object value) {
            this.builder.setField(field, value);
            return this;
        }

        public MergeTarget clearField(Descriptors.FieldDescriptor field) {
            this.builder.clearField(field);
            return this;
        }

        public MergeTarget setRepeatedField(Descriptors.FieldDescriptor field, int index, Object value) {
            this.builder.setRepeatedField(field, index, value);
            return this;
        }

        public MergeTarget addRepeatedField(Descriptors.FieldDescriptor field, Object value) {
            this.builder.addRepeatedField(field, value);
            return this;
        }

        public boolean hasOneof(Descriptors.OneofDescriptor oneof) {
            return this.builder.hasOneof(oneof);
        }

        public MergeTarget clearOneof(Descriptors.OneofDescriptor oneof) {
            this.builder.clearOneof(oneof);
            return this;
        }

        public Descriptors.FieldDescriptor getOneofFieldDescriptor(Descriptors.OneofDescriptor oneof) {
            return this.builder.getOneofFieldDescriptor(oneof);
        }

        public MergeTarget.ContainerType getContainerType() {
            return MergeTarget.ContainerType.MESSAGE;
        }

        public ExtensionRegistry.ExtensionInfo findExtensionByName(ExtensionRegistry registry, String name) {
            return registry.findImmutableExtensionByName(name);
        }

        public ExtensionRegistry.ExtensionInfo findExtensionByNumber(ExtensionRegistry registry, Descriptors.Descriptor containingType, int fieldNumber) {
            return registry.findImmutableExtensionByNumber(containingType, fieldNumber);
        }

        public Object parseGroup(CodedInputStream input, ExtensionRegistryLite extensionRegistry, Descriptors.FieldDescriptor field, Message defaultInstance) throws IOException {
            Message.Builder subBuilder;
            Message originalMessage;
            if (defaultInstance != null) {
                subBuilder = defaultInstance.newBuilderForType();
            } else {
                subBuilder = this.builder.newBuilderForField(field);
            }
            if (!field.isRepeated() && (originalMessage = (Message) getField(field)) != null) {
                subBuilder.mergeFrom(originalMessage);
            }
            input.readGroup(field.getNumber(), (MessageLite.Builder) subBuilder, extensionRegistry);
            return subBuilder.buildPartial();
        }

        public Object parseMessage(CodedInputStream input, ExtensionRegistryLite extensionRegistry, Descriptors.FieldDescriptor field, Message defaultInstance) throws IOException {
            Message.Builder subBuilder;
            Message originalMessage;
            if (defaultInstance != null) {
                subBuilder = defaultInstance.newBuilderForType();
            } else {
                subBuilder = this.builder.newBuilderForField(field);
            }
            if (!field.isRepeated() && (originalMessage = (Message) getField(field)) != null) {
                subBuilder.mergeFrom(originalMessage);
            }
            input.readMessage((MessageLite.Builder) subBuilder, extensionRegistry);
            return subBuilder.buildPartial();
        }

        public Object parseMessageFromBytes(ByteString bytes, ExtensionRegistryLite extensionRegistry, Descriptors.FieldDescriptor field, Message defaultInstance) throws IOException {
            Message.Builder subBuilder;
            Message originalMessage;
            if (defaultInstance != null) {
                subBuilder = defaultInstance.newBuilderForType();
            } else {
                subBuilder = this.builder.newBuilderForField(field);
            }
            if (!field.isRepeated() && (originalMessage = (Message) getField(field)) != null) {
                subBuilder.mergeFrom(originalMessage);
            }
            subBuilder.mergeFrom(bytes, extensionRegistry);
            return subBuilder.buildPartial();
        }

        public MergeTarget newMergeTargetForField(Descriptors.FieldDescriptor field, Message defaultInstance) {
            if (defaultInstance != null) {
                return new BuilderAdapter(defaultInstance.newBuilderForType());
            }
            return new BuilderAdapter(this.builder.newBuilderForField(field));
        }

        public WireFormat.Utf8Validation getUtf8Validation(Descriptors.FieldDescriptor descriptor) {
            if (descriptor.needsUtf8Check()) {
                return WireFormat.Utf8Validation.STRICT;
            }
            if (descriptor.isRepeated() || !(this.builder instanceof GeneratedMessage.Builder)) {
                return WireFormat.Utf8Validation.LOOSE;
            }
            return WireFormat.Utf8Validation.LAZY;
        }

        public Object finish() {
            return this.builder.buildPartial();
        }
    }

    static class ExtensionAdapter implements MergeTarget {
        private final FieldSet<Descriptors.FieldDescriptor> extensions;

        ExtensionAdapter(FieldSet<Descriptors.FieldDescriptor> extensions2) {
            this.extensions = extensions2;
        }

        public Descriptors.Descriptor getDescriptorForType() {
            throw new UnsupportedOperationException("getDescriptorForType() called on FieldSet object");
        }

        public Object getField(Descriptors.FieldDescriptor field) {
            return this.extensions.getField(field);
        }

        public boolean hasField(Descriptors.FieldDescriptor field) {
            return this.extensions.hasField(field);
        }

        public MergeTarget setField(Descriptors.FieldDescriptor field, Object value) {
            this.extensions.setField(field, value);
            return this;
        }

        public MergeTarget clearField(Descriptors.FieldDescriptor field) {
            this.extensions.clearField(field);
            return this;
        }

        public MergeTarget setRepeatedField(Descriptors.FieldDescriptor field, int index, Object value) {
            this.extensions.setRepeatedField(field, index, value);
            return this;
        }

        public MergeTarget addRepeatedField(Descriptors.FieldDescriptor field, Object value) {
            this.extensions.addRepeatedField(field, value);
            return this;
        }

        public boolean hasOneof(Descriptors.OneofDescriptor oneof) {
            return false;
        }

        public MergeTarget clearOneof(Descriptors.OneofDescriptor oneof) {
            return this;
        }

        public Descriptors.FieldDescriptor getOneofFieldDescriptor(Descriptors.OneofDescriptor oneof) {
            return null;
        }

        public MergeTarget.ContainerType getContainerType() {
            return MergeTarget.ContainerType.EXTENSION_SET;
        }

        public ExtensionRegistry.ExtensionInfo findExtensionByName(ExtensionRegistry registry, String name) {
            return registry.findImmutableExtensionByName(name);
        }

        public ExtensionRegistry.ExtensionInfo findExtensionByNumber(ExtensionRegistry registry, Descriptors.Descriptor containingType, int fieldNumber) {
            return registry.findImmutableExtensionByNumber(containingType, fieldNumber);
        }

        public Object parseGroup(CodedInputStream input, ExtensionRegistryLite registry, Descriptors.FieldDescriptor field, Message defaultInstance) throws IOException {
            Message originalMessage;
            Message.Builder subBuilder = defaultInstance.newBuilderForType();
            if (!field.isRepeated() && (originalMessage = (Message) getField(field)) != null) {
                subBuilder.mergeFrom(originalMessage);
            }
            input.readGroup(field.getNumber(), (MessageLite.Builder) subBuilder, registry);
            return subBuilder.buildPartial();
        }

        public Object parseMessage(CodedInputStream input, ExtensionRegistryLite registry, Descriptors.FieldDescriptor field, Message defaultInstance) throws IOException {
            Message originalMessage;
            Message.Builder subBuilder = defaultInstance.newBuilderForType();
            if (!field.isRepeated() && (originalMessage = (Message) getField(field)) != null) {
                subBuilder.mergeFrom(originalMessage);
            }
            input.readMessage((MessageLite.Builder) subBuilder, registry);
            return subBuilder.buildPartial();
        }

        public Object parseMessageFromBytes(ByteString bytes, ExtensionRegistryLite registry, Descriptors.FieldDescriptor field, Message defaultInstance) throws IOException {
            Message originalMessage;
            Message.Builder subBuilder = defaultInstance.newBuilderForType();
            if (!field.isRepeated() && (originalMessage = (Message) getField(field)) != null) {
                subBuilder.mergeFrom(originalMessage);
            }
            subBuilder.mergeFrom(bytes, registry);
            return subBuilder.buildPartial();
        }

        public MergeTarget newMergeTargetForField(Descriptors.FieldDescriptor descriptor, Message defaultInstance) {
            throw new UnsupportedOperationException("newMergeTargetForField() called on FieldSet object");
        }

        public WireFormat.Utf8Validation getUtf8Validation(Descriptors.FieldDescriptor descriptor) {
            if (descriptor.needsUtf8Check()) {
                return WireFormat.Utf8Validation.STRICT;
            }
            return WireFormat.Utf8Validation.LOOSE;
        }

        public Object finish() {
            throw new UnsupportedOperationException("finish() called on FieldSet object");
        }
    }

    static boolean mergeFieldFrom(CodedInputStream input, UnknownFieldSet.Builder unknownFields, ExtensionRegistryLite extensionRegistry, Descriptors.Descriptor type, MergeTarget target, int tag) throws IOException {
        Descriptors.FieldDescriptor field;
        Object value;
        CodedInputStream codedInputStream = input;
        UnknownFieldSet.Builder builder = unknownFields;
        ExtensionRegistryLite extensionRegistryLite = extensionRegistry;
        Descriptors.Descriptor descriptor = type;
        MergeTarget mergeTarget = target;
        int i = tag;
        if (!type.getOptions().getMessageSetWireFormat() || i != WireFormat.MESSAGE_SET_ITEM_TAG) {
            int wireType = WireFormat.getTagWireType(tag);
            int fieldNumber = WireFormat.getTagFieldNumber(tag);
            Message defaultInstance = null;
            if (!descriptor.isExtensionNumber(fieldNumber)) {
                field = target.getContainerType() == MergeTarget.ContainerType.MESSAGE ? descriptor.findFieldByNumber(fieldNumber) : null;
            } else if (extensionRegistryLite instanceof ExtensionRegistry) {
                ExtensionRegistry.ExtensionInfo extension = mergeTarget.findExtensionByNumber((ExtensionRegistry) extensionRegistryLite, descriptor, fieldNumber);
                if (extension == null) {
                    field = null;
                } else {
                    field = extension.descriptor;
                    defaultInstance = extension.defaultInstance;
                    if (defaultInstance == null && field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                        throw new IllegalStateException("Message-typed extension lacked default instance: " + field.getFullName());
                    }
                }
            } else {
                field = null;
            }
            boolean unknown = false;
            boolean packed = false;
            if (field == null) {
                unknown = true;
            } else if (wireType == FieldSet.getWireFormatForFieldType(field.getLiteType(), false)) {
                packed = false;
            } else if (!field.isPackable() || wireType != FieldSet.getWireFormatForFieldType(field.getLiteType(), true)) {
                unknown = true;
            } else {
                packed = true;
            }
            if (unknown) {
                return builder.mergeFieldFrom(i, codedInputStream);
            }
            if (packed) {
                int limit = codedInputStream.pushLimit(input.readRawVarint32());
                if (field.getLiteType() == WireFormat.FieldType.ENUM) {
                    while (input.getBytesUntilLimit() > 0) {
                        int rawValue = input.readEnum();
                        if (field.getFile().supportsUnknownEnumValue()) {
                            mergeTarget.addRepeatedField(field, field.getEnumType().findValueByNumberCreatingIfUnknown(rawValue));
                        } else {
                            Object value2 = field.getEnumType().findValueByNumber(rawValue);
                            if (value2 == null) {
                                return true;
                            }
                            mergeTarget.addRepeatedField(field, value2);
                        }
                    }
                } else {
                    while (input.getBytesUntilLimit() > 0) {
                        mergeTarget.addRepeatedField(field, WireFormat.readPrimitiveField(codedInputStream, field.getLiteType(), mergeTarget.getUtf8Validation(field)));
                    }
                }
                codedInputStream.popLimit(limit);
                return true;
            }
            switch (field.getType()) {
                case GROUP:
                    value = mergeTarget.parseGroup(codedInputStream, extensionRegistryLite, field, defaultInstance);
                    break;
                case MESSAGE:
                    value = mergeTarget.parseMessage(codedInputStream, extensionRegistryLite, field, defaultInstance);
                    break;
                case ENUM:
                    int rawValue2 = input.readEnum();
                    if (field.getFile().supportsUnknownEnumValue()) {
                        value = field.getEnumType().findValueByNumberCreatingIfUnknown(rawValue2);
                        break;
                    } else {
                        value = field.getEnumType().findValueByNumber(rawValue2);
                        if (value == null) {
                            builder.mergeVarintField(fieldNumber, rawValue2);
                            return true;
                        }
                    }
                    break;
                default:
                    value = WireFormat.readPrimitiveField(codedInputStream, field.getLiteType(), mergeTarget.getUtf8Validation(field));
                    break;
            }
            if (field.isRepeated()) {
                mergeTarget.addRepeatedField(field, value);
                return true;
            }
            mergeTarget.setField(field, value);
            return true;
        }
        mergeMessageSetExtensionFromCodedStream(input, unknownFields, extensionRegistry, type, target);
        return true;
    }

    private static void mergeMessageSetExtensionFromCodedStream(CodedInputStream input, UnknownFieldSet.Builder unknownFields, ExtensionRegistryLite extensionRegistry, Descriptors.Descriptor type, MergeTarget target) throws IOException {
        int typeId = 0;
        ByteString rawBytes = null;
        ExtensionRegistry.ExtensionInfo extension = null;
        while (true) {
            int tag = input.readTag();
            if (tag == 0) {
                break;
            } else if (tag == WireFormat.MESSAGE_SET_TYPE_ID_TAG) {
                typeId = input.readUInt32();
                if (typeId != 0 && (extensionRegistry instanceof ExtensionRegistry)) {
                    extension = target.findExtensionByNumber((ExtensionRegistry) extensionRegistry, type, typeId);
                }
            } else if (tag == WireFormat.MESSAGE_SET_MESSAGE_TAG) {
                if (typeId == 0 || extension == null || !ExtensionRegistryLite.isEagerlyParseMessageSets()) {
                    rawBytes = input.readBytes();
                } else {
                    eagerlyMergeMessageSetExtension(input, extension, extensionRegistry, target);
                    rawBytes = null;
                }
            } else if (!input.skipField(tag)) {
                break;
            }
        }
        input.checkLastTagWas(WireFormat.MESSAGE_SET_ITEM_END_TAG);
        if (rawBytes != null && typeId != 0) {
            if (extension != null) {
                mergeMessageSetExtensionFromBytes(rawBytes, extension, extensionRegistry, target);
            } else if (rawBytes != null) {
                unknownFields.mergeField(typeId, UnknownFieldSet.Field.newBuilder().addLengthDelimited(rawBytes).build());
            }
        }
    }

    private static void mergeMessageSetExtensionFromBytes(ByteString rawBytes, ExtensionRegistry.ExtensionInfo extension, ExtensionRegistryLite extensionRegistry, MergeTarget target) throws IOException {
        Descriptors.FieldDescriptor field = extension.descriptor;
        if (target.hasField(field) || ExtensionRegistryLite.isEagerlyParseMessageSets()) {
            target.setField(field, target.parseMessageFromBytes(rawBytes, extensionRegistry, field, extension.defaultInstance));
        } else {
            target.setField(field, new LazyField(extension.defaultInstance, extensionRegistry, rawBytes));
        }
    }

    private static void eagerlyMergeMessageSetExtension(CodedInputStream input, ExtensionRegistry.ExtensionInfo extension, ExtensionRegistryLite extensionRegistry, MergeTarget target) throws IOException {
        Descriptors.FieldDescriptor field = extension.descriptor;
        target.setField(field, target.parseMessage(input, extensionRegistry, field, extension.defaultInstance));
    }
}
