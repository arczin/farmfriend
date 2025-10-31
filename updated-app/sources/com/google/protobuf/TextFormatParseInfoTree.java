package com.google.protobuf;

import com.google.protobuf.Descriptors;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextFormatParseInfoTree {
    private Map<Descriptors.FieldDescriptor, List<TextFormatParseLocation>> locationsFromField;
    Map<Descriptors.FieldDescriptor, List<TextFormatParseInfoTree>> subtreesFromField;

    private TextFormatParseInfoTree(Map<Descriptors.FieldDescriptor, List<TextFormatParseLocation>> locationsFromField2, Map<Descriptors.FieldDescriptor, List<Builder>> subtreeBuildersFromField) {
        Map<Descriptors.FieldDescriptor, List<TextFormatParseLocation>> locs = new HashMap<>();
        for (Map.Entry<Descriptors.FieldDescriptor, List<TextFormatParseLocation>> kv : locationsFromField2.entrySet()) {
            locs.put(kv.getKey(), Collections.unmodifiableList(kv.getValue()));
        }
        this.locationsFromField = Collections.unmodifiableMap(locs);
        Map<Descriptors.FieldDescriptor, List<TextFormatParseInfoTree>> subs = new HashMap<>();
        for (Map.Entry<Descriptors.FieldDescriptor, List<Builder>> kv2 : subtreeBuildersFromField.entrySet()) {
            List<TextFormatParseInfoTree> submessagesOfField = new ArrayList<>();
            for (Builder subBuilder : kv2.getValue()) {
                submessagesOfField.add(subBuilder.build());
            }
            subs.put(kv2.getKey(), Collections.unmodifiableList(submessagesOfField));
        }
        this.subtreesFromField = Collections.unmodifiableMap(subs);
    }

    public List<TextFormatParseLocation> getLocations(Descriptors.FieldDescriptor fieldDescriptor) {
        List<TextFormatParseLocation> result = this.locationsFromField.get(fieldDescriptor);
        return result == null ? Collections.emptyList() : result;
    }

    public TextFormatParseLocation getLocation(Descriptors.FieldDescriptor fieldDescriptor, int index) {
        return (TextFormatParseLocation) getFromList(getLocations(fieldDescriptor), index, fieldDescriptor);
    }

    public List<TextFormatParseInfoTree> getNestedTrees(Descriptors.FieldDescriptor fieldDescriptor) {
        List<TextFormatParseInfoTree> result = this.subtreesFromField.get(fieldDescriptor);
        return result == null ? Collections.emptyList() : result;
    }

    public TextFormatParseInfoTree getNestedTree(Descriptors.FieldDescriptor fieldDescriptor, int index) {
        return (TextFormatParseInfoTree) getFromList(getNestedTrees(fieldDescriptor), index, fieldDescriptor);
    }

    public static Builder builder() {
        return new Builder();
    }

    private static <T> T getFromList(List<T> list, int index, Descriptors.FieldDescriptor fieldDescriptor) {
        String str;
        if (index < list.size() && index >= 0) {
            return list.get(index);
        }
        if (fieldDescriptor == null) {
            str = "<null>";
        } else {
            str = fieldDescriptor.getName();
        }
        throw new IllegalArgumentException(String.format("Illegal index field: %s, index %d", new Object[]{str, Integer.valueOf(index)}));
    }

    public static class Builder {
        private Map<Descriptors.FieldDescriptor, List<TextFormatParseLocation>> locationsFromField;
        private Map<Descriptors.FieldDescriptor, List<Builder>> subtreeBuildersFromField;

        private Builder() {
            this.locationsFromField = new HashMap();
            this.subtreeBuildersFromField = new HashMap();
        }

        public Builder setLocation(Descriptors.FieldDescriptor fieldDescriptor, TextFormatParseLocation location) {
            List<TextFormatParseLocation> fieldLocations = this.locationsFromField.get(fieldDescriptor);
            if (fieldLocations == null) {
                fieldLocations = new ArrayList<>();
                this.locationsFromField.put(fieldDescriptor, fieldLocations);
            }
            fieldLocations.add(location);
            return this;
        }

        public Builder getBuilderForSubMessageField(Descriptors.FieldDescriptor fieldDescriptor) {
            List<Builder> submessageBuilders = this.subtreeBuildersFromField.get(fieldDescriptor);
            if (submessageBuilders == null) {
                submessageBuilders = new ArrayList<>();
                this.subtreeBuildersFromField.put(fieldDescriptor, submessageBuilders);
            }
            Builder subtreeBuilder = new Builder();
            submessageBuilders.add(subtreeBuilder);
            return subtreeBuilder;
        }

        public TextFormatParseInfoTree build() {
            return new TextFormatParseInfoTree(this.locationsFromField, this.subtreeBuildersFromField);
        }
    }
}
