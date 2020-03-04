package com.reedelk.esb.component.stop;

import com.reedelk.esb.execution.FlowExecutor;
import com.reedelk.esb.execution.MessageAndContext;
import com.reedelk.esb.graph.ExecutionGraph;
import com.reedelk.esb.graph.ExecutionNode;
import org.reactivestreams.Publisher;

public class StopExecutor implements FlowExecutor {
    /**
     * When we find a stop node, we just return the parent flux.
     * Nothing needs to be added to the flux anymore.
     */
    @Override
    public Publisher<MessageAndContext> execute(Publisher<MessageAndContext> publisher, ExecutionNode currentNode, ExecutionGraph graph) {
        return publisher;
    }
}