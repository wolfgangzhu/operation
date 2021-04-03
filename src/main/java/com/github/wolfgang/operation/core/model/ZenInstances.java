package com.github.wolfgang.operation.core.model;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author wolfgang
 * @date 2020-02-14 18:33:54
 * @version $ Id: OperInstance.java, v 0.1  wolfgang Exp $
 */
public class ZenInstances {

    private String      type;
    private Set<String> instanceIds;

    public static final ZenInstances EMPTY = new ZenInstances(Collections.emptySet());

    public ZenInstances(Set<String> instanceId) {
        this.instanceIds = instanceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<String> getInstanceIds() {
        return instanceIds;
    }

    public void setInstanceIds(Set<String> instanceIds) {
        this.instanceIds = instanceIds;
    }

    public static class InstancesBuilder {
        private ZenInstances instances;

        private InstancesBuilder(ZenInstances instances) {
            this.instances = instances;
        }

        public InstancesBuilder type(String type) {
            instances.setType(type);
            return this;
        }

        public InstancesBuilder instance(String instanceId) {
            instances.getInstanceIds().add(instanceId);
            return this;
        }

        public ZenInstances build() {
            validateInstances(instances);
            return instances;
        }

        private void validateInstances(ZenInstances instances) {
            if (StringUtils.isEmpty(instances.getType())) {
                throw new IllegalArgumentException("type can't be null or empty");
            }
            if (CollectionUtils.isEmpty(instances.getInstanceIds())) {
                throw new IllegalArgumentException("instances can't be empty");
            }
        }
    }

    public static InstancesBuilder builder() {
        return new InstancesBuilder(new ZenInstances(new HashSet<>()));
    }
}

