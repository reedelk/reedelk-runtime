package com.reedelk.esb.flow.deserializer.node;

import com.reedelk.esb.graph.ExecutionNode;
import org.json.JSONObject;

public interface Deserializer {

    ExecutionNode deserialize(ExecutionNode parent, JSONObject componentDefinition);
}

