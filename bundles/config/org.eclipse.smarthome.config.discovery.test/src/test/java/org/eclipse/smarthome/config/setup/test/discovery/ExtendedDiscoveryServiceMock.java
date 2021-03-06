/**
 * Copyright (c) 2014,2018 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.smarthome.config.setup.test.discovery;

import java.util.Map;

import org.eclipse.smarthome.config.discovery.DiscoveryService;
import org.eclipse.smarthome.config.discovery.DiscoveryServiceCallback;
import org.eclipse.smarthome.config.discovery.ExtendedDiscoveryService;
import org.eclipse.smarthome.config.discovery.internal.DiscoveryResultImpl;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.ThingUID;

/**
 * The {@link DiscoveryServiceMock} is a mock for a {@link DiscoveryService}
 * which behaves like an @{ExtendedDiscoveryService}.<br>
 *
 * @author Simon Kaufmann - Initial Contribution
 */
public class ExtendedDiscoveryServiceMock extends DiscoveryServiceMock implements ExtendedDiscoveryService {

    public DiscoveryServiceCallback discoveryServiceCallback;
    public Map<String, Object> discoveryProperties;

    public ExtendedDiscoveryServiceMock(ThingTypeUID thingType, int timeout) {
        super(thingType, timeout);
        this.discoveryProperties = null;
    }

    public ExtendedDiscoveryServiceMock(ThingTypeUID thingType, int timeout, boolean faulty) {
        super(thingType, timeout, faulty);
        this.discoveryProperties = null;
    }

    @Override
    public void setDiscoveryServiceCallback(DiscoveryServiceCallback discoveryServiceCallback) {
        this.discoveryServiceCallback = discoveryServiceCallback;
    }

    @Override
    public void startScan() {
        thingDiscovered(new DiscoveryResultImpl(new ThingUID(thingType, "foo"), null, discoveryProperties, null, null,
                DEFAULT_TTL));
    }

    public void setDiscoveryProperties(Map<String, Object> discoveryProperties) {
        this.discoveryProperties = discoveryProperties;
    }
}
