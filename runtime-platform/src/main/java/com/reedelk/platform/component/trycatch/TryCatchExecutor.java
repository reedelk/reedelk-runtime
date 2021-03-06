package com.reedelk.platform.component.trycatch;

import com.reedelk.platform.execution.FlowExecutor;
import com.reedelk.platform.execution.FlowExecutorFactory;
import com.reedelk.platform.execution.MessageAndContext;
import com.reedelk.platform.graph.ExecutionGraph;
import com.reedelk.platform.graph.ExecutionNode;
import com.reedelk.runtime.api.message.Message;
import com.reedelk.runtime.api.message.MessageBuilder;
import com.reedelk.runtime.component.TryCatch;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static com.reedelk.platform.commons.NextNode.of;

public class TryCatchExecutor implements FlowExecutor {

    @Override
    public Publisher<MessageAndContext> execute(Publisher<MessageAndContext> publisher, ExecutionNode currentNode, ExecutionGraph graph) {

        TryCatchWrapper tryCatch = (TryCatchWrapper) currentNode.getComponent();

        ExecutionNode firstTryNode = tryCatch.getFirstTryNode();

        ExecutionNode firstCatchNode = tryCatch.getFirstCatchNode();

        Flux<MessageAndContext> result = Flux.from(publisher).flatMap((Function<MessageAndContext, Mono<MessageAndContext>>) messageAndContext -> {

            Publisher<MessageAndContext> tryExecution =
                    FlowExecutorFactory.get().execute(Mono.just(messageAndContext), firstTryNode, graph);

            return Mono.from(tryExecution).onErrorResume(throwable -> {

                Mono<MessageAndContext> mapped = Mono.just(messageAndContext).map(context -> {

                    Message messageWithException = MessageBuilder.get(TryCatch.class)
                            .withJavaObject(throwable)
                            .build();

                    context.replaceWith(messageWithException);

                    return context;

                });

                return Mono.from(FlowExecutorFactory.get().execute(mapped, firstCatchNode, graph));

            });

        });

        ExecutionNode stopNode = tryCatch.getStopNode();

        // If the Router is followed by other nodes, then we keep executing
        // the other nodes, otherwise we stop and we return the current publisher.
        return of(stopNode, graph)
                .map(nodeAfterStop -> FlowExecutorFactory.get().execute(result, nodeAfterStop, graph))
                .orElse(result); // The Router is the last execution node of the flow.
    }
}
