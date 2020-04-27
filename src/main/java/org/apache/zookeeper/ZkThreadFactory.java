package org.apache.zookeeper;

import javax.annotation.Nullable;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 曲元涛
 * @date 2020/4/15 09:31
 */
public class ZkThreadFactory implements ThreadFactory {

    private final String namePrefix;
    private final AtomicInteger nextId = new AtomicInteger(1);

    public ZkThreadFactory(String whatFeatureOfGroup) {
        this.namePrefix = "From ZkThreadFactory's " + whatFeatureOfGroup + "-Worker-";
    }

    @Override
    public Thread newThread(@Nullable Runnable task) {
        String threadName = namePrefix + nextId.getAndIncrement();
        return new Thread(null, task, threadName, 0);
    }
}
